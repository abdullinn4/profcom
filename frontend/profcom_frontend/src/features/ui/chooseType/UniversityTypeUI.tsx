import style from "./choose-type.module.sass"
import {ChangeEvent, FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";
export const UniversityTypeUI = () => {
    const [selectedOption, setSelectedOption] = useState("");
    const navigate = useNavigate();

    const handleOptionChange = (e: ChangeEvent<HTMLInputElement>) => {
        setSelectedOption(e.target.value);
    }
    const handleSubmit = (e : FormEvent) => {
        e.preventDefault()
        if (selectedOption){
            const typeId = selectedOption.split(" ")[0];
            const numericId = typeId.split(".").pop();
            navigate(`/generator_form/university_aid/${numericId}`);
        }else{
            alert("Пожалуйста, выберите один из вариантов.");
        }
    };

    const options = [
        "2.2.1 Единовременная выплата на усиленное питание",
        "2.2.2 Единовременная выплата на проезд в общественном городском транспорте",
        "2.2.3 Единовременная выплата для приобретения учебной литературы",
        "2.2.4 Единовременная выплата в связи с затратами на поездку домой в каникулярное время",
        "2.2.5 Единовременная выплата для приобретения лекарственных средств",
        "2.2.6 Предоставление талонов на социальное питание",
        "2.2.7 Единовременная материальная поддержка",
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