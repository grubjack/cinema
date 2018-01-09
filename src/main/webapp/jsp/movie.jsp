<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<jsp:include page="/fragment/headTag.jsp"/>
<body>
<jsp:include page="/fragment/bodyHeader.jsp"/>
<form method="post" action="${pageContext.request.contextPath}/">
    <ul class="form-style-1">
        <input type="hidden" name="command" value="createMovie">
        <li>
            <h2>${lang['movie.add']}</h2>
        </li>
        <li>
            <label>${lang['show.day']}</label>
            <input type="text" value="${lang['day.'.concat(fn:toLowerCase(day))]}" disabled/>
        </li>
        <li>
            <label>${lang['show.time']}</label>
            <input type="text" value="${time}" disabled/>
        </li>
        <li>
            <label>${lang['show.movie']}</label>
            <input type="text" name="movie" value="${sessionScope.movie}" required/>
        </li>
        <li>
            <input type="submit" value="${lang['entity.add']}">
            <input type="reset" value="${lang['app.reset']}"/>
        </li>
    </ul>
</form>
</body>
</html>
