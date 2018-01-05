<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="/fragment/headTag.jsp"/>
<body>
    <jsp:include page="/fragment/bodyHeader.jsp"/>
    <h1>${locale['app.schedule']}</h1>
    <table>
        <tr>
            <td/>
            <th>${locale['day.monday']}</th>
            <th>${locale['day.tuesday']}</th>
            <th>${locale['day.wednesday']}</th>
            <th>${locale['day.thursday']}</th>
            <th>${locale['day.friday']}</th>
            <th>${locale['day.saturday']}</th>
            <th>${locale['day.sunday']}</th>
        </tr>
        <c:forEach var="time" items="${times}">
            <tr>
                <th>${time}</th>
                <c:forEach var="day" items="${days}">
                    <c:set value="${showService.findByDayAndTime(day,time)}" var="show"/>
                    <td>
                        <c:choose>
                            <c:when test="${show != null}">
                                <form method="post" action="${pageContext.request.contextPath}/">
                                    <input type="hidden" name="command" value="hall">
                                    <input type="hidden" name="showId" value=${show.id}>
                                    <input id="movie" type="submit" value="&quot;${show.movie}&quot;">
                                </form>
                                <br>
                                <c:if test="${(loggedUser != null) && (loggedUser.hasRole('ROLE_ADMIN'))}">
                                    <br>
                                    <form method="post" action="${pageContext.request.contextPath}/">
                                        <input type="hidden" name="command" value="cancelMovie">
                                        <input type="hidden" name="showId" value=${show.id}>
                                        <input id="delete" type="submit" value=${locale['entity.delete']}>
                                    </form>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${(loggedUser != null) && (loggedUser.hasRole('ROLE_ADMIN'))}">
                                    <form method="post" action="${pageContext.request.contextPath}/">
                                        <input type="hidden" name="command" value="addMovie">
                                        <input type="hidden" name="day" value=${day}>
                                        <input type="hidden" name="time" value=${time}>
                                        <input id="add" type="submit" value=${locale['entity.add']}>
                                    </form>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
