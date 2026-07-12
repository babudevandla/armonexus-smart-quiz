<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="${practiceSet.id == null ? 'Add Practice Set' : 'Edit Practice Set'}" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>
<div class="card" style="max-width:600px;">
    <h3>${practiceSet.id == null ? 'Add Practice Set' : 'Edit Practice Set'}</h3>
    <form method="post" action="${pageContext.request.contextPath}/practice-sets/save">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="id" value="${practiceSet.id}"/>
        <div class="form-group">
            <label>Title</label>
            <input type="text" name="title" class="form-control" value="${practiceSet.title}" required>
        </div>
        <div class="form-group">
            <label>Subject</label>
            <select name="subject.id" class="form-control">
                <c:forEach var="s" items="${subjects}">
                    <option value="${s.id}" ${practiceSet.subject != null && practiceSet.subject.id == s.id ? 'selected' : ''}>${s.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>Description</label>
            <textarea name="description" class="form-control" rows="3">${practiceSet.description}</textarea>
        </div>
        <div class="form-group">
            <label><input type="checkbox" name="active" value="true" ${practiceSet.active ? 'checked' : ''}>
                <input type="hidden" name="_active" value="on"/> Active</label>
        </div>
        <button type="submit" class="btn">Save</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/practice-sets">Cancel</a>
    </form>
</div>
<%@ include file="../layout/page-foot.jsp" %>
