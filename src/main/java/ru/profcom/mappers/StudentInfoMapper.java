package ru.profcom.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.profcom.dto.StudentInfoDto;
import ru.profcom.entities.StudentInfoEntity;

@Component
@RequiredArgsConstructor
public class StudentInfoMapper {
    public StudentInfoDto toDto(StudentInfoEntity entity) {
        return new StudentInfoDto(entity.getCourse(), entity.getGroupNumber(), entity.getStudyType(),
            entity.getEducationForm(), entity.getInstitute(), entity.getSpecialityName(),
            entity.getSpecializationName(), entity.getPhotoUrl());
    }

    public StudentInfoEntity toEntity(StudentInfoDto dto) {
        StudentInfoEntity entity = new StudentInfoEntity();
        entity.setCourse(dto.getCourse());
        entity.setGroupNumber(dto.getGroupNumber());
        entity.setStudyType(dto.getStudyType());
        entity.setEducationForm(dto.getEducationForm());
        entity.setInstitute(dto.getInstitute());
        entity.setSpecialityName(dto.getSpecialityName());
        entity.setSpecializationName(dto.getSpecializationName());
        entity.setPhotoUrl(dto.getPhotoUrl());
        return entity;
    }
}
