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
    </tr>
    </thead>
    <tbody>
    <c:forEach var="ticket" items="${tickets}">
        <c:set value="${showService.findByTicket(ticket.id)}" var="show"/>
        <jsp:useBean id="ticket" class="com.grubjack.cinema.model.Ticket"/>
        <jsp:useBean id="show" class="com.grubjack.cinema.model.Show"/>
        <tr>
            <td>${lang['day.'.concat(myfn:toLowerCase(show.dayOfWeek))]}</td>
            <td>${show.timeOfDay.toString()}</td>
            <td>${show.movie}</td>
            <td>${ticket.row}</td>
            <td>${ticket.seat}</td>
            <td>${ticket.price}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>