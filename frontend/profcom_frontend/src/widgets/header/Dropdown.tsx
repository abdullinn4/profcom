import {useState} from "react";
import {MenuModal} from "./MenuModal.tsx";
import style from "./header.module.sass";
import {AiOutlineDown} from "react-icons/ai";

export const Dropdown = () => {
    const [showMenu, setShowMenu] = useState(false);

    const toggleMenu = () => {
        setShowMenu(!showMenu);
    }
    const handleMenuToggle = () => {
        setShowMenu(!showMenu);
    }

    return(
        <>
            <p
                className={style.header_dropdown}
                onMouseDown={handleMenuToggle}
            >
                Категории нуждаемости
                <AiOutlineDown className={showMenu ? style.icon_active : style.icon} />
            </p>
            {showMenu && <MenuModal onClose={toggleMenu}/>}
        </>
    )
}