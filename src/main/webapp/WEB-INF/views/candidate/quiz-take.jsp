<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Taking Quiz" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card">
    <div style="display:flex; justify-content:space-between; align-items:center;">
        <h3 style="margin:0;">${quiz.title}</h3>
        <div id="timer" style="font-weight:700; font-size:18px; color:#dc2626;"></div>
    </div>
    <p style="color:#6b7280;">${quiz.description}</p>
    <p><strong>Duration:</strong> ${quiz.durationMinutes} minutes &nbsp; | &nbsp;
       <strong>Total Marks:</strong> ${quiz.totalMarks} &nbsp; | &nbsp;
       <strong>Passing Marks:</strong> ${quiz.passingMarks}</p>
</div>

<form id="quizForm" method="post" action="${pageContext.request.contextPath}/candidate/quizzes/${quiz.id}/submit">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <c:forEach var="qq" items="${quiz.quizQuestions}" varStatus="i">
        <c:set var="question" value="${qq.question}"/>
        <div class="card">
            <p><strong>Q${i.index + 1}.</strong> ${question.questionText}
               <span style="color:#6b7280; font-size:13px;">(${question.marks} mark(s))</span></p>
            <c:forEach var="opt" items="${question.options}">
                <div style="margin-bottom:8px;">
                    <label>
                        <input type="radio" name="answers[${question.id}]" value="${opt.id}">
                        ${opt.optionText}
                    </label>
                </div>
            </c:forEach>
        </div>
    </c:forEach>

    <div class="card">
        <button type="submit" class="btn btn-success" onclick="return confirm('Submit your answers? This cannot be undone.');">
            Submit Quiz
        </button>
    </div>
</form>

<script>
    // Simple countdown timer — auto-submits the form when time runs out.
    (function () {
        var totalSeconds = ${quiz.durationMinutes} * 60;
        var timerEl = document.getElementById('timer');
        var formEl = document.getElementById('quizForm');

        function tick() {
            if (totalSeconds <= 0) {
                timerEl.textContent = "Time's up!";
                formEl.submit();
                return;
            }
            var m = Math.floor(totalSeconds / 60);
            var s = totalSeconds % 60;
            timerEl.textContent = "Time left: " + m + "m " + (s < 10 ? "0" : "") + s + "s";
            totalSeconds--;
            setTimeout(tick, 1000);
        }
        tick();
    })();
</script>

<%@ include file="../layout/page-foot.jsp" %>
