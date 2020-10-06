let openweathermapkey = 'c184fa574eb152bfe0cab958d44d62de';
let battutakey = 'fe5b8713c09e9050c7ef90f942895dab';

function fillArea()
{
  var checkboxes = document.getElementsByName('cbCategory');
  var checkboxesChecked = [];
  let counter = 0;
  for (var i=0; i<checkboxes.length; i++)
     if (checkboxes[i].checked)
    	 counter++;
  if(counter == 1)
	  document.getElementById("txtArea").innerHTML = counter + " kategorija";
  else if(counter == 0)
	  document.getElementById("txtArea").innerHTML = "";
  else
	  document.getElementById("txtArea").innerHTML = counter + " kategorije";
}

function initializeComponents()
{
	var searchInput = 'searchInput';
	var autocomplete;
	autocomplete =
		new google.maps.places.Autocomplete
		(	(document.getElementById(searchInput)),
			{types: ['geocode'],}
		);
  document.addEventListener("click", toggleDropdown);
  getFeed();
}

function focusCommentBox(id)
{
	 document.getElementById("commentBox"+id).focus();
}

function toggleDropdown(event)
{
	var checkboxes = document.getElementById("checkboxes");
    if (event.target.classList.contains('overSelect'))
    	showCheckboxes();
    else if(!event.target.classList.contains('checkboxes'))
    {
  	 	if(checkboxes.style.display == "block")
  	 		showCheckboxes();
    }
}

var expanded = false;

function showCheckboxes()
{
  var checkboxes = document.getElementById("checkboxes");
  if (!expanded)
  {
    checkboxes.style.display = "block";
    expanded = true;
  }
  else
  {
    checkboxes.style.display = "none";
    expanded = false;
  }
}

var num = 0;
var images = [];
var video;
function readImage()
{
	 if (window.File && window.FileList && window.FileReader)
	 {
		 var files = event.target.files; //FileList object
	     var output = document.getElementsByClassName("preview-images-zone")[0];
	     for (let i = 0; i < files.length; i++)
	     {
            var file = files[i];
            if (!file.type.match('image') && !file.type.match('video')) continue;
            var picReader = new FileReader();

            picReader.readAsDataURL(file);            
            picReader.addEventListener
            (
        		'load',
        		function (event)
	            {
	                var picFile = event.target;
	                var div = document.createElement("div");
	                var html;           
	    
	                if(file.type.match('image'))
	                {
	                	images.push(picFile.result);
	                	html = '<div style="float: left;" class="preview-image preview-show-' + num + '">' +
	                	'<div class="image-zone"><img style="width:100px;height:100px"  id="pro-img-' + num + '" src="' + picFile.result + '"></div>' +
	                	'</div>';
	                }
	                else
	                {
	                	var fileURL = URL.createObjectURL(file);
	                	video = fileURL;
	                	html = '<div style="float: left;" class="preview-image preview-show-' + num + '">' +
	                	'<div class="image-zone"><video style="width:100px;height:100px" controls autoplay src="'+ fileURL + '"></video>' +
	                	'</div>';
	
	                }
	                div.innerHTML = html;
	                num = num + 1;
	             
	                output.append(div);
	            }
            );

        }
        document.getElementById("pro-image").innerHTML= '';
	 }
}

function getImagePath()
{
     var output =  document.getElementById('profileImage');
     var file = files[0];
     picReader.readAsDataURL(file);            
      picReader.addEventListener
      (
		  'load',
		  function (event)
		  {
			  var picFile = event.target; 
			  var picFile = event.target;
			  output.src = picFile.result;
		  }
      );
}

var imageComment;
function readImageForComment(id)
{
	var num = 0;
	if (window.File && window.FileList && window.FileReader)
	{
		var files = event.target.files;
		var output = document.getElementsByClassName("preview-images-zone"+id)[0];
        for (let i = 0; i < files.length; i++)
        {
        	var file = files[i];
            if (!file.type.match('image')) continue;
            output.style.display = "block";
            var picReader = new FileReader();
            picReader.readAsDataURL(file);            
            picReader.addEventListener
            (
            	'load',
            	function (event)
            	{
		            if(num != 1)
		            {
			            var picFile = event.target;
			            var div = document.createElement("div");
			            imageComment = picFile.result;
			            var html =  '<div class="preview-image preview-show-' + num + '">' +
			                            '<div class="image-zone"><img  width="150" height="100" id="pro-img-' + num + '" src="' + picFile.result + '"></div>' +
			                            '</div>';
			            div.innerHTML = html;
			            num = num + 1;
			             
			            output.append(div);
			            focusCommentBox(id);
		            }
            	}
        	);
        }
        document.getElementById("pro-image").innerHTML= '';
	 }
}

