export interface UserProfile{
    firstName: string;
    lastName: string;
    middleName: string;
    email: string;
    studentGroup: { title: string };
    courseNumber: number;
    institute: { title: string };
    specialityName: string;
    specializationName: string;
    isTelegramConnected: boolean;
    photoUrl: string;

    // дополнительная часть
    id?: string;
    // surname: string;
    // name: string;
    // patronymic: string;
    birthDate: string;  // Дата рождения (ISO строка)
    addressLiving: string;  // Адрес проживания
    addressRegistration: string;  // Адрес регистрации
    passportSerial: string;
    passportNumber: string;
    issueDate: string;  // Дата выдачи паспорта (ISO строка)
    issuedByWhom: string;  // Кем выдан паспорт
    inn: string;
    snils: string;
    contactNumber: string;
    course: string;  // Курс обучения
    groupNumber: string;  // Номер группы
    studyType: string;  // Форма обучения (очная/заочная)
    fullStateSupport: boolean;  // Полное гос. обеспечение
    disabilityStatus: string;  // Детали инвалидности
}