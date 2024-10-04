<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Post Detail</title>
</head>
<body>
    <h1>${post.title}</h1>
    <p><strong>Author:</strong> ${post.name}</p>
    <p><strong>Email:</strong> ${post.email}</p>
    <p><strong>Website:</strong> ${post.web}</p>
    <p><strong>Text:</strong> ${post.text}</p>

    <!-- 댓글 목록 -->
    <h2>Comments</h2>
    <c:if test="${!empty comments}">
        <ul>
            <c:forEach var="comment" items="${comments}">
                <li>
                    <strong>${comment.name} (${comment.email})</strong>: ${comment.text}
                </li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${empty comments}">
        <p>No comments yet.</p>
    </c:if>

    <!-- 파일 업로드 -->
    <h2>Upload a File</h2>
	<form method="post" action="${pageContext.request.contextPath}/posts/${post.id}/files/upload" enctype="multipart/form-data">
	    <label for="file">Choose a file:</label>
	    <input type="file" name="file"/>
	    <br />
	    <button type="submit">Upload</button>
	</form>

    <!-- 업로드된 파일 목록 -->
    <h2>Uploaded Files</h2>
	<c:if test="${!empty files}">
	    <ul>
	        <c:forEach var="file" items="${files}">
	            <li>
	                <a href="${pageContext.request.contextPath}/posts/${post.id}/files/${file.id}/download">
	                    ${file.fileName}
	                </a>
	            </li>
	        </c:forEach>
	    </ul>
	</c:if>
	<c:if test="${empty files}">
	    <p>No files uploaded yet.</p>
	</c:if>

    <!-- 게시글 삭제 버튼 -->
    <form action="${pageContext.request.contextPath}/posts/${post.id}/delete" method="post">
        <button type="submit" class="btn btn-cancel" onclick="return confirm('Are you sure you want to delete this post?');">Delete Post</button>
    </form>

    <a href="${pageContext.request.contextPath}/posts">Back to Posts</a>
</body>
</html>