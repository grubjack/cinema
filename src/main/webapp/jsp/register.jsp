<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="/fragment/headTag.jsp"/>
<body>
<jsp:include page="/fragment/bodyHeader.jsp"/>
<form method="post" action="${pageContext.request.contextPath}/">
    <input type="hidden" name="command" value="registerUser">
    <input type="hidden" name="from" value="${param.from}">
    <ul class="form-style-1">
        <li>
            <h2>${lang['users.add']}</h2>
        </li>
        <li>
            <label>${lang['user.firstname']}</label>
            <input type="text" name="firstname" required/>
        </li>
        <li>
            <label>${lang['user.lastname']}</label>
            <input type="text" name="lastname" required/>
        </li>
        <li>
            <label>Email:</label>
            <input type="email" name="email" required/>
        </li>
        <li>
            <label>${lang['user.password']}</label>
            <input type="password" name="password" required/>
        </li>
        <li>
            <label>${lang['user.role']}</label>
            <select name="selectedRoles"  multiple="multiple" class="field-select">
                <c:forEach var="role" items="${roles}">
                    <option>${role}</option>
                </c:forEach>
            </select>
        </li>
        <li>
            <input type="submit" value="${lang['entity.add']}">
            <input type="reset" value="${lang['app.reset']}"/>
        </li>
    </ul>
</form>
</body>
</html>
