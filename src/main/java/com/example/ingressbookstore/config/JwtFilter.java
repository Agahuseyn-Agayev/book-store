package com.example.ingressbookstore.config;

import com.example.ingressbookstore.exception.UserForbiddenException;
import com.example.ingressbookstore.model.enums.UserType;
import com.example.ingressbookstore.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        if (request.getRequestURI().startsWith("/v1/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String jwtToken = extractJwtToken(request);
            hasAuthenticated(jwtToken);


            Authentication authentication = jwtService.buildAuthentication(jwtToken);
            String role = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse(null);
            if (request.getRequestURI().startsWith("/v1/students")) {
                if (!Objects.equals(role, UserType.STUDENT.name())) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{" +
                            "\"error\":\"" + authentication.getName() + " has not access to" + "'" + request.getRequestURI() + "'" + "\"" +
                            "}");
                    return;
                }
            }
            if (request.getRequestURI().startsWith("/v1/authors") ||
                    request.getRequestURI().startsWith("/v1/books")) {
                if (!Objects.equals(role, UserType.AUTHOR.name())) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{" +
                            "\"error\":\"" + authentication.getName() + " has not access to" + "'" + request.getRequestURI() + "'" + "\"" +
                            "}");
                    return;
                }
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (ExpiredJwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
            return;
        }

        filterChain.doFilter(request, response);
    }


    private void hasAuthenticated(String jwtToken) {
        if (jwtToken == null || jwtService.validateToken(jwtToken) == null) {
            throw new UserForbiddenException();
        }
    }


    private String extractJwtToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        return null;
    }


}
