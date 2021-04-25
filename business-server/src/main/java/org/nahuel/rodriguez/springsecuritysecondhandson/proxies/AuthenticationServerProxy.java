package org.nahuel.rodriguez.springsecuritysecondhandson.proxies;

import org.nahuel.rodriguez.springsecuritysecondhandson.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Component
public class AuthenticationServerProxy {
    @Value("${auth.server.base.url}")
    private String baseUrl;

    public void sendAuth(final String username, final String password) {
        final var user = new User();
        user.setUsername(username);
        user.setPassword(password);

        WebClient.create(baseUrl + "/user/auth")
                .post()
                .bodyValue(user)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public boolean sendOtp(final String username, final String code) {
        final var user = new User();
        user.setUsername(username);
        user.setCode(code);

        return Objects.requireNonNull(WebClient.create(baseUrl + "/otp/check")
                .post()
                .bodyValue(user)
                .retrieve()
                .toBodilessEntity()
                .block(), "Error when receiving the OTP response from authentication server.")
                .getStatusCode()
                .is2xxSuccessful();
    }
}
