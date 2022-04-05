import React, {useMemo, useState} from 'react';
import classes from "./GamesList.module.css";
import GameItem from "./GameItem/GameItem";
import {useNavigate} from "react-router-dom";


const GamesList = () => {

    const route = useNavigate();

    const [listGames, setListGames] = useState([
        {
            id: 1,
            img: "https://st2.depositphotos.com/5266903/8766/v/450/depositphotos_87660356-stock-illustration-healh-care-emblem-icon.jpg",title: "МинЗдрав"
        },
        {
            id: 2,
            img: "https://st2.depositphotos.com/5266903/8766/v/450/depositphotos_87660356-stock-illustration-healh-care-emblem-icon.jpg",
            title: "МинЗдрав2"
        },
        {
            id: 3,
            img: "https://st2.depositphotos.com/5266903/8766/v/450/depositphotos_87660356-stock-illustration-healh-care-emblem-icon.jpg",
            title: "МинЗдрав3"
        },
        {
            id: 4,
            img: "https://st2.depositphotos.com/5266903/8766/v/450/depositphotos_87660356-stock-illustration-healh-care-emblem-icon.jpg",
            title: "МинЗдрав3"
        },
        {
            id: 5,
            img: "https://st2.depositphotos.com/5266903/8766/v/450/depositphotos_87660356-stock-illustration-healh-care-emblem-icon.jpg",
            title: "МинЗдрав3"
        },
        {
            id: 6,
            img: "https://st2.depositphotos.com/5266903/8766/v/450/depositphotos_87660356-stock-illustration-healh-care-emblem-icon.jpg",
            title: "МинЗдрав3"
        },

    ]);

    const addingArray = () => {
        if (listGames.length < 12) {
            for (let i = 0; i < 12 - listGames.length; i++) {
                setListGames([...listGames, {
                    id: Date.now() + i*2,
                    img: "https://st2.depositphotos.com/5266903/8766/v/450/depositphotos_87660356-stock-illustration-healh-care-emblem-icon.jpg",
                    title: "МинЗдрав3"
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
                <div className={classes.gamesItem} onClick={()=> route("/games/"+game.id)} key={game.id}>
                    <GameItem img={game.img} title={game.title} />
                </div>
            )}
        </div>
    );
};

export default GamesList;