<%@ include file="layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Export Reports" scope="request"/>
<%@ include file="layout/page-head.jsp" %>
<div class="card">
    <h3>Export Reports</h3>
    <p style="color:#6b7280;">Download data exports as CSV files.</p>
    <a class="btn" href="${pageContext.request.contextPath}/export-reports/results.csv">Export Quiz Results (CSV)</a>
</div>
<%@ include file="layout/page-foot.jsp" %>
