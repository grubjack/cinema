<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="myfn" uri="http://cinema.grubjack.com/functions" %>
<jsp:include page="/fragment/headTag.jsp"/>
<body>
<jsp:include page="/fragment/bodyHeader.jsp"/>
<h1>${lang['ticket.title']}</h1>
<table border="2px" cellpadding="1" cellspacing="1">
    <thead>
    <tr>
        <th width="10%">${lang['show.day']}</th>
        <th width="10%">${lang['show.time']}</th>
        <th width="10%">${lang['show.movie']}</th>
        <th width="10%">${lang['ticket.row']}</th>
        <th width="10%">${lang['ticket.seat']}</th>
        <th width="10%">${lang['ticket.price']}</th>
        <th width="20%">${lang['app.action']}</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="ticket" items="${tickets}">
        <jsp:useBean id="ticket" type="com.grubjack.cinema.to.TicketWithShow"/>
        <tr>
            <td>${lang['day.'.concat(myfn:toLowerCase(ticket.dayOfWeek))]}</td>
            <td>${ticket.timeOfDay.toString()}</td>
            <td>${ticket.movie}</td>
            <td>${ticket.row}</td>
            <td>${ticket.seat}</td>
            <td>${ticket.price}</td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/">
                    <input type="hidden" name="command" value="cancelTicket">
                    <input type="hidden" name="ticketId" value=${ticket.id}>
                    <input id="delete" type="submit" value=${lang['entity.cancel']}>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>