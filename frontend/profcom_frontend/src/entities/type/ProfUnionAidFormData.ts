export interface ProfUnionAidFormData {
    id?: string;
    firstName: string;
    lastName: string;
    middleName: string;
    birthDate: string;  // Дата рождения (ISO строка)
    addressLiving: string;  // Адрес проживания
    passportSerial: string;
    passportNumber: string;
    issueDate: string;  // Дата выдачи паспорта (ISO строка)
    issuedByWhom: string;  // Кем выдан паспорт
    inn: string;
    contactNumber: string;
    course: string;  // Курс обучения
    groupNumber: string;  // Номер группы
    studyType: string;  // Форма обучения (очная/заочная)
}