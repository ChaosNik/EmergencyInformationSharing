<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.etfbl.dto.User"%>
<%@page import="net.etfbl.dto.Post"%>
<%@page import="net.etfbl.dto.Comment"%>
<%@page import="net.etfbl.dto.PostCategory"%>
<%@page import="net.etfbl.dao.UserDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<jsp:useBean id="userBean" type="net.etfbl.beans.UserBean" scope="session"/>
<jsp:useBean id="postBean" type="net.etfbl.beans.PostBean" scope="session"/>
<jsp:useBean id="postCategoryBean" type="net.etfbl.beans.PostCategoryBean" scope="session"/>
<script><%@include file="/js/home.js"%></script>
<style><%@ include file="/css/home.css"%></style>
<!DOCTYPE html>
<html>
<head>
<script> window.onload = initializeComponents;</script>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places&key=AIzaSyDtuDIzXbDOlTsmm3-7kqE1Wp-u1bv6V_Q"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<title>Pocetna stranica</title>
</head>
<body>

<jsp:include page="../pages/header.jsp"/>
<br>
    <div class="container-fluid gedf-wrapper">
        <div class="row">
            <div class="col-md-3">
                <div class="card">
                    <h4 >Broj prijava: <%out.println(userBean.getUser().getNumberOfLogins());%></h4>
                   	<img id = "profileImage" style="width:100%" src=<%=userBean.getUser().getPhoto()%> />
                    <h3 id = "profile-picture"><%out.println(userBean.getUser().getFirstName() + " " + userBean.getUser().getLastName());%></h3>
				</div>
				<%if(userBean.getUser().getNotificationInApp() == 1){ %>
				<div class="emergencyPostsZone">

			   <%
	               List<Post> posts = postBean.getAllActiveEmergencyPosts();
						   	   int[] i = {0};
	           	   for(Post p : posts){
	           	%>
				           	
				<div style=" margin-top: 15px;" class="card gedf-card">
                    <div class="card-header">
                        <div class="d-flex justify-content-between align-items-center">
                            <div class="d-flex justify-content-between align-items-center">
                                <div class="mr-2">
                                    <img class="rounded-circle" width="70" src=<%=p.getCreator().getPhoto()%> alt="">
                                </div>
                                <div class="ml-2">
                                    <div class="h5 m-0">@<%out.println(p.getCreator().getUsername());%></div>
                                    <div class="h7 text-muted"><%out.println(p.getCreator().getFirstName() + " " + p.getCreator().getLastName());%></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%
                    Date postTime = p.getCreationTime();
                    Long result = ((new Date().getTime()/60000) - (postTime.getTime()/60000));
                   	String differnce;
                   	if(result > 60)
                   	{
                   		result /= 60;
                   		if(result > 730){
                       		result /= 730;
                       		differnce = result.toString() + " mjeseci";
                       	}else
                   			differnce = result.toString() + " h";
                   	}else
                   	{
                   		differnce = result.toString() + " min";
                   	}
                    %>
                    
                    <div class="card-body">
                        <div class="text-muted h7 mb-2"> <i class="fa fa-clock-o"> Prije <%=differnce%> </i>
                     
                        <%
                        String location = p.getLocation();
                        if(location != null && !location.isEmpty()){%>
                    	<i style="float:right" class="fa fa-map-marker" aria-hidden="true"> <%=location%></i>
                        <%}%>
                        </div>
                        <h3 class="card-title">
                             <%
                            List<String> categories = p.getCategories();	
                            if(categories != null){
                            	for(String category : categories)
                            		if(categories.indexOf(category) == 0)
                            			out.print("Kategorije opasnosti: " + category);
                            		else
                            			out.print(", " + category);
                            }
                            %>
                            </h3>
                        <%
   						List<String> images = p.getImages();
                        for(String image : images){%>
                        <img width="200" height="200"  class="center-block img-responsive" src='<%=image%>' />
                        <%}%>
                        
                        <p class="card-text">
                             <% out.println(p.getText()); %>
                        </p>
                       	<%
                       	String link;
                       	if((link = p.getLink()) != null){%>
                        <a href=<%=link%> class="card-link">Detaljnije</a>
                        <%}%>
                    </div>
                </div>
				      <br>     	
				           	 <%
				           	 i[0]++;
				           	 } %>
      <br>
        </div> 
                        <%} %>
</div>
<div class="col-md-6 gedf-main">
<div class="card gedf-card">
                    
