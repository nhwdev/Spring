<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>게시판 삭제 화면</title>
</head>
<body>
<form action="delete" method="post" name="f">
    <spring:hasBindErrors name="board">
        <font color="red"><c:forEach items="${errors.globalErrors}" var="error"><spring:message code="${error.code}"/></c:forEach></font>
    </spring:hasBindErrors>
    <input type="hidden" name="num" value="${board.num}">
    <input type="hidden" name="boardid" value="${param.boardid}">
    <input type="hidden" name="title" value="${board.title}">
    <h4>게시글 삭제</h4>
    <table class="table">
        <tr><td>제목</td>
            <td>${board.title}</td></tr>
        <tr><td>게시글 비밀번호</td>
            <td><input type="password" name="pass" class="form-control"/></td></tr>
        <tr><td colspan="2" class="text-right"><button class="btn btn-danger">삭제</button></td></tr>
    </table>
</form>

</body>
</html>
