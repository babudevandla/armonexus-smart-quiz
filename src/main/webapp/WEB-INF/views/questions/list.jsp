<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Questions" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card">
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:16px;">
        <h3 style="margin:0;">Question Bank</h3>
        <a class="btn" href="${pageContext.request.contextPath}/questions/new">+ Add Question</a>
    </div>
    <table class="data-table">
        <thead>
        <tr><th>#</th><th>Question</th><th>Subject</th><th>Type</th><th>Difficulty</th><th>Marks</th><th>Status</th><th>Actions</th></tr>
        </thead>
        <tbody>
        <c:forEach var="q" items="${questions}" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${q.questionText.length() > 60 ? q.questionText.substring(0,60).concat('...') : q.questionText}</td>
                <td>${q.subject.name}</td>
                <td>${q.questionType.name}</td>
                <td>${q.difficultyLevel.name}</td>
                <td>${q.marks}</td>
                <td>
                    <c:choose>
                        <c:when test="${q.status == 'APPROVED'}"><span class="badge badge-approved">Approved</span></c:when>
                        <c:when test="${q.status == 'PENDING_REVIEW'}"><span class="badge badge-pending">Pending</span></c:when>
                        <c:when test="${q.status == 'REJECTED'}"><span class="badge badge-rejected">Rejected</span></c:when>
                        <c:otherwise><span class="badge badge-draft">Draft</span></c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${q.status == 'PENDING_REVIEW'}">
                            <a class="btn btn-sm btn-warning" href="${pageContext.request.contextPath}/questions/${q.id}/review">Review</a>
                        </c:when>
                        <c:when test="${q.status == 'DRAFT'}">
                            <a class="btn btn-sm" href="${pageContext.request.contextPath}/questions/${q.id}/edit">Edit</a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-sm" href="${pageContext.request.contextPath}/questions/${q.id}/edit">View</a>
                        </c:otherwise>
                    </c:choose>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/questions/${q.id}/delete"
                       onclick="return confirm('Delete this question?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="../layout/page-foot.jsp" %>
