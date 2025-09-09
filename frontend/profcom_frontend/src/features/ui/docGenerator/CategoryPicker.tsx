import {Category} from "../../../entities";
import style from "./category-picker.module.sass"

interface Props{
    categories: Category[];
    selected: Category[];
    onSelect: (cat: Category) => void;
    onRemove: (id: number) => void
}
export const CategoryPicker = ({categories, selected, onSelect, onRemove} : Props) => (
    <div className={style.categoryPicker}>
        <h2>Выберите категорию:</h2>
        <select
            value=""
            onChange={(e) => {
                const selectedId = Number(e.target.value);
                const selectedCat = categories.find((cat) => cat.id === selectedId);
                if (selectedCat && !selected.some((s) => s.id === selectedCat.id)) {
                    onSelect(selectedCat)
                }
            }}
                >
            <option value="" disabled>Выберите категорию</option>

            {categories.map((cat) => (
                <option
                    key={cat.id}
                    value={cat.id}
                    disabled={selected.some((s) => s.id === cat.id)}
                >
                    {cat.shortName}
                </option>
            ))}

        </select>

        {selected.length > 0 && (
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>Категория</th>
                            <th>Документы</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {selected.map((cat) => (
                            <tr key={cat.id}>
                                <td>{cat.fullName}</td>
                                <td>
                                    <ul>
                                    {cat.required.map((doc, i) => (
                                        <li key={`req-${cat.id}-${i}`}>{doc}</li>
                                    ))}
                                        {cat.optional.map((doc, i) => (
                                            <li key={`req-${cat.id}-${i}`}>{doc}</li>
                                        ))}
                                    </ul>
                                </td>
                                <td>
                                    <button
                                        className={style.delete_button}
                                        onClick={() => onRemove(cat.id)}
                                    >
                                        ✕
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        )}

    </div>
);


