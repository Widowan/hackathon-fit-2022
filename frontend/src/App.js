import React from "react";
import Header from "./components/semantics/Header/Header";
import "./App.css"
import IndexPage from "./components/Pages/IndexPage/IndexPage";

function App() {
  return (
    <div className="App">
        <Header></Header>
        <IndexPage/>
    </div>
  );
}

export default App;
