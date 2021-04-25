package org.nahuel.rodriguez.springsecuritysecondhandson.security.authentications;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UsernamePasswordAuthentication extends UsernamePasswordAuthenticationToken {
    public UsernamePasswordAuthentication(final Object principal,
                                          final Object credentials,
                                          final Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public UsernamePasswordAuthentication(final Object principal, final Object credentials) {
        super(principal, credentials);
    }
}
