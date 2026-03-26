<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title>회원 가입</title>
</head>
<body>
<h2>회원 가입</h2>
<form:form modelAttribute="user" method="post" action="join">
    <%--  errors.globalErrors : BindingResulte.reject(코드) 등록한 정보들 --%>
    <spring:hasBindErrors name="user">
        <font color="red">
            <c:forEach items="${errors.globalErrors}" var="error">
                <%-- <spring:message..> : messageSource 객체를 --%>
                <spring:message code="${error.code}"/><br>
            </c:forEach>
        </font>
    </spring:hasBindErrors>

    <table>
        <tr><td>아이디</td>
            <td><form:input path="userid"/><font color="red"><form:errors path="userid"/></font></td></tr>
        <tr><td>비밀번호</td>
            <td><form:password path="password"/><font color="red"><form:errors path="password"/></font></td></tr>
        <tr><td>이름</td>
            <td><form:input path="username"/><font color="red"><form:errors path="username"/></font></td></tr>
        <tr><td>전화번호</td><td><form:input path="phoneno"/></td></tr>
        <tr><td>우편번호</td><td><form:input path="postcode"/></td></tr>
        <tr><td>주소</td><td><form:input path="address"/></td></tr>
        <tr><td>이메일</td><td><form:input path="email"/>
            <font color="red"><form:errors path="email"/></font></td></tr>
        <tr><td>생년월일</td><td><form:input path="birthday"/>
            <font color="red"><form:errors path="birthday"/></font></td></tr>
        <tr><td><input type="submit" value="회원가입"><input type="reset" value="초기화"></td></tr>
    </table>
</form:form>
</body>
</html>
