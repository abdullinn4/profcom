package ru.profcom.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassportDto {
    private String serial;
    private String number;
    private String issueDate;
    private String issuedByWhom;
    private String addressRegistration;
}
