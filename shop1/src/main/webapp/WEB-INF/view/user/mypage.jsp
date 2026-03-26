<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>mypage</title>
</head>
<body>
<div id="info" class="info"></div>
<table class="table">
    <tr><td>아이디</td><td>${user.userid}</td></tr>
    <tr><td>이름</td><td>${user.username}</td></tr>
    <tr><td>우편번호</td><td>${user.postcode}</td></tr>
    <tr><td>전화번호</td><td>${user.phoneno}</td></tr>
    <tr><td>이메일</td><td>${user.email}</td></tr>
    <tr><td>생년월일</td><td><fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/></td></tr>
</table>
<br>
<a href="logout">[로그아웃]</a>&nbsp;
<a href="update?userid=${user.userid}">[회원정보수정]</a>&nbsp;
<a href="password">[비밀번호수정]</a>&nbsp;
<c:if test="${loginUser.userid != 'admin'}">
    <a href="delete?userid=${user.userid}">[회원탈퇴]</a>&nbsp;
</c:if>
<c:if test="${loginUser.userid == 'admin'}">
    <a href="../admin/list">[회원목록]</a>&nbsp;
</c:if>
</body>
</html>
