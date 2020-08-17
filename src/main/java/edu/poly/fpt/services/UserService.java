package edu.poly.fpt.services;

import edu.poly.fpt.models.User;

import java.util.List;
import java.util.Optional;


public interface UserService {
    List<User> findAll();

    User save(User user);

    Optional<User> findById(String s);

    boolean existsById(String s);

    long count();

    void deleteById(String s);

    List<User> findByUsernameLikeOrderByUsername(String fullname);

}
