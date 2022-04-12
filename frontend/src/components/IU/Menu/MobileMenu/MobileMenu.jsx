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
                <Link to="/auth" onClick={close} className={classes.startLink}>Меню</Link>
                <Link to="/auth" onClick={close} className={classes.link}>Личный кабинет</Link>
                <Link to="/games" onClick={close} className={classes.link}>Игры / Тренажёры</Link>
                {/*<Link to="/auth" onClick={close} className={classes.link}>Ретинг</Link>*/}
                {/*<Link to="/auth" onClick={close} className={classes.endLink}>О проекте</Link>*/}
                <svg className={classes.svg} width="225" height="215" viewBox="0 0 910 863" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <ellipse rx="380.12" ry="227.757" transform="matrix(-0.646484 0.762927 -0.76403 -0.645181 313.894 539.395)" fill="#0B40FF" fillOpacity="0.38"/>
                    <ellipse rx="380.059" ry="227.794" transform="matrix(-0.270084 -0.962837 0.963238 -0.268651 47.7946 558.09)" fill="#52FF59" fillOpacity="0.6"/>
                    <ellipse rx="380.539" ry="227.506" transform="matrix(-0.966706 0.255888 -0.257294 -0.966333 360.23 806.37)" fill="#E7FF52" fillOpacity="0.47"/>
                </svg>
            </div>
        </div>
    );
};

export default MobileMenu;