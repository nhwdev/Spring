<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>주문완료</title>
</head>
<body>
<h2>${order.user.username}님이 주문하신 정보 입니다.</h2>
<h2>배송지 정보</h2>
<table class="table">
  <tr><td>주문아이디</td><td>${sessionScope.loginUser.userid}</td></tr>
  <tr><td>이름</td><td>${sessionScope.loginUser.username}</td></tr>
  <tr><td>우편번호</td><td>${sessionScope.loginUser.postcode}</td></tr>
  <tr><td>주소</td><td>${sessionScope.loginUser.address}</td></tr>
  <tr><td>전화번호</td><td>${sessionScope.loginUser.phoneno}</td></tr>
</table>
<h2>주문상품</h2>
<table class="table">
  <tr><th>상품명</th><th>가격</th><th>수량</th><th>합계금액</th></tr>
  <c:forEach items="${order.itemList}" var="orderitem">
    <tr><td>${orderitem.item.name}</td>
        <td><fmt:formatNumber value="${orderitem.item.price}" type="currency"/></td>
        <td>${orderitem.quantity}</td>
        <td><fmt:formatNumber value="${orderitem.item.price * orderitem.quantity}" type="currency"/></td></tr>
  </c:forEach>
    <%--
    ${order.total} : Order 객체의 getTotal() 메서드의 결과 값
    --%>
  <tr><td colspan="4" align="right">총 주문금액 : <fmt:formatNumber value="${order.total}" type="currency"/></td></tr>
  <tr><td colspan="4"><a href="../item/list">상품목록</a></td></tr>
</table>
</body>
</html>
