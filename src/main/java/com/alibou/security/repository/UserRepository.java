package com.alibou.security.repository;


import com.alibou.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);


    @Query("select u from Order o full join User u on o.id = u.id group by u.id order by sum(o.price)")
    List<User> findExpensiveOrder();

    Optional<User> findByEmail(String email);


}
