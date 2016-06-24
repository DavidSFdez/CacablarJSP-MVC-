<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${mensaje != null}">
	<p id='mensaje'>${mensaje}</p>
</c:if>