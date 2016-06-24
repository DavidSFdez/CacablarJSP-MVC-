<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<p>
	<a id="navegaPrincipal" href="navegaPrincipal">Volver a inicio</a>
</p>

<c:if test="${user!=null}">
	<p>
		<a id="cerrarSesion" href="cerrarSesion">Cerrar sesión</a>
	</p>
</c:if>