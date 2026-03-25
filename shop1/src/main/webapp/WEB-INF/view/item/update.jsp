<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
    1. 파일 업로드
    2. 유효성 검증 : 입력값 검증. 백엔드에서 실행
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> <%-- 전체 스프링 태그 --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> <%-- html 관련된 태그 --%>
<html>
<head>
    <title>상품등록</title>
</head>
<body>
<%--
    <form:form ... > : html의 form 태그
    modelAttribute="item" : 화면 호출시 item 객체 필요
    action="create" : submit 버튼 클릭시 파라미터값을 가지고 create url 요청
    enctype="multipart/form-data" : 파일 업로드. 자동으로 method="post" 설정

    html 태그로 변환
    <form action="create" enctype="multipart/form-data" method="post">
--%>
<form:form modelAttribute="item" action="update" enctype="multipart/form-data">
    <%-- <input type="hidden" name="id" id="id" value="${id}"> --%>
    <form:hidden path="id"/>
    <form:hidden path="pictureUrl"/> <%-- 원래 파일정보 저장 --%>
    <h2>상품 정보 수정</h2>
    <table class="table">
        <tr><td>상품명</td>
            <td><form:input path="name"/></td>
            <td><font color="red"><form:errors path="name"/></font></td></tr>
        <tr><td>상품가격</td>
            <td><form:input path="price"/></td>
            <td><font color="red"><form:errors path="price"/></font></td></tr>
        <tr><td>상품이미지</td>
            <td colspan="2"><input type="file" name="picture"></td>
            <td>${item.pictureUrl}</td></tr>
        <tr><td>상품설명</td>
            <td><form:textarea path="description" cols="20" rows="5"/></td>
            <td><font color="red"><form:errors path="description"/></font></td></tr>
        <tr><td colspan="3"><input type="submit" value="수정등록">&nbsp;
            <input type="button" value="상품목록" onclick="location.href='list'"></td></tr>
    </table>
</form:form>
</body>
</html>
