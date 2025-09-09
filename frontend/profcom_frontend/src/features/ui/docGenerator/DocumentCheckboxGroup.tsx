import {Category} from "../../../entities";
import style from "./document-checkbox-group.module.sass"

interface Props {
    category: Category;
    selected: string[];
    onSelect: (doc: string) => void; // в параметрах убрал category.id, отсавил только doc - мб его тоже надо использовать
}
export const DocumentCheckboxGroup = ({category, selected, onSelect} : Props) => (
    <div className={style.checkboxGroup}>
        {category.optional.map((doc) => (
            <div className={style.docRow} key={doc}>
                <span>{doc}</span>
                <button
                    disabled={selected.includes(doc)}
                    onClick={() => onSelect(doc)}
                    className={style.selectButton}
                >
                    {selected.includes(doc) ? "Выбрано" : "Выбрать"}
                </button>
            </div>
        ))}
    </div>
)
