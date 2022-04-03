import React from 'react';
import classes from "./InputOne.module.css";

const InputOne = (props) => {
    return (
        <input className={classes.inputOne} {...props} />
    );
};

export default InputOne;