package ru.profcom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private String birthDate;
    private String addressLiving;
    private PassportDto passport;
    private String inn;
    private String snils;
    private String contactNumber;
    private boolean fullStateSupport;
    private String disabilityStatus;

    private Integer course;
    private String groupNumber;
    private String studyType; // 'budget' or 'contract'
    private String educationForm;
    private String institute;
    private String specialityName;
    private String specializationName;
    private String photoUrl;
}
