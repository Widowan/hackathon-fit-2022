import React from 'react';
import classes from "./DesktopMenu.module.css";
import {Link} from "react-router-dom";

const DesktopMenu = () => {
    return (
        <div className={classes.desktopMenu}>
            <div to="/auth" className={classes.startLink}>Меню</div>
            <Link to="/office" className={classes.link}>Личный кабинет</Link>
            <Link to="/games" className={classes.link}>Игры / Тренажёры</Link>
            {/*<Link to="/auth" className={classes.link}>Ретинг</Link>*/}
            {/*<Link to="/auth" className={classes.endLink}>О проекте</Link>*/}
            <div className={classes.svg}>
                <svg width="225" height="215" viewBox="0 0 910 863" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <ellipse rx="380.12" ry="227.757" transform="matrix(-0.646484 0.762927 -0.76403 -0.645181 313.894 539.395)" fill="#0B40FF" fillOpacity="0.38"/>
                    <ellipse rx="380.059" ry="227.794" transform="matrix(-0.270084 -0.962837 0.963238 -0.268651 47.7946 558.09)" fill="#52FF59" fillOpacity="0.6"/>
                    <ellipse rx="380.539" ry="227.506" transform="matrix(-0.966706 0.255888 -0.257294 -0.966333 360.23 806.37)" fill="#E7FF52" fillOpacity="0.47"/>
                </svg>
            </div>

        </div>
    );
};

export default DesktopMenu;