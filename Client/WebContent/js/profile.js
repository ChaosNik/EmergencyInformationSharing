let battutakey = 'fe5b8713c09e9050c7ef90f942895dab';

var countries = [];
var regions = [];
var cities = [];
var previousUsername;
var previousMail;

function initializeComponents(username, mail)
{
	previousUsername = username;
	previousMail = mail;
}

function validateFields(numberOfLogins){
	let firstName = document.getElementById("firstName").value;
	
	if(firstName == null || firstName == "")
	{
		document.getElementById("firstNameLabel").innerHTML = "* Polje ne moze biti prazno";
		return false; 
	}
	else	
		document.getElementById("firstNameLabel").innerHTML = "";
	
	let lastName = document.getElementById("lastName").value;	
	if(lastName == null || lastName == "")
	{
		document.getElementById("lastNameLabel").innerHTML = "* Polje ne smije biti prazno";
		return false; 
	}
	else
		document.getElementById("lastNameLabel").innerHTML = "";
		
	let username = document.getElementById("username").value;
	 
	if(/\s/.test(username)) {
 		document.getElementById("usernameLabel").innerHTML = "* Korisničko ime ne smije imati razmak";
		return false; 
	}
	else if(username == null || username == "")
	{
		document.getElementById("usernameLabel").innerHTML = "* Polje ne smije biti prazno";
		return false; 
	}
	else
		document.getElementById("usernameLabel").innerHTML = "";
 
	let password = document.getElementById("password").value; 
	if(password == null || password == "")
	{
		document.getElementById("passwordLabel").innerHTML = "* Polje ne smije biti prazno";
		return false; 
	}
	else
		document.getElementById("passwordLabel").innerHTML = "";

	let confirmedPassword = document.getElementById("confirmedPassword").value;
	if(password !== confirmedPassword) {
		document.getElementById("confirmedPasswordLabel").innerHTML = "Lozinke se ne poklapaju";
		return false;
	}else
		document.getElementById("confirmedPasswordLabel").innerHTML = "";
	
	let mail = document.getElementById("email").value;
	let regex = /([a-zA-Z0-9]+)([\.{1}])?([a-zA-Z0-9]+)\@gmail([\.])com/;
	if(!regex.test(String(mail).toLowerCase())) {
		document.getElementById("mailLabel").innerHTML = "* Samo google mail prihvatljiv";
		return false;
	}
	else if(password == null || password == "")
	{
		document.getElementById("mailLabel").innerHTML = "* Polje ne smije biti prazno";
		return false; 
	}	
	else
		document.getElementById("mailLabel").innerHTML = ""; 
 
	var countryOption = document.getElementById("country");
	var country  = countryOption.options[countryOption.selectedIndex].text;
	 
	var regionOption = document.getElementById("region");
	if(regionOption.options.length != 0)
		var region  = regionOption.options[regionOption.selectedIndex].value;
	
	var cityOption = document.getElementById("city");
	if(cityOption.options.length != 0)
	var city  = cityOption.options[cityOption.selectedIndex].value;
	var notificationOnMail;
	var notificationInApp;
	
	if(document.getElementById("notifications-blog").checked){
	var radioButtons = document.getElementsByName("notifications");
	 
	notificationOnMail = radioButtons[0].checked;
	notificationInApp = radioButtons[1].checked;
	}else{
		notificationOnMail = false;
		notificationInApp = false;
	}
	
    var output =  document.getElementById('profileImage');
    var photo;
    
    if((output.getAttribute('src') == "") || (output.getAttribute('src') == 'https://www.serenbooks.com/sites/default/files/default_images/default-user-image.png')|| output.getAttribute('src').includes("https://restcountries.eu"))
    {
    	var userCountry = countries.filter(function (c) {
    	    return c.name === country;
    	})[0];
    	photo = userCountry.flag;
    }else
    	photo = output.src;
    
	let object = {
			firstName: firstName,
			lastName: lastName,
			username: username,
			password: password,
			mail: mail,
			action: "updateProfile",
			country: country,
			region: region,
			city: city,
			photo: photo,
			notificationOnMail: notificationOnMail,
			notificationInApp: notificationInApp,
			numberOfLogins: numberOfLogins,
			previousUsername: previousUsername,
			previousMail: previousMail
	}
 
	let request = new XMLHttpRequest();
	request.onreadystatechange = function(){

		if((request.readyState ==4) && (request.status==200))
		{
			document.getElementById("successLabel").innerHTML = "";
			if((this.responseText.trim() == "USERNAME_ERROR") || (this.responseText.trim() == "MAIL_ERROR")) {
				if((this.responseText.trim() == "USERNAME_ERROR"))
					document.getElementById("usernameLabel").innerHTML = "Korisni&#269ko ime je zauzeto";
				if((this.responseText.trim() == "MAIL_ERROR")) 
					document.getElementById("mailLabel").innerHTML = "Mail je zauzet";
				
				return false;
			}
			else if(numberOfLogins == 0)
				window.location.replace("Login");
			else
				document.getElementById("successLabel").innerHTML = "Uspjesno izvrsena izmjena li&#269nih podataka";
		}
	}
	
	request.open("POST","Profile");
	request.setRequestHeader("Content-Type","application/json;charset=UTF-8");
	request.send(JSON.stringify(object));
}

