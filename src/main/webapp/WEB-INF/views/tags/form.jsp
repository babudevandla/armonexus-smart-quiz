<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="${tag.id == null ? 'Add Tag' : 'Edit Tag'}" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card" style="max-width:500px;">
    <h3>${tag.id == null ? 'Add Tag' : 'Edit Tag'}</h3>
    <form:form modelAttribute="tag" method="post" action="${pageContext.request.contextPath}/tags/save">
        <form:hidden path="id"/>
        <div class="form-group">
            <label>Name</label>
            <form:input path="name" cssClass="form-control" required="required"/>
        </div>
        <button type="submit" class="btn">Save</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/tags">Cancel</a>
    </form:form>
</div>
<%@ include file="../layout/page-foot.jsp" %>
