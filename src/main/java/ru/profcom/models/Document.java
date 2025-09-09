package ru.profcom.models;

import jakarta.persistence.Column;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.profcom.dto.UserProfileDto;
import ru.profcom.entities.PersonalDataEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Document {
    private Long id; // ID из таблицы (SERIAL)

    private String firstName; // Имя

    private String lastName; // Фамилия

    private String middleName; // Отчество

    private LocalDate birthDate; // Дата рождения

    private String addressLiving; // Адрес проживания

    private String addressRegistration; // Адрес регистрации

    private String passportSerial; // Серия паспорта

    private String passportNumber; // Номер паспорта

    private LocalDate issueDate; // Дата выдачи паспорта

    private String issueByWhom; // Кем выдан паспорт

    private String inn; // ИНН

    private Long snils; // СНИЛС (BIGINT)

    private String disabilityStatus;

    private String fullStateSupport; // Полное гос. обеспечение (Да/Нет)

    private String contactNumber; // Контактный номер

    private Integer course; // Курс обучения

    private String groupNumber; // Номер группы

    private String studyType; // Форма обучения (очная/заочная)

    public void toModel(UserProfileDto entity) {
        this.firstName = entity.getFirstName();
        this.middleName = entity.getMiddleName();
        this.lastName = entity.getLastName();
        this.birthDate = LocalDate.parse(entity.getBirthDate());
        this.addressLiving = entity.getAddressLiving();
        this.addressRegistration = entity.getPassport().getAddressRegistration();
        this.passportSerial = entity.getPassport().getSerial() != null ? entity.getPassport().getSerial() : "";
        this.passportNumber = entity.getPassport().getNumber();
        this.issueDate = LocalDate.parse(entity.getPassport().getIssueDate());
        this.issueByWhom = entity.getPassport().getIssuedByWhom();
        this.inn = entity.getInn();
        this.snils = Long.valueOf(entity.getSnils());
        this.disabilityStatus = entity.getDisabilityStatus();
        this.fullStateSupport = String.valueOf(entity.isFullStateSupport());
        this.contactNumber = entity.getContactNumber();
        this.course = entity.getCourse();
        this.groupNumber = entity.getGroupNumber();
        this.studyType = entity.getStudyType();
    }
}
