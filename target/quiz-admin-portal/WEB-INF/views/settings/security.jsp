<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Security Settings" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card" style="max-width:600px;">
    <h3>Security Settings</h3>
    <form method="post" action="${pageContext.request.contextPath}/settings/security/save">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group"><label>Password Minimum Length</label>
            <input type="number" name="passwordMinLength" class="form-control" value="${settings.passwordMinLength}"></div>
        <div class="form-group"><label>Session Timeout (minutes)</label>
            <input type="number" name="sessionTimeoutMinutes" class="form-control" value="${settings.sessionTimeoutMinutes}"></div>
        <div class="form-group"><label>Max Login Attempts</label>
            <input type="number" name="maxLoginAttempts" class="form-control" value="${settings.maxLoginAttempts}"></div>
        <div class="form-group">
            <label><input type="checkbox" name="enableTwoFactor" value="true" ${settings.enableTwoFactor ? 'checked' : ''}>
            <input type="hidden" name="_enableTwoFactor" value="on"/> Enable Two-Factor Authentication</label>
        </div>
        <button type="submit" class="btn">Save</button>
    </form>
</div>
<%@ include file="../layout/page-foot.jsp" %>
