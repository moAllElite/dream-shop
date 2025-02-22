package com.niangsa.dream_shop.security.config;

import com.niangsa.dream_shop.security.jwt.IJwtService;
import com.niangsa.dream_shop.service.user.IUserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@AllArgsConstructor
@Component
public class JwtAuthenFilter extends OncePerRequestFilter {
    private final IUserService userService;
    private IJwtService jwtUtils;
    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull   HttpServletResponse response,
            @NonNull   FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String jwtToken = parseJwtHeader(request);//get  token
            if(StringUtils.hasText(jwtToken) ){
                    //extract username
                String username = jwtUtils.getUsernameFromToken(jwtToken);
                UserDetails userDetails= userService.loadUserByUsername(username);// get user information base on username
                //check if user exist in db & not connect
                if(userDetails != null && SecurityContextHolder.getContext().getAuthentication() ==null){
                    if(jwtUtils.isTokenValidate(jwtToken,userDetails)){
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }

                    filterChain.doFilter(request,response);
                }
            }
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage()+": Invalid or expire token, you may login again!");
            return;
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }

    public String parseJwtHeader(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ") ){
            return headerAuth.substring(7);
        }
        return  null;
    }
}
