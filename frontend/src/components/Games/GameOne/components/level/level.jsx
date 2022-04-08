import React, {useContext} from 'react';
import classes from "./Ievel.module.css";
import {GameOneContext} from "../../../../../context";

const Level = () => {

    const {cycle, level, setLevel} = useContext(GameOneContext)

    const levelUp = () =>{
        setLevel(level+1)
    }
    const levelDrop = () =>{
        if(!(level<=1)) setLevel(level-1)
        else setLevel(1)
    }

    return (
        <div className={classes.level}>
            <div className={classes.text}>Уровень</div>
            <div className={classes.ui}>
                <div className={classes.value}>{level}</div>


                <div className={classes.buttons}>
                    <svg onClick={levelUp} className={!cycle?classes:classes.noButtons} width="48" height="48" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <rect x="0.771973" y="0.5" width="46.04" height="46.04" rx="4.5" fill="white" stroke="#80BB67"/>
                        <path d="M25.2062 14.2658C24.4252 13.4847 23.1588 13.4847 22.3778 14.2658L9.64989 26.9937C8.86884 27.7748 8.86884 29.0411 9.64989 29.8222C10.4309 30.6032 11.6973 30.6032 12.4783 29.8222L23.792 18.5084L35.1057 29.8221C35.8868 30.6031 37.1531 30.6031 37.9342 29.8221C38.7152 29.041 38.7152 27.7747 37.9342 26.9937L25.2062 14.2658ZM25.792 16.464L25.792 15.68L21.792 15.68L21.792 16.464L25.792 16.464Z" fill="#80BB67"/>
                    </svg>

                    <div className={classes.valueMobile}>{level}</div>

                    <svg onClick={levelDrop} className={!cycle?classes:classes.noButtons} width="48" height="48" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <rect x="46.812" y="47.5" width="46.04" height="46.04" rx="4.5" transform="rotate(-180 46.812 47.5)" fill="white" stroke="#80BB67"/>
                        <path d="M22.3778 33.7342C23.1588 34.5153 24.4252 34.5153 25.2062 33.7342L37.9341 21.0063C38.7151 20.2252 38.7151 18.9589 37.9341 18.1778C37.153 17.3968 35.8867 17.3968 35.1057 18.1778L23.792 29.4916L12.4782 18.1779C11.6972 17.3969 10.4309 17.3969 9.64982 18.1779C8.86877 18.959 8.86877 20.2253 9.64983 21.0063L22.3778 33.7342ZM21.792 31.536L21.792 32.32L25.792 32.32L25.792 31.536L21.792 31.536Z" fill="#80BB67"/>
                    </svg>
                </div>

            </div>
        </div>
    );
};

export default Level;