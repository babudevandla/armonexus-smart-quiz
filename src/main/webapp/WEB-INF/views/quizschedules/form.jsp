<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="${schedule.id == null ? 'Add Schedule' : 'Edit Schedule'}" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card" style="max-width:600px;">
    <h3>${schedule.id == null ? 'Add Schedule' : 'Edit Schedule'}</h3>
    <form method="post" action="${pageContext.request.contextPath}/quiz-schedules/save">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="id" value="${schedule.id}"/>

        <div class="form-group">
            <label>Quiz</label>
            <select name="quiz.id" class="form-control" required>
                <c:forEach var="q" items="${quizzes}">
                    <option value="${q.id}" ${schedule.quiz != null && schedule.quiz.id == q.id ? 'selected' : ''}>${q.title}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group" style="display:flex; gap:12px;">
            <div style="flex:1;">
                <label>Start Time</label>
                <input type="datetime-local" name="startTime" class="form-control" value="${schedule.startTime}" required>
            </div>
            <div style="flex:1;">
                <label>End Time</label>
                <input type="datetime-local" name="endTime" class="form-control" value="${schedule.endTime}" required>
            </div>
        </div>

        <div class="form-group">
            <label><input type="checkbox" name="active" value="true" ${schedule.active ? 'checked' : ''}>
                <input type="hidden" name="_active" value="on"/> Active</label>
        </div>

        <button type="submit" class="btn">Save</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/quiz-schedules">Cancel</a>
    </form>
</div>
<%@ include file="../layout/page-foot.jsp" %>
