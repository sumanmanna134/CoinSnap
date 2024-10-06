package com.offlixtrade.crypto.repository;

import com.offlixtrade.crypto.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE user", nativeQuery = true)
    public void truncateTable();

}
