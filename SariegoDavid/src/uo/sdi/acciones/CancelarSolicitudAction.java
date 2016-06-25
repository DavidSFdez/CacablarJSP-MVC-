package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Seat;
import uo.sdi.model.Trip;
import uo.sdi.model.User;
import uo.sdi.persistence.ApplicationDao;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.SeatDao;
import alb.util.log.Log;

public class CancelarSolicitudAction implements Accion {

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
	    Log.error("Datos obligatorios no introducidos o usuario no conectado");
	    return "FRACASO";
	}

	Trip trip = PersistenceFactory.newTripDao().findById(tId);
	if (trip == null || !trip.isActive()) {
	    Log.error("Viaje nulo o ya cerrado");
	    pasarValores(request, tId, user);
	    return "FRACASO";
	}

	ApplicationDao ad = PersistenceFactory.newApplicationDao();

	Long[] ids = { user.getId(), tId };

	ad.delete(ids);

	pasarValores(request, tId, user);

	Log.debug("Solicitud cancelada con exito");

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
