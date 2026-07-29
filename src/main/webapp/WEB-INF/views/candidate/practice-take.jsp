<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Practice" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card">
    <h3>${practiceSet.title}</h3>
    <p style="color:#6b7280;">${practiceSet.description}</p>
    <p style="color:#9ca3af; font-size:13px;">Untimed self-practice a take your time, there's no scoring penalty here.</p>
</div>

<c:if test="${empty questions}">
    <div class="card">
        <p style="color:#6b7280;">No approved questions are available for this subject yet.</p>
    </div>
</c:if>

<c:if test="${not empty questions}">
<form method="post" action="${pageContext.request.contextPath}/candidate/practice/${practiceSet.id}/submit">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <c:forEach var="question" items="${questions}" varStatus="i">
        <div class="card">
            <p><strong>Q${i.index + 1}.</strong> ${question.questionText}</p>
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
        <button type="submit" class="btn btn-success">Submit Practice</button>
    </div>
</form>
</c:if>

<%@ include file="../layout/page-foot.jsp" %>
