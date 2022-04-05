import React, {useState} from 'react';
import classes from "./RegForm.module.css";
import InputOne from "../../IU/Inputs/InputOne/InputOne";
import api from "../../../API/Api.js";

const RegForm = () => {

    const [form, setForm] = useState(true);

    const [error, setError] = useState({name:false, password:false, notFilled:false,});
    const [error2, setError2] = useState({name:false, password:false});

    const [name, setName] = useState("");

    const [password, setPassword] = useState("");
    const [password2, setPassword2] = useState("");

    const isForm = () =>{
        form? setForm(false): setForm(true)
    }

    async function reg(e) {
        e.preventDefault();

        if(!name || !password || !password2){
            setError({...error, notFilled:true})
            return
        }

        if(password != password2){
            setError({...error, password:true})
            return
        }

        // await api.reg(name,password);
    }


    return (
        form?
        <form className={classes.regForm}>
            <div className={classes.link} onClick={isForm}>
                Войти
            </div>
            <div className={classes.titel} >
                Регистрация
            </div>
            <div className={classes.margen}><InputOne value={name} onChange={(event) => setName(event.target.value)} placeholder="Имя (никнейм)"/></div>

            <InputOne value={password} type="password" onChange={(event) => setPassword(event.target.value)}  placeholder="Пароль"/>
            <div className={classes.razdelitel}></div>
            <InputOne value={password2} type="password" onChange={(event) => setPassword2(event.target.value)}  placeholder="Повторите пароль"/>


            <div className={classes.centr}>
                <button className={classes.button} onClick={(e)=>reg(e)}>
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
            <div className={classes.margen}>
                <InputOne placeholder="Имя (никнейм)"/>
                <div className={error.name?classes.error:classes.noError}>Данный ник уже занят!</div>
            </div>

            <InputOne placeholder="Пароль"/>
            <div className={classes.razdelitel}></div>
            <InputOne disabled style={{opacity:0}} placeholder="Повторите пароль"/>
            <div className={error.password?classes.error:classes.noError}>Пороли не совпадают!</div>
            <div className={error.notFilled?classes.error:classes.noError}>Не все поля заполнены!</div>

            <div className={classes.centr}>
                <button className={classes.button} >
                    Отправить
                </button>
            </div>
        </form>

    );
};

export default RegForm;