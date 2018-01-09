<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/fragment/headTag.jsp"/>
<body>
<jsp:include page="/fragment/bodyHeader.jsp"/>
<h1>${lang['users.title']}</h1>
<table border="2px" cellpadding="1" cellspacing="1">
    <thead>
    <tr>
        <th width="20%">${lang['user.firstname']}</th>
        <th width="20%">${lang['user.lastname']}</th>
        <th width="20%">Email</th>
        <th width="20%">${lang['app.action']}</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <jsp:useBean id="user" class="com.grubjack.cinema.model.User"/>
        <tr class="${user.hasRole('ROLE_ADMIN') ? 'admin' : 'user'}">
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.email}</td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/">
                    <input type="hidden" name="command" value="deleteUser">
                    <input type="hidden" name="userId" value=${user.id}>
                    <input id="delete" type="submit" value=${lang['entity.delete']}>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>