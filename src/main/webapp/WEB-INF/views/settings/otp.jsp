<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="OTP Settings" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card" style="max-width:600px;">
    <h3>OTP Settings</h3>
    <form method="post" action="${pageContext.request.contextPath}/settings/otp/save">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group"><label>OTP Length</label>
            <input type="number" name="otpLength" class="form-control" value="${settings.otpLength}"></div>
        <div class="form-group"><label>OTP Expiry (minutes)</label>
            <input type="number" name="otpExpiryMinutes" class="form-control" value="${settings.otpExpiryMinutes}"></div>
        <div class="form-group"><label>Provider</label>
            <input type="text" name="otpProvider" class="form-control" value="${settings.otpProvider}"></div>
        <div class="form-group">
            <label><input type="checkbox" name="enabled" value="true" ${settings.enabled ? 'checked' : ''}>
            <input type="hidden" name="_enabled" value="on"/> Enabled</label>
        </div>
        <button type="submit" class="btn">Save</button>
    </form>
</div>
<%@ include file="../layout/page-foot.jsp" %>
