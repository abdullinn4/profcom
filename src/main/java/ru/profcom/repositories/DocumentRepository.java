package ru.profcom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.profcom.entities.PersonalDataEntity;

public interface DocumentRepository extends JpaRepository<PersonalDataEntity, Long> {}