function focusShareOnFacebook(text, image, location, link)
{
	u="https://www.pngitem.com/pimgs/m/247-2473457_current-location-icon-png-location-vector-icon-transparent.png";
	t='dsadsdas';
	if(location != 'null')
		text += "  Lokacija: " + location;
	if(link != 'null')
		text+= "  Detaljnije: " + link;
	if(image)
	{
		window.open('http://www.facebook.com/sharer.php?u='+encodeURIComponent(image)+'&u='+encodeURIComponent(image)+'&quote=' + encodeURIComponent(text) +'&t='+encodeURIComponent(t),'sharer','toolbar=0,status=0,width=600,height=400');
		return false;
	}
	else
	{
		image = "https://thumbs.dreamstime.com/z/stencil-type-red-danger-word-watermark-frame-red-danger-stencil-103746758.jpg";
		window.open('http://www.facebook.com/sharer.php?u='+encodeURIComponent(image)+'&quote=' + encodeURIComponent(text) +'&t='+encodeURIComponent(t),'sharer','toolbar=0,status=0,width=626,height=436');
		return false;
	}
}

function focusShareOnTwitter(text, link, username, location)
{
	var shareURL = "http://twitter.com/share?";
	u="https://www.pngitem.com/pimgs/m/247-2473457_current-location-icon-png-location-vector-icon-transparent.png";
	if((location !== "") && (location != 'null'))
		username += "  Lokacija: " + location;
	if(link != 'null')
		text+= "  Detaljnije na linku: ";
	var params =
	{
		text: text,
		via: username,
		url: link, 
	}
    for(var prop in params)
    	shareURL += '&' + prop + '=' + encodeURIComponent(params[prop]);
	window.open(shareURL, '', 'left=0,top=0,width=650,height=650,personalbar=0,toolbar=0,scrollbars=0,resizable=0');
}

