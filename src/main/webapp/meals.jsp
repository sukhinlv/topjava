<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
    <title>Meals list</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<section>
    <table>
        <a href="meals?action=new"><img src="<c:url value='/img/add.png'/>" alt="new">Add new meal</a>
        <tr>
            <th>Date / Time</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        <jsp:useBean id="mealsTo" scope="request" type="java.util.List"/>
        <c:forEach items="${mealsTo}" var="mealTo">
            <tr class="${mealTo.excess ? 'excess' : 'notexcess'}">
                <td>${mealTo.dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"))}</td>
                <td>${mealTo.description}</td>
                <td>${mealTo.calories}</td>
                <td><img src="<c:url value='/img/delete.png'/>" alt="delete"></td>
                <td><img src="<c:url value='/img/pencil.png'/>" alt="edit"></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>