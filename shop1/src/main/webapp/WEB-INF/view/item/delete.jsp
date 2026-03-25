<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>상품 삭제 전 확인</title>
</head>
<body>
<h2>상품 삭제 전 확인</h2>
<table class="table">
  <tr><td><img src="../img/${item.pictureUrl}"/></td>
    <td><table>
      <tr><td>상품명</td><td>${item.name}</td></tr>
      <tr><td>가격</td><td>${item.price}</td></tr>
      <tr><td>상품설명</td><td>${item.description}</td></tr>
      <tr><td colspan="2">
        <form action="delete" method="POST">
          <input type="hidden" name="id" value="${item.id}">
          <button>상품삭제</button>
          <button type="button" onclick="location.href='list'">상품목록</button>
        </form>
      </td> </tr>
    </table>
    </td></tr>
</table>
</body>
</html>