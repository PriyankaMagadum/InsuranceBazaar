package com.synechron.insurancebazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synechron.insurancebazaar.modal.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
