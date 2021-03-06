<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Prog.kiev.ua</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <h2 style="text-align:center">Advertisements List</h2>

    <form class="form-inline" role="form" action="/SpringMVC_war_exploded/search" method="post">
        <input type="text" class="form-control" name="pattern" placeholder="Search">
        <input type="submit" class="btn btn-default" value="Search">
    </form>

    <form class="form-inline" role="form">
        <table class="table table-striped">
            <thead>
            <tr>
                <td><b></b></td>
                <td><b>Photo</b></td>
                <td><b>Name</b></td>
                <td><b>Short Desc</b></td>
                <td><b>Long Desc</b></td>
                <td><b>Phone</b></td>
                <td><b>Price</b></td>
                <td><b>Action</b></td>
            </tr>
            </thead>
            <c:forEach items="${advs}" var="adv">
                <tr>
                        <%--Добавляем чекбоксы--%>
                    <td><input type="checkbox" name="selected" value="${adv.id}"></td>
                    <td><img height="40" width="40" src="/SpringMVC_war_exploded/image/${adv.photo.id}" /></td>
                    <td>${adv.name}</td>
                    <td>${adv.shortDesc}</td>
                    <td>${adv.longDesc}</td>
                    <td>${adv.phone}</td>
                    <td>${adv.price}</td>
                    <td>
                        <a href="/SpringMVC_war_exploded/remove?id=${adv.id}" class="text-warning">Remove</a>
                        <br>
                        <a href="/SpringMVC_war_exploded/delete?id=${adv.id}" class="text-danger">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <%--Кнопка для удаления объявлений в корзину--%>
        <input type="submit" class="btn btn-warning" value="Remove selected"
               formaction="/SpringMVC_war_exploded/remove_selected">
        <%--Кнопка для безвозвратного удаления объявлений--%>
        <input type="submit" class="btn btn-danger" value="Delete selected"
               formaction="/SpringMVC_war_exploded/delete_selected">
    </form>

    <br>

    <form class="form-inline" role="form" method="post">
        <input type="submit" class="btn btn-primary" value="Add new"
               formaction="/SpringMVC_war_exploded/add_page">
        <input type="submit" class="btn btn-success" value="Trash"
               formaction="/SpringMVC_war_exploded/view_trash">
    </form>

</div>
</body>
</html>