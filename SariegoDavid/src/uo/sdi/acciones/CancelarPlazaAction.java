package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.model.Seat;
import uo.sdi.model.SeatStatus;
import uo.sdi.model.Trip;
import uo.sdi.model.TripStatus;
import uo.sdi.model.User;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.SeatDao;
import uo.sdi.persistence.TripDao;

public class CancelarPlazaAction implements Accion {

    @Override
    public String execute(HttpServletRequest request,
	    HttpServletResponse response) {

	String tripId = request.getParameter("tripId");
	User user = (User) request.getSession().getAttribute("user");

	Long tId = null;
	if (tripId != null && !tripId.equals("")) {
	    tId = Long.parseLong(tripId);
	}

	if (tripId == null || tripId.trim().equals("") || user == null) {
	    pasarValores(request, tId, user);
	    Log.error("Datos obligatorios no introducidos");
	    return "FRACASO";
	}

	TripDao td = PersistenceFactory.newTripDao();
	Trip trip = td.findById(tId);

	if (trip == null || !trip.isActive()) {
	    Log.error("Viaje nulo o ya cerrado");
	    pasarValores(request, tId, user);
	    return "FRACASO";
	}

	SeatDao sd = PersistenceFactory.newSeatDao();

	Long[] ids = { user.getId(), tId };

	Seat seat = sd.findById(ids);

	seat.setStatus(SeatStatus.EXCLUDED);

	sd.update(seat);

	if (trip.getAvailablePax() == 0 && trip.isActive())
	    trip.setStatus(TripStatus.OPEN);
	trip.setAvailablePax(trip.getAvailablePax() + 1);
	td.update(trip);

	pasarValores(request, tId, user);

	Log.debug("Plaza cancelada con exito");

	return "EXITO";
    }

    private void pasarValores(HttpServletRequest request, Long tId, User user) {
	SeatDao sd = PersistenceFactory.newSeatDao();
	Trip trip = PersistenceFactory.newTripDao().findById(tId);
	List<Seat> seats = sd.findByTrip(tId);
	request.setAttribute("seats", seats);
	request.setAttribute("trip", trip);
    }

}
