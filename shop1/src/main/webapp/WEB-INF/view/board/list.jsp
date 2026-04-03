<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
1. 첨부파일이 있는 경우 목록에 표시
2. 오늘 등록한 게시물의 날짜는 HH:mm:ss 로
   이전 등록한 게시물의 날짜는 yyyy-MM-dd HH:mm:ss 형식으로 출력하기
3. 중복된 검색이 가능하게 구현
--%>
<html>
<head>
    <title>${boardname}</title>
</head>
<body>
<h2>${boardname}</h2>
<form action="list" method="post" name="searchform">
<table class="table">
    <tr><td colspan="5">
        <div class="d-flex justify-content-center align-items-center">
            <input type="hidden" name="pagenum" value="1">
            <input type="hidden" name="boardid" value="${param.boardid}">
            <select name="searchtype" class="form-control w-auto mr-1">
                <option value="">전체</option>
                <option value="title">제목</option>
                <option value="writer">작성자</option>
                <option value="content">내용</option>
                <option value="title,writer">제목 + 작성자</option>
                <option value="title,content">제목 + 내용</option>
                <option value="writer,content">작성자 + 내용</option>
            </select>
            <script type="text/javascript">
                searchform.searchtype.value="${param.searchtype}"
            </script>
            <input type="text" name="searchcontent" value="${param.searchcontent}"
                   class="form-control w-50 mr-1" placeholder="검색어를 입력하세요">
            <div class="btn-group">
                <button class="btn btn-primary">검색</button>&nbsp;
                <button type="button" class="btn btn-success" onclick="location.href='list?boardid=${boardid}'" style="white-space: nowrap;">전체 게시글 보기</button>
            </div>
        </div>
    </td>
    </tr>
</table>
</form>
<table class="table">
    <c:if test="${countlist > 0}">
        <tr><td colspan="5" class="text-right">게시글 수: ${countlist}</td></tr>
        <tr><th>번호</th><th>제목</th><th>글쓴이</th><th>날짜</th><th>조회수</th></tr>
        <c:forEach var="board" items="${listboard}">
            <tr><td>${boardno}</td><c:set var="boardno" value="${boardno-1}"/>
                <td><c:if test="${board.grplevel > 0}">
                    <c:forEach begin="2" end="${board.grplevel}">&emsp;</c:forEach>↪</c:if>
                <a href="detail?num=${board.num}&boardid=${boardid}">${(empty board.fileurl) ? board.writer : "".concat(board.title).concat(" 📄")}</a></td>
                <td>${board.writer}</td>
                <td><fmt:formatDate value="${board.regdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${board.readcnt}</td>
            </tr>
        </c:forEach>
        <tr><td colspan="5" class="text-center">
            <c:if test="${pagenum > 1}"><a href="javascript:listpage(${pagenum - 1})" class="btn btn-primary">이전</a></c:if>
            <c:if test="${pagenum <= 1}"><span class="btn btn-secondary">이전</span></c:if>
            <c:forEach var="currentpage" begin="${startpage}" end="${endpage}">
                <c:if test="${currentpage == pagenum}"><span class="btn btn-success">${currentpage}</span></c:if>
                <c:if test="${currentpage != pagenum}"><a href="javascript:listpage(${currentpage})" class="btn btn-secondary">${currentpage}</a></c:if>
            </c:forEach>
            <c:if test="${pagenum < maxpage}"><a href="javascript:listpage(${pagenum + 1})" class="btn btn-primary">다음</a></c:if>
            <c:if test="${pagenum >= maxpage}"><span class="btn btn-secondary">다음</span></c:if>
        </td></tr>
    </c:if>
    <c:if test="${countlist == 0}"><tr><td colspan="5">등록된 게시물이 없습니다.</td></tr></c:if>
    <tr><td colspan="5" class="text-center"><a href="write?boardid=${boardid}" class="btn btn-danger">글쓰기</a></td></tr>
</table>
<script type="text/javascript">
    function listpage(page) {
        document.searchform.pagenum.value=page;
        document.searchform.submit(); // submit 버튼 클릭 효과
    }
</script>
</body>
</html>