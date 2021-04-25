package org.nahuel.rodriguez.springsecuritysecondhandson.security.authenticationproviders;

import org.nahuel.rodriguez.springsecuritysecondhandson.proxies.AuthenticationServerProxy;
import org.nahuel.rodriguez.springsecuritysecondhandson.security.authentications.UsernamePasswordAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    private final AuthenticationServerProxy proxy;

    public UsernamePasswordAuthenticationProvider(final AuthenticationServerProxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final var username = authentication.getName();
        final var password = String.valueOf(authentication.getCredentials());

        proxy.sendAuth(username, password);

        return new UsernamePasswordAuthenticationToken(username, password);
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthentication.class.isAssignableFrom(authentication);
    }
}
