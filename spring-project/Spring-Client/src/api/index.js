import {request} from './ajax'
import {serverUri} from '@/config'

let apiToken = "";

function setToken(token) {
    apiToken = token;
}

function getContent(successCallback, errorCallback) {
    request("GET", `${serverUri}/content`, {}, successCallback, errorCallback, apiToken)
}

function logout(successCallback, errorCallback) {
    request("POST", `${serverUri}/logout`, {token: apiToken}, successCallback, errorCallback, apiToken)
}

function login(login, password, successCallback, errorCallback) {
    request("GET", `${serverUri}/login?login=${encodeURIComponent(login)}&password=${encodeURIComponent(password)}`,
        {}, successCallback, errorCallback, apiToken)
}

function register(login, password, successCallback, errorCallback) {
    request("POST", `${serverUri}/register?login=${encodeURIComponent(login)}&password=${encodeURIComponent(password)}`,
        {}, successCallback, errorCallback, apiToken)
}

export default {
    setToken,
    logout,
    getContent,
    register,
    login
}