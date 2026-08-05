<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="${category.id == null ? 'Add Category' : 'Edit Category'}" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card" style="max-width:500px;">
    <h3>${category.id == null ? 'Add Category' : 'Edit Category'}</h3>

    <form method="post" action="${pageContext.request.contextPath}/categories/save">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="id" value="${category.id}"/>
        <div class="form-group">
            <label>Name</label>
            <input type="text" name="name" class="form-control" value="${category.name}" required>
        </div>
        <div class="form-group">
            <label>Subject</label>
            <select name="subject.id" class="form-control">
                <c:forEach var="s" items="${subjects}">
                    <option value="${s.id}" ${category.subject != null && category.subject.id == s.id ? 'selected' : ''}>${s.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label><input type="checkbox" name="active" value="true" ${category.active ? 'checked' : ''}>
                <input type="hidden" name="_active" value="on"/> Active</label>
        </div>
        <button type="submit" class="btn">Save</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/categories">Cancel</a>
    </form>
</div>

<%@ include file="../layout/page-foot.jsp" %>
