<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>게시글 수정</title>
</head>
<body>
<form:form modelAttribute="board" action="update" enctype="multipart/form-data" name="f">
    <form:hidden path="num"/>
    <form:hidden path="boardid"/>
    <form:hidden path="fileurl"/> <%-- 기존에 업로드 되어있던 파일 이름 --%>
    <h4 style="font-weight: bold">${board.boardName} 수정</h4>
    <table class="table">
        <tr><td>글쓴이</td><td><form:input path="writer"/><form:errors path="writer" cssStyle="color: red" cssClass="form-control"/></td></tr>
        <tr><td>비밀번호</td><td><form:password path="pass"/><form:errors path="pass" cssStyle="color: red" cssClass="form-control"/></td></tr>
        <tr><td>제목</td><td><form:input path="title"/><form:errors path="title" cssStyle="color: red" cssClass="form-control"/></td></tr>
        <tr><td>내용</td><td><form:input path="content" rows="15" cols="80" id="summernote" cssClass="form-control"/><form:errors path="content" cssStyle="color: red"/></td></tr>
        <tr><td>첨부파일</td>
            <td><c:if test="${!empty board.fileurl}">
                <div id="file_desc">
                    <a href="file/${board.fileurl}">${board.fileurl}</a>
                    <a href="javascript:file_delete()" class="btn btn-danger">첨부파일삭제</a>
                </div>
                </c:if>
            <input type="file" name="file1"></td></tr>
        <tr><td colspan="2" class="text-center">
                <a href="javascript:document.f.submit()" class="btn btn-success">수정</a>
                <a href="list" class="btn btn-secondary">목록</a></td></tr>
    </table>
</form:form>
<script type="text/javascript">
    function file_delete() {
        document.f.fileurl.value = ""
        file_desc.style.display = "none"
    }
</script>
</body>
</html>
