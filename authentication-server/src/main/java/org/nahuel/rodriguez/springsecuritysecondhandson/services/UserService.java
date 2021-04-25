package org.nahuel.rodriguez.springsecuritysecondhandson.services;

import org.nahuel.rodriguez.springsecuritysecondhandson.repositories.OtpRepository;
import org.nahuel.rodriguez.springsecuritysecondhandson.repositories.UserRepository;
import org.nahuel.rodriguez.springsecuritysecondhandson.entities.Otp;
import org.nahuel.rodriguez.springsecuritysecondhandson.entities.User;
import org.nahuel.rodriguez.springsecuritysecondhandson.util.GenerateCodeUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;

    public UserService(final PasswordEncoder passwordEncoder,
                       final UserRepository userRepository,
                       final OtpRepository otpRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
    }

    public void addUser(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void auth(final User user) {
        final var savedUserOptional = userRepository.findUserByUsername(user.getUsername());

        if (savedUserOptional.isPresent()) {
            final var savedUser = savedUserOptional.get();

            if (passwordEncoder.matches(user.getPassword(), savedUser.getPassword())) {
                renewOtp(savedUser);
            } else {
                throw new BadCredentialsException("Bad credentials!");
            }
        } else {
            throw new BadCredentialsException("Bad credentials!");
        }
    }

    public boolean check(final Otp otpToValidate) {
        return otpRepository.findOtpByUsername(otpToValidate.getUsername())
                .filter(otp -> otp.getCode().equals(otpToValidate.getCode()))
                .isPresent();
    }

    private void renewOtp(final User user) {
        final var code = GenerateCodeUtil.generateCode();
        final var otpOptional = otpRepository.findOtpByUsername(user.getUsername());

        if (otpOptional.isPresent()) {
            otpOptional.get().setCode(code);
        } else {
            final var otp = new Otp();
            otp.setUsername(user.getUsername());
            otp.setCode(code);
            otpRepository.save(otp);
        }
    }
}
