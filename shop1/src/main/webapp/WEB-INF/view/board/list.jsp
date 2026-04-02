<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>${boardname}</title>
</head>
<body>
<h2>${boardname}</h2>
<table class="table">
    <c:if test="${countlist > 0}">
        <tr><td colspan="5" class="text-right">게시글 수: ${countlist}</td></tr>
        <tr><th>번호</th><th>제목</th><th>글쓴이</th><th>날짜</th><th>조회수</th></tr>
        <c:forEach var="board" items="${listboard}">
            <tr>
                <td>${boardno}</td><c:set var="boardno" value="${boardno-1}"/>
                <td><a href="detail?num=${board.num}&boardid=${boardid}">${board.title}</a></td>
                <td>${board.writer}</td>
                <td><fmt:formatDate value="${board.regdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${board.readcnt}</td>
            </tr>
        </c:forEach>
        <tr><td colspan="5" class="text-center">
            <c:if test="${pagenum > 1}"><a href="list?pagenum=${pagenum - 1}&boardid=${boardid}" class="btn btn-primary">이전</a></c:if>
            <c:if test="${pagenum <= 1}"><span class="btn btn-secondary">이전</span></c:if>
            <c:forEach var="currentpage" begin="${startpage}" end="${endpage}">
                <c:if test="${currentpage == pagenum}"><span class="btn btn-success">${currentpage}</span></c:if>
                <c:if test="${currentpage != pagenum}"><a href="list?pagenum=${currentpage}&boardid=${boardid}" class="btn btn-secondary">${currentpage}</a></c:if>
            </c:forEach>
            <c:if test="${pagenum < maxpage}"><a href="list?pagenum=${pagenum + 1}&boardid=${boardid}" class="btn btn-primary">다음</a></c:if>
            <c:if test="${pagenum >= maxpage}"><span class="btn btn-secondary">다음</span></c:if>
        </td></tr>
    </c:if>
    <c:if test="${countlist == 0}"><tr><td colspan="5">등록된 게시물이 없습니다.</td></tr></c:if>
    <tr><td colspan="5" class="text-center"><a href="write?boardid=${boardid}" class="btn btn-danger">글쓰기</a></td></tr>
</table>
</body>
</html>