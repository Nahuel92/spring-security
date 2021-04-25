package org.nahuel.rodriguez.springsecuritysecondhandson.security.authenticationproviders;

import org.nahuel.rodriguez.springsecuritysecondhandson.proxies.AuthenticationServerProxy;
import org.nahuel.rodriguez.springsecuritysecondhandson.security.authentications.OtpAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {
    private final AuthenticationServerProxy proxy;

    public OtpAuthenticationProvider(final AuthenticationServerProxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final var username = authentication.getName();
        final var code = String.valueOf(authentication.getCredentials());
        final var result = proxy.sendOtp(username, code);

        if (result) {
            return new OtpAuthentication(username, code);
        }
        throw new BadCredentialsException("Bad credentials!");
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
