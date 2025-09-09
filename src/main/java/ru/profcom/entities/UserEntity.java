package ru.profcom.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    private String email;

    @Column(name = "id_kfu") private Integer idKfu;

    @Column(name = "last_login") private LocalDateTime lastLogin;

    @Column(name = "is_staff") private boolean isStaff;

    @ManyToOne @JoinColumn(name = "personal_data_id") private PersonalDataEntity personalData;

    @ManyToOne @JoinColumn(name = "student_info_id") private StudentInfoEntity studentInfo;

    @Column(name = "created_at") private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at") private LocalDateTime updatedAt = LocalDateTime.now();
}
