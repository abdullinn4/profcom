import {createContext, ReactNode, useContext, useEffect, useState} from "react";

type AuthContextType = {
    isAuthenticated: boolean,
    setIsAuthenticated: (auth: boolean) => void;
};

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({children}: { children: ReactNode }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        fetch("/profcom/is-authenticated", {
            credentials: "include"
        })
            .then((res) => res.json())
            .then((data) => (setIsAuthenticated(data.authenticated)))
            .catch(() => setIsAuthenticated(false))
    }, []);

    return (
        <AuthContext.Provider value={{isAuthenticated, setIsAuthenticated}}>
            {children}
        </AuthContext.Provider>
    )
}

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) throw new Error("useAuth must be used within AuthProvider");
    return context;
}