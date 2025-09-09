package ru.profcom.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "passport")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassportEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @Column(name = "passport_serial") private String serial;

    @Column(name = "passport_number") private String number;

    @Column(name = "passport_issue_date") private LocalDate issueDate;

    @Column(name = "passport_issue_by_whom") private String issuedByWhom;

    @Column(name = "address_registration") private String addressRegistration;
}
