import React, {useState} from 'react';
import classes from "./RegForm.module.css";
import InputOne from "../../IU/Inputs/InputOne/InputOne";

const RegForm = () => {

    const [form, setForm] = useState(true);

    const isForm = () =>{
        form? setForm(false): setForm(true)
    }

    return (
        form?
        <form className={classes.regForm}>
            <div className={classes.link} onClick={isForm}>
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
            :
        <form className={classes.regForm}>
            <div className={classes.link} onClick={isForm}>
                Регистрация
            </div>
            <div className={classes.titel}>
                Войти
            </div>
            <div className={classes.margen}><InputOne placeholder="Имя (никнейм)"/></div>

            <InputOne placeholder="Пароль"/>
            <div className={classes.razdelitel}></div>
            <InputOne disabled style={{opacity:0}} placeholder="Повторите пароль"/>

            <div className={classes.centr}>
                <button className={classes.button} >
                    Отправить
                </button>
            </div>
        </form>

    );
};

export default RegForm;