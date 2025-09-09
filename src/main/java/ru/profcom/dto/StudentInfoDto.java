package ru.profcom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentInfoDto {
    private Integer course;
    private String groupNumber;
    private String studyType; // 'budget' or 'contract'
    private String educationForm;
    private String institute;
    private String specialityName;
    private String specializationName;
    private String photoUrl;
}
