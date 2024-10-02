<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>Post List</title>
</head>
<body>
    <h1>Posts</h1>

    <a href="${pageContext.request.contextPath}/posts/new" class="btn">Create New Post</a>

    <table border="1" cellpadding="10">
        <thead>
            <tr>
                <th>Title</th>
                <th>Author</th>
                <th>Comments</th> <!-- 댓글 수를 표시하는 컬럼 -->
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="post" items="${posts}">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/posts/${post.id}">${post.title}</a></td>
                    <td>${post.name}</td>
                    <td>${post.commentList.size()}</td> <!-- 댓글 수 표시 -->
                    <td>
                        <!-- 게시글 상세보기 링크 -->
                        <a href="${pageContext.request.contextPath}/posts/${post.id}" class="btn">View</a>

                        <!-- 게시글 삭제 버튼 -->
                        <form action="${pageContext.request.contextPath}/posts/${post.id}/delete" method="post" style="display:inline;">
                            <button type="submit" class="btn btn-cancel" onclick="return confirm('Are you sure you want to delete this post?');">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>