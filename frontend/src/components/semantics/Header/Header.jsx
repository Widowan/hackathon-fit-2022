import React from 'react';
import classes from './Header.module.css'
import CircleAvatar from "../../IU/Avatars/CircleAvatar/CircleAvatar";
import Logo from "../../IU/Logo/Logo";
import MobileMenu from "../../IU/MobileMenu/MobileMenu";

const Header = () => {
    return (
        <div className={classes.header}>
            <div className={classes.info}>
                <MobileMenu/>
                <Logo/>
            </div>

            <div className={classes.info}>
                <div>Никнейм</div>
                <CircleAvatar src="http://krutna.ddns.net/static/default.svg"></CircleAvatar>
            </div>
        </div>
    );
};

export default Header;