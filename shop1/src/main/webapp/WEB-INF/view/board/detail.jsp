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
            <c:if test="${board.boardid != 1 || loginUser.userid == 'admin'}">
            <a href="update?num=${board.num}&boardid=${board.boardid}" class="btn btn-outline-primary">수정</a>
            <a href="delete?num=${board.num}&boardid=${board.boardid}" class="btn btn-outline-danger">삭제</a>
            </c:if>
            <a href="list?boardid=${board.boardid}" class="btn btn-outline-secondary">목록</a>
            <span class="readcnt float-right">조회수 ${board.readcnt}</span></td></tr>
</table>
<%-- 댓글 등록, 조회, 삭제 --%>
<%--
    http://localhost:8080/shop1/board/detail?num=1#comment
    → detail.jsp 페이지에서 id=comment 인 영역을 보여줌

    URL 명칭
    http : 프로토콜. 전문
    localhost : ip 주소. 내 컴퓨터
    8080 : 포트
    shop1/board/detail : Path. 웹 어플리케이션 이름 + 요청 페이지
    ?num=1 : 쿼리. 파라미터값 표현

    #comment : 프래그먼트(Fragment) 서버에 전송되지 않는 영역. 브라우저내부에서 활용
               id=comment 인 영역으로 화면을 스크롤
--%>
<span id="commit">
    <form:form modelAttribute="comment" action="comment" method="post" name="commForm">
        <input type="hidden" name="num" value="${board.num}"/>
        <div class="row align-items-center my-3">
            <div class="col-2">
                <form:input path="writer" class="form-control form-control-sm" placeholder="작성자"/>
                <form:errors path="writer" cssStyle="color: red; font-size:11px;"/>
            </div>
            <div class="col-2">
                <form:input path="pass" class="form-control form-control-sm" placeholder="비밀번호"/>
                <form:errors path="pass" cssStyle="color: red; font-size:11px;"/>
            </div>
            <div class="col-6">
                <form:input path="content" class="form-control form-control-sm" placeholder="댓글내용"/>
                <form:errors path="content" cssStyle="color: red; font-size:11px;"/>
            </div>
            <div class="col-2">
                <button class="btn btn-primary btn-sm w-100">댓글등록</button>
            </div>
        </div>
    </form:form>
    <%-- 댓글 목록 --%>
    <div class="container"> <%-- BootStrap에서 사용되는 영역 지정. 자동으로 여백을 줌. 최대크기도 지정됨 --%>
        <table class="table table-sm table-borderless">
            <c:forEach var="c" items="${commlist}" varStatus="stat">
                <tr>
                    <td class="text-muted small" style="width:40px;">${c.seq}</td>
                    <td class="small fw-bold" style="width:80px;">${c.writer}</td>
                    <td class="small">${c.content}</td>
                    <td class="text-muted small" style="width:150px;">
                        <fmt:formatDate value="${c.regdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td class="text-right" style="width:220px;">
                        <form action="commdel" method="post" name="commdel${stat.index}" class="d-flex justify-content-end align-items-center gap-1">
                            <input type="hidden" name="num" value="${c.num}">
                            <input type="hidden" name="seq" value="${c.seq}">
                            <input type="password" name="pass" placeholder="비밀번호" class="form-control form-control-sm" style="width:90px;">
                            <button class="btn btn-outline-danger btn-sm">삭제</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</span>
</body>
</html>