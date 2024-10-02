<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>Edit Comment</title>
</head>
<body>
    <h1>Edit Comment</h1>
    <form:form method="post" action="${pageContext.request.contextPath}/posts/${post.id}/comments/${comment.id}/edit" modelAttribute="comment">
        <label for="name">Name:</label>
        <form:input path="name" />
        <br />

        <label for="email">Email:</label>
        <form:input path="email" />
        <br />

        <label for="text">Comment:</label>
        <form:textarea path="text" />
        <br />

        <button type="submit">Update Comment</button>
    </form:form>

    <a href="${pageContext.request.contextPath}/posts/${post.id}">Back to Post</a>
</body>
</html>