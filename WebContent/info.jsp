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
	</head>
	<body>
		<form action="Controler" method="GET">
			<input type="hidden" name="action" value="info"></input>
			<select name="country">
				<c:forEach items="${countries}" var="country">
					<option value="${country}">${country}</option>
				</c:forEach>
			</select>
			<button type="submit">Let's look at this country!</button>
		</form>
	</body>
	
	<div>Country : ${country.name}</div>
	<div>Capital : ${country.capital}</div>
	<div>Nb Innhabitants : ${country.nbInhabitants}</div>
</body>
</html>