import {UserProfile} from "../entities";

export async function fetchUserProfile(): Promise<UserProfile> {
    const response = await fetch('/profcom/profile', {
        method: 'GET',
        credentials: 'include'
    });

    if (!response.ok) {
        throw new Error('Ошибка при получении профиля');
    }
    return response.json();
}

export const handleLogout = async (navigate: (path: string) => void,
                                   setIsAuthenticated: (auth: boolean) => void) => {

    try {
        await fetch("/profcom/logout", {
            method: "POST",
            credentials: "include"
        });
        setIsAuthenticated(false);
        navigate("/home")
    } catch (error) {
        console.error("Ошибка при выходе:", error);
    }
};

export const updateUserExtraInfo = async (data : Partial<UserProfile>) => {
    const response = await fetch("/profcom/profile/update", {
        method: "PUT",
        credentials: "include",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        throw new Error("Ошибка при обновлении профиля");
    }
    return response.json();
}