<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>게시판 댓글 작성</title>
</head>
<body>
    <form:form modelAttribute="board" action="reply" method="post" name="f">
        <form:hidden path="num"/>
        <form:hidden path="boardid"/>
        <form:hidden path="grp"/>
        <form:hidden path="grplevel"/>
        <form:hidden path="grpstep"/>
        <h2>${board.boardName} 댓글 작성</h2>
        <table class="table">
            <tr><td>글쓴이</td><td><input type="text" name="writer" class="form-control"><form:errors path="writer" cssStyle="color: red"/></td></tr>
            <tr><td>비밀번호</td><td><form:password path="pass" cssClass="form-control" /><form:errors path="pass" cssStyle="color: red"/></td></tr>
            <tr><td>제목</td><td><form:input path="title" value="RE:${board.title}" cssClass="form-control"/><form:errors path="title" cssStyle="color: red"/></td></tr>
            <tr><td>내용</td><td><textarea name="content" rows="15" cols="80" id="summernote" class="form-control"></textarea><form:errors path="content" cssStyle="color: red"/></td></tr>
            <tr><td colspan="2" class="text-center"><button class="btn btn-primary">댓글 등록</button></td></tr>
        </table>
    </form:form>
<script>
   $("#summernote").summernote({
       height: 200,
       callbacks : {
           onImageUpload : function(images) {
               for(let i=0; i< images.length; i++){
                   sendFile(images[i])
               }
           }
       }
   })

   function sendFile(file) {
       let data = new FormData() // 파일업로드를 위한 컨테이너 객체 생성
       data.append("image", file) // file : 업로드 되는 데이터
       $.ajax({
           url: "/shop1/ajax/uploadImage",
           type: "post", // poist 방식의 요청
           data: data, // FormData 객체
           processData: false, // 문자열 전송❌ 파일업로드시 사용
           contentType: false, // 컨텐트 타입 설정❌ 파일업로드시 사용
           success: function (src) {
               console.log(src) // 업로드된 파일의 이름
               $("#summernote").summernote("insertImage", src)
           },
           error: function (e) {
               alert("이미지 업로드 실패: " + e.status)
           }
       })
   }
</script>
</body>
</html>
