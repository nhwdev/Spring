<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>mypage</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <style type="text/css">
        body { padding: 20px; }
        table.table th:nth-child(1), table.table td:nth-child(1) { width: 15%; text-align: center; }
        table.table th:nth-child(2), table.table td:nth-child(2) { width: 50%; text-align: center; }
        table.table th:nth-child(3), table.table td:nth-child(3) { width: 35%; text-align: right; }
        .orderLine table td:nth-child(2), .orderLine table td:nth-child(4) { text-align: right; }
        body { padding: 20px; }
        table.table th:nth-child(1), table.table td:nth-child(1) { width: 15%; text-align: center; }
        table.table th:nth-child(2), table.table td:nth-child(2) { width: 50%; text-align: center; }
        table.table th:nth-child(3), table.table td:nth-child(3) { width: 35%; text-align: right; }
        .orderLine table td:nth-child(2), .orderLine table td:nth-child(4) { text-align: right; }

        /* 주문상품 상세 테이블 컬럼 너비 고정 */
        .orderLine table th:nth-child(1), .orderLine table td:nth-child(1) { width: 40%; text-align: left; }
        .orderLine table th:nth-child(2), .orderLine table td:nth-child(2) { width: 20%; text-align: right; }
        .orderLine table th:nth-child(3), .orderLine table td:nth-child(3) { width: 15%; text-align: center; }
        .orderLine table th:nth-child(4), .orderLine table td:nth-child(4) { width: 25%; text-align: right; }
    </style>
</head>
<body>

<!-- 탭 버튼 -->
<ul class="nav nav-tabs mb-3">
    <li class="nav-item">
        <a id="tab1" class="nav-link active" href="javascript:disp_div('minfo', 'tab1')">회원정보</a>
    </li>
    <c:if test="${param.userid != 'admin'}">
        <li class="nav-item">
            <a id="tab2" class="nav-link" href="javascript:disp_div('oinfo','tab2')">주문정보</a>
        </li>
    </c:if>
</ul>

<!-- 주문정보 -->
<div id="oinfo" class="info" style="display: none;">
    <table class="table table-hover table-bordered">
        <thead class="thead-dark">
        <tr><th>주문번호</th><th>주문일자</th><th>주문금액</th></tr>
        </thead>
        <tbody>
        <c:forEach items="${orderlist}" var="order" varStatus="stat">
            <tr>
                <td align="center"><a href="javascript:list_disp('orderLine${stat.index}')">${order.orderid}</a></td>
                <td align="center"><fmt:formatDate value="${order.orderdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td align="right"><fmt:formatNumber value="${order.total}" type="currency" /></td>
            </tr>
            <tr id="orderLine${stat.index}" class="orderLine">
                <td colspan="3" align="center">
                    <table class="table table-sm table-bordered w-75 mx-auto">
                        <thead class="thead-secondary">
                        <tr><th>상품명</th><th>상품가격</th><th>주문수량</th><th>상품총액</th></tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${order.itemList}" var="orderItem">
                            <%--
                            ${orderItem.item.name} : orderItem.getItem().getName() → 상품명
                            ${orderItem.item.price}: orderItem.getItem().getPrice() → 가격
                            --%>
                            <tr><td>${orderItem.item.name}</td>
                                <td><fmt:formatNumber value="${orderItem.item.price}" currencySymbol="₩"/></td>
                                <td>${orderItem.quantity}</td>
                                <td><fmt:formatNumber value="${orderItem.item.price * orderItem.quantity}" type="currency"/></td></tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- 회원정보 -->
<div id="minfo" class="info">
    <table class="table table-bordered w-60">
        <tr><th class="table-secondary">아이디</th><td>${user.userid}</td></tr>
        <tr><th class="table-secondary">이름</th><td>${user.username}</td></tr>
        <tr><th class="table-secondary">우편번호</th><td>${user.postcode}</td></tr>
        <tr><th class="table-secondary">전화번호</th><td>${user.phoneno}</td></tr>
        <tr><th class="table-secondary">이메일</th><td>${user.email}</td></tr>
        <tr><th class="table-secondary">생년월일</th><td><fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/></td></tr>
    </table>

    <!-- 액션 버튼들 -->
    <div class="d-flex flex-wrap mt-3">
        <a href="logout" class="btn btn-outline-secondary mr-2">로그아웃</a>
        <a href="update?userid=${user.userid}" class="btn btn-primary mr-2">회원정보수정</a>
        <a href="password" class="btn btn-warning mr-2">비밀번호수정</a>
        <c:if test="${loginUser.userid != 'admin'}">
            <a href="delete?userid=${user.userid}" class="btn btn-danger mr-2">회원탈퇴</a>
        </c:if>
        <c:if test="${loginUser.userid == 'admin'}">
            <a href="../admin/list" class="btn btn-success mr-2">회원목록</a>
        </c:if>
    </div>
</div>

<script type="text/javascript">
    $(function() { // 준비되면
        $("#minfo").show() // 회원정보 보여줌
        $("#oinfo").hide() // 주문정보 숨김
        $(".orderLine").each(function() { // 주문상품 숨김
            $(this).hide()
        })
    })
    function disp_div(id, tab) { // tab 선택시 호출되는 함수
        // id : minfo, tab: tab1
        $(".info").each(function() { // 회원정보, 주문정보 데이터 숨김
            $(this).hide()
        })
        $(".nav-link").each(function() { // 회원정보, 주문정보 제목 active 속성 제거
            $(this).removeClass("active")
        })
        $("#"+id).show() // 선택한 정보를 보여주기
        $("#"+tab).addClass("active") // tab에 class 속성을 active 값 주기
    }
    function list_disp(id) { // 주문상품을 볼수 있도록 선택. 현재 안보이는 경우 보이도록, 현재 보이는 경우는 안보이도록
        $("#"+id).toggle()
    }
</script>
</body>
</html>