<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>네이버 검색</title>
    <style>
        .form-label{
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="form-row align-items-end">

    <%-- 검색 유형 --%>
    <div class="col-md-3 mb-2">
        <label class="form-label" >검색 유형</label>
        <select id="type" class="form-control">
            <option value="blog">블로그</option>
            <option value="news">뉴스</option>
            <option value="book">책</option>
            <option value="encyc">백과사전</option>
            <option value="cafearticle">카페글</option>
            <option value="kin">지식인</option>
            <option value="local">지역</option>
            <option value="webkr">웹문서</option>
            <option value="image">이미지</option>
            <option value="shop">쇼핑</option>
            <option value="doc">전문자료</option>
        </select>
    </div>

    <%-- 페이지당 갯수 --%>
    <div class="col-md-2 mb-2">
        <label class="form-label">페이지당 갯수</label>
        <select id="display" class="form-control">
            <option>10</option>
            <option>20</option>
            <option>50</option>
        </select>
    </div>

    <%-- 검색어 → 페이지당 갯수 바로 오른쪽 --%>
    <div class="col-md-5 mb-2">
        <label class="form-label">검색어</label>
        <input type="text" id="data" placeholder="검색어를 입력하세요" class="form-control">
    </div>

    <%-- 검색 버튼 --%>
    <div class="col-md-2 mb-2">
        <label class="form-label">&nbsp;</label>
        <button class="btn btn-primary btn-block d-block" onclick="naversearch(1)">검색</button>
    </div>

</div>
<div id="result"></div>
<script type="text/javascript">
    function naversearch(start) {
        $.ajax({
            type: "POST",
            url: "naversearch",
            data: {"data": $("#data").val(),
                "display": $("#display").val(),
                "start": start,
                "type": $("#type").val()},
            success: function (json) {
                let total = json.total ?? 0
                let html = ""
                let num = (start - 1) * $("#display").val() + 1
                let maxpage = Math.ceil(total / parseInt($("#display").val()))
                let startpage = (Math.ceil(start / 10) - 1) * 10 + 1
                let endpage = startpage + 9
                if (endpage > maxpage) endpage = maxpage
                html += "<table class='table table-bordered'><tr><td colspan='4' align='center'> 전체 조회 건수 : " + total + ", 현재페이지: " + start + "/" + maxpage + "</td></tr><tr><th>번호</th><th>제목</th><th>링크</th><th>썸네일</th></tr>"
                $.each(json.items, function (i, item) {
                    html += "<tr><td>" + num + "</td><td>" + item.title + "</td><td>"
                    if ($("#type").val() == 'image') {
                        html += "<a href='" + item.link + "'><img src='" + item.thumbnail + "'></a></td><td>"
                    } else {
                        html += "<a href='" + item.link + "'>" + item.link + "</a></td><td>" + item.description
                    }
                    html += "</td></tr>"
                    num++;
                })
                html += "<tr><td colspan='4' align='center'>"
                if (start > 1) {
                    html += "<button class='btn btn-success' onclick= 'naversearch(" + (start - 1) + ")'>이전</button>"
                }
                for (let i = startpage; i <= endpage; i++) {
                    if (i == start) {
                        html += "<button class='btn btn-primary' onclick='naversearch(" + i + ")'>" + i + "</button>"

                    } else {
                        html += "<button class='btn-secondary' onclick='naversearch(" + i + ")'>" + i + "</button>"
                    }
                }
                if (maxpage > start) {
                    html += "<button class='btn btn-success' onclick='naversearch(" + (start + 1) + ")'>다음</button>"
                }
                html += "</td></tr></table>"
                $("#result").html(html)
            }
        })
    }
</script>
</body>
</html>
