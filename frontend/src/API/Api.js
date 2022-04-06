import axios from "axios"
import serves from "./Serves.js"


export default class Api {
 // const Domen = "http://rutko.ddns.net/api/"

 static async reg(name, password){
  const response = await axios.get(serves.domen()+"signUp?username="+name+"&password="+password);
  return response;
 }

 static async auth(name, password){
  const response = await axios.get(serves.domen()+"signIn?username="+name+"&password="+password);
  // console.log(response);
  return response;
 }

 static async getMe(){
  const response = await axios.get(serves.domen()+"getMe?token="+localStorage.getItem('token'));
  return response;
 }

 static async exit(){
  const response = await axios.get(serves.domen()+"signOut?token="+localStorage.getItem('token'));
  return response;
 }
}

