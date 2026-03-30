<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>회원 목록</title>
    <style>
        .noline {
            text-decoration: none;
        }
    </style>
</head>
<body>
<form action="mailform" method="post">
    <table class="table">
        <tr><th>아이디</th><th>이름</th><th>전화번호</th><th>생년월일</th><th>이메일</th><th>관리</th>
            <th><input type="checkbox" name="allchk" onchange="allchkbox(this)"></th></tr>
        <c:forEach items="${list}" var="user">
            <tr><td>${user.userid}</td>
                <td>${user.username}</td>
                <td>${user.phoneno}</td>
                <td><fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/></td>
                <td>${user.email}</td>
                <td><a href="../user/update?userid=${user.userid}">수정</a>
                    <a href="../user/delete?userid=${user.userid}">탈퇴</a>
                    <a href="../user/mypage?userid=${user.userid}">회원정보</a></td>
                <td><input type="checkbox" name="idchks" class="idchks" value="${user.userid}" class="form-check-input"/></td></tr>
        </c:forEach>
        <tr><td colspan="7"><input type="submit" value="메일보내기"></td></tr>
    </table>
</form>
<script type="text/javascript">
    function allchkbox(allchk) {
        $(".idchks").prop("checked", allchk.checked)
    }
</script>
</body>
</html>
