<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/jsp/lang.jsp"/>
<p><a class="button" href="${pageContext.request.contextPath}/">${lang['app.home']}</a></p>
<c:choose>
    <c:when test="${loggedUser != null}">
        ${lang['user.logged']}, ${loggedUser.lastName} ${loggedUser.firstName}
        <br>
        <br>
        <c:if test="${loggedUser.hasRole('ROLE_USER')}">
            <form method="post" action="${pageContext.request.contextPath}/">
                <input type="hidden" name="command" value="tickets">
                <input class="button" type="submit" value="${lang['ticket.title']}">
            </form>
        </c:if>
        <c:if test="${loggedUser.hasRole('ROLE_ADMIN')}">
            <form method="post" action="${pageContext.request.contextPath}/">
                <input type="hidden" name="command" value="users">
                <input class="button" type="submit" value="${lang['users.title']}">
            </form>
        </c:if>
        <form method="post" action="${pageContext.request.contextPath}/">
            <input type="hidden" name="command" value="logout">
            <input class="button" type="submit" value="${lang['app.logout']}">
        </form>
    </c:when>
    <c:otherwise>
        <form method="post" action="${pageContext.request.contextPath}/">
            <input type="hidden" name="command" value="login">
            <input type="hidden" name="from" value="${pageContext.request.requestURI}">
            <input class="button" type="submit" value="${lang['app.login']}">
        </form>
        <form method="post" action="${pageContext.request.contextPath}/">
            <input type="hidden" name="command" value="addUser">
            <input type="hidden" name="from" value="${pageContext.request.requestURI}">
            <input class="button" type="submit" value="${lang['app.register']}">
        </form>
    </c:otherwise>
</c:choose>
<c:if test="${userAdded !=null}">
    <br><br>
    <div class="notification">
            ${lang['user.registered']}
    </div>
    <br>
</c:if>