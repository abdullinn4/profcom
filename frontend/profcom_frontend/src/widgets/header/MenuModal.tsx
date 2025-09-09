import {MouseEventHandler} from "react";
import style from "./header.module.sass"
import {Link} from "react-router-dom";

export const MenuModal = ({onClose}:{onClose: MouseEventHandler<HTMLLIElement>}) => {
    return(
        <div className={style.header_dropdown}>
            <ul className={style.menu_modal}>
                <li onClick={onClose}>
                    <Link to="/categories/budget">Университетская помощь</Link>
                </li>
                <li onClick={onClose}>
                    <Link to="/categories/profcom">Профсоюзная помощь</Link>
                </li>
            </ul>
        </div>
    )
}