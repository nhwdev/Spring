<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AI 챗봇</title>
    <link href="/shop1/css/chatbot.css" rel="stylesheet">
</head>
<body>
<div id="chatbotarea" class="text-center reounded" style="background-color: #1A6511">
    <label for="gptarea" class="text-light fw-bold">AI 챗봇</label>
    <div id="gptanswerarea" class="chatbot" style="background-color: #A8C0D6">
        <div class="chat bot">
            <div class="icon"><i></i></div>
            <div class="textbox text-start">AI 챗봇입니다. 궁금하신 점을 문의해 주세요.</div>
        </div>
    </div>
</div>
<hr>
<div id="gptarea">
    <textarea id="gpt_question" rows="2" class="form-control"></textarea></div>
<div class="text-end d-flex justify-content-between">
    <button class="btn btn-primary text-light" onclick="gptquestion()">
        AI문의
    </button>
</div>
<br>
<script type="text/javascript">
    let gpt_question = document.querySelector("#gpt_question")
    gpt_question.addEventListener("keydown", (e) => { // keydown 이벤트 처리
        if (e.keyCode == 13) { // 입력된 키가 Enter 키?
            gptquestion()
        }
    })

    function gptquestion() {
        let gpt_question = document.querySelector("#gpt_question").value
        if (gpt_question == '') {
            alert("AI챗봇에게 질문할 내용을 입력해 주세요")
            document.querySelector("#gpt_question").focus()
            return
        }
        if (gpt_question.length < 10) { // 질문의 글자수가 10 미만인 경우 전송 불가
            alert("AI챗봇에게 질문할 내용을 좀더 자세히 입력해 주세요.")
            document.querySelector("#gpt_question").focus()
            return
        }
        let html = "<div class='chat me'><div class='icon'><i></i></div>"
        html += "<div class='textbox'>" + gpt_question + "</div></div>"
        document.querySelector("#gptanswerarea").innerHTML += html
        document.querySelector("#gpt_question").value = ""
        let gptanswerarea = document.querySelector("#gptanswerarea")
        gptanswerarea.scrollTop = gptanswerarea.scrollHeight
        /*
         * headers: {"Content-Type":"application/x-www-form-urlencoded;charset=UTF-8"}, key=value 형식으로 전송. 파라미터 인코딩
         * cache: "no-cache" : cache 사용 안함. 매번 서버에 요청 설정
         * referrerPlicy : "no=referrer" : 보안 설정. 요청의 주소값은 서버에 전송❌
         * body : 파라미터 값
         */
        paramdata = { // 파라미터값
            method: "POST", // header 필요
            headers: {"Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"},
            cache: "no-cache",
            referrerPolicy: "no-referrer",
            body: "question=" + gpt_question
        }
        fetch('/shop1/ajax/gptquestion', paramdata) // ES6 이후에 추가된 함수. 서버와 비동기 통신 함수
            .then(response => response.text())
            .then(gptdata => { // gptdata : gpt 전달해준 결과 값
                let html = "<div class='chat bot'><div class='icon'><i></i></div>"
                html += "<div class='textbox'>" + gptdata.replaceAll("\n", "<br>").replaceAll(" ", "&nbsp;") + "</div></div>"
                document.querySelector("#gptanswerarea").innerHTML += html
                let gptanswerarea = document.querySelector("#gptanswerarea")
                gptanswerarea.scrollTop = gptanswerarea.scrollHeight
            })
    }
</script>
</body>
</html>