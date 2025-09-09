import style from "./header.module.sass"
import kfu_icon from "../../assets/icons/kfu.png"
import {Link} from "react-router-dom";
import {Dropdown} from "./Dropdown.tsx";
import {useState} from "react";

export const HeaderGuest = () => {
    const [menuOpen, setMenuOpen] = useState(false);
    const toggleMenu = () => {
        setMenuOpen(!menuOpen)
    }

    return(
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
                <nav className={`${style.header_nav} ${menuOpen ? style.active : ""}`}>
                    <ul className={style.header_nav_li}>
                        <li>
                        <Link to="/home">Главная</Link>
                        </li>
                        <li>
                            <Dropdown/>
                        </li>
                        <li className={style.mobile_only}>
                            {/*<a href="/login">Вход</a>*/}
                             <Link to="/profcom/oauth2/authorization/uenv">Войти через UniEnv</Link>
                        </li>   
                    </ul>
                </nav>
                <div className={style.header_login}>
                <a href="/profcom/oauth2/authorization/uenv">Войти через UniEnv</a>
                </div>
            </div>
        </header>
    )
}