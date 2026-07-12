<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Application Settings" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card" style="max-width:600px;">
    <h3>Application Settings</h3>
    <form method="post" action="${pageContext.request.contextPath}/settings/application/save">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group"><label>Site Name</label>
            <input type="text" name="siteName" class="form-control" value="${settings.siteName}"></div>
        <div class="form-group"><label>Site Logo URL</label>
            <input type="text" name="siteLogoUrl" class="form-control" value="${settings.siteLogoUrl}"></div>
        <div class="form-group"><label>Timezone</label>
            <input type="text" name="timezone" class="form-control" value="${settings.timezone}"></div>
        <div class="form-group"><label>Date Format</label>
            <input type="text" name="dateFormat" class="form-control" value="${settings.dateFormat}"></div>
        <div class="form-group"><label>Items Per Page</label>
            <input type="number" name="itemsPerPage" class="form-control" value="${settings.itemsPerPage}"></div>
        <div class="form-group">
            <label><input type="checkbox" name="maintenanceMode" value="true" ${settings.maintenanceMode ? 'checked' : ''}>
            <input type="hidden" name="_maintenanceMode" value="on"/> Maintenance Mode</label>
        </div>
        <button type="submit" class="btn">Save</button>
    </form>
</div>
<%@ include file="../layout/page-foot.jsp" %>
