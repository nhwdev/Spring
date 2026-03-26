<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%--
    isErorPage="true" : 발생한 Exception 객체가 전달됨.
--%>
<script>
    // exception : exception.CartException 객체
    alert("${exception.message}") // exception.getMessage() 함수 호출
    location.href="${exception.url}" // exception.getUrl() 함수호출
</script>
