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
				<%@include file="selector.jsp" %>
			
				<div class="data">
					<div>Country : ${country.name}</div>
					<div>Capital : ${country.capital}</div>
					<div>Nb Innhabitants : ${country.nbInhabitants}</div>
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
				$.getJSON("https://maps.googleapis.com/maps/api/geocode/json?components=country:${country.name}&key=AIzaSyD3W99j-EKYSr4tCN8yileLK88d7eYoO8M&sensor=false",
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
</body>
</html>