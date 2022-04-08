import React, {useContext, useEffect, useState} from 'react';
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
import {BallsContext, GameOneContext} from "../../../context";
import {flushSync} from "react-dom";
import Service from "../../../Service/Service";

const GameOne = ({id}) => {


    const [cycle, setCycle] = useState(false)
    const [disabled, setDisabled] = useState(false)
    const [level, setLevel] = useState(1)
    const [noteArr, setNoteArr] = useState([])
    const [active, setActive] = useState(7)
    const [motion, setMotion] = useState(0)
    const {balls, setBalls} = useContext(BallsContext)



    useEffect(() => {
        setActive(active)
    }, [active])

    const [text, setText] = useState("Старт")

    const [audio, setAudio] = useState({
        Do: new Audio(Do),
        Re: new Audio(Re),
        Mi: new Audio(Mi),
        Fa: new Audio(Fa),
        Sol: new Audio(Sol),
        Lya: new Audio(Lya),
        Si: new Audio(Si),
    })

    const association = ["Do", "Re", "Mi", "Fa", "Sol", "Lya", "Si"];

    const [color, setColor] = useState("#78AEFF");

    const getRandomArr = (level=level) => {
        let arr = [];
        for (let i = 0; i < level; i++) {
            arr.push(Math.floor(Math.random() * 7));
        }

        return arr;
    }

    const systemAudioPlay = (note, id) => {
        flushSync(() => {
            setActive(id);
        });
        audio[note].play();
    }

    const audioPlay = (note) => {
        audio[note].play();
    }

    const playArr = (notes) => {
        flushSync(() => {
            setDisabled(true);
        });
        for (let i = 0; i < notes.length; i++) {
            setTimeout(() => systemAudioPlay(association[notes[i]], notes[i]), 700 * i);

        }
        setTimeout(() => {
            flushSync(() => {
                setActive(7);
                setDisabled(false);
            })
        }, 700 * notes.length);

    }

    const start =async (e) => {
        !cycle ? setText("Закончить") : setText("Старт");
        setCycle(!cycle);

        if (!cycle) {
            restart();
        }else{
            const response = await Service.addGameResult(id,balls);
            setBalls(0);
            if(response) alert("Ошибка на сервере, результат не сохранён!");
        }
    }

    const restart = (levels= level) => {
        const notes = getRandomArr(levels);
        setNoteArr(notes);
        playArr(notes);
    }

    const endGame = () =>{
        setLevel(level+1);
        setMotion(0);

        restart(level+1);
    }

    const programActiveStick = (daley, noteId ,colorActive = "#78AEFF")=>{
        flushSync(() => {
            setColor(colorActive);
            setActive(noteId);
        })
        setTimeout(() => {
            flushSync(() => {
                setActive(7);
                setColor("#78AEFF");
            })
        }, daley)
    }

    const pointPlay = (noteId) => {
        audioPlay(association[noteId])

        if (cycle){
            if (noteArr[motion]==noteId){

                setBalls(balls+level*2);
                setMotion(motion + 1);
                if(motion+1==level) {
                    programActiveStick(500, noteId, "#04DA00"   );
                    setTimeout(()=>{endGame()},1200 );
                }
                else programActiveStick(500, noteId);
            }
            else{
                programActiveStick(500, noteId, "#DA0000");

                let ball = balls-level*2;
                if(ball<10) ball = 10;
                setBalls(ball);

            }
        }

    }

    return (
        <GameOneContext.Provider value={{
            cycle,
            level,
            setLevel
        }}

        >
            <div className={classes.gameOne}>
                <div className={classes.game}>

                    <div className={!(active == 5) ? classes.stick : classes.ActiveStick}
                         onClick={() => pointPlay( 5)}><Stick number="6" wh="2.75"
                                                                color={(active == 5) ? color : "#F74253"}/></div>

                    <div className={!(active == 3) ? classes.stick : classes.ActiveStick}
                         onClick={() => pointPlay( 3)}><Stick number="4" wh="1.75"
                                                                color={(active == 3) ? color : "#F6965C"}/></div>

                    <div className={!(active == 1) ? classes.stick : classes.ActiveStick}
                         onClick={() => pointPlay( 1)}><Stick number="2" wh="1.25"
                                                                color={(active == 1) ? color : "#F5C556"}/></div>

                    <div className={!(active == 0) ? classes.stick : classes.ActiveStick}
                         onClick={() => pointPlay( 0)}><Stick number="1" wh="1"
                                                                color={(active == 0) ? color : "#80BB67"}/></div>

                    <div className={!(active == 2) ? classes.stick : classes.ActiveStick}
                         onClick={() => pointPlay( 2)}><Stick number="3" wh="1.25"
                                                                 color={(active == 2) ? color : "#6385B5"}/></div>

                    <div className={!(active == 4) ? classes.stick : classes.ActiveStick}
                         onClick={() => pointPlay( 4)}><Stick number="5" wh="1.75"
                                                                 color={(active == 4) ? color : "#6C5BCE"}/></div>

                    <div className={!(active == 6) ? classes.stick : classes.ActiveStick}
                         onClick={() => pointPlay( 6)}><Stick number="7" wh="2.75"
                                                                color={(active == 6) ? color : "#A0429A"}/></div>
                </div>
                <div className={classes.menu}>
                    <div className={classes.button}>
                        <input disabled={disabled} onClick={(e) => start(e)} type="button" value={text}/>
                    </div>

                    <Level/>

                </div>
            </div>
        </GameOneContext.Provider>
    );
};

export default GameOne;