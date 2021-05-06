package com.sns.zuzuclub.domain.user.repository;

import com.sns.zuzuclub.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
