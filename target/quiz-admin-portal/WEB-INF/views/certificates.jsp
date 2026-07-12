<%@ include file="layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Certificates" scope="request"/>
<%@ include file="layout/page-head.jsp" %>

<div class="card">
    <h3>${isAdmin ? 'Issued Certificates' : 'My Certificates'}</h3>
    <c:choose>
        <c:when test="${empty results}">
            <p style="color:#6b7280;">
                <c:choose>
                    <c:when test="${isAdmin}">No passed quiz attempts yet.</c:when>
                    <c:otherwise>You haven't passed a quiz yet — certificates appear here once you do.</c:otherwise>
                </c:choose>
            </p>
        </c:when>
        <c:otherwise>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>#</th>
                        <c:if test="${isAdmin}"><th>Student</th></c:if>
                        <th>Quiz</th>
                        <th>Score</th>
                        <th>Certificate</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="r" items="${results}" varStatus="i">
                    <tr>
                        <td>${i.index + 1}</td>
                        <c:if test="${isAdmin}"><td>${r.user.fullName}</td></c:if>
                        <td>${r.quiz.title}</td>
                        <td>${r.scoreObtained} / ${r.totalMarks}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty r.certificateUrl}">
                                    <a class="btn btn-sm" href="${pageContext.request.contextPath}${r.certificateUrl}" target="_blank">Download</a>
                                    <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/certificates/${r.id}/generate">Regenerate</a>
                                </c:when>
                                <c:otherwise>
                                    <a class="btn btn-sm btn-success" href="${pageContext.request.contextPath}/certificates/${r.id}/generate">Generate</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="layout/page-foot.jsp" %>
