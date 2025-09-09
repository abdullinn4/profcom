package ru.profcom.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    private Integer course;

    @Column(name = "group_number") private String groupNumber;

    @Column(name = "study_type") private String studyType;

    @Column(name = "education_form") private String educationForm;

    @Column(name = "institute") private String institute;

    @Column(name = "speciality") private String specialityName;

    @Column(name = "training_profile") private String specializationName;

    @Column(name = "photo_url") private String photoUrl;
}
