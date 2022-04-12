import React, {useContext, useState} from 'react';
import classes from './Header.module.css'
import CircleAvatar from "../../IU/Avatars/CircleAvatar/CircleAvatar";
import Logo from "../../IU/Logo/Logo";
import MobileMenu from "../../IU/Menu/MobileMenu/MobileMenu";
import {AuthContext} from "../../../context";
import exitIcon from "./../../../img/exit.svg"
import Service from "../../../Service/Service";

const Header = () => {



    const {token, user, setToken, setUser} = useContext(AuthContext);

    async function exit(){
        if(await Service.exit()){
            setToken('');
            setUser({
                id: "",
                img: "",
                name: ""
            })
        }
        else alert("Ошибка сервера!")
    }

    return (
        <div className={classes.header}>
            <div className={classes.menu}>
                <MobileMenu/>
                <Logo/>
            </div>

            <div className={token? classes.info: classes.infoBlur}>
                <div>{user.name}</div>
                <CircleAvatar src="http://krutna.ddns.net/static/default.svg"></CircleAvatar>
                <div onClick={exit}><CircleAvatar src={exitIcon}></CircleAvatar></div>

            </div>
        </div>
    );
};

export default Header;