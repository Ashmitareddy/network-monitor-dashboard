package com.example.neon.repository;

import com.example.neon.model.UserEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntryRepository extends JpaRepository<UserEntry, Long> {
}