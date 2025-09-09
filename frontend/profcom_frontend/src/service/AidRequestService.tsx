import {ProfUnionAidFormData, UniversityAidFormData} from "../entities";

const API_URL = "/profcom/aid-request";

export const submitAidUniRequest = async (formData: UniversityAidFormData): Promise<UniversityAidFormData> => {
    try {
        const response = await fetch(`${API_URL}/university/${formData.id}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(formData)
        });

        const responseData = await response.json();

        if (!response.ok) {
            throw new Error(responseData.message || "Ошибка при отправки формы");
        }
        return responseData;

    } catch (error) {
        console.error("Ошибка при отправке запроса:", error);
        throw error;
    }
}
export const submitAidProfRequest = async (formData: ProfUnionAidFormData): Promise<ProfUnionAidFormData> => {
    try {
        const response = await fetch(`${API_URL}/prof-union/${formData.id}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(formData)
        });

        const responseData = await response.json();

        if (!response.ok) {
            throw new Error(responseData.message || "Ошибка при отправки формы");
        }
        return responseData;

    } catch (error) {
        console.error("Ошибка при отправке запроса:", error);
        throw error;
    }
}