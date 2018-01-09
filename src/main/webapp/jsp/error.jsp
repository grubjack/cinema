<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<jsp:include page="/fragment/headTag.jsp"/>
<body>
<jsp:include page="/fragment/bodyHeader.jsp"/>
<h1>${lang['app.error']}: ${errorMessage != null ? errorMessage : lang['error.unknown']}</h1>
</body>
</html>