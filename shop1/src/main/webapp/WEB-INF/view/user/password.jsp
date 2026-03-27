<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>비밀번호 재설정</title>
</head>
<body>
<h3>비밀번호 재설정</h3>
<form action="password" method="post" name="f" onsubmit="return inchk(this)">
    <table class="table">
        <tr><th>현재 비밀번호</th>
            <td><input type="password" name="password" class="form-control"></td></tr>
        <tr><th>새 비밀번호</th>
            <td><input type="password" name="chgpass" class="form-control"></td></tr>
        <tr><th>새 비밀번호 확인</th>
            <td><input type="password" name="chgpass2" class="form-control"></td></tr>
        <tr><td colspan="2" class="text-center"><button class="btn btn-primary">비밀번호 재설정</button></td></tr>
    </table>
</form>
<script type="text/javascript">
    function inchk(f) {
        if(f.password.value === "") {
            alert("현재 비밀번호를 입력하세요.")
            f.password.focus()
            return false;
        }
        if(f.chgpass.value === "") {
            alert("새 비밀번호를 입력하세요.")
            f.chgpass.focus()
            return false;
        }
        if(f.chgpass2.value === "" || f.chgpass2.value !== f.chgpass.value) {
            alert("비밀번호가 서로 다릅니다!")
            f.chgpass2.value=""
            f.chgpass2.focus()
            return false;
        }
        return true
    }
</script>
</body>
</html>
