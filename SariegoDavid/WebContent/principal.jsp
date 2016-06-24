<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${user == null}">
	<%@ include file="login.jsp" %>
</c:if>

<c:if test="${user != null}">
	<%@ include file="menu.jsp" %>
</c:if>