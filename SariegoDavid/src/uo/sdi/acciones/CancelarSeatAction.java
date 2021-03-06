package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.model.Application;
import uo.sdi.model.Seat;
import uo.sdi.model.SeatStatus;
import uo.sdi.model.Trip;
import uo.sdi.model.TripStatus;
import uo.sdi.model.User;
import uo.sdi.persistence.ApplicationDao;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.SeatDao;
import uo.sdi.persistence.TripDao;

public class CancelarSeatAction implements Accion {

    @Override
    public String execute(HttpServletRequest request,
	    HttpServletResponse response) {
	String userId = request.getParameter("userId");
	String tripId = request.getParameter("tripId");
	User user = (User) request.getSession().getAttribute("user");

	Long tId = null;
	Long uId = null;

	TripDao td = PersistenceFactory.newTripDao();
	Trip trip = null;

	if (userId != null && tripId != null) {
	    tId = Long.parseLong(tripId);
	    uId = Long.parseLong(userId);
	    trip = td.findById(tId);

	} else if (user == null) {
	    pasarValores(request, trip);
	    Log.error("No hay usuario conectado");
	    return "FRACASO";
	} else {
	    Log.error("Datos obligatorios no introducidos");
	    return "FRACASO";
	}

	if (trip == null || !trip.isActive()) {
	    Log.error("Viaje nulo o ya cerrado");
	    pasarValores(request, trip);
	    return "FRACASO";
	}

	if (!trip.getPromoterId().equals(user.getId())) {
	    pasarValores(request, trip);
	    Log.error("Usuario conectado no es el promotor del viaje");
	    return "FRACASO";
	}

	Long[] ids = { uId, tId };

	SeatDao sd = PersistenceFactory.newSeatDao();

	Seat seat = sd.findById(ids);

	seat.setStatus(SeatStatus.EXCLUDED);

	sd.update(seat);

	if (trip.getAvailablePax() == 0 && trip.isActive())
	    trip.setStatus(TripStatus.OPEN);
	trip.setAvailablePax(trip.getAvailablePax() + 1);
	td.update(trip);

	pasarValores(request, trip);

	Log.debug("Plaza cancelada con exito");

	return "EXITO";
    }

    private void pasarValores(HttpServletRequest request, Trip trip) {
	SeatDao sd = PersistenceFactory.newSeatDao();
	ApplicationDao ad = PersistenceFactory.newApplicationDao();

	request.setAttribute("trip", trip);
	List<Seat> seats = sd.findByTrip(trip.getId());
	request.setAttribute("seats", seats);
	List<Application> applications = ad.findByTripId(trip.getId());
	request.setAttribute("applications", applications);
    }

}
