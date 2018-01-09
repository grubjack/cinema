<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/fragment/headTag.jsp"/>
<body>
<jsp:include page="/fragment/bodyHeader.jsp"/>
<form method="post" action="${pageContext.request.contextPath}/">
    <ul class="form-style-1">
        <input type="hidden" name="command" value="checkLogin">
        <input type="hidden" name="from" value="${param.from}">
        <li>
            <h2>${lang['app.welcome']}</h2>
        </li>
        <li>
            <label>Email:</label>
            <input type="email" value="${sessionScope.login}" name="login" placeholder="admin@example.com"
                   class="field-long" required>
        </li>
        <li>
            <label>${lang['user.password']}:</label>
            <input type="password" value="${sessionScope.password}" name="password" placeholder="mypass"
                   class="field-long" required/>
        </li>
        <li>
            <input type="submit" value="${lang['app.login']}">
            <input type="reset" value="${lang['app.reset']}"/>
        </li>
    </ul>
</form>
</body>
</html>
