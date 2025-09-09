package ru.profcom.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.profcom.dto.PassportDto;
import ru.profcom.dto.StudentInfoDto;
import ru.profcom.dto.UserProfileDto;
import ru.profcom.entities.PassportEntity;
import ru.profcom.entities.PersonalDataEntity;
import ru.profcom.entities.StudentInfoEntity;
import ru.profcom.entities.UserEntity;
import ru.profcom.util.DateUtil;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PersonalDataMapper personalDataMapper;
    private final StudentInfoMapper studentInfoMapper;

    public UserProfileDto toDto(UserEntity user) {
        PersonalDataEntity pd = user.getPersonalData();
        PassportEntity passport = pd.getPassport();
        PassportDto passportDto = null;
        if (passport != null) {
            passportDto =
                new PassportDto(passport.getSerial(), passport.getNumber(), DateUtil.format(passport.getIssueDate()),
                    passport.getIssuedByWhom(), passport.getAddressRegistration());
        }

        StudentInfoEntity si = user.getStudentInfo();

        return new UserProfileDto(user.getEmail(), pd.getFirstName(), pd.getLastName(), pd.getMiddleName(),
            DateUtil.format(pd.getBirthDate()), pd.getAddressLiving(), passportDto, pd.getInn(),
            String.valueOf(pd.getSnils()), pd.getContactNumber(), Boolean.parseBoolean(pd.getFullStateSupport()),
            pd.getDisabilityStatus(), si.getCourse(), si.getGroupNumber(), si.getStudyType(), si.getEducationForm(),
            si.getInstitute(), si.getSpecialityName(), si.getSpecializationName(), si.getPhotoUrl());
    }

    //    public UserEntity toEntity(UserProfileDto dto) {
    //        UserEntity entity = new UserEntity();
    //        entity.setId(dto.getId());
    //        entity.setEmail(dto.getEmail());
    //        entity.setIdKfu(dto.getIdKfu());
    //        entity.setStaff(dto.isStaff());
    //        entity.setPersonalData(personalDataMapper.toEntity(dto.getPersonalData()));
    //        return entity;
    //    }
}
