package com.niangsa.dream_shop.security.config;

import com.niangsa.dream_shop.repositories.UserRepository;
import com.niangsa.dream_shop.security.jwt.IJwtUtils;
import com.niangsa.dream_shop.security.user.ShopUserDetails;
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
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@AllArgsConstructor

public class AuthenFilter extends OncePerRequestFilter {
    private final ShopUserDetails userDetails;
    private final IUserService userService;
    private IJwtUtils jwtUtils;
    private final UserRepository userRepository;
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
            if(StringUtils.hasText(jwtToken) && jwtUtils.validateToken(jwtToken)){
                    //extract username
                String username = jwtUtils.getUsernameFromToken(jwtToken);
                UserDetails user= userService.loadUserByUsername(username);// get user information base on username
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
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
