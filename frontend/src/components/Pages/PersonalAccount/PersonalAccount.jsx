import React from 'react';
import classes from "./PersonalAccount.module.css";
import PageTitle from "../../IU/Text/PageTitle/PageTitle";
import DesktopMenu from "../../IU/Menu/DesktopMenu/DesktopMenu";
import GamesList from "../../IU/List/GamesList/GamesList";



const PersonalAccount = () => {


    return (
        <div className={classes.gamesPage}>

            <PageTitle text="Личный кабинет"/>

            <div className={classes.content}>
                <DesktopMenu/>
                <div className={classes.gameList}>
                    <PageTitle text="В разработке"/>

                </div>
            </div>

        </div>
    );
};

export default PersonalAccount;