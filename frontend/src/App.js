import React from "react";
import Header from "./components/semantics/Header/Header";
import "./App.css"
import {BrowserRouter, Redirect, Route, Routes} from "react-router-dom";
import IndexPage from "./components/Pages/IndexPage/IndexPage";
import GamesPage from "./components/Pages/GamesPage/GamesPage";
import GamePage from "./components/Pages/GamePage/GamePage";

function App() {
  return (
    <div className="App">

        <BrowserRouter>
            <Header></Header>
            <Routes>
                <Route path="index" element={<IndexPage/>}/>
                <Route path="games" element={<GamesPage/>}/>
                <Route path="games/:id" element={<GamePage/>}/>
                <Route path="*" element={<IndexPage/>}/>
            </Routes>
        </BrowserRouter>
    </div>
  );
}

export default App;
