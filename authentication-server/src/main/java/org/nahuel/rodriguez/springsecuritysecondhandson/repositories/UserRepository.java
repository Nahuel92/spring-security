package org.nahuel.rodriguez.springsecuritysecondhandson.repositories;

import org.nahuel.rodriguez.springsecuritysecondhandson.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findUserByUsername(final String username);
}
