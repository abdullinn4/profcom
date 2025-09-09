import style from '../budgetCategories/budget_categories.module.sass';
import { PROFCOM_CATEGORIES } from "../../data/"

export const ProfcomCategoriesPage = () => {
    return (
        <div className={style.container}>
            <header className={style.header}>
                <h1 className={style.title}>Члены профсоюза</h1>
            </header>

            <div className={style.tableWrapper}>
                <table className={style.categoriesTable}>
                    <thead>
                    <tr>
                        <th className={style.categoryColumn}>Категория</th>
                        <th className={style.documentsColumn}>Подтверждающие документы</th>
                    </tr>
                    </thead>
                    <tbody>
                    {PROFCOM_CATEGORIES.map((category) => (
                        <tr key={category.id} className={style.tableRow}>
                            <td>{category.name}</td>
                            <td>{category.documents}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};