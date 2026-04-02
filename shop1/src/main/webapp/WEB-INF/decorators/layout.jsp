<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />

<head>
    <title><sitemesh:write property="title"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700;900&family=Space+Mono:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        :root {
            --bg:       #fdf6f8;
            --surface:  #ffffff;
            --surface2: #fce8ef;
            --accent:   #e8789a;
            --accent2:  #d45c80;
            --accent3:  #f4a7bc;
            --text:     #2d1f26;
            --muted:    #5a3d48;
            --muted2:   #7a5462;
            --border:   #f0d0da;
        }

        *, *::before, *::after { box-sizing: border-box; }

        body {
            font-family: 'Noto Sans KR', sans-serif;
            background: var(--bg);
            color: var(--text);
            min-height: 100vh;
        }

        /* ── HERO ── */
        .jumbotron {
            position: relative;
            background: linear-gradient(135deg, #fff0f4 0%, #fdf6f8 50%, #ffeef3 100%) !important;
            border-radius: 0 !important;
            border-bottom: 1px solid var(--border);
            padding: 56px 20px !important;
            overflow: hidden;
            margin-bottom: 0 !important;
        }
        .jumbotron::before {
            content: '';
            position: absolute;
            inset: 0;
            background:
                    radial-gradient(ellipse 60% 60% at 25% 50%, rgba(232,120,154,.12) 0%, transparent 70%),
                    radial-gradient(ellipse 50% 50% at 75% 50%, rgba(212,92,128,.07) 0%, transparent 70%);
            pointer-events: none;
        }
        .jumbotron h1 {
            position: relative;
            font-size: clamp(1.5rem, 3vw, 2.4rem);
            font-weight: 900;
            background: linear-gradient(135deg, var(--text) 20%, var(--accent2) 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            margin-bottom: 12px;
        }
        .jumbotron p {
            position: relative;
            font-family: 'Space Mono', monospace;
            font-size: .82rem;
            color: var(--muted) !important;
        }

        /* footer jumbotron */
        .jumbotron.footer-jumbotron {
            padding: 28px 20px !important;
            border-top: 1px solid var(--border);
            border-bottom: none;
        }
        .jumbotron.footer-jumbotron p {
            margin: 0;
            font-size: .78rem;
        }

        /* ── NAVBAR ── */
        .navbar {
            background: rgba(255,255,255,.95) !important;
            backdrop-filter: blur(12px);
            border-bottom: 1px solid var(--border) !important;
            padding: 0 20px !important;
            min-height: 52px;
            position: sticky;
            top: 0;
            z-index: 1030;
            box-shadow: 0 1px 12px rgba(232,120,154,.08);
        }
        .navbar-brand {
            font-family: 'Space Mono', monospace;
            font-size: .78rem;
            font-weight: 700;
            color: var(--accent2) !important;
            border: 1px solid var(--accent);
            border-radius: 4px;
            padding: 5px 12px !important;
            letter-spacing: .05em;
            transition: all .2s;
        }
        .navbar-brand:hover {
            background: var(--accent);
            color: #fff !important;
            border-color: var(--accent);
        }
        .nav-link {
            font-size: .85rem !important;
            font-weight: 500;
            color: var(--text) !important;
            padding: 6px 14px !important;
            border-radius: 4px;
            transition: all .2s;
        }
        .nav-link:hover {
            color: var(--accent2) !important;
            background: var(--surface2) !important;
        }
        .navbar-toggler {
            border-color: var(--border) !important;
        }
        .navbar-toggler-icon {
            background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' width='30' height='30' viewBox='0 0 30 30'%3e%3cpath stroke='rgba(212, 92, 128, 0.8)' stroke-linecap='round' stroke-miterlimit='10' stroke-width='2' d='M4 7h22M4 15h22M4 23h22'/%3e%3c/svg%3e") !important;
        }

        /* ── LAYOUT ── */
        .container {
            max-width: 1200px;
        }

        /* ── SIDEBAR ── */
        .col-sm-4 h2 {
            font-size: 1rem;
            font-weight: 700;
            color: var(--text);
            margin-bottom: 12px;
        }
        .col-sm-4 h3 {
            font-size: .9rem;
            font-weight: 700;
            color: var(--text);
            margin: 16px 0 10px;
        }
        .col-sm-4 h5 {
            font-size: .78rem;
            font-weight: 600;
            color: var(--text);
            margin-bottom: 8px;
        }
        .col-sm-4 p {
            font-size: .85rem;
            color: var(--muted);
            line-height: 1.7;
        }

        .fakeimg {
            height: 160px !important;
            background: linear-gradient(135deg, #fce8ef 0%, #fad0de 100%) !important;
            border-radius: 10px;
            border: 1px solid var(--border);
            display: flex;
            align-items: center;
            justify-content: center;
            color: var(--accent2);
            font-size: .82rem;
            font-weight: 600;
            margin-bottom: 12px;
        }

        /* sidebar nav pills */
        .nav-pills .nav-link {
            font-size: .85rem !important;
            color: var(--text) !important;
            padding: 7px 14px !important;
            border-radius: 6px;
            border: 1px solid transparent;
            margin-bottom: 3px;
            transition: all .2s;
        }
        .nav-pills .nav-link:hover {
            color: var(--accent2) !important;
            background: var(--surface2) !important;
            border-color: var(--border) !important;
        }
        .nav-pills .nav-link.active {
            background: linear-gradient(135deg, var(--accent) 0%, var(--accent2) 100%) !important;
            color: #fff !important;
            border-color: transparent !important;
            box-shadow: 0 2px 10px rgba(212,92,128,.3);
        }
        .nav-pills .nav-link.disabled {
            opacity: .4;
        }

        /* ── CONTENT AREA ── */
        .col-sm-8 {
            background: var(--surface);
            border: 1px solid var(--border);
            border-radius: 12px;
            padding: 28px !important;
            min-height: 400px;
            box-shadow: 0 2px 16px rgba(232,120,154,.07);
        }
        .col-sm-8 h1,
        .col-sm-8 h2,
        .col-sm-8 h3 {
            color: var(--text);
            font-weight: 700;
            line-height: 1.4;
        }
        .col-sm-8 p,
        .col-sm-8 span {
            color: var(--text);
            font-size: .92rem;
            line-height: 1.8;
        }

        /* ── SCROLLBAR ── */
        ::-webkit-scrollbar { width: 6px; height: 6px; }
        ::-webkit-scrollbar-track { background: var(--bg); }
        ::-webkit-scrollbar-thumb { background: var(--accent3); border-radius: 3px; }
        ::-webkit-scrollbar-thumb:hover { background: var(--accent); }
    </style>
    <sitemesh:write property="head"/>
</head>
<body>

<div class="jumbotron text-center">
    <h1>클라우드 활용 자바 스프링 개발 부트캠프</h1>
    <p>Resize this responsive page to see the effect!</p>
</div>

<nav class="navbar navbar-expand-sm navbar-dark">
    <a class="navbar-brand" href="#">Spring</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="${path}/user/mypage?userid=${loginUser.userid}">마이페이지</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${path}/item/list">상품관리</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${path}/board/list?boardid=1">공지사항</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${path}/board/list?boardid=2">자유 게시판</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${path}/board/list?boardid=3">도움말</a>
            </li>
        </ul>
    </div>
</nav>

<div class="container" style="margin-top:30px">
    <div class="row">
        <div class="col-sm-4">
            <ul class="nav nav-pills flex-column">
                <li class="nav-item">
                    <c:if test="${empty sessionScope.loginUser}">
                        <a href="${path}/user/login" class="nav-link">로그인</a>
                        <a href="${path}/user/join" class="nav-link">회원가입</a>
                    </c:if>
                    <c:if test="${!empty sessionScope.loginUser}">${sessionScope.loginUser.username}님이 로그인 하셨습니다.&nbsp;&nbsp;<a href="${path}/user/logout">로그아웃</a>
                    </c:if>
                </li>
            </ul>
            <hr class="d-sm-none" style="border-color: var(--border);">
            <h2>About Me</h2>
            <h5>Photo of me:</h5>
            <div class="fakeimg">Fake Image</div>
            <p>Some text about me in culpa qui officia deserunt mollit anim..</p>
            <h3>Some Links</h3>
            <p>Lorem ipsum dolor sit ame.</p>
        </div>
        <div class="col-sm-8">
            <sitemesh:write property="body"/>
        </div>
    </div>
</div>

<div class="jumbotron footer-jumbotron text-center" style="margin-bottom:0">
    <p>Footer</p>
</div>

</body>
</html>
