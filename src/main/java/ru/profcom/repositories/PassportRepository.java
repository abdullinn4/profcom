package ru.profcom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.profcom.entities.PassportEntity;

public interface PassportRepository extends JpaRepository<PassportEntity, Long> {}
