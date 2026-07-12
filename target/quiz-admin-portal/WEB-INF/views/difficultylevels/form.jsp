<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="${level.id == null ? 'Add Level' : 'Edit Level'}" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card" style="max-width:500px;">
    <h3>${level.id == null ? 'Add Level' : 'Edit Level'}</h3>
    <form:form modelAttribute="level" method="post" action="${pageContext.request.contextPath}/difficulty-levels/save">
        <form:hidden path="id"/>
        <div class="form-group">
            <label>Name</label>
            <form:input path="name" cssClass="form-control" required="required"/>
        </div>
        <div class="form-group">
            <label>Weight (used for scoring multiplier)</label>
            <form:input path="weight" type="number" cssClass="form-control" required="required"/>
        </div>
        <button type="submit" class="btn">Save</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/difficulty-levels">Cancel</a>
    </form:form>
</div>
<%@ include file="../layout/page-foot.jsp" %>
