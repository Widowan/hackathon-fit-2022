import React from 'react';
import classes from "./GameItem.module.css";

const GameItem = (props) => {
    return (
        <div className={classes.gameItem}>
            <img src={props.img} alt="Нет изображения"/>
            <div className={classes.title}>{props.title}</div>
        </div>
    );
};

export default GameItem;