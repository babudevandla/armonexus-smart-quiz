<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="${role.id == null ? 'Add Role' : 'Edit Role'}" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card" style="max-width:500px;">
    <h3>${role.id == null ? 'Add Role' : 'Edit Role'}</h3>
    <form:form modelAttribute="role" method="post" action="${pageContext.request.contextPath}/roles/save">
        <form:hidden path="id"/>
        <div class="form-group">
            <label>Role Name (e.g. ROLE_ADMIN)</label>
            <form:input path="name" cssClass="form-control" required="required"/>
        </div>
        <div class="form-group">
            <label>Description</label>
            <form:input path="description" cssClass="form-control"/>
        </div>
        <button type="submit" class="btn">Save</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/roles">Cancel</a>
    </form:form>
</div>

<%@ include file="../layout/page-foot.jsp" %>
