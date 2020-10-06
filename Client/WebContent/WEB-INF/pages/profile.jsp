<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.etfbl.dto.User"%>
<jsp:useBean id="userBean" type="net.etfbl.beans.UserBean" scope="session"/>

<script><%@include file="/js/profile.js"%></script>
<script><%@include file="/js/script.js"%></script>

<%
	User user = userBean.getUser();
	%>
<head>
<script> window.onload = initializeComponents('<%=userBean.getUser().getUsername()%>', '<%=userBean.getUser().getMail()%>');</script>
  <title>Izmjena profila</title>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
</head>
<body>
<div class="container bootstrap snippet">
    <div class="row">
  		<div class="col-sm-3">
               
      <div class="text-center">
     <%
     String userPhoto = user.getPhoto();
     if(userPhoto == null){ %>
        <img id="profileImage" src="https://www.serenbooks.com/sites/default/files/default_images/default-user-image.png" class="avatar img-circle img-thumbnail" alt="avatar">
     <%}else{ %>  
        <img id="profileImage" src=<%=userPhoto%> class="avatar img-circle img-thumbnail" alt="avatar">
    
        <%} %>
     <div class="d-flex ml-1 mr-1 mt-1" style="float:right;">
						      <label class="btn btn-secondary" style="background-color:blue"> Pronađite novu sliku <input id="image-input" type="file" accept="image/*" hidden="hidden" onchange="showImage()"> </label>
						</div></div><br>
        </div>
    	<div class="col-sm-9">   
          <div class="tab-content">
            <div class="tab-pane active" id="home">
                <hr>
                  <form method="POST" action="?action=login" id="profileForm" class="form-horizontal">
                      <div class="form-group">
                          
                          <div class="col-xs-6">
                              <label for="firstName"><h4>Ime</h4></label>
                              <input type="text" class="form-control" name="firstName" id="firstName" placeholder="Ime" oninvalid="this.setCustomValidity('Unesite pravilno ime.')" oninput="this.setCustomValidity('')" required value=<%= user.getFirstName() %>>
                           <label id="firstNameLabel"></label>
                          </div>
                      </div>
          
                      <div class="form-group">
                          
                          <div class="col-xs-6">
                              <label for="lastName"><h4>Prezime</h4></label>
                              <input type="text" class="form-control" name="lastName" id="lastName" placeholder="Prezime" oninvalid="this.setCustomValidity('Unesite pravilno prezime.')" oninput="this.setCustomValidity('')" required value=<%= user.getLastName() %>>
                         <label id="lastNameLabel"></label>
                          </div>
                      </div>
          
                      <div class="form-group">
                          <div class="col-xs-6">
                             <label for="username"><h4>Korisničko ime</h4></label>
                              <input type="text" class="form-control" name="username" id="username" placeholder="Korisničko ime" oninvalid="this.setCustomValidity('Unesite pravilno korisničko ime.')" oninput="this.setCustomValidity('')" required value=<%= user.getUsername() %>>
                           <label id="usernameLabel"></label>
                          </div>
                      </div>
                      <div class="form-group">
             
                          <div class="col-xs-6">
                              <label for="email"><h4>Email</h4></label>
                              <input type="email" class="form-control" name="email" id="email" placeholder="user@host.com" title="email" required pattern="[^@]+@[^@]+.[a-zA-Z]{2,6}"  oninvalid="this.setCustomValidity('Unesite pravilno mail. ')" onchange="try{setCustomValidity('')}catch(e){}" oninput="setCustomValidity(' ')"value=<%= user.getMail() %>>
                          <label id="mailLabel"></label>
                          </div>
                      </div>
                      <div class="form-group">
                          
                          <div class="col-xs-6">
                              <label for="password"><h4>Lozinka</h4></label>
                              <input type="password" class="form-control" name="password" id="password" placeholder="Lozinka" oninvalid="this.setCustomValidity('Unesite pravilno lozinku.')" oninput="this.setCustomValidity('')" required value=<%= user.getPassword() %>>
                        <label id="passwordLabel"></label>
                          </div>
                      </div>
                      <div class="form-group">
                          
                          <div class="col-xs-6">
                              <label for="password"><h4>Potvrda lozinke</h4></label>
                              <input type="password" class="form-control" name="confirmedPassword" id="confirmedPassword" placeholder="Potvrdite lozinku" oninvalid="this.setCustomValidity('Unesite pravilno lozinku.')" oninput="this.setCustomValidity('')" required value=<%= user.getPassword() %>>
                         	  <label id="confirmedPasswordLabel"></label>
                          </div>
                      </div>
                      <div class="form-group">
                          <div class="col-xs-7">
                             <label for="country"><h4>Država</h4></label>
                             
                               <select class="form-control" id="country"  onChange="fillRegions()">
                <script>fillCountries();</script> 
                <%String country = userBean.getUser().getCountry();
                if(country != null){ %>
                <option selected>  
  				<%=country%>
  				</option> 
  				<%}%>
                </select>
                         </div>
                         </div>
                     
                       <div class="form-group">
                    <div class="col-xs-7">
                             <label for="region"><h4>Region</h4></label>
                               <select class="form-control" id="region"  onChange="fillCities()">
                               <%String region = userBean.getUser().getRegion();
                				if(region != null){ %>
                				<option selected>  
  								<%=region%>
  								</option> 
  								<%}%>
                </select>
                         </div>
                         </div> 
                    
                       <div class="form-group">
                    <div class="col-xs-7">
                             <label for="city"><h4>Grad</h4></label>
                               <select id="city" class="form-control">
                                <%String city = userBean.getUser().getCity();
                				if(city != null){ %>
                				<option selected>  
  								<%=city%>
  								</option> 
  								<%}%>
               		 </select>
                         </div>
                         </div> 
                      
                       <div class="form-group">
                    <div class="col-xs-7" style="margin-left:25px">
                    <br>
                    <%Integer notificationOnMail = userBean.getUser().getNotificationOnMail();
                   	  Integer notificationInApp = userBean.getUser().getNotificationInApp();	
                      String display;
                      String nOM = "";
                      String nIA = ""; 
                      String cbChecked = "";
                   	  if((notificationOnMail != null) && (notificationOnMail == 1)){
                   		 display = "block";
       					 nOM = "checked";
       				   	 cbChecked = "checked";
                      }else if((notificationInApp != null) && (notificationInApp == 1)){
                    		 display = "block";
           					 nIA = "checked";
           				   	 cbChecked = "checked";
                      }
                   	   else
                    	 display = "none";
                   	 if("".equals(cbChecked)){
                   	 %>
                            <input  style="position: absolute;margin-left: 600px;"  type="checkbox" class="custom-control-input" id="notifications-blog" onChange="toggle_visibility()">
                              <%}else{ %>
                                  <input  style="position: absolute;margin-left: 600px;"  type="checkbox" class="custom-control-input" id="notifications-blog" checked="checked" onChange="toggle_visibility()">
                              <%}%>
                                <label class="custom-control-label" for="notifications-blog">Notifikacije o hitnim upozorenjima</label>
                                    <div class="col-sm-6" id="radioButtons" style="display:<%=display%>">
                        <div class="row">
                            <div class="col-sm-4">
                                <label class="radio-inline">
                                    <input type="radio" name="notifications" value="notificationsOnMail" checked="<%=nOM%>">Na mail
                                </label>
                            </div>
                            <div class="col-sm-4">
                                <label class="radio-inline">
                                    <input type="radio" name="notifications" value="notificationsInApp" checked="<%=nIA%>" style="padding-right: 20px;">U aplikaciji
                                </label>
                            </div>
                        </div>
                    </div>
                
                         </div>
                         </div>         
                              
                      <div class="form-group">
                           <div class="col-xs-12">
                                <br>
                              	<button class="btn btn-lg btn-success" style="background-color:blue" type="submit" onclick="event.preventDefault(); return validateFields(<%=userBean.getUser().getNumberOfLogins() != null? userBean.getUser().getNumberOfLogins():0%>)"><i class="glyphicon glyphicon-ok-sign"></i> Sačuvaj</button>
                               	&nbsp&nbsp<label style="color: green" id="successLabel"></label>
                            </div>
                      </div>
              	</form>
              
              <hr>
              
             </div>
              </div>
        </div>
    </div>
</div>
</body>                                                      