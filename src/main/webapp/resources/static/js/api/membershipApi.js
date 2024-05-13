import { convertApiDataFormat } from "../utils/convertor.js";
import requestApi from "./ajax.js";

function insertUserInfo(userInfo){
	const apiData = convertApiDataFormat(null,null,userInfo,"json");
	return requestApi('post','/membership/signup', apiData,'json');
}

function checkDuplicatedUserInfo(inputType, inputValue){
	const requestParam = {
		'type': inputType,
		'value': inputValue
	}
	const apiData = convertApiDataFormat(null,requestParam);
	return requestApi('get', '/membership/user', apiData);
}

export { insertUserInfo, checkDuplicatedUserInfo }