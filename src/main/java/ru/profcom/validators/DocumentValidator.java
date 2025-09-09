package ru.profcom.validators;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import ru.profcom.dto.UserProfileDto;
import ru.profcom.entities.PersonalDataEntity;

@Component
public class DocumentValidator {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{10,15}$");
    private static final Pattern INN_PATTERN = Pattern.compile("^[0-9]{10,12}$");
    //    private static final Pattern SNILS_PATTERN = Pattern.compile("^[0-9]{11}$");

    public List<String> validate(UserProfileDto userProfileDto) {
        List<String> errors = new ArrayList<>();

        if (userProfileDto.getLastName() == null || userProfileDto.getLastName().isBlank()) {
            errors.add("Фамилия не может быть пустой");
        }
        if (userProfileDto.getFirstName() == null || userProfileDto.getFirstName().isBlank()) {
            errors.add("Имя не может быть пустым");
        }
        if (userProfileDto.getBirthDate() == null
            || LocalDate.parse(userProfileDto.getBirthDate()).isAfter(LocalDate.now())) {
            errors.add("Некорректная дата рождения");
        }
        if (userProfileDto.getCourse() == null || userProfileDto.getCourse() <= 0 || userProfileDto.getCourse() > 6) {
            errors.add("Некорректный курс");
        }
        if (userProfileDto.getGroupNumber() == null || userProfileDto.getGroupNumber().isBlank()) {
            errors.add("Группа не может быть пустой");
        }
        if (userProfileDto.getInn() == null || !INN_PATTERN.matcher(userProfileDto.getInn()).matches()) {
            errors.add("Некорректный ИНН");
        }
        ////        if (userProfileDto.getSnils() == null ||
        ///!SNILS_PATTERN.matcher(userProfileDto.getSnils()).matches()) { / errors.add("Некорректный СНИЛС");
        ////        }
        if (userProfileDto.getContactNumber() == null
            || !PHONE_PATTERN.matcher(userProfileDto.getContactNumber()).matches()) {
            errors.add("Некорректный номер телефона");
        }

        return errors;
    }
}
