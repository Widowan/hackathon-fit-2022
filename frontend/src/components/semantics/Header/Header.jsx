import React from 'react';
import classes from './Header.module.css'
import CircleAvatar from "../../IU/Avatars/CircleAvatar/CircleAvatar";
import Logo from "../../IU/Logo/Logo";

const Header = () => {
    return (
        <div className={classes.header}>
            <Logo/>
            <div className={classes.info}>
                <div>Никнейм</div>
                <CircleAvatar src="http://krutna.ddns.net/static/default.svg"></CircleAvatar>
            </div>
        </div>
    );
};

export default Header;