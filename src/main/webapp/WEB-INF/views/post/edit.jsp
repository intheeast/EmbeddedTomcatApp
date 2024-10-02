<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>Edit Post</title>
</head>
<body>
    <h1>Edit Post</h1>

    <form:form method="post" action="${pageContext.request.contextPath}/posts/${post.id}/edit" modelAttribute="post">
        <label for="name">Name:</label>
        <form:input path="name" />
        <br />

        <label for="email">Email:</label>
        <form:input path="email" />
        <br />

        <label for="web">Website:</label>
        <form:input path="web" />
        <br />

        <label for="title">Title:</label>
        <form:input path="title" />
        <br />

        <label for="text">Text:</label>
        <form:textarea path="text" />
        <br />

        <button type="submit">Update</button>
    </form:form>

    <a href="${pageContext.request.contextPath}/posts">Cancel</a>
</body>
</html>
