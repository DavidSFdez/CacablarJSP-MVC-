<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>ShareMyTrip - Listado de viajes</title>
</head>
<body>
	<table border="1" align="center">
		<tr>
			<th><a href="listarRelacionados">ID viaje</a></th>
			<th><a href="listarRelacionados?orderBy=Departure_City">Origen</a></th>
			<th><a href="listarRelacionados?orderBy=Destination_City">Destino</a></th>
			<th>Plazas libres</th>
			<th>Estado</th>
		</tr>
		<c:forEach var="entry" items="${listaViajes}" varStatus="i">
			<tr id="item_${i.index}">
				<td><a id="${entry.id}" href="mostrarViaje?id=${entry.id}">${entry.id}</a></td>
				<td>${entry.departure.city}</td>
				<td>${entry.destination.city}</td>
				<td>${entry.availablePax}</td>
				<td>${entry.status}</td>
				<c:if
					test="${entry.status.open==true and user.id==entry.promoterId}">
					<form action="cancelarViaje?tripId=${entry.id}" method="post">
						<td><input type="submit"
							id="cancelarViaje_${entry.departure.city}"
							name="cancelarViaje_${entry.departure.city}" value="Cancelar" /></td>
					</form>
					<form action="navegaModificarViaje?tripId=${entry.id}"
						method="post">
						<td><input type="submit" id="modificarViaje"
							value="Modificar" /></td>
					</form>
				</c:if>
			</tr>
		</c:forEach>
		<tr>
			<form action="filtrarRelacionados">
				<td><input type="text" name="filtrar" align="left" size="15"></td>

				<td><button type="submit" id="filtrar">Filtrar Destino</button></td>
			</form>
		</tr>
	</table>
	<%@ include file="foot.jsp"%>
</body>
</html>