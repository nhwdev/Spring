<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>게시글 작성</title>
    <style>
        body { padding: 30px; }
        .table { width: 100%; border-collapse: collapse; max-width: 800px; }
        .table th, .table td { padding: 10px 14px; border: 1px solid #ddd; vertical-align: middle; }
        .table th { width: 120px; background: #f5f5f5; text-align: left; white-space: nowrap; }
        .form-control { width: 100%; padding: 6px 10px; font-size: 14px; box-sizing: border-box; }
        .btn { padding: 8px 18px; font-size: 14px; text-decoration: none; border-radius: 4px; cursor: pointer; }
        .btn-primary { background: #337ab7; color: #fff; }
        .btn-success { background: #5cb85c; color: #fff; }
        .text-center { text-align: center; }
    </style>
</head>
<body>
<form:form modelAttribute="board" action="write" enctype="multipart/form-data" name="f">
    <input type="hidden" name="boardid" value="${param.boardid}">
    <table class="table">
        <tr><th>글쓴이</th><td><form:input path="writer" class="form-control"/><form:errors path="writer" cssStyle="color:red;"/></td></tr>
        <tr><th>비밀번호</th><td><form:password path="pass" class="form-control"/><form:errors path="pass" cssStyle="color:red;"/></td></tr>
        <tr><th>제목</th><td><form:input path="title" class="form-control"/><form:errors path="title" cssStyle="color:red;"/></td></tr>
        <tr><th>내용</th>
            <td><form:textarea path="content" rows="15" cols="80" id="summernote"/><form:errors path="content" cssStyle="color:red;"/></td></tr>
        <tr><th>첨부파일</th><td><input type="file" name="file1"></td></tr>
        <tr><td colspan="2" class="text-center">
            <a href="javascript:document.f.submit()" class="btn btn-primary">게시글 등록</a>
            <a href="list?boardid=${param.boardid}" class="btn btn-success">게시글 목록</a>
        </td></tr>
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