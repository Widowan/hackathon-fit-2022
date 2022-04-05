import React from 'react';
import classes from "./GamesPage.module.css";
import PageTitle from "../../IU/Text/PageTitle/PageTitle";
import DesktopMenu from "../../IU/Menu/DesktopMenu/DesktopMenu";
import GamesList from "../../IU/List/GamesList/GamesList";

const GamesPage = () => {
    return (
        <div className={classes.gamesPage}>

            <PageTitle text="Игры / Тренажоры"/>

            <div className={classes.content}>
                <DesktopMenu/>
                <div className={classes.gameList}>
                    <GamesList/>
                </div>
            </div>

        </div>
    );
};

export default GamesPage;