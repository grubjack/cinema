<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/fragment/headTag.jsp"/>
<body>
<p><a class="button" href="${pageContext.request.contextPath}/">${locale['app.home']}</a></p>
<form method="post" action="${pageContext.request.contextPath}/">
    <ul class="form-style-1">
        <input type="hidden" name="command" value="checkLogin">
        <li>
            <h2>${locale['app.welcome']}</h2>
        </li>
        <li>
            <label>Email:</label>
            <input type="email" name="login" placeholder="admin@example.com" class="field-long" required>
        </li>
        <li>
            <label>${locale['user.password']}:</label>
            <input type="password" name="password" placeholder="mypass" class="field-long" required/>
        </li>
        <li>
            <input type="submit" value="${locale['app.login']}">
            <input type="reset" value="${locale['app.reset']}"/>
        </li>
    </ul>
</form>
</body>
</html>
