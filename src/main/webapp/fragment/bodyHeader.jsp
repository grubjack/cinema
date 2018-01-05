<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<p><a class="button" href="${pageContext.request.contextPath}/">${locale['app.home']}</a></p>
<c:choose>
    <c:when test="${loggedUser != null}">
        ${locale['user.logged']}, ${loggedUser.lastName} ${loggedUser.firstName}
        <br>
        <br>
        <c:if test="${loggedUser.hasRole('ROLE_USER')}">
            <form method="post" action="${pageContext.request.contextPath}/">
                <input type="hidden" name="command" value="tickets">
                <input class="button" type="submit" value="${locale['ticket.title']}">
            </form>
        </c:if>
        <c:if test="${loggedUser.hasRole('ROLE_ADMIN')}">
            <form method="post" action="${pageContext.request.contextPath}/">
                <input type="hidden" name="command" value="users">
                <input class="button" type="submit" value="${locale['users.title']}">
            </form>
        </c:if>
        <form method="post" action="${pageContext.request.contextPath}/">
            <input type="hidden" name="command" value="logout">
            <input class="button" type="submit" value="${locale['app.logout']}">
        </form>
    </c:when>
    <c:otherwise>
        <form method="post" action="${pageContext.request.contextPath}/">
            <input type="hidden" name="command" value="login">
            <input class="button" type="submit" value="${locale['app.login']}">
        </form>
        <form method="post" action="${pageContext.request.contextPath}/">
            <input type="hidden" name="command" value="addUser">
            <input class="button" type="submit" value="${locale['app.register']}">
        </form>
    </c:otherwise>
</c:choose>
