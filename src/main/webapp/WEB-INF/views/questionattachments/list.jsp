<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Question Attachments" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card">
    <h3>Upload New Attachment</h3>
    <form method="post" action="${pageContext.request.contextPath}/question-attachments/upload" enctype="multipart/form-data">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group" style="display:flex; gap:12px; align-items:flex-end;">
            <div style="flex:1;">
                <label>Question</label>
                <select name="questionId" class="form-control" required>
                    <c:forEach var="q" items="${questions}">
                        <option value="${q.id}">${q.questionText.length() > 60 ? q.questionText.substring(0,60).concat('...') : q.questionText}</option>
                    </c:forEach>
                </select>
            </div>
            <div style="flex:1;">
                <label>File</label>
                <input type="file" name="file" class="form-control" required>
            </div>
            <div>
                <button type="submit" class="btn">Upload</button>
            </div>
        </div>
    </form>
</div>

<div class="card">
    <h3>Existing Attachments</h3>
    <table class="data-table">
        <thead><tr><th>#</th><th>File Name</th><th>Type</th><th>Question</th><th>Uploaded At</th><th>Actions</th></tr></thead>
        <tbody>
        <c:forEach var="a" items="${attachments}" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${a.fileName}</td>
                <td>${a.fileType}</td>
                <td>${a.question != null ? a.question.questionText.substring(0, a.question.questionText.length() > 40 ? 40 : a.question.questionText.length()) : '-'}</td>
                <td>${a.uploadedAt}</td>
                <td>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/question-attachments/${a.id}/delete"
                       onclick="return confirm('Delete this attachment?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="../layout/page-foot.jsp" %>
