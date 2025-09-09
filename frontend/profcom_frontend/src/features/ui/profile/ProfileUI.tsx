import style from "./profile.module.sass";
import {useEffect, useState} from "react";
import {fetchUserProfile} from "../../../service";
import {UserProfile} from "../../../entities";
import {useNavigate} from "react-router-dom";

export const ProfileUI = () => {
    const [userData, setUserData] = useState<UserProfile | null>(null);
    const navigate = useNavigate();

    useEffect(() => {

        fetchUserProfile()
            .then(setUserData)
            .catch(console.error);

    }, []);

    if (!userData) return <div className={style.profile}>Загрузка...</div>;

    return (
        <main className={style.profile}>
            <h1>Личный кабинет</h1>

            <div className={style.profileBlock}>
                <img className={style.avatar} src={userData.photoUrl} alt="avatar" />
                <div>
                    <p><strong>ФИО:</strong> {userData.firstName} {userData.lastName} {userData.middleName}</p>
                    <p><strong>Email:</strong> {userData.email}</p>
                    <p><strong>Группа:</strong> {userData.studentGroup.title}</p>
                    <p><strong>Курс:</strong> {userData.courseNumber}</p>
                    <p><strong>Институт:</strong> {userData.institute.title}</p>
                    <p><strong>Специальность:</strong> {userData.specialityName}</p>
                    <p><strong>Профиль:</strong> {userData.specializationName}</p>
                    <p><strong>Telegram:</strong> {userData.isTelegramConnected ? 'Подключен' : 'Не подключен'}</p>
                </div>
            </div>

            <h2>Дополнительная информация</h2>
            <table className={style.infoTable}>
                <tbody>
                <tr><td>Дата рождения</td><td>{userData.birthDate}</td></tr>
                <tr><td>Адрес проживания</td><td>{userData.addressLiving}</td></tr>
                <tr><td>Адрес регистрации</td><td>{userData.addressRegistration}</td></tr>
                <tr><td>Паспорт</td><td>{userData.passportSerial} {userData.passportNumber}</td></tr>
                <tr><td>Кем выдан</td><td>{userData.issuedByWhom}</td></tr>
                <tr><td>Дата выдачи</td><td>{userData.issueDate}</td></tr>
                <tr><td>ИНН</td><td>{userData.inn}</td></tr>
                <tr><td>СНИЛС</td><td>{userData.snils}</td></tr>
                <tr><td>Номер телефона</td><td>{userData.contactNumber}</td></tr>
                <tr><td>Форма обучения</td><td>{userData.studyType}</td></tr>
                <tr><td>Гос. обеспечение</td><td>{userData.fullStateSupport ? "Да" : "Нет"}</td></tr>
                <tr><td>Инвалидность</td><td>{userData.disabilityStatus || "Нет"}</td></tr>
                </tbody>
            </table>

            <button
                className={style.editButton}
                onClick={() => navigate("/profile/edit")}
            >
                Редактировать
            </button>
        </main>
    );
};
