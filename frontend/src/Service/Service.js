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
}