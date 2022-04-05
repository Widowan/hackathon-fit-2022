import React, {useState} from 'react';
import classes from './Header.module.css'
import CircleAvatar from "../../IU/Avatars/CircleAvatar/CircleAvatar";
import Logo from "../../IU/Logo/Logo";
import MobileMenu from "../../IU/Menu/MobileMenu/MobileMenu";

const Header = () => {
    const [isAuth, setIsAuth] = useState(false);

    return (
        <div className={classes.header}>
            <div className={classes.menu}>
                <MobileMenu/>
                <Logo/>
            </div>

            <div className={isAuth? classes.info: classes.infoBlur}>
                <div>Никнейм</div>
                <CircleAvatar src="http://krutna.ddns.net/static/default.svg"></CircleAvatar>
            </div>
        </div>
    );
};

export default Header;