<div class="card-body">
<table style="width:100%; margin-top:0px; margin-bottom: 0px">
 	<tr style="width:100%; margin-top:0px; margin-bottom: 0px">
 		<td style="width:100%; margin-top:0px; margin-bottom: 0px">
			<div class="multiselect" style="margin-top:0px; margin-bottom: 0px">
			    <div class="selectBox" >
			     	<select id="selectedCategories" style="width:100%; margin-top:0px; margin-bottom: 0px"></select>
			    	<div class="overSelect" id="txtArea"></div>
			    </div>
			    <div id="checkboxes">
					<%
			            List<PostCategory> postCategories = postCategoryBean.getAllActivePostCategories();
			          	for(PostCategory pc : postCategories)
			          	{%>
				          	<label style="font-weight:bold"><input id="<%=pc.getId() + " " + pc.getName()%>" class="checkboxes" type="checkbox" name="cbCategory" onClick="fillArea();"/> <%out.println(pc.getName());%></label>
			            <%}
			        %>
			    </div>
			 </div>
		 </td>
 		<td style="width:100%; margin-top:0px; margin-bottom: 0px">
			<div class="form-group">
				<br>
				<br>
			    <input style="width:250px" type="text" class="form-control" id="searchInput" placeholder="Lokacija" />
			</div>
		 </td>
	 </tr>
</table>
<br>
<div class="tab-pane fade show active" id="posts" role="tabpanel" aria-labelledby="posts-tab">
    <div class="form-group">
        <textarea class="form-control" id="dangerDetails" rows="3" placeholder="Unesite tekst"></textarea>
    </div>
</div>
<div style="float:right">
<label>
<span>Hitno?</span>
<input type="checkbox" id="emergencyCb" class="checkbox style-2 right">
</label> </div>
                         <fieldset class="form-group">
        <a href="javascript:void(0)" onclick="$('#pro-image').click()">Zakači</a>
        <input accept="image/*,video/*" type="file" id="pro-image" name="pro-image" style="display: none;" class="form-control" onChange="readImage()" multiple>
    </fieldset>
                                   <div class="preview-images-zone">
    </div>
  
<br>

<br>
   <% 
   List<Post> posts = postBean.getAllActiveUnemergencyPosts();
  	  %>  
                        <div style="float:right" class="btn-toolbar justify-content-between">
                            <div class="btn-group">
                                <button type="submit" class="btn btn-primary" onClick="shareEmergency('<%=posts.size()%>', '<%=userBean.getUser().getUsername()%>', '<%=userBean.getUser().getFirstName() + " " + userBean.getUser().getLastName()%>','<%=userBean.getUser().getPhoto()%>')">Podijeli</button>
                            </div>
                            
                        </div>
                    </div>
                </div>
 
