import style from "./attached-documents.module.sass"
interface Props {
    documents: string[];
    onRemove: (doc: string) => void;
}
export const AttachedDocuments = ({documents, onRemove} : Props) => (
    <div className={style.attachedDocuments}>
            <div>
                <ul>
                    {documents.map((doc) => (
                        <li className={style.li_all_docs} key={doc}>
                            <span>{doc}</span>
                            <button
                                className={style.delete_button}
                                onClick={() => onRemove(doc)}
                            >
                                ✕
                            </button>
                        </li>
                    ))}
                </ul>
            </div>
    </div>
)