import style from "./home.module.sass"
import {Link} from "react-router-dom";
export const HomeUI = () => {
    return(
        <main>
            <div className={style.home_container}>
                <h1>Добро пожаловать!</h1>
                <p>Вы находитесь на главной странице профкома, предоставляющего возможность автозаполнения заявлений на различные выплаты.</p>
                <p>Выберите ниже, на какой вид помощи вы хотите подать заявления</p>

                <div className={style.choose_button_container}>
                    <div className={style.type_of_help_container}>
                        <h3>Материальная помощь от университета</h3>
                        <p>Только для студентов бюджетной формы обучения</p>
                        <Link to="/choose-type/university" className={style.choose_button}>Выбрать</Link>

                    </div>
                    <div className={style.type_of_help_container}>
                        <h3>Материальная помощь от профкома</h3>
                        <p>Для всех членов Профсоюза</p>
                        <Link to="/choose-type/profcom" className={style.choose_button}>Выбрать</Link>
                    </div>

                </div>

            </div>
        </main>
    )
}