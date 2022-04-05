import React, {useState} from 'react';
import classes from "./ModalRating.module.css";
import RatingItem from "../../IU/List/RatingList/RatingItem/RatingItem";

const ModalRating = (props) => {

    const [modal, setModal] = useState(false);
    const zaglushka="https://st2.depositphotos.com/5266903/8766/v/450/depositphotos_87660356-stock-illustration-healh-care-emblem-icon.jpg";
    const [rating, setRating]= useState([
        {nik:"lol kek", img: zaglushka, count:200, index: 1},
        {nik:"lol kek", img: zaglushka, count:200, index: 2},
        {nik:"lol kek", img: zaglushka, count:200, index: 3},
        {nik:"lol kek", img: zaglushka, count:200, index: 4},
        {nik:"lol kek", img: zaglushka, count:200, index: 5},
        {nik:"lol kek", img: zaglushka, count:200, index: 6},
        {nik:"lol kek", img: zaglushka, count:200, index: 7},
        {nik:"lol kek", img: zaglushka, count:200, index: 8},
        {nik:"lol kek", img: zaglushka, count:200, index: 9},
    ])

    return (
        <div className={classes.modalRating}>
            <input onClick={()=>setModal(true)} type="button" value="Рейтинг"/>
            <div onClick={()=>setModal(false)} className={modal?classes.smoky:classes.noSmoky}>

            </div>
            <div className={modal?classes.modal:classes.noModal}>
                <div className={classes.close}>
                    <svg onClick={()=>setModal(false)} width="37" height="37" viewBox="0 0 37 37" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <circle cx="18.5" cy="18.5" r="18" fill="white" stroke="#76BA7D"/>
                        <rect width="25.3524" height="4.62014" rx="2" transform="matrix(-0.744576 0.667538 -0.756474 -0.654024 29.9639 10.907)" fill="#76BA7D"/>
                        <rect width="25.4139" height="4.60895" rx="2" transform="matrix(0.757107 0.653291 -0.743926 0.668262 10.707 8.82556)" fill="#76BA7D"/>
                    </svg>
                </div>
                <div className={classes.content}>
                    <div className={classes.title}> Рейтинг</div>
                </div>
                <div className={classes.rating}>
                    {
                        rating.map(rat=>
                            <RatingItem nik={rat.nik} img={rat.img} count={rat.count} color="green" index={rat.index} key={rat.index}/>
                        )
                    }

                </div>
                <div className={classes.footer}>
                    <RatingItem nik="никнейм" img={zaglushka} count="200" color="green" index="55"/>
                </div>


            </div>
        </div>
    );
};

export default ModalRating;