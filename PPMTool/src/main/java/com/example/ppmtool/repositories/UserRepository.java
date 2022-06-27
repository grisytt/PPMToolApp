package com.example.ppmtool.repositories;

import com.example.ppmtool.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

//    User getById(Long id); // Exception: could not initialize proxy
    Optional<User> findById(Long id);

}
