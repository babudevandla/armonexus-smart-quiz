<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="${type.id == null ? 'Add Type' : 'Edit Type'}" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card" style="max-width:500px;">
    <h3>${type.id == null ? 'Add Type' : 'Edit Type'}</h3>
    <form:form modelAttribute="type" method="post" action="${pageContext.request.contextPath}/question-types/save">
        <form:hidden path="id"/>
        <div class="form-group">
            <label>Name (e.g. SINGLE_CHOICE, MULTIPLE_CHOICE, TRUE_FALSE)</label>
            <form:input path="name" cssClass="form-control" required="required"/>
        </div>
        <button type="submit" class="btn">Save</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/question-types">Cancel</a>
    </form:form>
</div>
<%@ include file="../layout/page-foot.jsp" %>
