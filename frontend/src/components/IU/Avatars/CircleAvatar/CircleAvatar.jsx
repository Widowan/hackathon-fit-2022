import React from 'react';
import classes from "./CircleAvatar.module.css";

const CircleAvatar = (props) => {
    return (
        <div className={classes.avatar}>
            <img src={props.src} alt="Ошибка загрузки"/>
        </div>
    );
};

export default CircleAvatar;