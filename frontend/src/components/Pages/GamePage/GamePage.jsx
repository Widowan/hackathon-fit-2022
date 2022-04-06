import React, {useState} from 'react';
import classes from "./GamePage.module.css";
import PageTitle from "../../IU/Text/PageTitle/PageTitle";
import {useNavigate, useParams} from "react-router-dom";
import DescriptionMobile from "./DescriptionMobaile/DescriptionMobile";
import ModalRating from "../../ModalWindows/ModalRating/ModalRating";
import GameOne from "../../Games/GameOne/GameOne";

const GamePage = () => {

    const params = useParams();
    const route = useNavigate();
    const [title, setTitle] = useState("Название игры")

    const fish = "Для современного мира новая модель организационной деятельности играет определяющее значение для первоочередных требований. Разнообразный и богатый опыт говорит нам, что понимание сути ресурсосберегающих технологий способствует повышению качества системы обучения кадров, соответствующей насущным потребностям.";

    return (
        <div className={classes.gamePage}>
            <div className={classes.title}>
                <svg className={classes.svgDesktop} onClick={()=>route('/games')} width="62" height="62" viewBox="0 0 62 62" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path
                        d="M59.5 30.828C59.5 47.0405 46.5012 60.1559 30.5 60.1559C14.4988 60.1559 1.5 47.0405 1.5 30.828C1.5 14.6154 14.4988 1.5 30.5 1.5C46.5012 1.5 59.5 14.6154 59.5 30.828Z"
                        fill="white" stroke="#7AD183" strokeWidth="3"/>
                    <path
                        d="M10.6985 29.8923C9.72224 30.8686 9.72224 32.4515 10.6985 33.4279L26.6085 49.3378C27.5848 50.3141 29.1677 50.3141 30.144 49.3378C31.1203 48.3614 31.1203 46.7785 30.144 45.8022L16.0019 31.6601L30.144 17.518C31.1203 16.5416 31.1203 14.9587 30.144 13.9824C29.1677 13.0061 27.5848 13.0061 26.6085 13.9824L10.6985 29.8923ZM53.252 29.1601L12.4663 29.1601V34.1601L53.252 34.1601V29.1601Z"
                        fill="#BCE8C1"/>
                </svg>

                <div className={classes.pageTitle}>
                    <PageTitle text={title} />
                </div>

                <div className={classes.svgMobile}><DescriptionMobile text={fish}/></div>


            </div>
            <div className={classes.content}>

                <div className={classes.description}>
                    <div className={classes.descriptionTitle}>Правила</div>
                    <div className={classes.descriptionText}>
                        {fish}
                    </div>

                </div>
                <div className={classes.game}>
                    <GameOne/>
                </div>
                <div className={classes.status}>
                    <div className={classes.descriptionTitle}>Статус</div>
                    <div className={classes.descriptionTitle}>Статус</div>
                    <div className={classes.modalRating}>
                       <ModalRating id={params.id}/>
                    </div>
                </div>

            </div>

        </div>
    );
};

export default GamePage;