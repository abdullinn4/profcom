package ru.profcom.mappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.springframework.stereotype.Component;
import ru.profcom.dto.PassportDto;
import ru.profcom.entities.PassportEntity;

@Component
public class PassportMapper {
    public PassportDto toDto(PassportEntity entity) {
        var a = entity.getIssueDate().toString();
        return new PassportDto(entity.getSerial(), entity.getNumber(), entity.getIssueDate().toString(),
            entity.getIssuedByWhom(), entity.getAddressRegistration());
    }

    public PassportEntity toEntity(PassportDto dto) {
        PassportEntity entity = new PassportEntity();
        entity.setSerial(dto.getSerial());
        entity.setNumber(dto.getNumber());
        entity.setIssueDate(LocalDate.parse(dto.getIssueDate()));
        entity.setIssuedByWhom(dto.getIssuedByWhom());
        entity.setAddressRegistration(dto.getAddressRegistration());
        return entity;
    }
}
