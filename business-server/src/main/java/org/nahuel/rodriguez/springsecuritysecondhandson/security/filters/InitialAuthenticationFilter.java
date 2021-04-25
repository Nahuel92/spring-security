package org.nahuel.rodriguez.springsecuritysecondhandson.security.filters;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.nahuel.rodriguez.springsecuritysecondhandson.security.authentications.OtpAuthentication;
import org.nahuel.rodriguez.springsecuritysecondhandson.security.authentications.UsernamePasswordAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

@Component
public class InitialAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${jwt.signing.key}")
    private String signingKey;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) {
        final var username = request.getHeader("username");
        final var password = request.getHeader("password");
        final var code = request.getHeader("code");

        if (Objects.isNull(code)) {
            final var authentication = new UsernamePasswordAuthentication(username, password);
            authenticationManager.authenticate(authentication);
        } else {
            final var authentication = new OtpAuthentication(username, code);
            authenticationManager.authenticate(authentication);
        }

        final var key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));

        final var jwt = Jwts.builder()
                .setClaims(Map.of("username", username))
                .signWith(key)
                .compact();

        response.setHeader("Authorization", jwt);
    }

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) {
        return !"/login".equals(request.getServletPath());
    }
}
