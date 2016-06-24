package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Application;
import uo.sdi.model.Rating;
import uo.sdi.model.Seat;
import uo.sdi.model.Trip;
import uo.sdi.model.User;
import uo.sdi.persistence.ApplicationDao;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.SeatDao;
import uo.sdi.persistence.TripDao;
import alb.util.log.Log;

public class MostrarViajeAction implements Accion {

    @Override
    public String execute(HttpServletRequest request,
	    HttpServletResponse response) {
	String id = request.getParameter("id");
	if (id != null && !id.equals("")) {
	    Long tripId = Long.parseLong(id);
	    TripDao td = PersistenceFactory.newTripDao();
	    SeatDao sd = PersistenceFactory.newSeatDao();
	    ApplicationDao ad = PersistenceFactory.newApplicationDao();

	    Trip trip = td.findById(tripId);
	    request.setAttribute("trip", trip);
	    User user = (User) request.getSession().getAttribute("user");
	    if (user != null && !user.getId().equals(trip.getPromoterId())) {
		Seat seat = sd.findByUserAndTrip(user.getId(), tripId);
		if (seat == null) {

		    Long[] ids = { user.getId(), tripId };
		    Application application = ad.findById(ids);
		    if (application != null)
			request.setAttribute("application", application);
		} else
		    request.setAttribute("seat", seat);
	    } else {

		List<Application> applications = ad.findByTripId(tripId);
		request.setAttribute("applications", applications);
	    }
	    List<Seat> seats = sd.findByTrip(tripId);
	    request.setAttribute("seats", seats);
	    List<Rating> ratings = PersistenceFactory.newRatingDao()
		    .FindByTrip(tripId);
	    request.setAttribute("ratings", ratings);
	    Log.debug("Viaje cargado con exito");

	    return "EXITO";
	}
	Log.error("Datos obligatorios no introducidos");
	return "FRACASO";
    }

}
