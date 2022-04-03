import React from 'react';
import classes from "./Logo.module.css";

const Logo = () => {
    return (
        <div className={classes.logo}>
            <img src="http://krutna.ddns.net/static/logo.png" alt="Ошибка загрузки"/>
        </div>
    );
};

export default Logo;