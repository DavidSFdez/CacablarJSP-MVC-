<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!doctype html>
<html>
<head>
<title>ShareMyTrip - Registrar Usuario</title>
</head>
<body>
<form action="registrarse">
	<table>
		<tr>
			<td>Login:</td>
			<td><input type="text" name="login" size="15" required="required" value="${login}" /></td>
		</tr>
		<tr>
			<td>Nombre:</td>
			<td><input type="text" name="nombreUsuario" size="15" required="required" value="${nombre}"  /></td>
		</tr>
		<tr>
			<td>Apellidos:</td>
			<td><input type="text" name="apellidoUsuario" size="15" required="required" value="${apellidos}" /></td>
		</tr>
		<tr>
			<td>Email:</td>
			<td><input type="text" name="emailUsuario" size="15" required="required" value="${email}" /></td>
		</tr>
		<tr>
			<td>Contraseña:</td>
			<td><input type="password" name="password" size="15" required="required" /></td>
		</tr>
		<tr>
			<td>Repite Contraseña:</td>
			<td><input type="password" name="password2" size="15" required="required" /></td>
		</tr>
		<tr>
			<td><input type="submit" value="Enviar" />
		</tr>
	</table>
	</form>
	<jsp:include page="mensajes.jsp"></jsp:include>
	<%@ include file="foot.jsp" %>
</body>
</html>
