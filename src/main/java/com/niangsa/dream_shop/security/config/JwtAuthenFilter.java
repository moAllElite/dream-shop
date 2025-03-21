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
     * Intercepte les requêtes HTTP pour valider l'authentification JWT.
     *
     * @param request     La requête HTTP entrante.
     * @param response    La réponse HTTP à retourner en cas d'erreur.
     * @param filterChain La chaîne de filtres à exécuter après ce filtre.
     * @throws ServletException En cas d'erreur au niveau du filtrage.
     * @throws IOException      En cas de problème d'entrée/sortie.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull   HttpServletResponse response,
            @NonNull   FilterChain filterChain
    ) throws ServletException, IOException {
        String jwtToken = null;
        try {
            // Extraction du token JWT depuis l'en-tête Authorization
            jwtToken = parseJwtHeader(request);
            if (StringUtils.hasText(jwtToken)) {
                //extract username
                String username = jwtUtils.getUsernameFromToken(jwtToken);
                UserDetails userDetails = userService.loadUserByUsername(username);// get user information base on username
                //check if user exist in db & not connect
                if (userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtUtils.isTokenValidate(jwtToken, userDetails)) {
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                        // Ajout des détails de la requête à l'objet d'authentification
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
               //     filterChain.doFilter(request, response);
                }
            }
        }  catch (JwtException e) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token, you may login again!");
            return;
        } catch (Exception e) {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal error occurred.");
            return;
        }
        filterChain.doFilter(request, response);
    }

    // Extraction du token JWT depuis l'en-tête Authorization
    public String parseJwtHeader(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");
        // Vérification que l'en-tête Authorization est bien présent et correctement formaté
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ") ){
            return headerAuth.substring(7);// Extraction du token après "Bearer "
        }
        return  null;
    }


    /**
     * Envoie une réponse d'erreur JSON bien formatée.
     *
     * @param response La réponse HTTP.
     * @param status   Le code HTTP à retourner.
     * @param message  Le message d'erreur à afficher.
     * @throws IOException En cas d'erreur d'écriture dans la réponse.
     */
    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status);
        response.getWriter().write("{\"error\": \"" + message + "\", \"status\": " + status + "}");
    }
}
