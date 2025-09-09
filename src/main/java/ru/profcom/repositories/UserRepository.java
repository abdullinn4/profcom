package ru.profcom.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.profcom.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
