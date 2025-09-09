package ru.profcom.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// import java.sql.Date;

@Entity
@Table(name = "personal_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalDataEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @Column(name = "first_name") private String firstName;

    @Column(name = "last_name") private String lastName;

    @Column(name = "middle_name") private String middleName;

    @Column(name = "birth_date") private LocalDate birthDate;

    @Column(name = "address_living") private String addressLiving;

    @ManyToOne @JoinColumn(name = "passport_id") private PassportEntity passport;

    private String inn;
    private Long snils;

    @Column(name = "disability_status") private String disabilityStatus;

    @Column(name = "full_state_support") private String fullStateSupport;

    @Column(name = "contact_number") private String contactNumber;

    @Column(name = "created_at") private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at") private LocalDateTime updatedAt = LocalDateTime.now();
}
