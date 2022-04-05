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
            <CircleAvatar src={props.img} alt="ava"/>
            <div>{props.index}</div>
            <div>{props.nik}</div>
            <div>{props.count}</div>
        </div>
    );
};

export default RatingItem;