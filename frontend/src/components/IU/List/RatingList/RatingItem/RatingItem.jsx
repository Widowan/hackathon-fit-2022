import React from 'react';
import classes from "./RatingItem.module.css";
import CircleAvatar from "../../../Avatars/CircleAvatar/CircleAvatar";

const RatingItem = (props) => {
    return (
        <div className={classes.ratingItem} style={{
            color: props.color,
            border: props.color+" 1px solid",
            borderRadius: "41px"
        }}>
            {/*<CircleAvatar src={props.img} alt="ava"/>*/}
            <div className={classes.avatar}>
                <CircleAvatar src="http://krutna.ddns.net/static/default.svg" alt="ava"/>
            </div>
            <div className={classes.index}>{props.index}</div>
            <div className={classes.nik} tabIndex={99999}>{props.nik}</div>
            <div className={classes.balls}>{props.count}</div>
        </div>
    );
};

export default RatingItem;