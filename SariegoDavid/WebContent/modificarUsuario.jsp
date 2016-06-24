<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!doctype html>
<html>
<head>
<title>ShareMyTrip - Página principal del usuario</title>
</head>
<body>
	<form action="modificarUsuario">
		<table>
			<tr>
				<td>Login:</td>
				<td><input type="text" name="login" size="15"
					required="required" value="${user.login}"/></td>
			</tr>
			<tr>
				<td>Nombre:</td>
				<td><input type="text" name="nombreUsuario" size="15"
					required="required" value="${user.name}" /></td>
			</tr>
			<tr>
				<td>Apellidos:</td>
				<td><input type="text" name="apellidoUsuario" size="15"
					required="required" value="${user.surname}"/></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><input type="text" name="emailUsuario" size="15"
					required="required" value="${user.email}"/></td>
			</tr>
			<tr>
				<td>Contraseña antigua:</td>
				<td id="password">
				<td><input type="password" name="password" size="15"
					required="required" /></td>
			</tr>
			<tr>
				<td>Contraseña nueva:</td>
				<td id="passwordNueva"><input type="password"
					name="passwordNueva" size="15" /></td>
			</tr>
			<tr>
				<td>Repite contraseña:</td>
				<td id="passwordNueva2"><input type="password"
					name="passwordNueva2" size="15"></td>
			</tr>
			<tr>
				<td><input type="submit" value="Enviar" /></td>
			</tr>

		</table>
	</form>
	<br />
	<jsp:include page="mensajes.jsp"></jsp:include>
	<%@ include file="foot.jsp"%>
</body>
</html>
