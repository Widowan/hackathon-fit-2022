import React, {useEffect, useMemo, useState} from 'react';
import classes from "./GamesList.module.css";
import GameItem from "./GameItem/GameItem";
import {useNavigate} from "react-router-dom";
import Service from "../../../../Service/Service";


const GamesList = () => {

    const route = useNavigate();

    const [listGames, setListGames] = useState([]);

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
    }, [])

    const addingArray = () => {
        if (listGames.length < 12) {
            for (let i = 0; i < 12 - listGames.length; i++) {
                setListGames([...listGames, {
                    id: Date.now() + i * 2,
                    icon: "http://krutna.ddns.net/static/gameIcon1.svg",
                    name: "В разработке"
                }])
            }
        }
    }

    useMemo(() => {
        addingArray();
    })


    return (
        <div className={classes.gamesList}>
            {listGames.map(game =>
                <div className={classes.gamesItem} onClick={() => route("/games/" + game.id)} key={game.id}>
                    <GameItem img={game.icon} title={game.name}/>
                </div>
            )}
        </div>
    );
};

export default GamesList;