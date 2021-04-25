package org.nahuel.rodriguez.springsecuritysecondhandson.repositories;

import org.nahuel.rodriguez.springsecuritysecondhandson.entities.Otp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends CrudRepository<Otp, String> {
    Optional<Otp> findOtpByUsername(final String username);
}
