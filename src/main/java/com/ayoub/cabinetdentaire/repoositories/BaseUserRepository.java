package com.ayoub.cabinetdentaire.repoositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseUserRepository<T, ID> extends JpaRepository<T, ID> {
    Optional<T> findByEmail(String email);
    boolean existsByEmail(String email);
}