function showImage(){
	 if (window.File && window.FileList && window.FileReader) {
		 var files = event.target.files; //FileList object
	     var output =  document.getElementById('profileImage');
         var file = files[0];
         if (!file.type.match('image')) 
        	 return false;
         var picReader = new FileReader();
         picReader.readAsDataURL(file);            
          picReader.addEventListener('load', function (event) {
               var picFile = event.target; 
               var picFile = event.target;
               output.src = picFile.result;
         });
	 }	
}

function handleImageChange(){
    
	 if (window.File && window.FileList && window.FileReader) {
		 var files = event.target.files; //FileList object
	     var output =  document.getElementById('profileImage');
         var file = files[0];
         if (file.type.match('image'))
         {
	         var picReader = new FileReader();
	         picReader.readAsDataURL(file);            
	          picReader.addEventListener('load', function (event) {
	               var picFile = event.target; 
	               var picFile = event.target;
	               output.src = picFile.result;
	         })
         }
	 }
}
         
function loadCountries() {
	var elem = document.getElementById("country");
	countries.forEach(country =>{
		var option = document.createElement("option");
		option.text = country.name;
		option.value = country.alpha2Code;
		elem.add(option); 
	});
}

function loadRegions(){
	var elem = document.getElementById("region");
	elem.innerHTML = "";
	regions.forEach(region =>{		
		
		var option = document.createElement("option");		
		option.text = region.region;
		option.value = region.region;
		elem.add(option); 
	});
	if(elem.options.length == 0)
		document.getElementById("city").innerHTML = "";
	else
		fillCities();
}

function loadCities(){
	var elem = document.getElementById("city");
	elem.innerHTML = "";
	cities.forEach(city =>{		
		var option = document.createElement("option");		
		option.text = city.city;
		option.value = city.city;
		elem.add(option); 
	});
}

function JsonpHttpRequest(url, callback) {
    var e = document.createElement('script');
    e.src = url;
    document.body.appendChild(e);
    window[callback] = (data) => {
    regions = data;   
    loadRegions();
    }
}

function JsonpHttpsRequest(url, callback) {
    var e = document.createElement('script');
    e.src = url;
    document.body.appendChild(e);
    window[callback] = (data) => {
    cities = data;   
    loadCities();
    }
}

function fillRegions(){
	var selectedCountry = document.getElementById("country");
	var alpha2Code = selectedCountry.options[selectedCountry.selectedIndex].value; 
	JsonpHttpRequest('http://battuta.medunes.net/api/region/' + alpha2Code + '/all/?key=' + battutakey +'&callback=cb', "cb");	
};

function fillCountries(){
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if ((request.readyState == 4) && (request.status == 200)) {	
			var result = JSON.parse(request.responseText);
			countries = result;
			loadCountries();
			fillRegions();
		}
	};
	request.open("GET", "https://restcountries.eu/rest/v2/region/europe", true);
	request.send(null);
};

function fillCities(){
	  var dynamicSelect = document.getElementById("city");
	  var selectedCountry = document.getElementById("country");
	  var alpha2Code = selectedCountry.options[selectedCountry.selectedIndex].value; 
	  var selectedRegion = document.getElementById("region");
	  var region = selectedRegion.options[selectedRegion.selectedIndex].value; 
	
	  JsonpHttpsRequest('http://battuta.medunes.net/api/city/' + alpha2Code + '/search/?region='+ region + '&key=' + battutakey + '&callback=cb', "cb");

};


function toggle_visibility() 
{
	var e = document.getElementById("radioButtons");
	
    if ( e.style.display == 'block' )
        e.style.display = 'none';
    else
        e.style.display = 'block';
}