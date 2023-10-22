package com.example.linguatune.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

        Logger logger = Logger.getLogger(JWTRequestFilter.class.getName());

        private MyUserDetailsService myUserDetailsService;

        @Autowired
        public void setMyUserDetailsService(MyUserDetailsService myUserDetailsService) {
            this.myUserDetailsService = myUserDetailsService;
        }
        @Autowired
        public void setJwtUtils(JWTUtils jwtUtils) {
            this.jwtUtils = jwtUtils;
        }
        private JWTUtils jwtUtils;

        private String parseJwt(HttpServletRequest request) {
            String headerAuth = request.getHeader("Authorization");
            if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer ")) {
                return headerAuth.substring(7);
            }
            return null;
        }
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            try {
                String jwt = parseJwt(request);
                if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                    String username = jwtUtils.getUserNameFromJwtToken(jwt);
                    UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username); // email address
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (Exception e) {
                logger.info("cannot set user authentication token");
            }
            filterChain.doFilter(request, response);
        }
}
