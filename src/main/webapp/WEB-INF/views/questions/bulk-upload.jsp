<%@ include file="../layout/taglibs.jsp" %>
<c:set var="pageTitle" value="Bulk Upload Questions" scope="request"/>
<%@ include file="../layout/page-head.jsp" %>

<div class="card" style="max-width:700px;">
    <h3>Bulk Upload Questions (CSV)</h3>
    <p style="color:#6b7280; font-size:14px;">
        CSV columns (with header row): <br>
        <code>questionText,subjectId,categoryId,questionTypeId,difficultyId,marks,negativeMarks,option1,option2,option3,option4,correctOptionIndex</code><br>
        <code>correctOptionIndex</code> is 1-based (1 = option1 is correct).
    </p>

    <form method="post" action="${pageContext.request.contextPath}/questions/bulk-upload" enctype="multipart/form-data">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group">
            <label>CSV File</label>
            <input type="file" name="file" accept=".csv" class="form-control" required>
        </div>
        <button type="submit" class="btn">Upload &amp; Import</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/questions">Back to Questions</a>
    </form>
</div>

<div class="card" style="max-width:700px;">
    <h4>Sample CSV row</h4>
    <pre style="background:#f9fafb; padding:12px; border-radius:6px; overflow-x:auto; font-size:12px;">questionText,subjectId,categoryId,questionTypeId,difficultyId,marks,negativeMarks,option1,option2,option3,option4,correctOptionIndex
"What is the capital of France?",1,1,1,1,1,0,Paris,London,Berlin,Madrid,1</pre>
</div>

<%@ include file="../layout/page-foot.jsp" %>
