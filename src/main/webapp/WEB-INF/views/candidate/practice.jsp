<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Practice Sets" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card">
    <h3>Practice Sets</h3>
    <c:choose>
        <c:when test="${empty practiceSets}">
            <p style="color:#6b7280;">No practice sets are available right now.</p>
        </c:when>
        <c:otherwise>
            <table class="data-table">
                <thead><tr><th>Title</th><th>Subject</th><th>Description</th><th>Action</th></tr></thead>
                <tbody>
                <c:forEach var="p" items="${practiceSets}">
                    <tr>
                        <td>${p.title}</td>
                        <td>${p.subject != null ? p.subject.name : '-'}</td>
                        <td>${p.description}</td>
                        <td><a class="btn btn-sm" href="${pageContext.request.contextPath}/candidate/practice/${p.id}/take">Start Practice</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="../layout/page-foot.jsp" %>
