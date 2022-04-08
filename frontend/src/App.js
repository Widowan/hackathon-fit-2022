import React, {useEffect, useState} from "react";
import Header from "./components/semantics/Header/Header";
import "./App.css"
import {BrowserRouter, Redirect, Route, Routes} from "react-router-dom";
import IndexPage from "./components/Pages/IndexPage/IndexPage";
import GamesPage from "./components/Pages/GamesPage/GamesPage";
import GamePage from "./components/Pages/GamePage/GamePage";
import {AuthContext} from "./context";
import Service from "./Service/Service";

function App() {
    const [user, setUser] = useState({
        id: "",
        img: "",
        name: ""
    });

    const [token, setToken] = useState("");


    const isAuth = async () => {
        if (!localStorage.getItem('token')) return

        const user = await Service.getMe();
        if (user.isError) {
            localStorage.removeItem('token')
        } else {
            setUser({
                id: user.user.id,
                img: user.user.img,
                name: user.user.username,
            })
            setToken(localStorage.getItem('token'))
        }
    }

    useEffect(() => {
        isAuth();
    },[token])


    return (
        <AuthContext.Provider value={{
            user,
            setUser,
            token,
            setToken
        }}>
            <div className="App">

                <BrowserRouter>
                    <Header></Header>
                    {token ?
                        <Routes>
                            <Route path="games" element={<GamesPage/>}/>
                            <Route path="games/:id" element={<GamePage/>}/>
                            <Route path="*" element={<GamesPage/>}/>
                        </Routes>
                        :
                        <Routes>
                            <Route path="*" element={<IndexPage/>}/>
                        </Routes>
                    }
                </BrowserRouter>
            </div>
        </AuthContext.Provider>
    );
}

export default App;
