package com.offlixtrade.crypto.repository;

import com.offlixtrade.crypto.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
