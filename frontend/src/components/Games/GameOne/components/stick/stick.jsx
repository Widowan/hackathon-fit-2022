import React from 'react';
import classes from "./stick.module.css";

const Stick = (props) => {
    return (
        <div className={classes.stick} style={{
            border: "solid",
            borderWidth: "0px 1px 1px 1px",
            borderColor: props.color,
            color:props.color
        }}>
            <div className={classes.footing} style={{
                height: "clamp(calc(175px / "+props.wh+"), calc(20vw / "+props.wh+"), calc(600px / "+props.wh+"))"
            }}>
                {props.number}
            </div>
            <div className={classes.tip} style={{
                background: props.color
            }}>

            </div>
        </div>
    );
};

export default Stick;