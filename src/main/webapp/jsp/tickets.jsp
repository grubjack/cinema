<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="myfn" uri="http://cinema.grubjack.com/functions" %>
<jsp:include page="/fragment/headTag.jsp"/>
<body>
<jsp:include page="/fragment/bodyHeader.jsp"/>
<h1>${locale['ticket.title']}</h1>
<table border="2px" cellpadding="1" cellspacing="1">
    <thead>
    <tr>
        <th width="10%">${locale['show.day']}</th>
        <th width="10%">${locale['show.time']}</th>
        <th width="10%">${locale['show.movie']}</th>
        <th width="10%">${locale['ticket.row']}</th>
        <th width="10%">${locale['ticket.seat']}</th>
        <th width="10%">${locale['ticket.price']}</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="ticket" items="${tickets}">
        <c:set value="${myfn:findByTicket(ticket.id)}" var="show"/>
        <jsp:useBean id="ticket" class="com.grubjack.cinema.model.Ticket"/>
        <jsp:useBean id="show" class="com.grubjack.cinema.model.Show"/>
        <tr>
            <td>${locale['day.'.concat(fn:toLowerCase(show.dayOfWeek))]}</td>
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