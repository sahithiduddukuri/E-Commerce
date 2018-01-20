 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>

<head>

<title>EcomFront</title>

<meta name="viewport" content="width=device-width,initial-scale=1">

<meta charset="utf-8">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">

</head>

<body>

<jsp:include page="/WEB-INF/views/header.jsp"></jsp:include>

<div class="container">

<div class="row">

<div class="col-sm-4 item-photo">

<img style="max-width:100%;margin-top:30px;" src="${pageContext.request.contextPath}/resources/${prod.imagName}">

</div>

<div class="col-sm-5" style="border:0px solid gry">

<h3>${product.pname}</h3>

<h4>${product.pdescription}</h4>

<h4>${product.price}</h4>

<h5>${product.supplier.sname}</h5>

<div class="section" style="padding-bottom:20px;"></div>

<form action="${pageContext.request.contextPath}/addToCart/${product.pid}" method="post">



<input type="hidden" value="${product.pname}" name="pname"/>

<input type="hidden" value="${product.pid}" name="pid"/>

<input type="hidden" value="${product.price}" name="price"/>

<input type="hidden" value="${product.imagName}" name="imagName"/>





<input type="number" class="form-control"name="pQty" required/><br><br>

<input class="btn btn-warning btn-lg" type="submit" value="AddToCart">

<h6><span class="glyphicon-heart-empty" style="cursor:pointer;"></span>Wish List</h6>

</form>

</div>

</div>

<hr>

<div class="col-sm-9">

<h3>Product Details</h3>

<ul class="menu-items">

<li>here are at least 800 breeds. Dogs whose parents were the same breed </li>

<li>Pets are often called "man's best friend" because they fit in with human life.</li>

<li>Different dog breeds have different lifespans. </li>

</ul>

</div>

</div>







</body>