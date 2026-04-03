<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>게시물 상세보기</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <style type="text/css">
        .leftcol {
            text-align: left;
            vertical-align: top;
        }
        .lefttoptable {
            height: auto;
            border-width: 0px;
            text-align: left;
            vertical-align: top;
            padding: 0px;
        }
        .board-name {
            font-size: 1.5rem;
            font-weight: bold;
        }
        .readcnt {
            font-size: 0.8rem;
            color: gray;
        }
    </style>
</head>
<body>
<table class="table">
    <tr><td colspan="2" class="board-name">${board.boardName}</td></tr>
    <tr><td width="15%">글쓴이</td><td width="85%" class="leftcol">${board.writer}</td></tr>
    <tr><td>제목</td><td class="leftcol">${board.title}</td></tr>
    <tr><td>내용</td>
        <td class="leftcol lefttoptable">${board.content}</td></tr>
    <tr><td>첨부파일</td><td>&nbsp;
        <c:if test="${!empty board.fileurl}">
            <a href="file/${board.fileurl}">${board.fileurl}</a>
        </c:if></td>
    </tr>
    <tr><td colspan="2">
            <a href="reply?num=${board.num}" class="btn btn-outline-success">댓글</a>
            <a href="update?num=${board.num}" class="btn btn-outline-primary">수정</a>
            <a href="delete?num=${board.num}" class="btn btn-outline-danger">삭제</a>
            <a href="list?boardid=${board.boardid}" class="btn btn-outline-secondary">목록</a>
            <span class="readcnt float-right">조회수 ${board.readcnt}</span></td></tr>
</table>
</body>
</html>