<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>회원 수정</title>
</head>
<body>
<h2>회원 수정</h2>
<form:form modelAttribute="user" method="post" action="update">
    <spring:hasBindErrors name="user">
        <font color="red">
            <c:forEach items="${errors.globalErrors}" var="error">
                <spring:message code="${error.code}"/><br>
            </c:forEach>
        </font>
    </spring:hasBindErrors>
    <table class="table">
        <tr><th width="15%">아이디</th>
            <td><form:input path="userid" readonly="true" class="form-control"/><font color="red"><form:errors path="userid"/></font></td></tr>
        <tr><th>비밀번호</th>
            <td><form:password path="password" class="form-control"/><font color="red"><form:errors path="password"/></font></td></tr>
        <tr><th>이름</th>
            <td><form:input path="username" class="form-control"/><font color="red"><form:errors path="username"/></font></td></tr>
        <tr><th>전화번호</th><td><form:input path="phoneno" class="form-control"/></td></tr>
        <tr><th>우편번호</th><td><form:input path="postcode" class="form-control"/></td></tr>
        <tr><th>주소</th><td><form:input path="address" class="form-control"/></td></tr>
        <tr><th>이메일</th>
            <td><form:input path="email" class="form-control"/><font color="red"><form:errors path="email"/></font></td></tr>
        <tr><th>생년월일</th>
            <td><form:input path="birthday" class="form-control"/><font color="red"><form:errors path="birthday"/></font></td></tr>
        <tr><th colspan="2" align="center">
                <button class="btn btn-primary">회원수정</button>
                <button type="reset" class="btn btn-secondary">초기화</button></th></tr>
    </table>
</form:form>
</body>
</html>
