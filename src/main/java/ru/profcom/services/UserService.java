package ru.profcom.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ru.profcom.dto.UserProfileDto;
import ru.profcom.entities.PassportEntity;
import ru.profcom.entities.PersonalDataEntity;
import ru.profcom.entities.StudentInfoEntity;
import ru.profcom.entities.UserEntity;
import ru.profcom.mappers.UserMapper;
import ru.profcom.repositories.PassportRepository;
import ru.profcom.repositories.PersonalDataRepository;
import ru.profcom.repositories.StudentInfoRepository;
import ru.profcom.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PersonalDataRepository personalDataRepository;
    private final StudentInfoRepository studentInfoRepository;
    private final UserMapper userMapper;
    private final PassportRepository passportRepository;

    public UserEntity findOrCreateUser(OAuth2User oauth2User) {
        Map<String, Object> attr = oauth2User.getAttributes();

        String email = (String) attr.get("email");

        return userRepository.findByEmail(email).orElseGet(() -> {
            Integer idKfu = (Integer) attr.get("id");
            LocalDateTime lastLogin = LocalDateTime.now();
            Boolean isStaff = ((String) attr.get("role")).equals("student");

            // Personal data
            String firstName = (String) attr.get("first_name");
            String lastName = (String) attr.get("last_name");
            String middleName = (String) attr.get("middle_name");

            // student info
            Integer course = (Integer) attr.get("course_number");
            String group = null;
            if (attr.get("student_group") instanceof Map<?, ?> groupMap) {
                group = (String) groupMap.get("title");
            }
            String photoUrl = (String) attr.get("photo_url");

            String institute = null;
            if (attr.get("institute") instanceof Map<?, ?> instMap) {
                institute = (String) instMap.get("title");
            }

            UserEntity user = new UserEntity();
            PersonalDataEntity pd = new PersonalDataEntity();
            StudentInfoEntity si = new StudentInfoEntity();
            user.setEmail(email);
            user.setIdKfu(idKfu);
            user.setLastLogin(lastLogin);
            user.setStaff(isStaff);
            pd.setFirstName(firstName);
            pd.setLastName(lastName);
            pd.setMiddleName(middleName);
            si.setCourse(course);
            si.setGroupNumber(group);
            si.setInstitute(institute);
            si.setSpecialityName((String) attr.get("speciality_name"));
            si.setSpecializationName((String) attr.get("specialization_name"));
            si.setPhotoUrl(photoUrl);

            user.setPersonalData(pd);
            user.setStudentInfo(si);
            System.out.println(user.toString());
            studentInfoRepository.save(si);
            personalDataRepository.save(pd);
            return userRepository.save(user);
        });
    }
    public UserProfileDto getUserProfileDto(OAuth2AuthenticationToken token) {
        OAuth2User oauth2User = token.getPrincipal();
        String email = oauth2User.getAttribute("email");

        UserEntity user =
            userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found: " + email));

        return userMapper.toDto(user);
    }

    public void updateUserProfileFields(Authentication authentication, Map<String, Object> updates) {
        String email = authentication.getName();
        UserEntity user =
            userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        PersonalDataEntity pd = user.getPersonalData();
        StudentInfoEntity si = user.getStudentInfo();

        // ----------- PERSONAL DATA ----------
        if (updates.containsKey("firstName")) {
            pd.setFirstName((String) updates.get("firstName"));
        }
        if (updates.containsKey("lastName")) {
            pd.setLastName((String) updates.get("lastName"));
        }
        if (updates.containsKey("middleName")) {
            pd.setMiddleName((String) updates.get("middleName"));
        }
        if (updates.containsKey("birthDate")) {
            String value = (String) updates.get("birthDate");
            if (value != null && !value.isEmpty()) {
                try {
                    pd.setBirthDate(LocalDate.parse(value)); // ISO
                } catch (Exception ex) {
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    pd.setBirthDate(LocalDate.parse(value, fmt));
                }
            }
        }
        if (updates.containsKey("addressLiving")) {
            pd.setAddressLiving((String) updates.get("addressLiving"));
        }
        if (updates.containsKey("inn")) {
            pd.setInn((String) updates.get("inn"));
        }
        if (updates.containsKey("snils")) {
            try {
                String snilsStr = updates.get("snils").toString();
                if (!snilsStr.isEmpty()) {
                    pd.setSnils(Long.parseLong(snilsStr.replaceAll("\\D", "")));
                }
            } catch (Exception ignored) {
            }
        }
        if (updates.containsKey("disabilityStatus")) {
            pd.setDisabilityStatus((String) updates.get("disabilityStatus"));
        }
        if (updates.containsKey("fullStateSupport")) {
            // ожидаем строку ("Да"/"Нет"), если приходит boolean — преобразуем
            Object val = updates.get("fullStateSupport");
            pd.setFullStateSupport(val instanceof Boolean ? ((Boolean) val ? "Да" : "Нет") : val.toString());
        }
        if (updates.containsKey("contactNumber")) {
            pd.setContactNumber((String) updates.get("contactNumber"));
        }

        // ----------- PASSPORT DATA ----------
        PassportEntity passport = pd.getPassport();
        if (passport == null) {
            passport = new PassportEntity();
            pd.setPassport(passport);
        }

        if (updates.containsKey("passportSerial")) {
            passport.setSerial((String) updates.get("passportSerial"));
        }
        if (updates.containsKey("passportNumber")) {
            passport.setNumber((String) updates.get("passportNumber"));
        }
        if (updates.containsKey("issueDate")) {
            String value = (String) updates.get("issueDate");
            if (value != null && !value.isEmpty()) {
                try {
                    passport.setIssueDate(LocalDate.parse(value));
                } catch (Exception ex) {
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    passport.setIssueDate(LocalDate.parse(value, fmt));
                }
            }
        }
        if (updates.containsKey("issuedByWhom")) {
            passport.setIssuedByWhom((String) updates.get("issuedByWhom"));
        }
        if (updates.containsKey("addressRegistration")) {
            passport.setAddressRegistration((String) updates.get("addressRegistration"));
        }

        passportRepository.save(passport);
        personalDataRepository.save(pd);

        // ----------- STUDENT INFO ----------
        if (si != null) {
            if (updates.containsKey("course")) {
                try {
                    String courseStr = updates.get("course").toString();
                    si.setCourse(Integer.parseInt(courseStr));
                } catch (Exception ignored) {
                }
            }
            if (updates.containsKey("groupNumber")) {
                si.setGroupNumber((String) updates.get("groupNumber"));
            }
            if (updates.containsKey("studyType")) {
                si.setStudyType((String) updates.get("studyType"));
            }
            studentInfoRepository.save(si);
        }

        userRepository.save(user);
    }

    public Map<String, Object> userEntityToMap(UserEntity user) {
        Map<String, Object> map = new HashMap<>();

        // user
        map.put("email", user.getEmail());
        map.put("idKfu", user.getIdKfu());
        map.put("isStaff", user.isStaff());

        // PersonalData
        PersonalDataEntity pd = user.getPersonalData();
        if (pd != null) {
            map.put("firstName", pd.getFirstName());
            map.put("lastName", pd.getLastName());
            map.put("middleName", pd.getMiddleName());
            map.put("birthDate", pd.getBirthDate() != null ? pd.getBirthDate().toString() : null);
            map.put("addressLiving", pd.getAddressLiving());
            map.put("inn", pd.getInn());
            map.put("snils", pd.getSnils());
            map.put("contactNumber", pd.getContactNumber());
            map.put("disabilityStatus", pd.getDisabilityStatus());
            map.put("fullStateSupport", pd.getFullStateSupport());
        }

        // Паспорт
        PassportEntity passport = pd != null ? pd.getPassport() : null;
        if (passport != null) {
            map.put("passportSerial", passport.getSerial());
            map.put("passportNumber", passport.getNumber());
            map.put("issueDate", passport.getIssueDate() != null ? passport.getIssueDate().toString() : null);
            map.put("issuedByWhom", passport.getIssuedByWhom());
            map.put("passportAddressRegistration", passport.getAddressRegistration());
        }

        // StudentInfo
        StudentInfoEntity si = user.getStudentInfo();
        if (si != null) {
            map.put("course", si.getCourse());
            map.put("groupNumber", si.getGroupNumber());
            map.put("studyType", si.getStudyType());
            map.put("educationForm", si.getEducationForm());
            map.put("institute", si.getInstitute());
            map.put("specialityName", si.getSpecialityName());
            map.put("specializationName", si.getSpecializationName());
            map.put("photoUrl", si.getPhotoUrl());
        }

        // created/updated
        map.put("createdAt", user.getCreatedAt() != null ? user.getCreatedAt().toString() : null);
        map.put("updatedAt", user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : null);

        return map;
    }
}
