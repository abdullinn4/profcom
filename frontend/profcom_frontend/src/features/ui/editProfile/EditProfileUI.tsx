import {useEffect, useState} from "react";
import {UserProfile} from "../../../entities";
import {fetchUserProfile, updateUserExtraInfo} from "../../../service";
import style from "./edit-profile.module.sass";
import InputMask from "react-input-mask";

export const EditProfileUI = () => {
    const [initialData, setInitialData] = useState<Partial<UserProfile>>({});
    const [formData, setFormData] = useState<Partial<UserProfile>>({});
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        fetchUserProfile()
            .then((data) =>{
                setInitialData(data);
                setFormData(data)
            })
            .catch(console.error);
    }, []);

    const handleChange = (field: keyof UserProfile, value: string | boolean) => {
        setFormData((prev) => ({...prev, [field]: value}))
    }
    const handleSubmit = async () => {
        const changedFields: Partial<UserProfile> = {};

        Object.entries(formData).forEach(([key, value]) => {
            if (value !== initialData[key as keyof UserProfile]) {
                changedFields[key as keyof UserProfile] = value;
            }
        });

        if (Object.keys(changedFields).length === 0){
            alert("Вы не изменили ни одного поля");
            return;
        }

        setIsSubmitting(true);
        try{
            await updateUserExtraInfo(changedFields);
            setSuccess(true);
            setInitialData({...initialData, ...changedFields})
        }catch (err) {
            console.error("Ошибка при обновлении профиля:", err);
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <main className={style.editProfile}>
            <h1>Редактирование профиля</h1>

            <div className={style.formGroup}>
                <label>Дата рождения</label>
                <InputMask
                    mask="9999.99.99"
                    value={formData.birthDate || ""}
                    onChange={(e) => handleChange("birthDate", e.target.value)}
                >
                    {(inputProps) => <input {...inputProps} placeholder="дд.мм.гггг" />}
                </InputMask>
            </div>

            <div className={style.formGroup}>
                <label>Адрес проживания</label>
                <input
                    value={formData.addressLiving || ""}
                    onChange={(e) => handleChange("addressLiving", e.target.value)}
                />
            </div>

            <div className={style.formGroup}>
                <label>Паспорт</label>
                <div className={style.passportFields}>
                    <InputMask
                        mask="9999"
                        value={formData.passportSerial || ""}
                        onChange={(e) => handleChange("passportSerial", e.target.value)}
                    >
                        {(inputProps) => <input {...inputProps} placeholder="Серия" />}
                    </InputMask>
                    <InputMask
                        mask="999999"
                        value={formData.passportNumber || ""}
                        onChange={(e) => handleChange("passportNumber", e.target.value)}
                    >
                        {(inputProps) => <input {...inputProps} placeholder="Номер" />}
                    </InputMask>
                </div>
            </div>

            <div className={style.formGroup}>
                <label>Дата выдачи</label>
                <InputMask
                    mask="99.99.9999"
                    value={formData.issueDate || ""}
                    onChange={(e) => handleChange("issueDate", e.target.value)}
                >
                    {(inputProps) => <input {...inputProps} placeholder="дд.мм.гггг" />}
                </InputMask>
            </div>

            <div className={style.formGroup}>
                <label>ИНН</label>
                <InputMask
                    mask="999999999999"
                    value={formData.inn || ""}
                    onChange={(e) => handleChange("inn", e.target.value)}
                >
                    {(inputProps) => <input {...inputProps} placeholder="ИНН" />}
                </InputMask>
            </div>

            <div className={style.formGroup}>
                <label>СНИЛС</label>
                <InputMask
                    mask="999-999-999 99"
                    value={formData.snils || ""}
                    onChange={(e) => handleChange("snils", e.target.value)}
                >
                    {(inputProps) => <input {...inputProps} placeholder="СНИЛС" />}
                </InputMask>
            </div>

            <div className={style.formGroup}>
                <label>Номер телефона</label>
                <InputMask
                    mask="+7 (999) 999-99-99"
                    value={formData.contactNumber || ""}
                    onChange={(e) => handleChange("contactNumber", e.target.value)}
                >
                    {(inputProps) => <input {...inputProps} placeholder="+7" />}
                </InputMask>
            </div>

            <div className={style.formGroup}>
                <label>Адрес регистрации</label>
                <input
                    value={formData.addressRegistration || ""}
                    onChange={(e) => handleChange("addressRegistration", e.target.value)}
                    placeholder="Адрес регистрации"
                />
            </div>

            <div className={style.formGroup}>
                <label>Кем выдан паспорт</label>
                <input
                    value={formData.issuedByWhom || ""}
                    onChange={(e) => handleChange("issuedByWhom", e.target.value)}
                    placeholder="Кем выдан"
                />
            </div>

            <div className={style.formGroup}>
                <label>Номер группы</label>
                <input
                    value={formData.groupNumber || ""}
                    onChange={(e) => handleChange("groupNumber", e.target.value)}
                    placeholder="Группа"
                />
            </div>

            <div className={style.formGroup}>
                <label>Курс</label>
                <input
                    type="number"
                    value={formData.course || ""}
                    onChange={(e) => handleChange("course", e.target.value)}
                    placeholder="Курс"
                />
            </div>

            <div className={style.formGroup}>
                <label>
                    Форма обучения
                </label>
                <select
                    value={formData.studyType || ""}
                    onChange={(e) => handleChange("studyType", e.target.value)}
                >
                    <option value="">Выберите...</option>
                    <option value="Очная">Очная</option>
                    <option value="Заочная">Заочная</option>
                </select>
            </div>

            <div className={style.checkboxGroup}>
                <label htmlFor="fullStateSupport">Полное гос. обеспечение</label>
                <input
                    type="checkbox"
                    id="fullStateSupport"
                    checked={formData.fullStateSupport || false}
                    onChange={(e) => handleChange("fullStateSupport", e.target.checked)}
                />
            </div>

            <div className={style.formGroup}>
                <label>Инвалидность</label>
                <select
                    value={formData.disabilityStatus || ""}
                    onChange={(e) => handleChange("disabilityStatus", e.target.value)}
                >
                    <option value="">Выберите...</option>
                    <option value="Нет">Нет</option>
                    <option value="1 группа">1 группа</option>
                    <option value="2 группа">2 группа</option>
                    <option value="3 группа">3 группа</option>
                </select>
            </div>

            <button
                className={style.submitButton}
                onClick={handleSubmit}
                disabled={isSubmitting}
            >
                {isSubmitting ? "Сохранение..." : "Сохранить изменения"}
            </button>

            {success && <p className={style.successMsg}>Изменения сохранены!</p>}
        </main>
    );
}