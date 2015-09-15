<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date,java.text.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Country - Information about ${country.name}</title>
		
        <link rel=StyleSheet href="css/style.css" type="text/css"></link>
        
		<script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
	</head>
	<body>
	
		<div class="body">
			<div class="menu">
				<div class="selector">
					<form action="Controler" method="GET">
						<input type="hidden" name="action" value="info"></input>
						<select name="country">
							<c:forEach items="${countries}" var="country">
								<option value="${country}">${country}</option>
							</c:forEach>
						</select>
						<button type="submit">Go!</button>
					</form>
				</div>
			
				<div class="data">
					<div><img src="http://blogs.arts.ac.uk/libraryservices/files/2011/06/information-icon-UAL-blue-HQ-vers-1-062311.png"></img> Select a country</div>
					<div><img src="http://www.plantalecara.com/plantalecara.com/admin/uploads/manita%20cursor(1).jpg"></img> Put the cursor here</div>
				</div>
			</div>
		
			<div id="map"></div>
		</div>
		
		
		
		<!-- /!\ -->
	    <!-- Thank to not use my Google key "AIzaSyD3W99j-EKYSr4tCN8yileLK88d7eYoO8M" -->
	    <!-- Merci de ne pas utiliser ma clef Google "AIzaSyD3W99j-EKYSr4tCN8yileLK88d7eYoO8M" -->
	    <script type="text/javascript">
			var map;
			function initMap()
			{
				$.getJSON("https://maps.googleapis.com/maps/api/geocode/json?components=country:France&key=AIzaSyD3W99j-EKYSr4tCN8yileLK88d7eYoO8M&sensor=false",
						function(data)
						{
							map = new google.maps.Map(document.getElementById('map'),
							{
								center: data.results[0].geometry.location,
								zoom: 5
							});
					    });
			}
	    </script>
	    
		<!-- /!\ -->
	    <!-- Thank to not use my Google key "AIzaSyD3W99j-EKYSr4tCN8yileLK88d7eYoO8M" -->
	    <!-- Merci de ne pas utiliser ma clef Google "AIzaSyD3W99j-EKYSr4tCN8yileLK88d7eYoO8M" -->
		<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD3W99j-EKYSr4tCN8yileLK88d7eYoO8M&callback=initMap"></script>
	</body>
</html>