<%@page contentType="text/html" pageEncoding="UTF-8" %>
<form method="post" action="${pageContext.request.contextPath}/">
    <input type="hidden" name="command" value="changeLocale">
    <input type="hidden" name="from" value="${pageContext.request.requestURI}">
    <input type="hidden" name="language" value="en">
    <input type="image" src="images/en.png" border="0" alt="English"/>
</form>

<form method="post" action="${pageContext.request.contextPath}/">
    <input type="hidden" name="command" value="changeLocale">
    <input type="hidden" name="from" value="${pageContext.request.requestURI}">
    <input type="hidden" name="language" value="ru">
    <input type="image" src="images/ru.png" border="0" alt="Русский"/>
</form>

<form method="post" action="${pageContext.request.contextPath}/">
    <input type="hidden" name="command" value="changeLocale">
    <input type="hidden" name="from" value="${pageContext.request.requestURI}">
    <input type="hidden" name="language" value="uk">
    <input type="image" src="images/uk.png" border="0" alt="Українська"/>
</form>