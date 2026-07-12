<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Email Configuration" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card" style="max-width:600px;">
    <h3>Email Configuration (SMTP)</h3>
    <form method="post" action="${pageContext.request.contextPath}/settings/email-configuration/save">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group"><label>SMTP Host</label>
            <input type="text" name="smtpHost" class="form-control" value="${settings.smtpHost}"></div>
        <div class="form-group"><label>SMTP Port</label>
            <input type="number" name="smtpPort" class="form-control" value="${settings.smtpPort}"></div>
        <div class="form-group"><label>SMTP Username</label>
            <input type="text" name="smtpUsername" class="form-control" value="${settings.smtpUsername}"></div>
        <div class="form-group"><label>SMTP Password</label>
            <input type="password" name="smtpPassword" class="form-control" value="${settings.smtpPassword}"></div>
        <div class="form-group"><label>From Address</label>
            <input type="email" name="fromAddress" class="form-control" value="${settings.fromAddress}"></div>
        <div class="form-group">
            <label><input type="checkbox" name="useTls" value="true" ${settings.useTls ? 'checked' : ''}>
            <input type="hidden" name="_useTls" value="on"/> Use TLS</label>
        </div>
        <button type="submit" class="btn">Save</button>
    </form>
</div>
<%@ include file="../layout/page-foot.jsp" %>
