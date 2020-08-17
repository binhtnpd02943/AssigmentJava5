package edu.poly.fpt.repositories;

import edu.poly.fpt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByUsernameLikeOrderByUsername(String fullname);
    User findByUsername(String username);
}