function shareEmergency(id, username, fullName, userPhoto)
{
	var description = document.getElementById('dangerDetails').value;
	var location = document.getElementById('searchInput').value;	 
	var checkboxes = document.getElementsByName('cbCategory');
	var checkboxesChecked = [];
	var categoriesNames = [];
	var cbId;
	for (var i=0; i< checkboxes.length; i++)
	{
	   if (checkboxes[i].checked)
	   {
	       cbId = checkboxes[i].id.split(" "); 
		   checkboxesChecked.push(cbId[0]);
		   categoriesNames.push(cbId[1]);   	  
	   }
	}
	
	var url = description;
    var regExp = /(?:https:www\.youtube\.com\/(?:[^\/]+\/.+\/|(?:v|e(?:mbed)?)\/|.*[?&]v=)|youtu\.be\/)([^"&?\/ ]{11})/i, match = String(url).match(regExp);
    var link;
    
    if (match)
    {
    	link = match[0];
    	description = description.replace(link,'');
    }
    else
    {
    	var str = description;
    	var urlRE= new RegExp("([a-zA-Z0-9]+://)?([a-zA-Z0-9_]+:[a-zA-Z0-9_]+@)?([a-zA-Z0-9.-]+\\.[A-Za-z]{2,4})(:[0-9]+)?([^ ])+");
    	
    	if(str.match(urlRE) != null)
    	{
    		link = str.match(urlRE)[0];
    		description = description.replace(link,'');
		}
    }

    var emergencyCb = document.getElementById('emergencyCb');
    
    if((video == null) && (images.length == 0) && (description == "") && (link == "") && (location == ""))
    	return false;
    
	let object =
	{
		description: description,
		location: location,
		checkboxesChecked: checkboxesChecked,
		images: images,
	    video: video,
	    isEmergency: emergencyCb.checked,
	    link: link
	}
 
	let request = new XMLHttpRequest();
	request.onreadystatechange = 
		function()
		{
			if((request.readyState ==4) && (request.status==200))
			{
				if(emergencyCb.checked == 0)
					displayEmergency(username, fullName, userPhoto, description, location, images, video, link, id, categoriesNames);
				else
					displayEmergency(username, fullName, userPhoto, description, location, images, video, link, id, categoriesNames, "emergencyPostsZone") 
				images = [];
				video = null;
				document.getElementById('dangerDetails').value = "";
				document.getElementById('searchInput').value = "";	 
				checkboxes = document.getElementsByName('cbCategory');
				for (var i=0; i< checkboxes.length; i++)
					checkboxes[i].checked = false;	
				document.getElementById('emergencyCb').checked = false;	
			    var previewImage = document.getElementsByClassName("preview-images-zone")[0];
		        previewImage.innerHTML = "";
		        document.getElementById("txtArea").innerHTML = "";
			}
		}
	
	request.open("POST","Post");
	request.setRequestHeader("Content-Type","application/json;charset=UTF-8");
	request.send(JSON.stringify(object));

}

function displayEmergency(username, fullName, userPhoto, description, location, images, video, link, id, categoriesNames, emergencyZone)
{
    var div = document.createElement("div");
    var output;
    if(emergencyZone == null)
    	output = document.getElementsByClassName("postsZone")[0];  
    else
    	output = document.getElementsByClassName(emergencyZone)[0];  
    
    var imagesHtml ="";
    var categoriesHtml ="";
    var locationHtml ="";
    var videoHtml = "";
    var i = 0; 
    categoriesNames.forEach
    (
    	function (category, index)
    	{
			if(categoriesNames.indexOf(category) == 0)
				categoriesHtml+= "<h3 class='card-title'>Kategorije: " + category;
			else
				categoriesHtml+= ", " + category;
			if (categoriesNames.indexOf(category) == (categoriesNames.length - 1))
				categoriesHtml+=" </h3>";  
    	}
    );
    if(video != null && video != '')
    	videoHtml+= '<video style="width:200px;height:200px" controls autoplay src='+ video + '></video>&nbsp;';		
    imagesHtml = videoHtml; 
    images.forEach
    (
    	function (image, index)
    	{
	    	if(index != 0 && index % 4 == 0)
	    		imagesHtml += ' <div style="float: left;" class="image-zone"><img style="width:200px;height:200px"  id="pro-img-' + i++ + '" src="' + image + '"></div> &nbsp;<br>';
	    	else
	    		imagesHtml += '<div style="float: left;" class="image-zone"><img style="width:200px;height:200px"  id="pro-img-' + i++ + '" src="' + image + '"></div>&nbsp;';
    	}
    ); 
    if(imagesHtml != "")
    	imagesHtml += '<br><br><br><br><br><br><br><br><br>'
    var linkHtml = "";
    if(location != null && location != '')
    	locationHtml+= '<i style="float:right" class="fa fa-map-marker" aria-hidden="true">&nbsp;' + location + '</i>';
    if(link != null)
    {
    	if(link.includes("youtube"))
    		linkHtml+='<a href=' + link + 'class="card-link">Pogledajte vi&#353;e</a>' 
    	else
    		linkHtml+='<a href=' + link + 'class="card-link">Pro&#269;itajte vi&#353;e</a>' 
    }
    var html;
    if(emergencyZone == null)
    {
	    var proimage = "pro-image2" + id;
	    var commentBox = "commentBox" + id;
		var previewImagesZone = "preview-images-zone" + id;
	   
	    html =  '<br><div class="card gedf-card"><div class="card-header"><div class="d-flex justify-content-between align-items-center"><div class="d-flex justify-content-between align-items-center">' +
	    '<div class="mr-2"><img class="rounded-circle" width="45" src=' + userPhoto + ' alt=""></div><div class="ml-2"><div class="h5 m-0">@' + username + '</div><div class="h7 text-muted">' + fullName + '</div>' +
	    '</div></div><div></div></div></div>' +
	    '<div class="card-body"><div class="well"> <div class="row"><div class="col-md-12"><div class="text-muted h7 mb-2"> <i class="fa fa-clock-o"></i> Prije 0 minuta' + locationHtml + '</div>' +
	    '<h1>'+ categoriesHtml +'</h1>' +	imagesHtml
	     +
	    '<p>' + description + '<br></p>'  + linkHtml + '</div></div></div></div>' +
	    '<div class="card-footer"><button type="button" onClick="focusCommentBox(' + id + ')" class="btn btn-link"><i class="fa fa-comment"></i>Komentari&#353;i</button>' +
	    '<button type="button" onClick="focusShareOnFacebook(500, 300)" class="btn btn-link"><i class="fa fa-facebook-square" aria-hidden="true"></i>Podijeli na fb</button>' +
	    '<button type="button" onClick="focusShareOnTwitter()" class="btn btn-link"><i class="fa fa-twitter-square" aria-hidden="true"></i>Podijeli na twitter</button>' +
	    '</div></div><div class="commentsZone'+id+'"><div class="card gedf-card"><div class="panel panel-info"><div class="panel-body"><textarea id=' + commentBox +' style="width:100%" placeholder="Napi&#353;ite svoj komentar ovdje" class="pb-cmnt-textarea" onkeypress="onEnterPress(\'' + id + '\',\'' + fullName + '\',\'' + userPhoto + '\')";  ></textarea>' +
	    '<div style="float:right; display: none;" class=' + previewImagesZone + '></div></div></div></div></div><fieldset style="float:right" class="form-group"><a  href="javascript:void(0)" style="float:right;"onclick="$(\'#pro-image2' + id + '\').click()"><span class="fa fa-picture-o fa-lg"></span>Dodaj sliku</a>' +
	    '<input type="file" id='+ proimage +' name=' + proimage + ' style="display: none;" class="form-control" onChange="readImageForComment(' + id + ')" ></fieldset><br><br>';
    }
    else
    {
    	html =  '<br><div class="card gedf-card"><div class="card-header"><div class="d-flex justify-content-between align-items-center"><div class="d-flex justify-content-between align-items-center">' +
        '<div class="mr-2"><img class="rounded-circle" width="45" src=' + userPhoto + ' alt=""></div><div class="ml-2"><div class="h5 m-0">@' + username + '</div><div class="h7 text-muted">' + fullName + '</div>' +
        '</div></div><div></div></div></div>' +
        '<div class="card-body"><div class="well"> <div class="row"><div class="col-md-12"><div class="text-muted h7 mb-2"> <i class="fa fa-clock-o"></i> Prije 0 minuta' + locationHtml + '</div>' +
        '<h1>'+ categoriesHtml +'</h1>' +	imagesHtml
         +
        '<p>' + description + '<br></p>'  + linkHtml + '</div></div></div></div></div>';
    }
	div.innerHTML = html + output.innerHTML;
	output.innerHTML = "";
	output.append(div);	
}

var weatherNum = 0;
var countries = [];
var regions = [];
var citiesForWeather = [];
var cities = [];

function JsonpHttpsRequest(url, callback, isLastRegion)
{
	var e = document.createElement('script');
	e.src = url;
	document.body.appendChild(e);
	  
	window[callback] = (data) => {
    	data.forEach(function(city) {
    		citiesForWeather.push(city);	
    	}); 
    	
    	if(isLastRegion && citiesForWeather.length > 0 && cities.length < 3){
    		while(cities.length < 3){
    		    var r = Math.floor(Math.random() * citiesForWeather.length);
    		    
    		    var city = citiesForWeather[r].city;
    		    if(cities.indexOf(city) === -1) 
    		    	cities.push(city);
    		}
    		cities.forEach(function(city) {
    			searchWeather(city)
    			});
    		cities = [];
    		citiesForWeather = [];
    	}
    }

    if(cities.length == 0)
    	document.getElementById("errorLabel").innerHTML = "Vremenska prognoza";
}

function JsonpHttpRequest(url, callback, alpha2Code)
{
    var e = document.createElement('script');
    e.src = url;
    document.body.appendChild(e);
    window[callback] = (data) => {
    regions = data;  
    var isLastRegion = false;
    regions.forEach(function(region, idx, array){
    	   if (idx === regions.length - 1){ 
    	 		isLastRegion = true;
    	   }
    	   	JsonpHttpsRequest('http://battuta.medunes.net/api/city/' + alpha2Code + '/search/?region='+ region.region + '&key=' + battutakey + '&callback=cb', "cb", isLastRegion); 
    	});
    }
    if(regions.length == 0)
    	document.getElementById("errorLabel").innerHTML = "Greska";
}

function fillCountries(country)
{
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if ((request.readyState == 4) && (request.status == 200)) {	
			var result = JSON.parse(request.responseText);
			countries = result;
			var userCountry = countries.filter(function (c) {
	    	    return c.name === country;
	    	})[0];
			JsonpHttpRequest('http://battuta.medunes.net/api/region/' + userCountry.alpha2Code + '/all/?key=' + battutakey + '&callback=cb', "cb", userCountry.alpha2Code);
	};
	}
		request.open("GET", "https://restcountries.eu/rest/v2/region/europe", true);
		request.send(null);		
}
let myNum = 0;
let cityNames = ['Banja Luka', 'Bijeljina', 'Prijedor', 'Doboj', 'Teslic', 'Gacko', 'Trebinje', 'Zvornik', 'Gradiska', 'Mrkonjic Grad'];
function searchWeather(cityName)
{
	cityName = cityNames[Math.floor(Math.random() * 10)];
	let units = 'metric';
	let searchMethod = 'q';
	fetch(`http://api.openweathermap.org/data/2.5/weather?q=` + cityName + `&APPID=` + openweathermapkey + `&units=metric`)
	        .then((result) => {
	            return result.json();
	        }).then((res) => {
	            init(res, myNum++);
	    }).catch(function() {
	        console.log("error");
	        --myNum;
	    });;
}

   
function init(resultFromServer, num)
{
	let city = document.getElementById('weather-city-name-' + num);
    let tempterature = document.getElementById('weather-temperature-' + num);
    let pressure = document.getElementById('weather-pressure-' + num);
    let humidity = document.getElementById('weather-humidity-' + num);
    let wind = document.getElementById('weather-wind-' + num);

    city.innerHTML = resultFromServer.name;
    tempterature.innerHTML = 'Temperatura ' + Math.floor(resultFromServer.main.temp) + '&#176;';
    pressure.innerHTML = 'Pritisak ' + Math.floor(resultFromServer.wind.speed) + ' bar';
    humidity.innerHTML = 'Vlaznost ' + resultFromServer.main.humidity + '%';
    wind.innerHTML = 'Vjetrovi ' + Math.floor(resultFromServer.wind.speed) + ' m/s';
		
    setPositionForWeatherInfo();
}

function setPositionForWeatherInfo()
{
	let weatherCards = document.getElementById('weatherCards');
	weatherCards.style.visibility = 'visible';
    weatherNum = weatherNum + 1;
}
	
function onEnterPress(id, fullName, userPhoto, postId, userId)
{
    var key = window.event.keyCode;
 
    if (key === 13) {
    	var text = document.getElementById("commentBox"+id).value;
	if(text != '' || imageComment != null){
	console.log(text, imageComment, id, fullName, userPhoto, postId, userId);
	addComment(text, imageComment, id, fullName, userPhoto, postId, userId);

    var previewImage = document.getElementsByClassName("preview-images-zone"+id)[0];
    previewImage.innerHTML = "";
    previewImage.style.display = "none";
	document.getElementById("commentBox"+id).value = "";
	document.getElementById("commentBox"+id).blur();
	imageComment = null;
	return false;
    }}
    else {
        return true;
    }
}

function addComment(text, imageComment, id, fullName, userPhoto, postId, userId)
{
	  if((text == null) && (imageComment == null))
	    	return false;
		let object = {
				description: text,
				image: imageComment,
				userId: userId,
				postId: postId,
		}
		let request = new XMLHttpRequest();
		request.onreadystatechange = function(){
			
			if((request.readyState ==4) && (request.status==200))
			{
				displayComment(text, imageComment, id, fullName, userPhoto);
			}
		}
		request.open("POST","Comment");
		request.setRequestHeader("Content-Type","application/json;charset=UTF-8");
		request.send(JSON.stringify(object));			
}	

function displayComment(text, imageComment, id, fullName, userPhoto)
{
    var div = document.createElement("div");
    var output = document.getElementsByClassName("commentsZone"+id)[0];  
    var nodesSameClass = output.getElementsByClassName("row");
    var numberOfComments = nodesSameClass.length;
    
    var html;
    var hrTag = "";
    var datetime = new Date().toLocaleString();
    
    if(numberOfComments != 0)
    	hrTag = "<hr>";
    
    if(imageComment != null){
    	html =  '<div class="row" style="margin:5px"><div class="col-sm-2 text-center"><img class="rounded-circle" width="70" src=' + userPhoto + ' alt="">' +
		'</div><div class="col-sm-10"><h4>' + fullName  + ' </h4><div class="text-muted h7 mb-2"><i class="fa fa-clock-o"></i> Prije 0 minuta</div><p>' + text + '</p>' +
		'<img height="170" width="170" src=' + imageComment + '><br></div></div>' + hrTag;
    }else{
    	html =  '<div class="row" style="margin:5px"><div class="col-sm-2 text-center"><img class="rounded-circle" width="70" src=' + userPhoto + ' alt="">' +
		'</div><div class="col-sm-10"><h4>' + fullName  + '</h4><div class="text-muted h7 mb-2"><i class="fa fa-clock-o"></i> Prije 0 minuta</div><p>' + text + '</p>' +
		'<br></div></div>' + hrTag;
    }
    
	div.innerHTML = html + output.innerHTML;
	output.innerHTML = "";
	output.append(div);
}

function getFeed()
{
	var feedURL = "https://europa.eu/newsroom/calendar.xml_en?field_nr_events_by_topic_tid=151";
	$.ajax({
		  type: 'GET',
		  url: "https://api.rss2json.com/v1/api.json?rss_url=" + feedURL,
		  dataType: 'jsonp',
		  success: function(result) {
		  }
		});
}
	 