<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import='java.util.*, com.rgdgr8.travel_thru_air.*'%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home | Deals</title>
</head>
<body>
	<%
		String head = request.getAttribute("head").toString();
		out.print(head);
	%>
	<c:forEach var="offer" items="${offers}">
		<h3>
			<c:out value="${offer}" />
		</h3>
	</c:forEach>
	<form action="search">
		From <input name='from'><br>
		To <input name='to'><br>
		Date <input name='date' type="date" value=""> (optional) <br> 
		Hour <input name='hour' type="number" min='0' max='23'> (optional) <!-- Minute <input name='min' type="number" min='0' max='59'>--><br>
		<input type="submit"> <button type='button' onclick="javascript:date.value=''">Clear Date</button>
	</form>
	<%-- <%
		List<List<Flight>> search = (List<List<Flight>>)request.getAttribute("search");
		if(search!=null){
			for(int i=0;i<search.size();i++){
				out.println("<h2>Route "+(i+1)+": ("+search.get(i).size()+" legs)</h2>");
				for(Flight f : search.get(i)){
					out.println("<h3>"+f+"</h3>");
				}
			}
		}
	%> --%>
	<c:forEach var="flights" items="${search}" varStatus="loop">
		<h2><c:out value="Route ${loop.count} has ${flights.size()} legs:"></c:out></h2>
		<c:forEach var="flight" items="${flights}">
			<h3>
				<c:out value="${flight}" />
			</h3>
		</c:forEach>
	</c:forEach>
</body>
</html>