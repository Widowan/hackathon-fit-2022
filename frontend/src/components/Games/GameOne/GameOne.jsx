import React, {useState} from 'react';
import classes from "./GameOne.module.css";
import Stick from "./components/stick/stick";
import Level from "./components/level/level";
import Do from "./audio/notes/do.mp3"
import Re from "./audio/notes/re.mp3"
import Mi from "./audio/notes/mi.mp3"
import Fa from "./audio/notes/fa.mp3"
import Sol from "./audio/notes/sol.mp3"
import Lya from "./audio/notes/lya.mp3"
import Si from "./audio/notes/si.mp3"

const GameOne = () => {

    const [audio, setAudio]=useState({
        Do: new Audio(Do),
        Re: new Audio(Re),
        Mi: new Audio(Mi),
        Fa: new Audio(Fa),
        Sol: new Audio(Sol),
        Lya: new Audio(Lya),
        Si: new Audio(Si),
    })

    const audioPlay = (note) =>{
        audio[note].play();
    }

    return (
        <div className={classes.gameOne}>
            <div className={classes.game}>

                <div className={classes.stick} onClick={() =>audioPlay("Do")}><Stick number="6" wh="2.75" color="#F74253"/></div>
                <div className={classes.stick} onClick={() =>audioPlay("Re")}><Stick number="4" wh="1.75" color="#F6965C"/></div>
                <div className={classes.stick} onClick={() =>audioPlay("Mi")}><Stick number="2" wh="1.25" color="#F5C556"/></div>
                <div className={classes.stick} onClick={() =>audioPlay("Fa")}><Stick number="1" wh="1" color="#80BB67"/></div>
                <div className={classes.stick} onClick={() =>audioPlay("Sol")}><Stick number="3" wh="1.25" color="#6385B5"/></div>
                <div className={classes.stick} onClick={() =>audioPlay("Lya")}><Stick number="5" wh="1.75" color="#6C5BCE"/></div>
                <div className={classes.stick} onClick={() =>audioPlay("Si")}><Stick number="7" wh="2.75" color="#A0429A"/></div>
            </div>
            <div className={classes.menu}>
                <div className={classes.button}>
                    <input type="button"/>
                </div>

                <Level/>

            </div>
        </div>
    );
};

export default GameOne;