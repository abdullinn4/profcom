package ru.profcom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.profcom.entities.PersonalDataEntity;

public interface PersonalDataRepository extends JpaRepository<PersonalDataEntity, Long> {}
