<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>ShareMyTrip - Menu</title>
</head>
<body>
	<table>
		<tr>
			<td><a id="listarViajes" href="listarViajes">Lista de viajes
					disponibles</a></td>
		</tr>
		<tr>
			<td><a id="listarRelacionados" href="listarRelacionados">Listar
					viajes relacionados</a></td>
		</tr>
		<tr>
			<td><a id="navegaRegistrarViaje" href="navegaRegistrarViaje">Registrar
					nuevo viaje</a></td>
		</tr>
		<tr>
			<td><a id="modificarUsuario" href="navegaModificarUsuario">Modificar
					datos</a></td>
		</tr>
		<tr>
			<td><a id="cerrarSesion" href="cerrarSesion">Cerrar sesión</a></td>
		</tr>
	</table>
	<jsp:include page="mensajes.jsp"></jsp:include>
</body>
</html>
