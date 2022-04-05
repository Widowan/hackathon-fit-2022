import React from 'react';
import classes from "./PageTitle.module.css";

const PageTitle = (props) => {
    return (
        <div className={classes.PageTitle}>
            {props.text}
        </div>
    );
};

export default PageTitle;