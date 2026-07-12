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

<form id="quizForm" method="post" action="${pageContext.request.contextPath}/student/quizzes/${quiz.id}/submit">
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
        var windowBlurred = false;

        // === TIMER FUNCTIONALITY ===
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

        // === BLOCK COPY/PASTE FUNCTIONALITY ===
        // Block Ctrl+C (Copy)
        document.addEventListener('keydown', function(e) {
            if (e.ctrlKey && e.key === 'c') {
                e.preventDefault();
                alert('Copy is disabled during the quiz!');
                return false;
            }
            // Block Ctrl+X (Cut)
            if (e.ctrlKey && e.key === 'x') {
                e.preventDefault();
                alert('Cut is disabled during the quiz!');
                return false;
            }
            // Block Ctrl+V (Paste)
            if (e.ctrlKey && e.key === 'v') {
                e.preventDefault();
                alert('Paste is disabled during the quiz!');
                return false;
            }
        });

        // Block right-click context menu
        document.addEventListener('contextmenu', function(e) {
            e.preventDefault();
            alert('Right-click is disabled during the quiz!');
            return false;
        });

        // Block paste via right-click menu
        document.addEventListener('paste', function(e) {
            e.preventDefault();
            alert('Paste is disabled during the quiz!');
            return false;
        });

        // Block cut via right-click menu
        document.addEventListener('cut', function(e) {
            e.preventDefault();
            alert('Cut is disabled during the quiz!');
            return false;
        });

        // === AUTO-SUBMIT ON WINDOW BLUR/TAB SWITCH ===
        window.addEventListener('blur', function() {
            windowBlurred = true;
            console.log('User switched tabs/windows - Auto-submitting quiz');

            // Optional: Show warning first
            setTimeout(function() {
                if (windowBlurred) {
                    alert('You switched to another window. Your quiz will be submitted now!');
                    formEl.submit();
                }
            }, 1000); // 1 second delay to show message
        });

        window.addEventListener('focus', function() {
            windowBlurred = false;
        });

        // === BLOCK F12 (Developer Tools) ===
        document.addEventListener('keydown', function(e) {
            if (e.key === 'F12' || (e.ctrlKey && e.shiftKey && e.key === 'I')) {
                e.preventDefault();
                alert('Developer tools are disabled during the quiz!');
                return false;
            }
        });

        // === PREVENT NAVIGATION AWAY ===
        window.addEventListener('beforeunload', function(e) {
            e.preventDefault();
            e.returnValue = '';
            return 'Your quiz responses will be lost. Are you sure?';
        });

        // === DISABLE PRINTING ===
        document.addEventListener('keydown', function(e) {
            if (e.ctrlKey && e.key === 'p') {
                e.preventDefault();
                alert('Printing is disabled during the quiz!');
                return false;
            }
        });
    })();
</script>
<%@ include file="../layout/page-foot.jsp" %>
