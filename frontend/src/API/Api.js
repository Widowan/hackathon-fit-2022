import axios from "axios"
import serves from "./Serves.js"


export default class Api {
    // const Domen = "http://rutko.ddns.net/api/"

    static async reg(name, password) {
        const response = await axios.get(serves.domen() + "signUp?username=" + name + "&password=" + password);
        return response;
    }

    static async auth(name, password) {
        const response = await axios.get(serves.domen() + "signIn?username=" + name + "&password=" + password);
        // console.log(response);
        return response;
    }

    static async getMe() {
        const response = await axios.get(serves.domen() + "getMe?token=" + localStorage.getItem('token'));
        return response;
    }

    static async exit() {
        const response = await axios.get(serves.domen() + "signOut?token=" + localStorage.getItem('token'));
        return response;
    }

    static async getAllGames() {
        const response = await axios.get(serves.domen() + "getAllGames");
        return response;
    }

    static async addGameResult(gameId, score, timeElapsed = 0) {
        // let form = new FormData;
        // form.append("token", localStorage.getItem("token"))
        // form.append("gameId ", gameId)
        // form.append("result ", null)
        // form.append("score", score)
        // form.append("timeElapsed ", timeElapsed)
        const response = await axios.get(serves.domen() + "addGameResult?" +
            "token=" + localStorage.getItem("token") +
            "&gameId=" + gameId +
            "&result=true" +
            "&score=" + score +
            "&timeElapsed=" + timeElapsed);
        return response;
    }

    static async getBalls(numberDays, gameId) {
        const response = await axios.get(serves.domen() + "getGameTotalResult?" +
            "token=" + localStorage.getItem("token") +
            "&gameId=" + gameId +
            "&days=" + numberDays
        );
        return response;
    }

    static async getLeaderBoard(gameId) {
        const response = await axios.get(serves.domen() + "getLeaderboard?" +
            "token=" + localStorage.getItem("token") +
            "&gameId=" + gameId
        );
        return response;

    }

}

