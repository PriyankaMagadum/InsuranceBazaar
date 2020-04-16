package com.synechron.insurancebazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synechron.insurancebazaar.modal.PolicyHolder;

@Repository
public interface PolicyHolderRepository extends JpaRepository<PolicyHolder, Long> {

}
