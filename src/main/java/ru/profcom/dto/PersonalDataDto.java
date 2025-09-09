package ru.profcom.dto;

import java.time.LocalDate;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalDataDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate birthDate;
    private String addressLiving;
    private PassportDto passport;
    private String inn;
    private Long snils;
    private String disabilityStatus;
    private String fullStateSupport;
    private String contactNumber;
}
