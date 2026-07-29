<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="${user.id == null ? 'Add User' : 'Edit User'}" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card" style="max-width:600px;">
    <h3>${user.id == null ? 'Add User' : 'Edit User'}</h3>

    <form:form modelAttribute="user" method="post" action="${pageContext.request.contextPath}/users/save">
        <form:hidden path="id"/>

        <div class="form-group">
            <label>Full Name</label>
            <form:input path="fullName" cssClass="form-control" required="required"/>
        </div>

        <div class="form-group">
            <label>Email</label>
            <form:input path="email" type="email" cssClass="form-control" required="required" readonly="true"/>
        </div>

        <div class="form-group">
            <label>Phone</label>
            <form:input path="phone" cssClass="form-control" />
        </div>


<div class="form-group">
    <label>Roles</label>
    <div class="checkbox-list">
        <c:forEach var="role" items="${allRoles}">
            <label>
                <input type="checkbox"
                       name="roleIds"
                       value="${role.id}"
                       disabled="disabled"
                       <c:if test="${selectedRoleIds.contains(role.id)}">checked</c:if>>
                ${role.name}
            </label>

            <!-- Hidden field to submit the selected roles -->
            <c:if test="${selectedRoleIds.contains(role.id)}">
                <input type="hidden" name="roleIds" value="${role.id}"/>
            </c:if>
        </c:forEach>
    </div>
</div>

        <div class="form-group">
            <label><form:checkbox path="enabled"/> Enabled</label>
        </div>

        <button type="submit" class="btn">Save</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/users">Cancel</a>
    </form:form>
</div>

<%@ include file="../layout/page-foot.jsp" %>
