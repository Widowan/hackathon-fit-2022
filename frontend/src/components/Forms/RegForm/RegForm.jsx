import React from 'react';
import classes from "./RegForm.module.css";
import InputOne from "../../IU/Inputs/InputOne/InputOne";

const RegForm = () => {
    return (
        <form className={classes.regForm}>
            <div className={classes.link}>
                Войти
            </div>
            <div className={classes.titel}>
                Регистрация
            </div>
            <div className={classes.margen}><InputOne placeholder="Имя (никнейм)"/></div>

            <InputOne placeholder="Пароль"/>
            <div className={classes.razdelitel}></div>
            <InputOne placeholder="Повторите пароль"/>


            <div className={classes.centr}>
                <button className={classes.button}>
                    Отправить
                </button>
            </div>
        </form>
    );
};

export default RegForm;