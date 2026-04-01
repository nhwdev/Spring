<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>주문 전 상품 목록 보기</title>
</head>
<body>
<h2>배송지 정보</h2>
<table class="table table-bordered">
    <tr><td>주문아이디</td><td>${loginUser.userid}</td></tr>
    <tr><td>이름</td><td>${loginUser.username}</td></tr>
    <tr><td>우편번호</td><td>${loginUser.postcode}</td></tr>
    <tr><td>주소</td><td>${loginUser.address}</td></tr>
    <tr><td>전화번호</td><td>${loginUser.phoneno}</td></tr>
</table>
<h2>구매 상품</h2>
<table class="table table-bordered">
    <tr><th>상품명</th><th>가격</th><th>수량</th><th>합계</th></tr>
    <c:forEach items="${sessionScope.cart.itemSetList}" var="itemSet" varStatus="stat">
        <tr><td>${itemSet.item.name}</td>
            <td><fmt:formatNumber value="${itemSet.item.price}" type="currency"/></td>
            <td><fmt:formatNumber value="${itemSet.quantity}" pattern="###,###"/></td>
            <td><fmt:formatNumber value="${itemSet.item.price * itemSet.quantity}" type="currency"/></td></tr>
    </c:forEach>
    <tr><td colspan="4" align="right">총 구입 금액 : <fmt:formatNumber value="${sessionScope.cart.total}" type="currency"/></td></tr>
    <tr><td colspan="4" class="text-center">
        <a href="orderitem" class="btn btn-primary">확정하기</a>&nbsp;
        <a href="../item/list" class="btn btn-success">상품 목록</a>&nbsp;</td></tr>
</table>
</body>
</html>
