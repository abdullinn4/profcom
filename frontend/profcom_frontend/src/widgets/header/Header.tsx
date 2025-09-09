import {HeaderUser} from "./HeaderUser.tsx";
import {HeaderGuest} from "./HeaderGuest.tsx";
import {useAuth} from "../../app/AuthContext.tsx";

export const Header = () => {
    const {isAuthenticated} = useAuth()

    return isAuthenticated ? <HeaderUser/> : <HeaderGuest/>;
}