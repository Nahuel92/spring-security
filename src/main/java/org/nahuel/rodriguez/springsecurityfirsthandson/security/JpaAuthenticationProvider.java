package org.nahuel.rodriguez.springsecurityfirsthandson.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JpaAuthenticationProvider implements AuthenticationProvider {
    private final JpaUserDetailsService jpaUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SCryptPasswordEncoder sCryptPasswordEncoder;

    public JpaAuthenticationProvider(final JpaUserDetailsService jpaUserDetailsService,
                                     final BCryptPasswordEncoder bCryptPasswordEncoder,
                                     final SCryptPasswordEncoder sCryptPasswordEncoder) {
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.sCryptPasswordEncoder = sCryptPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final var name = authentication.getName();
        final var password = authentication.getCredentials().toString();
        final var user = jpaUserDetailsService.loadUserByUsername(name);

        switch (user.getUser().getAlgorithm()) {
            case BCRYPT:
                return checkPassword(user, password, bCryptPasswordEncoder);
            case SCRYPT:
                return checkPassword(user, password, sCryptPasswordEncoder);
        }
        throw new BadCredentialsException("Bad credentials!");
    }

    private Authentication checkPassword(final CustomUserDetails user, String password,
                                         final PasswordEncoder passwordEncoder) {
        if (passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user.getUser(),
                    user.getPassword(),
                    user.getAuthorities()
            );
        }
        throw new BadCredentialsException("Bad credentials!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
