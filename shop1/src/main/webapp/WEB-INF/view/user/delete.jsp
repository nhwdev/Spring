<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>회원탈퇴</title>
</head>
<body>
<table class="table">
    <tr><td>아이디</td><td>${user.userid}</td></tr>
    <tr><td>이름</td><td>${user.username}</td></tr>
    <tr><td>생년월일</td><td><fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/></td></tr>
</table>
<form method="post" action="delete" name="deleteForm">
    <input type="hidden" name="userid" value="${param.userid}">
    비밀번호 : <input type="password" name="password">
    <a href="javascript:document.deleteForm.submit()" class="btn btn-danger">탈퇴하기</a>
</form>
</body>
</html>
