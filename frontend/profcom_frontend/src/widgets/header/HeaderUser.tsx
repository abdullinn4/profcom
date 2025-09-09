import style from "./header.module.sass";
import kfu_icon from "../../assets/icons/kfu.png";
import {Dropdown} from "./Dropdown.tsx";
import {useState} from "react";
import {handleLogout} from "../../service";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../../app/AuthContext.tsx";

export const HeaderUser = () => {
    const [menuOpen, setMenuOpen] = useState(false);
    const navigate = useNavigate();
    const {setIsAuthenticated} = useAuth()
    const toggleMenu = () => {
        setMenuOpen(!menuOpen)
    }

    return (
        <header>
            <div className={style.header_wrapper}>
                <div className={style.header_logo}>
                    <img src={kfu_icon} alt="Иконка КФУ" className={style.header_icon}/>
                    <p>Профком</p>
                </div>
                <button className={`${style.hamburger} ${menuOpen? style.open : ""}`}
                    onClick={toggleMenu}
                >
                    <span></span>
                    <span></span>
                    <span></span>
                </button>

                <nav className={`${style.header_nav} ${menuOpen? style.active : ""}`}>
                    <ul className={style.header_nav_li}>
                        <li>
                            <a href="/home">Главная</a>
                        </li>
                        <li>
                            <Dropdown/>
                        </li>
                        <li className={style.mobile_only}>
                            <a href="/profile">Профиль</a>
                        </li>
                        <li className={style.mobile_only}>
                            <a href="/profcom/logout">Выход</a>
                        </li>
                    </ul>
                </nav>
                <div className={style.header_login}>
                    <li>
                        <a href="/profile">Профиль</a>
                    </li>
                    <a onClick={() => handleLogout(navigate,setIsAuthenticated)} style={{cursor: "pointer"}}>Выход</a>
                </div>
            </div>
        </header>
    )
}