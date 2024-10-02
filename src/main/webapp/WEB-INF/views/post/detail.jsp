<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>Post Detail</title>
</head>
<body>
    <h1>${post.title}</h1>
    <p><strong>Author:</strong> ${post.name}</p>
    <p><strong>Email:</strong> ${post.email}</p>
    <p><strong>Website:</strong> ${post.web}</p>
    <p><strong>Text:</strong> ${post.text}</p>
    <p><strong>Author's IP Address:</strong> ${post.ipAddress}</p> <!-- 게시글 작성자의 IP 주소 -->

    <!-- 댓글 목록 -->
    <h2>Comments</h2>
    <c:if test="${!empty comments}">
        <ul>
            <c:forEach var="comment" items="${comments}">
                <li>
                    <strong>${comment.name} (${comment.email})</strong>: ${comment.text}
                    <br />
                    <strong>Commenter's IP Address:</strong> ${comment.ipAddress} <!-- 댓글 작성자의 IP 주소 -->
                    <br />
                    <!-- 댓글 수정 및 삭제 버튼 -->
                    <a href="${pageContext.request.contextPath}/posts/${post.id}/comments/${comment.id}/edit">Edit</a>
                    <form action="${pageContext.request.contextPath}/posts/${post.id}/comments/${comment.id}/delete" method="post" style="display:inline;">
                        <button type="submit" class="btn btn-cancel" onclick="return confirm('Are you sure you want to delete this comment?');">Delete</button>
                    </form>
                </li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${empty comments}">
        <p>No comments yet.</p>
    </c:if>

    <!-- 새 댓글 추가 폼 -->
    <h2>Add a Comment</h2>
    <form:form method="post" action="${pageContext.request.contextPath}/posts/${post.id}/comments" modelAttribute="comment">
        <label for="name">Name:</label>
        <form:input path="name" />
        <br />

        <label for="email">Email:</label>
        <form:input path="email" />
        <br />

        <label for="text">Comment:</label>
        <form:textarea path="text" />
        <br />

        <button type="submit">Submit Comment</button>
    </form:form>

    <!-- 게시글 삭제 버튼 -->
    <form action="${pageContext.request.contextPath}/posts/${post.id}/delete" method="post">
        <button type="submit" class="btn btn-cancel" onclick="return confirm('Are you sure you want to delete this post?');">Delete Post</button>
    </form>

    <a href="${pageContext.request.contextPath}/posts">Back to Posts</a>
</body>
</html>