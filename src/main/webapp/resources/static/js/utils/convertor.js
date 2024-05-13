function convertFormDataToObject(form){
	const object = {}
	form.forEach(el=>{
		object[el.name] = el.value;
	})
	return object;
}

 function convertStringToBytes(content){
	const textEncoder = new TextEncoder();
	const byteSize = textEncoder.encode(content).byteLength;
	return byteSize;
 }

function getPathNameNumber(){
	return window.location.pathname.split('/').pop();
}

function convertApiDataFormat(pathVar=null,requestParams=null,data=null,type='json'){ 
	return {
		'pathVar': pathVar,
		'requestParams': requestParams,
		'data': data,
		'formatType': type
	};;
}

function changeObjectKeyName(object, prevName, afterName){
	if(object[prevName]){
		object[afterName] = object[prevName];
		delete object[prevName];
	}
}

export { convertFormDataToObject, convertStringToBytes, getPathNameNumber, convertApiDataFormat, changeObjectKeyName }
