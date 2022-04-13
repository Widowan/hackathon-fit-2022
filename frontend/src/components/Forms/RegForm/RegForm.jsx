import React, {useContext, useState} from 'react';
import classes from "./RegForm.module.css";
import InputOne from "../../IU/Inputs/InputOne/InputOne";
import api from "../../../API/Api.js";
import {useHistory, useNavigate} from "react-router-dom";
import {AuthContext} from "../../../context";
import Service from "../../../Service/Service";


const RegForm = () => {

    const {setToken, setUser} = useContext(AuthContext);

    const nave = useNavigate()

    const [form, setForm] = useState(true);

    const [error, setError] = useState({name: false, password: false, notFilled: false});
    const [error2, setError2] = useState({error: false, notFilled: false});

    const [name, setName] = useState("");

    const [password, setPassword] = useState("");
    const [password2, setPassword2] = useState("");

    const isForm = () => {
        form ? setForm(false) : setForm(true)
    }

    async function getMe(){
        setToken(localStorage.getItem('token'))
        const user = await Service.getMe();
        setUser({
            id:user.user.id,
            img:user.user.img,
            name:user.user.username,
        })
    }

    async function reg(e) {
        e.preventDefault();

            let isError = error;

        if (name == "" || password == "" || password2 == "") {
            isError.notFilled = true;
        } else
            isError.notFilled = false;


        if (password != password2) {
            isError.password = true;
        } else
            isError.password = false;

        setError({...error,  password:isError.password, notFilled:isError.notFilled,})

        if (error.password || error.notFilled) return;

        try {
            const response = await api.reg(name,password);
            console.log(response)
            localStorage.setItem('token',response.data.token)
            getMe();
        } catch (response){
            console.log(response)
            setError({name:true, password:false, notFilled:false,});
        }

        return


    }

    async function auth(e){
        e.preventDefault();

        if (name == "" || password == "") {
            setError2({...error2, notFilled:true});
        } else
            setError2({...error2, notFilled:false});

       const response = await Service.auth(name, password);

       if(response) getMe();
       else
           setError2({error: true, notFilled: false});

       return

    }


    return (
        form ?
            <form className={classes.regForm}>
                <div className={classes.link} onClick={isForm}>
                    Войти
                </div>
                <div className={classes.titel}>
                    Регистрация
                </div>
                <div className={classes.margen}>
                    <InputOne value={name} onChange={(event) => setName(event.target.value)}
                              placeholder="Имя (никнейм)"/>
                    <div className={error.name ? classes.error : classes.noError}>Данный ник уже занят!</div>
                </div>


                <InputOne value={password} type="password" onChange={(event) => setPassword(event.target.value)}
                          placeholder="Пароль"/>
                <div className={classes.razdelitel}></div>
                <InputOne value={password2} type="password" onChange={(event) => setPassword2(event.target.value)}
                          placeholder="Повторите пароль"/>
                <div className={error.password ? classes.error : classes.noError}>Пароли не совпадают!</div>
                <div className={error.notFilled ? classes.error : classes.noError}>Не все поля заполнены!</div>


                <div className={classes.centr}>
                    <button className={classes.button} onClick={e => reg(e)}>
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
                    <InputOne value={name} onChange={(event) => setName(event.target.value)}
                              placeholder="Имя (никнейм)"/>
                </div>

                <InputOne value={password} type="password" onChange={(event) => setPassword(event.target.value)}
                          type="password" placeholder="Пароль"/>

                <div className={error2.error ? classes.error : classes.noError}>Неправильное имя или пароль!</div>
                <div className={error2.notFilled ? classes.error : classes.noError}>Не все поля заполнены!</div>
                <div className={classes.razdelitel}></div>
                <InputOne disabled style={{opacity: 0}} placeholder="Повторите пароль"/>

                <div className={classes.centr}>
                    <button className={classes.button} onClick={auth}>
                        Отправить
                    </button>
                </div>
            </form>

    );
};

export default RegForm;