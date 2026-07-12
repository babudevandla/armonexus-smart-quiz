<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Payment Gateway Settings" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card" style="max-width:600px;">
    <h3>Payment Gateway Settings</h3>
    <form method="post" action="${pageContext.request.contextPath}/settings/payment-gateway/save">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group"><label>Provider</label>
            <input type="text" name="provider" class="form-control" value="${settings.provider}"></div>
        <div class="form-group"><label>API Key</label>
            <input type="text" name="apiKey" class="form-control" value="${settings.apiKey}"></div>
        <div class="form-group"><label>API Secret</label>
            <input type="password" name="apiSecret" class="form-control" value="${settings.apiSecret}"></div>
        <div class="form-group"><label>Currency</label>
            <input type="text" name="currency" class="form-control" value="${settings.currency}"></div>
        <div class="form-group">
            <label><input type="checkbox" name="testMode" value="true" ${settings.testMode ? 'checked' : ''}>
            <input type="hidden" name="_testMode" value="on"/> Test Mode</label>
        </div>
        <button type="submit" class="btn">Save</button>
    </form>
</div>
<%@ include file="../layout/page-foot.jsp" %>
