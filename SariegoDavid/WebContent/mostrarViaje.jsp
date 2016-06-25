<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>ShareMyTrip - Menu</title>
</head>
<body>
	<table border="1" align="center">
		<tr>
			<c:if test="${user != null}">
				<th>Id Promotor</th>
			</c:if>
			<th>Fecha cierre</th>
			<th>Fecha salida</th>
			<th>Fecha llegada</th>
			<th>Origen</th>
			<th>Destino</th>
			<th>Plazas libres</th>
		</tr>
		<tr>
			<c:if test="${user != null}">
				<td>${trip.promoterId }</td>
			</c:if>
			<td>${trip.closingDate}</td>
			<td>${trip.departureDate}</td>
			<td>${trip.arrivalDate}</td>
			<td>${trip.departure.city}</td>
			<td>${trip.destination.city}</td>
			<td>${trip.availablePax}</td>
		</tr>
	</table>

	<c:if test="${user != null}">

		<!--  Si el usuario no es promotor -->
		<c:if test="${user.id != trip.promoterId }">
			<c:if
				test="${(seat==null || seat.status.accepted==false) and application==null and trip.active}">

				<form action="solicitarPlaza?tripId=${requestScope.trip.id}"
					method="post">
					<input type="submit" id="solicitarPlaza" name="solicitarPlaza"
						value="Solicitar Plaza" />
				</form>
			</c:if>

			<c:if test="${application!=null and trip.active }">
				<form
					action="cancelarSolicitud?tripId=${requestScope.application.tripId}"
					method="post">
					<input type="submit" id="cancelarSolicitud"
						name="cancelarSolicitud" value="Cancelar	Solicitud" />
				</form>
				</td>
			</c:if>
		</c:if>

		<!--  si el usuario es promotor -->

		<table border="1">
			<tr>
				<th>Id usuario</th>
				<th>Comentario</th>
				<th>Estado</th>
				<c:if test="${trip.passed==true}">
					<th>Comentar ratting</th>
					<th>valorar ratting</th>
				</c:if>
			</tr>
			<c:forEach var="entry" items="${seats}">
				<tr>
					<td>${entry.userId}</td>
					<td>${entry.comment}</td>
					<td>${entry.status}</td>
					<c:if
						test="${entry.status.accepted and user.id==trip.promoterId and trip.active and entry.userId!=trip.promoterId}">
						<form
							action="cancelarSeat?tripId=${entry.tripId}&userId=${entry.userId}"
							method="post">
							<td><input type="submit" id="cancelarSeat_${entry.userId}"
								name="cancelarSeat_${entry.userId}" value="Cancelar	Plaza" /></td>
						</form>
					</c:if>
					<c:if
						test="${seat!=null and seat.status.accepted and entry.userId==user.id and trip.active}">
						<form action="cancelarPlaza?tripId=${requestScope.seat.tripId}"
							method="post">
							<td><input type="submit" id="cancelarPlaza_${entry.userId}"
								name="cancelarPlaza_${entry.userId}" value="Cancelar	Plaza" /></td>
						</form>
					</c:if>
					<c:if
						test="${entry.status.accepted and entry.userId!=user.id and trip.passed==true and ((seat!=null and seat.status.accepted) or user.id==trip.promoterId)}">
						<form
							action="comentarSeat?tripId=${entry.tripId}&userId=${entry.userId}"
							method="post">
							<td><input type="text" name="comentarioSeat"
								id="comentarioSeat_${entry.userId}" align="left" size="15"></td>
							<td><input type="text" name="valoracionSeat"
								id="valoracionSeat_${entry.userId}" align="left" size="15"></td>
							<td><input type="submit" id="comentarioSeat_${entry.userId}"
								name="comentarioSeat_${entry.userId}" value="Comentar" /></td>
						</form>
					</c:if>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${trip.passed }">
			<table border=1>
				<tr>
					<th>Del usuario</th>
					<th>Sobre usuario</th>
					<th>Comentario</th>
					<th>Valoracion</th>
				</tr>
				<c:forEach var="entry" items="${ratings}">
					<tr>
						<td>${entry.seatFromUserId}</td>
						<td>${entry.seatAboutUserId}</td>
						<td>${entry.comment}</td>
						<td>${entry.value}</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${user.id==trip.promoterId }">
			<table border="1">
				Pendientes
				<tr>
					<th>Id usuario</th>
				</tr>
				<c:forEach var="entry" items="${applications}">
					<tr>
						<td>${entry.userId}</td>
						<form
							action="cancelarSolicitudComoPromotor?userId=${entry.userId}&tripId=${trip.id}"
							method="post">
							<td><input type="submit" id="cancelarSeat_${entry.userId}"
								name="cancelarSeat_${entry.userId}" value="Cancelar Plaza" /></td>
						</form>
						<form
							action="aceptarSolicitud?userId=${entry.userId}&tripId=${trip.id}"
							method="post">
							<td><input type="submit"
								id="aceptarSolicitud_${entry.userId}"
								name="aceptarSolicitud_${entry.userId}" value="Aceptar Plaza" /></td>
						</form>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</c:if>

	<%@ include file="mensajes.jsp"%>
	<%@ include file="foot.jsp"%>
</body>
</html>
