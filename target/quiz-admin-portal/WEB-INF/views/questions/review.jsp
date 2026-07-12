<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Review Questions" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card">
    <h3>Pending Review</h3>
    <table class="data-table">
        <thead><tr><th>#</th><th>Question</th><th>Subject</th><th>Created By</th><th>Actions</th></tr></thead>
        <tbody>
        <c:forEach var="q" items="${questions}" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${q.questionText}</td>
                <td>${q.subject.name}</td>
                <td>${q.createdBy != null ? q.createdBy.fullName : '-'}</td>
                <td>
                    <form style="display:inline" method="post" action="${pageContext.request.contextPath}/question-approval/${q.id}/approve">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="submit" class="btn btn-sm btn-success">Approve</button>
                    </form>
                    <form style="display:inline" method="post" action="${pageContext.request.contextPath}/question-approval/${q.id}/reject">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="submit" class="btn btn-sm btn-danger">Reject</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="../layout/page-foot.jsp" %>
