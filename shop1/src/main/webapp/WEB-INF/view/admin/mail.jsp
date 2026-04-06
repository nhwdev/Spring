<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>메일 보내기</title>
</head>
<body>
<h2>메일 보내기</h2>
<form:form modelAttribute="mail" name="mailform" action="mail" enctype="multipart/form-data">
    Google 아이디 ↴ <form:input path="googleid" class="form-control"/>
              <form:errors path="googleid" class="text-danger"/><br>
    Google 패스워드 ↴  <form:password path="googlepw" class="form-control"/>
              <form:errors path="googlepw" class="text-danger"/><br>
    <table class="table">
        <tr><td>보내는 사람</td><td>${loginUser.email}</td></tr>
        <tr><td>받는사람</td><td><form:input path="recipient" class="form-control"/></td></tr>
        <tr><td>제목</td><td><form:input path="title" class="form-control"/></td>
                             <form:errors path="title" class="text-danger"/></tr>
        <tr><td>메시지 형식</td>
            <td><select name="mtype" class="form-control">
                <option value="text/html; charset=utf-8">HTML</option>
                <option value="text/plain; charset=utf-8">TEXT</option></select></td></tr>
        <tr><td>첨부파일1</td><td><input type="file" name="file1"></td></tr>
        <tr><td>첨부파일2</td><td><input type="file" name="file1"></td></tr>
        <tr><td colspan="2"><form:textarea path="contents" cols="120" rows="10" class="form-control" id="summernote"/>
                            <form:errors path="contents" class="text-danger"/></td></tr>
        <tr><td colspan="2" class="text-center"><button class="btn btn-primary">메일 보내기</button></td></tr>
    </table>
</form:form>
<script type="text/javascript">
    $("#summernote").summernote({
        height:300,
        /*
         * callbacks : 이벤트 처리
         * onImageUpload : 이미지 업로드시 처리
         * onInit : 에디터 로드시. 초기화면 설정 ...
         */
        callbacks : {
            onImageUpload : function(images) {
                for(let i=0; i < images.length; i++) {
                    sendFile(images[i])
                }
            }
        }
    })
</script>
</body>
</html>
