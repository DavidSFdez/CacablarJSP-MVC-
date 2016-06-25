<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!doctype html>
<html>
<head>
<title>ShareMyTrip - Registrar Viaje</title>
</head>
<body>
	<form action="registrarViaje" method="post">
		<table>
			<tr>
				<td align="right">id: ${trip.id}</td>
				<td><input type="text" name="idTrip" size="15"
					value="${trip.id}" hidden="hidden" /></td>
			</tr>
			<tr>
				<td align="right">Calle salida</td>
				<td><input type="text" name="calleSalida" size="15"
					required="required" value="${trip.departure.address}" /></td>
			</tr>
			<tr>
				<td align="right">Ciudad salida</td>
				<td><input type="text" name="ciudadSalida" size="15"
					required="required" value="${trip.departure.city}" /></td>
			</tr>
			<tr>
				<td align="right">Provincia salida</td>
				<td><input type="text" name="provinciaSalida" size="15"
					required="required" value="${trip.departure.state}" /></td>
			</tr>
			<tr>
				<td align="right">País salida</td>
				<td><input type="text" name="paisSalida" size="15"
					required="required" value="${trip.departure.country}" /></td>
			</tr>
			<tr>
				<td align="right">Codigo postal salida</td>
				<td><input type="text" name="cpSalida" size="15"
					required="required" value="${trip.departure.zipCode}" /></td>
			</tr>
			<tr>
				<td align="right">Coordenadas latitud (opcional)</td>
				<td><input type="text" name="coordenadasLatSalida" size="15"
					value="${trip.departure.waypoint.lat}" /></td>
			</tr>
			<tr>
				<td align="right">Coordenadas longitud (opcional)</td>
				<td><input type="text" name="coordenadasLonSalida" size="15"
					value="${trip.departure.waypoint.lon}" /></td>
			</tr>
			<tr>
				<td align="right">Fecha salida (yyyy-mm-dd hh:mm:ss)</td>
				<td><input type="text" name="fechaSalida" size="15"
					required="required" value="${trip.departureDate}" /></td>
			</tr>
			<tr>
				<td align="right">Calle Destino</td>
				<td><input type="text" name="calleDestino" size="15"
					required="required" value="${trip.destination.address}" /></td>
			</tr>
			<tr>
				<td align="right">Ciudad Destino</td>
				<td><input type="text" name="ciudadDestino" size="15"
					required="required" value="${trip.destination.city}" /></td>
			</tr>
			<tr>
				<td align="right">Provincia Destino</td>
				<td><input type="text" name="provinciaDestino" size="15"
					required="required" value="${trip.destination.state}" /></td>
			</tr>
			<tr>
				<td align="right">País Destino</td>
				<td><input type="text" name="paisDestino" size="15"
					required="required" value="${trip.destination.country}" /></td>
			</tr>
			<tr>
				<td align="right">Codigo postal Destino</td>
				<td><input type="text" name="cpDestino" size="15"
					required="required" value="${trip.destination.zipCode}" /></td>
			</tr>
			<tr>
				<td align="right">Coordenadas latitud (opcional)</td>
				<td><input type="text" name="coordenadasLatDestino" size="15"
					value="${trip.destination.waypoint.lat}" /></td>
			</tr>
			<tr>
				<td align="right">Coordenadas longitud (opcional)</td>
				<td><input type="text" name="coordenadasLonDestino" size="15"
					value="${trip.destination.waypoint.lon}" /></td>
			</tr>
			<tr>
				<td align="right">Fecha llegada (yyyy-mm-dd hh:mm:ss)</td>
				<td><input type="text" name="fechaDestino" size="15"
					required="required" value="${trip.arrivalDate}" /></td>
			</tr>
			<tr>
				<td align="right">Fecha límite para apuntarse (yyyy-mm-dd hh:mm:ss)</td>
				<td><input type="text" name="fechaLimite" size="15"
					required="required" value="${trip.closingDate}" /></td>
			</tr>
			<tr>
				<td align="right">Coste estimado del viaje en total</td>
				<td><input type="text" name="coste" size="15"
					required="required" value="${trip.estimatedCost}" /></td>
			</tr>
			<tr>
				<td align="right">Descripción o comentarios</td>
				<td><input type="text" name="descripcion" size="15"
					required="required" value="${trip.comments}" /></td>
			</tr>
			<tr>
				<td align="right">Número de plazas</td>
				<td><input type="text" name="nPlazasMax" size="15"
					required="required" value="${trip.maxPax}" /></td>
			</tr>
			<tr>
				<td><input type="submit" name="Registrar" value="Enviar" /></td>

			</tr>
		</table>
	</form>
	<jsp:include page="mensajes.jsp"></jsp:include>
	<%@ include file="foot.jsp"%>
</body>
</html>