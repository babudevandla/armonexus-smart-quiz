<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="${question.id == null ? 'Add Question' : 'Edit Question'}" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card" style="max-width:800px;">
    <h3>${question.id == null ? 'Add Question' : 'Edit Question'}</h3>

    <!-- Plain form used (instead of form:form) since we bind a nested list of options manually -->
    <form method="post" action="${pageContext.request.contextPath}/questions/save">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="id" value="${question.id}"/>

        <div class="form-group">
            <label>Question Text</label>
            <textarea name="questionText" class="form-control" rows="3" required>${question.questionText}</textarea>
        </div>

        <div class="form-group" style="display:flex; gap:12px;">
            <div style="flex:1;">
                <label>Subject</label>
                <select name="subject.id" class="form-control">
                    <c:forEach var="s" items="${subjects}">
                        <option value="${s.id}" ${question.subject != null && question.subject.id == s.id ? 'selected' : ''}>${s.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div style="flex:1;">
                <label>Category</label>
                <select name="category.id" class="form-control">
                    <c:forEach var="c" items="${categories}">
                        <option value="${c.id}" ${question.category != null && question.category.id == c.id ? 'selected' : ''}>${c.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group" style="display:flex; gap:12px;">
            <div style="flex:1;">
                <label>Question Type</label>
                <select name="questionType.id" class="form-control">
                    <c:forEach var="t" items="${questionTypes}">
                        <option value="${t.id}" ${question.questionType != null && question.questionType.id == t.id ? 'selected' : ''}>${t.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div style="flex:1;">
                <label>Difficulty Level</label>
                <select name="difficultyLevel.id" class="form-control">
                    <c:forEach var="d" items="${difficultyLevels}">
                        <option value="${d.id}" ${question.difficultyLevel != null && question.difficultyLevel.id == d.id ? 'selected' : ''}>${d.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group" style="display:flex; gap:12px;">
            <div style="flex:1;">
                <label>Marks</label>
                <input type="number" name="marks" class="form-control" value="${question.marks != null ? question.marks : 1}" min="1">
            </div>
            <div style="flex:1;">
                <label>Negative Marks</label>
                <input type="number" name="negativeMarks" class="form-control" value="${question.negativeMarks != null ? question.negativeMarks : 0}" min="0">
            </div>
        </div>

        <div class="form-group">
            <label>Answer Options (check the correct one(s); leave text blank to skip a row)</label>
            <c:forEach var="opt" items="${question.options}" varStatus="i">
                <div style="display:flex; align-items:center; gap:10px; margin-bottom:8px;">
                    <input type="checkbox" name="options[${i.index}].isCorrect" value="true" ${opt.isCorrect ? 'checked' : ''}>
                    <input type="hidden" name="_options[${i.index}].isCorrect" value="on"/>
                    <input type="hidden" name="options[${i.index}].id" value="${opt.id}"/>
                    <input type="text" name="options[${i.index}].optionText" class="form-control"
                           placeholder="Option ${i.index + 1}" value="${opt.optionText}">
                </div>
            </c:forEach>
        </div>

        <div class="form-group">
            <label>Explanation (optional)</label>
            <textarea name="explanation" class="form-control" rows="2">${question.explanation}</textarea>
        </div>

        <button type="submit" class="btn">Save</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/questions">Cancel</a>
    </form>
</div>

<%@ include file="../layout/page-foot.jsp" %>