<div class="postsZone">

   						  <%
   						 	   int[] i = {0}; 
				           	   for(Post p : posts){
				           	%>
				           	
				           	                <div style=" margin-top: 15px;" class="card gedf-card">
                    <div class="card-header">
                        <div class="d-flex justify-content-between align-items-center">
                            <div class="d-flex justify-content-between align-items-center">
                                <div class="mr-2">
                                    <img class="rounded-circle" width="70" src=<%=p.getCreator().getPhoto()%> alt="">
                                </div>
                                <div class="ml-2">
                                    <div class="h5 m-0">@<%out.println(p.getCreator().getUsername());%></div>
                                    <div class="h7 text-muted"><%out.println(p.getCreator().getFirstName() + " " + p.getCreator().getLastName());%></div>
                                </div>
                            </div>
                        </div>

                    </div>
                             <%
                        Date postTime = p.getCreationTime();
                        Long result = ((new Date().getTime()/60000) - (postTime.getTime()/60000));
                       	String differnce;
                        if(result > 60)
                       	{
                       		result /= 60;
                        	if(result > 730){
                           		result /= 730;
                           		differnce = result.toString() + " mjeseci";
                           	}else{
                       		differnce = result.toString() + " h";
                       	}}else
                       	{
                       		differnce = result.toString() + " min";
                       	}
                        %>
                    
                    <div class="card-body">
                        <div class="text-muted h7 mb-2"> <i class="fa fa-clock-o"> Prije <%=differnce%> </i>
                     
                        <%
                        String location = p.getLocation();
                        if(location != null && !location.isEmpty()){%>
                    	<i style="float:right" class="fa fa-map-marker" aria-hidden="true"> <%=location%></i>
                        <%}%>
                        </div>
                        <h3 class="card-title">
                             <%
                            List<String> categories = p.getCategories();	
                            if(categories != null){
                            	for(String category : categories){
                            		if(categories.indexOf(category) == 0)
                            			out.print("Kategorije opasnosti: " + category);
                            		else
                            			out.print(", " + category);
                            }}
                            %>
                            </h3>
                        <%
                        String video = p.getVideo();
                        
                        if(video != null){%>
                  	     <video style="width:200px;height:200px" class="center-block img-responsive"  controls autoplay src='<%=video%>'></video>
                        <%} %>
                       
                        <%
   						List<String> images = p.getImages();
                        String firstImage ="";
                        
                        if(images != null){
                        if(images.size() > 0)
                        	firstImage = images.get(0);
                      
                        for(String image : images){
                        %>
                        <img width="200" height="200"  class="center-block img-responsive" src='<%=image%>' />
                        <%}}%>
                       
                        <p class="card-text">
                             <% out.println(p.getText()); %>
                        </p>
                       	<%
                       	String link;
                       	if((link = p.getLink()) != null){
                       	if(link.contains("youtube")){%>
                       	<a href=<%=link%> class="card-link">Više</a>	
                       	<%}else{%>
                        <a href=<%=link%> class="card-link">Više</a>
                        <%}}%>
                    </div>
                    <div class="card-footer">
                       <button type="button" onClick="focusShareOnFacebook('<%=p.getText()%>', '<%=firstImage%>', '<%=location%>','<%=p.getLink()%>' )" class="btn btn-link"><i class="fa fa-facebook-square"></i></button>
                       <button type="button" onClick="focusShareOnTwitter('<%=p.getText()%>','<%=p.getLink()%>', '<%=userBean.getUser().getUsername()%>', '<%=location%>')" class="btn btn-link"><i class="fa fa-twitter-square"></i></button>
                    </div>
                    <%if(!p.getIsFeed()){ %>
                    <div class="commentsZone<%=i[0]%>">  
                    <% List<Comment> postComments = p.getCommments();   
                    for(Comment comment : postComments){
                    	User user = UserDAO.getById(comment.getUserId()); 
                        Date commentTime = comment.getTime();
                        Long resultTime = ((new Date().getTime()/60000) - (commentTime.getTime()/60000));
                       	String differnceTime;
                        if(resultTime > 60)
                       	{
                        	resultTime /= 60;
                       		
                        	if(result > 730){
                           		result /= 730;
                           		differnceTime = result.toString() + " mjeseci";
                           	}else
                       			differnceTime = resultTime.toString() + " h";
                       	}else
                       	{
                       		differnceTime = resultTime.toString() + " min";
                       	}
                        if(resultTime < 0 )
                       		System.out.println((new Date())  + " " + (commentTime));
                        
                        %>
                    
                    <div class="row" style="margin:5px" >
    <div class="col-sm-2 text-center">
                <img class="rounded-circle" width="60" src="<%=user.getPhoto()%>" alt="">
    </div>
    <div class="col-sm-10">
      <h4><%=user.getFirstName()  + " " + user.getLastName()%></h4>
      <div class="text-muted h7 mb-2"><i class="fa fa-clock-o"></i> Prije <%=differnceTime%></div>
      <p><%=comment.getText()%></p>
      <%String image = comment.getImage();
      if(image!= null){%>
      <img height="170" width="170" src='<%=image%>'>
      <%}%>
      <br>
      </div>
    </div>
    <%  if(postComments.indexOf(comment) != postComments.size()-1){ %>
    	<hr>	
    <%}}%>
    <div class="card gedf-card">
            <div class="panel panel-info">
                <div class="panel-body">
                    <textarea id="commentBox<%=i[0]%>" style="width:100%" placeholder="Komentar" class="pb-cmnt-textarea" onkeypress="onEnterPress('<%=i[0]%>', '<%=userBean.getUser().getUsername()%>', '<%=userBean.getUser().getPhoto()%>', '<%=p.getId()%>', '<%=userBean.getUser().getId()%>');"></textarea>
      <div style="float:right; display: none;" class="preview-images-zone<%=i[0]%>"></div>      
            </div>
        </div>
    </div>  
                </div>
                    <fieldset style="float:right" class="form-group">
        <a  href="javascript:void(0)" style="float:right;" onclick="$('#pro-image2<%=i[0]%>').click()"><span class="fa fa-picture-o fa-lg"></span>Dodaj sliku</a>
      
        <input type="file" id="pro-image2<%=i[0]%>" name="pro-image2<%=i[0]%>" style="display: none;" class="form-control" onChange="readImageForComment('<%=i[0] %>')"></input>
    </fieldset>
    <%}%>
                </div>
				      <br>     	
				           	 <%
				           	 i[0]++;
				           	 } %>
      <br>
	  </div>
      <br><br><br>
      </div>
      <div id="weatherCards" class="col-md-3 weather-cards">
			<div class="card gedf-card">
				<h3 id="weather-city-name-0"></h3>
				<h5 id="weather-temperature-0"></h5>
				<h5 id="weather-pressure-0"></h5>
				<h5 id="weather-humidity-0"></h5>
				<h5 id="weather-wind-0"></h5>
			</div>
			<div class="card gedf-card">
				<h3 id="weather-city-name-1"></h3>
				<h5 id="weather-temperature-1"></h5>
				<h5 id="weather-pressure-1"></h5>
				<h5 id="weather-humidity-1"></h5>
				<h5 id="weather-wind-1"></h5>
			</div>
			<div class="card gedf-card">
				<h3 id="weather-city-name-2"></h3>
				<h5 id="weather-temperature-2"></h5>
				<h5 id="weather-pressure-2"></h5>
				<h5 id="weather-humidity-2"></h5>
				<h5 id="weather-wind-2"></h5>
			</div>
			<label id="errorLabel" style="visibility:hidden;"></label>
      </div>
      <script>
	      var country = "<%= userBean.getUser().getCountry() %>";
	      fillCountries(country);
      </script>
  
            </div>
        </div>
</body>
</html>