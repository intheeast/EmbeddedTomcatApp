<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Us</title>
</head>
<body>
    <h1>Contact Us</h1>
    <p>Please use the form below to contact us with ideas, issues, questions, general feedback or anything else. We typically respond within one business day.</p>
    <p>All fields are required.</p>

    <form:form action="/contact" method="POST" modelAttribute="userMessage">
        <div>
            <label for="name">Your name:</label>
            <form:input path="name" id="name" />
            <form:errors path="name" cssClass="errorMessage" />
        </div>
        <div>
            <label for="email">Your e-mail address:</label>
            <form:input path="email" id="email" />
            <form:errors path="email" cssClass="errorMessage" />
        </div>
        <div>
            <label for="message">Your message:</label>
            <form:textarea path="text" id="message" />
            <form:errors path="text" cssClass="errorMessage" />
        </div>
        <div>
            <input type="submit" value="Submit" />
        </div>
    </form:form>
</body>
</html>
