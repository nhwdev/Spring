<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>장바구니</title>
</head>
<body>
<h2>장바구니</h2>
<table class="table">
    <tr><td colspan="4">장바구니 상품 목록</td></tr>
    <tr><th>상품명</th><th>가격</th><th>수량</th><th>합계</th></tr>
    <c:forEach items="${cart.itemSetList}" var="set" varStatus="stat">
        <tr><td>${set.item.name}</td>
            <td><fmt:formatNumber value="${set.item.price}" pattern="###,###"/></td>
            <td><fmt:formatNumber value="${set.quantity}" pattern="###,###"/></td>
            <td><fmt:formatNumber value="${set.quantity * set.item.price}" pattern="###,###"/>
                <a href="cartDelete?index=${stat.index}">⨂</a></td></tr>
<%--        <c:set var="total" value="${total + set.quantity * set.item.price}"/>--%>
    </c:forEach>
    <tr><td colspan="4" align="right">총 구입 금액: <fmt:formatNumber value="${cart.total}" pattern="###,###"/>원 </td></tr>
     <%-- ${cart.total} : cart 객체의 getTotal() 메서드 호출. 결과값을 출력 --%>
</table>
<br>${message}<br>
<a href="../item/list" class="btn btn-secondary">상품목록</a>
<a href="checkout" class="btn btn-success">주문하기</a>
</body>
</html>
