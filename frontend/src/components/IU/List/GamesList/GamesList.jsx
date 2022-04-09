import React, {useEffect, useMemo, useState} from 'react';
import classes from "./GamesList.module.css";
import GameItem from "./GameItem/GameItem";
import {useNavigate} from "react-router-dom";
import Service from "../../../../Service/Service";
import img from "./../../../../img/iconDefault.png"


const GamesList = () => {

    const route = useNavigate();

    const [listGames, setListGames] = useState([]);
    const [listGames2, setListGames2] = useState([]);

    const getAllGames = async () => {
        const games = await Service.getAllGames();
        console.log(games.games);
        if (games)
            setListGames(games.games);
        else
            alert("Ошибка сервера!");
    }

    useEffect(() => {
        getAllGames();
        addingArray();
    }, [])

    const addingArray = () => {
        if (listGames.length < 12) {
            let count = 10 - listGames.length;
            let gemesArr = [];
            for (let i = 0; i < count; i++) {
                gemesArr.push({
                    id: Date.now() + i * 2,
                    icon: img,
                    name: "В разработке"
                });
            }
            setListGames2(gemesArr);
        }
    }


    return (
        <div className={classes.gamesList}>
            {listGames.map(game =>
                <div className={classes.gamesItem} onClick={() => route("/games/" + game.id)} key={game.id}>
                    <GameItem img={game.icon} title={game.name}/>
                </div>
            )}
            {listGames2.map(game =>
                <div className={classes.gamesItem} key={game.id}>
                    <GameItem img={game.icon} title={game.name}/>
                </div>
            )}
        </div>
    );
};

export default GamesList;