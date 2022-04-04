import React, {useState} from 'react';
import classes from "./MobileMenu.module.css";
import {Link} from "react-router-dom";

const MobileMenu = () => {

    const [menu, setMenu] = useState(false);

    const close = () =>{
        setMenu(!menu);
    }

    return (
        <div className={classes.mobileMenu} >

            {!menu?
            <svg onClick={close} className={classes.button} width="22" height="20" viewBox="0 0 22 20" fill="none"
                 xmlns="http://www.w3.org/2000/svg">
                <rect width="22" height="4" rx="2" fill="#C4C4C4"/>
                <rect y="8" width="22" height="4" rx="2" fill="#C4C4C4"/>
                <rect y="16" width="22" height="4" rx="2" fill="#C4C4C4"/>
            </svg>
                :
            <svg onClick={close} className={classes.buttonClose} width="22" height="20" viewBox="0 0 22 20" fill="none"
                 xmlns="http://www.w3.org/2000/svg">
                <rect width="24.5866" height="4.48058" rx="2"
                      transform="matrix(-0.744576 0.667538 -0.756474 -0.654024 22 2.93036)" fill="#C4C4C4"/>
                <rect width="24.6462" height="4.46973" rx="2"
                      transform="matrix(0.757107 0.653291 -0.743926 0.668262 3.3252 0.911865)" fill="#C4C4C4"/>
            </svg>}


            <div className={!menu ? classes.dropdownContent : classes.dropdownContentClose}>
                <Link to="/auth" className={classes.startLink}>Меню</Link>
                <Link to="/auth" className={classes.link}>Личный кабинет</Link>
                <Link to="/auth" className={classes.link}>Игры / Тренажоры</Link>
                <Link to="/auth" className={classes.link}>Ретинг</Link>
                <Link to="/auth" className={classes.endLink}>О проекте</Link>
            </div>
        </div>
    );
};

export default MobileMenu;