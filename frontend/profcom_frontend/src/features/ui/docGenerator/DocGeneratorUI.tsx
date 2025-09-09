import {CategoryPicker} from "./CategoryPicker.tsx";
import {useEffect, useState} from "react";
import {Category} from "../../../entities";
import {generateDocument, getCategories} from "../../../service";
import {DocumentCheckboxGroup} from "./DocumentCheckboxGroup.tsx";
import {AttachedDocuments} from "./AttachedDocuments.tsx";
import style from "./doc_generator.module.sass"
import {mockCategories} from "../../../service/DocumentService.tsx";

interface DocGeneratorUIProps {
    type: "university" | "profcom";
    id?: string;
}

export const DocGeneratorUI = ({ type, id }: DocGeneratorUIProps) => {
    const [categories, setCategories] = useState<Category[]>([]);
    const [selectedCategories, setSelectedCategories] = useState<Category[]>([])
    const [selectedDocuments, setSelectedDocuments] = useState<string[]>([]);

    useEffect(() => {
       setCategories(mockCategories);
    }, [type]);

    const handleAddDocuments = ((doc: string) => {
        if (!selectedDocuments.includes(doc)) {
            setSelectedDocuments([...selectedDocuments, doc])
        }
    })

    const handleRemoveDocuments = ((doc: string) => {
        if (selectedDocuments.includes(doc)) {
            setSelectedDocuments(selectedDocuments.filter((d) => d !== doc));
        }
    })

    const handleDownloadAfterSubmit = async (format: "pdf" | "docx") => {
        if (selectedCategories.length === 0 || selectedDocuments.length === 0) {
            alert("Выберите категории и документы");
            return;
        }

        const payload = {
            categories: selectedCategories.map((cat) => cat.id),
            documents: selectedDocuments,
            format,
        };

        try {
            const blob = await generateDocument(payload);
            alert("Заявление успешно отправлено и сгенерировано!");

            const url = URL.createObjectURL(blob);
            const a = document.createElement("a");
            a.href = url;
            a.download = `document.${format}`;
            document.body.appendChild(a);
            a.click();
            URL.revokeObjectURL(url);
        } catch (error) {
            console.error("Ошибка:", error);
            alert("Ошибка при отправке или скачивании файла");
        }
    };


    return (
        <div className={style.docGenerator}>
            <h1>Генерация заявления</h1>
            <CategoryPicker categories={categories}
                            selected={selectedCategories}
                            onSelect={(cat) => setSelectedCategories([...selectedCategories, cat])}
                            onRemove={(id) => {
                                if (confirm("Удалить категорию и связанные документы?")) {
                                    const categoryToRemove = selectedCategories.find((c) => c.id === id);
                                    setSelectedCategories(selectedCategories.filter((c) => c.id !== id));

                                    if (categoryToRemove) {
                                        const docsToRemove = categoryToRemove.optional;
                                        setSelectedDocuments(selectedDocuments.filter((doc) => !docsToRemove.includes(doc)))

                                    }
                                }
                            }}

            />

            {selectedCategories.length > 0 && (
                <>
                    <h1>Приложение</h1>
                    <ul>
                        {type === "profcom" && (
                            <li className={style.li_all_docs}>
                                Копия продленного профсоюзного билета
                            </li>
                        )}

                        {selectedCategories.flatMap((cat) =>
                            cat.required.map((doc, i) =>
                                <li key={`${cat.id}-req-${i}`}
                                    className={style.li_all_docs}
                                >
                                    {doc}
                                </li>))}

                    </ul>
                    <AttachedDocuments
                        documents={selectedDocuments}
                        onRemove={handleRemoveDocuments}/>

                </>
            )}

            <h2>Выберите документы</h2>
            {selectedCategories.map((cat) => (
                <DocumentCheckboxGroup
                    key={cat.id}
                    category={cat}
                    selected={selectedDocuments}
                    onSelect={handleAddDocuments}
                />
            ))}


            <div className={style.buttonGroup}>
                <button
                    onClick={() => handleDownloadAfterSubmit("pdf")}
                    className={style.submit_button}
                >Скачать PDF
                </button>
                <button
                    onClick={() => handleDownloadAfterSubmit("docx")}
                    className={style.submit_button}
                >Скачать DOCX
                </button>
            </div>
        </div>
    )
}