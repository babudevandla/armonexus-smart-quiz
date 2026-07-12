<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="${quiz.id == null ? 'Create Quiz' : 'Edit Quiz'}" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card" style="max-width:700px;">
    <h3>${quiz.id == null ? 'Create Quiz' : 'Edit Quiz'}</h3>

    <form method="post" action="${pageContext.request.contextPath}/quizzes/save">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="id" value="${quiz.id}"/>

        <div class="form-group">
            <label>Title</label>
            <input type="text" name="title" class="form-control" value="${quiz.title}" required>
        </div>

        <div class="form-group">
            <label>Description</label>
            <textarea name="description" class="form-control" rows="2">${quiz.description}</textarea>
        </div>

        <div class="form-group" style="display:flex; gap:12px;">
            <div style="flex:1;">
                <label>Subject</label>
                <select name="subject.id" class="form-control">
                    <c:forEach var="s" items="${subjects}">
                        <option value="${s.id}" ${quiz.subject != null && quiz.subject.id == s.id ? 'selected' : ''}>${s.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div style="flex:1;">
                <label>Duration (minutes)</label>
                <input type="number" name="durationMinutes" class="form-control" value="${quiz.durationMinutes}" min="1" required>
            </div>
        </div>

        <div class="form-group" style="display:flex; gap:12px;">
            <div style="flex:1;">
                <label>Total Marks</label>
                <input type="number" name="totalMarks" class="form-control" value="${quiz.totalMarks}" min="0">
            </div>
            <div style="flex:1;">
                <label>Passing Marks</label>
                <input type="number" name="passingMarks" class="form-control" value="${quiz.passingMarks}" min="0">
            </div>
        </div>

        <div class="form-group">
            <label><input type="checkbox" name="shuffleQuestions" value="true" ${quiz.shuffleQuestions ? 'checked' : ''}>
                <input type="hidden" name="_shuffleQuestions" value="on"/> Shuffle Questions</label>
        </div>

        <div class="form-group">
            <label><input type="checkbox" name="active" value="true" ${quiz.active ? 'checked' : ''}>
                <input type="hidden" name="_active" value="on"/> Active</label>
        </div>

        <div class="form-group">
            <label>Select Questions (approved only) — hold Ctrl/Cmd to select multiple</label>
            <select name="questionIds" class="form-control" multiple size="8">
                <c:forEach var="ques" items="${approvedQuestions}">
                    <c:set var="isSelected" value="false"/>
                    <c:forEach var="qq" items="${quiz.quizQuestions}">
                        <c:if test="${qq.question.id == ques.id}"><c:set var="isSelected" value="true"/></c:if>
                    </c:forEach>
                    <option value="${ques.id}" ${isSelected ? 'selected' : ''}>
                        ${ques.questionText.length() > 70 ? ques.questionText.substring(0,70).concat('...') : ques.questionText}
                    </option>
                </c:forEach>
            </select>
        </div>

        <button type="submit" class="btn">Save</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/quizzes">Cancel</a>
    </form>
</div>

<%@ include file="../layout/page-foot.jsp" %>
