import {useNavigate, useParams} from "react-router-dom";
import {ChangeEvent, FormEvent, useEffect, useState} from "react";
import style from "./university_form.module.sass"
import {submitAidUniRequest} from "../../../service";
import InputMask from "react-input-mask";

export const UniversityAidFormUI = () => {
    const {id} = useParams();
    const navigate = useNavigate();

    //автоматически загружает известные данные о пользователе
    useEffect(() => {
        fetch("/profcom/user-data")
            .then((res) => res.json())
            .then((data) => setFormData(data))
            .catch((err) => console.error("Ошибка загрузки данных:", err));
    }, []);

    const [formData, setFormData] = useState({
        id: id,
        lastName: "",
        firstName: "",
        middleName: "",
        birthDate: "",
        addressLiving: "",
        addressRegistration: "",
        passportSerial: "",
        passportNumber: "",
        issueDate: "",
        issuedByWhom:"",
        inn:"",
        snils:"",
        contactNumber: "",
        course: "",
        groupNumber: "",
        studyType: "",
        fullStateSupport: false,
        disabilityStatus: "none"
    });

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault()
        setErrors([]);

        try{
            const response = await submitAidUniRequest(formData);
            console.log('Форма успешно отправлена:', response);
            navigate(`/generator_form/university/${id}`);
        }catch (error: any) {
            setErrors([error.message])
        }
    }
    const handleInputChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const target = e.target as HTMLInputElement;
        
        setFormData(prev => ({
          ...prev,
          [target.name]: target.type === 'checkbox' 
            ? target.checked // boolean
            : target.value
        }));
      };

    const [errors,setErrors] = useState<string[]>([])

    return  (
        <main className={style.universityAidForm}>
            <h1>Форма на заполнение университетской помощи</h1>

            {errors.length > 0 && (
                <div className={style.errorContainer}>
                    <ul>
                        {errors.map((error, index) => (
                            <li key={index}>{error}</li>
                        ))}
                    </ul>
                </div>
            )}

            <form onSubmit={handleSubmit}>
                <div className={style.formGroup}>
                    <label>
                        Фамилия
                        <input
                            type="text"
                            name="lastName"
                            value={formData.lastName}
                            onChange={handleInputChange}
                            required
                        />
                    </label>
                    <label>
                        Имя
                        <input
                            type="text"
                            name="firstName"
                            value={formData.firstName}
                            onChange={handleInputChange}
                            required
                        />
                    </label>
                </div>

                <div className={style.formGroup}>
                    <label>
                        Отчество
                        <input
                            type="text"
                            name="middleName"
                            value={formData.middleName}
                            onChange={handleInputChange}
                        />
                    </label>
                    <label>
                        Дата рождения
                        <InputMask
                            mask="99.99.9999"
                            value={formData.birthDate}
                            onChange={(e) => handleInputChange(e)}
                        >
                            {(inputProps) => (
                                <input
                                    {...inputProps}
                                    type="text"
                                    name="birthDate"
                                    placeholder="дд.мм.гггг"
                                    required
                                />
                            )}
                        </InputMask>
                    </label>
                </div>

                <div className={style.formGroup}>
                    <label>
                        Курс
                        <input
                            type="text"
                            name="course"
                            value={formData.course}
                            onChange={handleInputChange}
                            inputMode="numeric"
                        />
                    </label>
                    <label>
                        Группа
                        <input
                            type="text"
                            name="groupNumber"
                            value={formData.groupNumber}
                            onChange={handleInputChange}
                            required
                            inputMode="numeric"
                        />
                    </label>
                </div>

                <div className={style.formGroup}>
                    <label>
                        Форма обучения
                        <select name="studyType" value={formData.studyType} onChange={handleInputChange} required>
                            <option value="">Выберите...</option>
                            <option value="Очная">Очная</option>
                            <option value="Заочная">Заочная</option>
                        </select>
                    </label>
                </div>

                <div className={style.formGroup}>
                    <label>
                        Адрес регистрации
                        <input
                            type="text"
                            name="address_registration"
                            value={formData.addressRegistration}
                            onChange={handleInputChange}
                            required
                        />
                    </label>
                    <label>
                        Адрес проживания
                        <input
                            type="text"
                            name="addressLiving"
                            value={formData.addressLiving}
                            onChange={handleInputChange}
                            required
                        />
                    </label>
                </div>

                <div className={style.formGroup}>
                    <label>
                        Серия паспорта
                        <InputMask
                            mask="9999"
                            value={formData.passportSerial}
                            onChange={(e) => handleInputChange(e)}
                        >
                            {(inputProps) => (
                                <input
                                    {...inputProps}
                                    type="text"
                                    name="passportSerial"
                                    placeholder="0000"
                                    required
                                />
                            )}
                        </InputMask>
                    </label>

                    <label>
                        Номер паспорта
                        <InputMask
                            mask="999999"
                            value={formData.passportNumber}
                            onChange={(e) => handleInputChange(e)}
                        >
                            {(inputProps) => (
                                <input
                                    {...inputProps}
                                    type="text"
                                    name="passportNumber"
                                    placeholder="000000"
                                    required
                                />
                            )}
                        </InputMask>
                    </label>
                </div>

                <div className={style.formGroup}>
                    <label>
                        Дата выдачи
                        <InputMask
                            mask="9999.99.99"
                            value={formData.issueDate}
                            onChange={(e) => handleInputChange(e)}
                        >
                            {(inputProps) => (
                                <input
                                    {...inputProps}
                                    type="text"
                                    name="issueDate"
                                    placeholder="гггг.мм.дд"
                                    required
                                />
                            )}
                        </InputMask>
                    </label>

                    <label>
                        Кем выдан
                        <input
                            type="text"
                            name="issuedByWhom"
                            value={formData.issuedByWhom}
                            onChange={handleInputChange}
                            required
                        />
                    </label>
                </div>

                <div className={style.formGroup}>
                    <label>
                        ИНН
                        <InputMask
                            mask="9999999999"
                            value={formData.inn}
                            onChange={(e) => handleInputChange(e)}
                        >
                            {(inputProps) => (
                                <input
                                    {...inputProps}
                                    type="text"
                                    name="inn"
                                    placeholder="ИНН"
                                    required
                                />
                            )}
                        </InputMask>
                    </label>

                    <label>
                        СНИЛС
                        <InputMask
                            mask="999-999-999 99"
                            value={formData.snils}
                            onChange={(e) => handleInputChange(e)}
                        >
                            {(inputProps) => (
                                <input
                                    {...inputProps}
                                    type="text"
                                    name="snils"
                                    placeholder="000-000-000 00"
                                    required
                                />
                            )}
                        </InputMask>
                    </label>
                </div>

                <div className={style.formGroup}>
                    <label>
                        Контактный номер
                        <InputMask
                            mask="+7 (999) 999-99-99"
                            value={formData.contactNumber}
                            onChange={(e) => handleInputChange(e)}
                        >
                            {(inputProps) => (
                                <input
                                    {...inputProps}
                                    type="text"
                                    name="contactNumber"
                                    placeholder="+7 (___) ___-__-__"
                                    required
                                />
                            )}
                        </InputMask>
                    </label>
                </div>

                <div className={style.formGroup}>
                    <label>
                        Государственное обеспечение
                        <input
                            type="checkbox"
                            name="fullStateSupport"
                            checked={formData.fullStateSupport}
                            onChange={handleInputChange}
                        />
                    </label>
                </div>

                <div className={style.formGroup}>
                    <label>
                        Инвалидность
                        <select
                            name="disabilityStatus"
                            value={formData.disabilityStatus}
                            onChange={handleInputChange}
                        >
                            <option value="">Выберите...</option>
                            <option value="none">Нет</option>
                            <option value="1">1 группа</option>
                            <option value="2">2 группа</option>
                        </select>
                    </label>
                </div>

                <button type="submit" className={style.submitButton}>
                    Сгенерировать
                </button>
            </form>
        </main>

    )

}