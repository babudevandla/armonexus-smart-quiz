<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Difficulty Levels" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card">
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:16px;">
        <h3 style="margin:0;">Difficulty Levels</h3>
        <a class="btn" href="${pageContext.request.contextPath}/difficulty-levels/new">+ Add Level</a>
    </div>
    <table class="data-table">
        <thead><tr><th>#</th><th>Name</th><th>Weight</th><th>Actions</th></tr></thead>
        <tbody>
        <c:forEach var="lvl" items="${levels}" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${lvl.name}</td>
                <td>${lvl.weight}</td>
                <td>
                    <a class="btn btn-sm" href="${pageContext.request.contextPath}/difficulty-levels/${lvl.id}/edit">Edit</a>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/difficulty-levels/${lvl.id}/delete"
                       onclick="return confirm('Delete this level?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="../layout/page-foot.jsp" %>
