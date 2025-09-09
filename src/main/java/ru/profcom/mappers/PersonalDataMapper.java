package ru.profcom.mappers;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.profcom.dto.PersonalDataDto;
import ru.profcom.dto.UserProfileDto;
import ru.profcom.entities.PersonalDataEntity;

@Component
@RequiredArgsConstructor
public class PersonalDataMapper {
    private final PassportMapper passportMapper;
    private final StudentInfoMapper studentInfoMapper;

    public PersonalDataDto toDto(PersonalDataEntity entity) {
        return new PersonalDataDto(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getMiddleName(),
            entity.getBirthDate(), entity.getAddressLiving(), passportMapper.toDto(entity.getPassport()),
            entity.getInn(), entity.getSnils(), entity.getDisabilityStatus(), entity.getFullStateSupport(),
            entity.getContactNumber());
    }

    public PersonalDataEntity toEntity(UserProfileDto dto) {
        PersonalDataEntity entity = new PersonalDataEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setMiddleName(dto.getMiddleName());
        entity.setBirthDate(LocalDate.parse(dto.getBirthDate()));
        entity.setAddressLiving(dto.getAddressLiving());
        entity.setPassport(passportMapper.toEntity(dto.getPassport()));
        entity.setInn(dto.getInn());
        entity.setSnils(Long.valueOf(dto.getSnils()));
        entity.setDisabilityStatus(dto.getDisabilityStatus());
        entity.setFullStateSupport(String.valueOf(dto.isFullStateSupport()));
        entity.setContactNumber(dto.getContactNumber());
        return entity;
    }
}
