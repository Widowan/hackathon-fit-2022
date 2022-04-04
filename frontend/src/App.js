import React from "react";
import Header from "./components/semantics/Header/Header";
import "./App.css"
import {BrowserRouter, Redirect, Route, Routes} from "react-router-dom";
import IndexPage from "./components/Pages/IndexPage/IndexPage";

function App() {
  return (
    <div className="App">

        <BrowserRouter>
            <Header></Header>
            <Routes>
                <Route path="*" element={<IndexPage/>}/>
            </Routes>
        </BrowserRouter>
    </div>
  );
}

export default App;
