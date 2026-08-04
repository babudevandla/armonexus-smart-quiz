<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="${subject.id == null ? 'Add Subject' : 'Edit Subject'}" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>


<div class="card" style="max-width:500px;">
    <h3>${subject.id == null ? 'Add Subject' : 'Edit Subject'}</h3>
    <form:form modelAttribute="subject" method="post" action="${pageContext.request.contextPath}/subjects/save">
        <form:hidden path="id"/>
        <div class="form-group">
            <label>Name</label>
            <form:input path="name" cssClass="form-control" required="required"/>
            <form:errors path="name" cssClass="text-danger"/>
        </div>
        <div class="form-group">
            <label>Description</label>
            <form:textarea path="description" cssClass="form-control" rows="3"/>
        </div>
        <div class="form-group">
            <label><form:checkbox path="active"/> Active</label>
        </div>
        <button type="submit" class="btn">Save</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/subjects">Cancel</a>
    </form:form>
</div>

<%@ include file="../layout/page-foot.jsp" %>
