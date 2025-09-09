import {ChangeEvent, FormEvent, useState} from "react";
import style from "./choose-type.module.sass"
import {useNavigate} from "react-router-dom";

export const ProfcomTypeUI =() => {
    const [selectedOption, setSelectedOption] = useState("")
    const navigate = useNavigate();

    const handleOptionChange = (e: ChangeEvent<HTMLInputElement>) => {
        setSelectedOption(e.target.value)
    }

    const handleSubmit = (e: FormEvent) => {
        e.preventDefault()
        if (selectedOption){
            const numericId = selectedOption.split(".")[0];
            navigate(`/generator_form/prof-union_aid/${numericId}`)
        }else{
            alert("Пожалуйста, выберите один из вариантов.");
        }
    };

    const options = [
        "1. Материальная помощь",
        "2. Электронный кошелёк (общественный транспорт)",
        "3. Социальное питание",
    ];

    return (
        <main>
            <div className={style.choose_container}>
                <h1>Выберите тип помощи</h1>
                <form onSubmit={handleSubmit} className={style.choose_type_form}>
                    {options.map((option, index) => (
                        <label key={index}>
                            <input
                                type="radio"
                                name="helpType"
                                value={option}
                                checked={selectedOption === option}
                                onChange={handleOptionChange}
                            />
                            {option}
                        </label>
                        ))}
                    <button type="submit" className={style.choose_button}>
                        Выбрать
                    </button>
                </form>
            </div>
        </main>
    )
}