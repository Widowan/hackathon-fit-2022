import React from 'react';
import classes from "./IndexPage.module.css";
import petals from "./../../../img/petals.svg";
import fone from "./../../../img/fone1.png";
import RegForm from "../../Forms/RegForm/RegForm";

const IndexPage = () => {
    return (
        <div className={classes.indexPage}>
            <div className={classes.desktopContent}>
                <div className={classes.text}>
                    <div className={classes.textContent}>
                        <div className={classes.textStart}>Запоминать умеет тот,</div>
                        <div className={classes.textEnd}>кто умеет быть внимательным.</div>
                    </div>
                    <div className={classes.fone}>
                        <img src={fone} alt=""/>
                    </div>
                </div>

                <img className={classes.petals} src={petals} alt="Ошибка загрузки"/>
            </div>

            <div className={classes.permanentContent}>
                <div className={classes.conceptText}>
                    Веб-платформа <span>для развития <br/> памяти</span>  <span2>у людей с ОВЗ</span2>
                </div>

                <RegForm/>

                <div className={classes.infoText}>
                    <div className={classes.str1}>Платформа разработана командой </div>
                    <div className={classes.str2}><span>“Гипножаба”</span> в рамках хакатона, </div>
                    <div className={classes.str3}>проводимого в РГСУ. 2022 г.</div>
                </div>
            </div>

        </div>
    );
};

export default IndexPage;