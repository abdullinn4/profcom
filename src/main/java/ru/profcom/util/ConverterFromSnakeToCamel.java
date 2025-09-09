package ru.profcom.util;

import java.util.*;
import ru.profcom.entities.PassportEntity;
import ru.profcom.entities.PersonalDataEntity;
import ru.profcom.entities.StudentInfoEntity;
import ru.profcom.entities.UserEntity;

public class ConverterFromSnakeToCamel {
    @SuppressWarnings("unchecked")
    public static Map<String, Object> snakeToCamelCaseMap(Map<String, Object> source) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            String camelKey = snakeToCamel(entry.getKey());
            Object value = entry.getValue();
            if (value instanceof Map) {
                value = snakeToCamelCaseMap((Map<String, Object>) value);
            } else if (value instanceof List) {
                List<Object> list = (List<Object>) value;
                List<Object> newList = new ArrayList<>();
                for (Object item : list) {
                    if (item instanceof Map) {
                        newList.add(snakeToCamelCaseMap((Map<String, Object>) item));
                    } else {
                        newList.add(item);
                    }
                }
                value = newList;
            }

            result.put(camelKey, value);
        }
        return result;
    }

    public static Map<String, Object> userEntityToUserProfileMap(UserEntity user) {
        Map<String, Object> map = new LinkedHashMap<>();
        PersonalDataEntity pd = user.getPersonalData();
        StudentInfoEntity si = user.getStudentInfo();
        PassportEntity passport = (pd != null) ? pd.getPassport() : null;

        map.put("firstName", pd != null ? pd.getFirstName() : null);
        map.put("lastName", pd != null ? pd.getLastName() : null);
        map.put("middleName", pd != null ? pd.getMiddleName() : null);
        map.put("email", user.getEmail());

        if (si != null && si.getGroupNumber() != null) {
            Map<String, Object> group = new LinkedHashMap<>();
            group.put("title", si.getGroupNumber());
            map.put("studentGroup", group);
        } else {
            map.put("studentGroup", null);
        }

        map.put("courseNumber", si != null ? si.getCourse() : null);

        if (si != null && si.getInstitute() != null) {
            Map<String, Object> institute = new LinkedHashMap<>();
            institute.put("title", si.getInstitute());
            map.put("institute", institute);
        } else {
            map.put("institute", null);
        }

        map.put("specialityName", si != null ? si.getSpecialityName() : null);
        map.put("specializationName", si != null ? si.getSpecializationName() : null);

        map.put("isTelegramConnected", false);

        map.put("photoUrl", si != null ? si.getPhotoUrl() : null);

        map.put("id", user.getId() != null ? user.getId().toString() : null);

        map.put("birthDate", pd != null && pd.getBirthDate() != null ? pd.getBirthDate().toString() : null);
        map.put("addressLiving", pd != null ? pd.getAddressLiving() : null);

        map.put("passportSerial", passport != null ? passport.getSerial() : null);
        map.put("passportNumber", passport != null ? passport.getNumber() : null);
        map.put("issueDate",
            passport != null && passport.getIssueDate() != null ? passport.getIssueDate().toString() : null);
        map.put("issuedByWhom", passport != null ? passport.getIssuedByWhom() : null);

        map.put("inn", pd != null ? pd.getInn() : null);
        map.put("snils", pd != null && pd.getSnils() != null ? pd.getSnils().toString() : null);
        map.put("contactNumber", pd != null ? pd.getContactNumber() : null);

        map.put("course", si != null && si.getCourse() != null ? String.valueOf(si.getCourse()) : null);
        map.put("groupNumber", si != null ? si.getGroupNumber() : null);
        map.put("studyType", si != null ? si.getStudyType() : null);

        if (pd != null && pd.getFullStateSupport() != null) {
            String val = pd.getFullStateSupport().trim().toLowerCase();
            map.put("fullStateSupport", val.equals("да") || val.equals("true") || val.equals("yes") || val.equals("1"));
        } else {
            map.put("fullStateSupport", false);
        }

        map.put("disabilityStatus", pd != null ? pd.getDisabilityStatus() : null);

        return map;
    }

    private static String snakeToCamel(String s) {
        StringBuilder sb = new StringBuilder();
        boolean upperNext = false;
        for (char c : s.toCharArray()) {
            if (c == '_') {
                upperNext = true;
            } else if (upperNext) {
                sb.append(Character.toUpperCase(c));
                upperNext = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
