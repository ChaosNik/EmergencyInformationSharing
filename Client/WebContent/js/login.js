function validateFields(){
	
	let username = document.getElementById("username").value;
	var condition = true;
	if(/\s/.test(username)) {
 		document.getElementById("usernameLabel").innerHTML = "Pogresno korisnicko ime";
 		condition = false; 
	}
	else if(username == null || username == "")
	{
		document.getElementById("usernameLabel").innerHTML = "Prazno polje";
		condition =  false; 
	}else
		document.getElementById("usernameLabel").innerHTML = "";
 
	let password = document.getElementById("password").value; 
	if(password == null || password == "")
	{
		document.getElementById("passwordLabel").innerHTML = "Prazno polje";
		condition =  false; 
	}else
		document.getElementById("passwordLabel").innerHTML = "";

	if(condition == false)
		return condition;
	 
	let object = {
			username: username,
			password: password,
			action: "login"
	}
 
	let request = new XMLHttpRequest();
	request.onreadystatechange = function(){
		
		if((request.readyState ==4) && (request.status==200))
		{
			if(this.responseText.trim() == "ERROR") {
				document.getElementById("errorLabel").innerHTML = "Pogresno korisnicko ime ili lozinka";
				return false;
			}
			else
				window.location.replace("Home");
		}
	}
	
	request.open("POST","Login");
	request.setRequestHeader("Content-Type","application/json;charset=UTF-8");
	request.send(JSON.stringify(object));
}
