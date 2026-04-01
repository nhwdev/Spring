<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${title} 찾기</title>
</head>
<body>
<table>
    <c:if test="${title=='아이디'}"><tr><th>${title} : </th><td>${result}</td></tr></c:if>
    <c:if test="${title!='아이디'}"><tr><th colspan="2">비밀번호를 이메일로 보내드렸습니다.</th></tr></c:if>
    <tr><td colspan="2">
        <c:if test="${title=='아이디'}">
            <input type="button" value="아이디 전송" onclick="sendclose()"></c:if>
        <c:if test="${title!='아이디'}">
            <input type="button" value="닫기" onclick="self.close()"></c:if>
    </td></tr>
</table>
<script type="text/javascript">
    function sendclose() {
        // opener : login 화면
        opener.document.loginform.userid.value='${result}'
        self.close() // 현재페이지 닫기
    }
</script>
</body>
</html>
