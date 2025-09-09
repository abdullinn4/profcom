package ru.profcom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.profcom.entities.StudentInfoEntity;

public interface StudentInfoRepository extends JpaRepository<StudentInfoEntity, Long> {}
