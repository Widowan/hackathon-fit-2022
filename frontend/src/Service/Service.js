import Api from "../API/Api";

export default class Service {
    static async getMe(){

        try {
            const response = await Api.getMe();
            console.log({
                isError:false,
                user:response.data.me
            })
            return {
                isError:false,
                user:response.data.me
            }

        }catch (response){
            return {
                isError:true,
                error:response.name,
                message:response.message
            }
        }
    }

    static async auth(name, password){

        try {
            const response = await Api.auth(name, password);

            localStorage.setItem('token', response.data.token);

            return true;

        }catch (error){
            return false
        }
    }

    static async exit(){

        try {
            const response = await Api.exit();

            localStorage.removeItem('token');

            return true;

        }catch (error){
            return false
        }
    }

    static async getAllGames(){

        try {
            const response = await Api.getAllGames();

            return response.data;

        }catch (error){
            return false
        }
    }

    static async addGameResult(gameId,score,timeElapsed = 0){

        try {

            await Api.addGameResult(gameId,score,timeElapsed);
            return true;

        }catch (error){

            console.log(error)
            const chek = await Api.getMe()
            // if(chek.isError){
            //     localStorage.removeItem("token")
            //     Document.location.reload();
            //     return true;
            // }
            return false
        }
    }

    static async getStatusBalls(gameId){
        try {

            const result1 = await Api.getBalls(1,gameId);
            const result2 = await Api.getBalls(7,gameId);
            return {
                days: result1.data.gameTotalResult.sumScore,
                month: result2.data.gameTotalResult.sumScore,
            };

        }catch (error){

            console.log(error)
            const chek = await Api.getMe()
            if(chek.isError){
                localStorage.removeItem("token")
                Document.location.reload();
                return {
                    days:"Ошибка подгрузки",
                    month:"Ошибка подгрузки"
                };
            }
            return {
                days:"Ошибка подгрузки",
                month:"Ошибка подгрузки"
            };
        }
    }

    static async getLeaderBoard(gameId){
        try {

            const result = await Api.getLeaderBoard(gameId);
            return result.data.leaderboard;

        }catch (error){

            return {"userId": "error",
                "place": 1,
                "sumScore": "error",
                "username": "error"}
        }
    }


}