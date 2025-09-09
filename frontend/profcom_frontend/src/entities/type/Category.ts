export type Category = {
    id: number;
    fullName: string;      // длинное название категории
    shortName: string;     // короткое название
    required: string[];    // обязательные документы
    optional: string[];    // документы на выбор
};
