<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="${group.id == null ? 'Add Group' : 'Edit Group'}" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card" style="max-width:500px;">
    <h3>${group.id == null ? 'Add Group' : 'Edit Group'}</h3>
    <form:form modelAttribute="group" method="post" action="${pageContext.request.contextPath}/user-groups/save">
        <form:hidden path="id"/>
        <div class="form-group">
            <label>Name</label>
            <form:input path="name" cssClass="form-control" required="required"/>
        </div>
        <div class="form-group">
            <label>Description</label>
            <form:input path="description" cssClass="form-control"/>
        </div>
        <button type="submit" class="btn">Save</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/user-groups">Cancel</a>
    </form:form>
</div>
<%@ include file="../layout/page-foot.jsp" %>
