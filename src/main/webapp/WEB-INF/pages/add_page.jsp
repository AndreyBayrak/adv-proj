<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <form role="form" enctype="multipart/form-data" class="form-horizontal" action="/SpringMVC_war_exploded/add" method="post">
        <h2 style="text-align:center">New advertisement</h2>
        <h4>Complete the form</h4>

        <table class="table table-striped">
            <colgroup>
                <col class="col-md-1">
                <col class="col-md-7">
            </colgroup>
            <tbody>
            <tr>
                <td><b>Name</b></td>
                <td><input type="text" class="form-control" name="name" maxlength="100"></td>
            </tr>
            <tr>
                <td><b>Short description</b></td>
                <td><input type="text" class="form-control" name="shortDesc" maxlength="100"></td>
            </tr>
            <tr>
                <td><b>Long description</b></td>
                <td><input type="text" class="form-control" name="longDesc" maxlength="500"></td>
            </tr>
            <tr>
                <td><b>Phone</b></td>
                <td><input type="text" class="form-control" name="phone" maxlength="32"></td>
            </tr>
            <tr>
                <td><b>Price</b></td>
                <td><input type="text" class="form-control" name="price" maxlength="15"></td>
            </tr>
            <tr>
                <td><b>Photo</b></td>
                <td><input type="file" name="photo" accept="image/jpeg,image/png"></td>
            </tr>
            </tbody>
        </table>

        <input type="submit" class="btn btn-primary" value="Add form data">
    </form>

    <br>

    <form role="form" enctype="multipart/form-data" class="form-horizontal" action="/SpringMVC_war_exploded/add-file" method="post">
        <h4 style="display: inline">Or load advertisements from an XML file:</h4>
        <input style="display: inline" type="file" name="xml-file" accept="application/xml">
        <input style="display: block" type="submit" class="btn btn-primary" value="Add file data">
    </form>
</div>
</body>
</html>