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
import uo.sdi.persistence.RatingDao;
import uo.sdi.persistence.SeatDao;
import uo.sdi.persistence.TripDao;
import alb.util.log.Log;

public class ComentarSeatAction implements Accion {

    @Override
    public String execute(HttpServletRequest request,
	    HttpServletResponse response) {

	String tripId = request.getParameter("tripId"); 
	String userId = request.getParameter("userId"); //about user
	String comentarioSeat = request.getParameter("comentarioSeat");
	String valoracion = request.getParameter("valoracionSeat");
	User user = (User) request.getSession().getAttribute("user"); //from user

	RatingDao rd = PersistenceFactory.newRatingDao();
	TripDao td = PersistenceFactory.newTripDao();
	ApplicationDao ad = PersistenceFactory.newApplicationDao();

	if (tripId == null || tripId.equals("") || userId == null
		|| userId.equals("") || comentarioSeat == null
		|| comentarioSeat.equals("") || valoracion == null
		|| valoracion.equals("")) {
	    Log.error("Faltan datos obligatorios");
	    request.setAttribute("mensaje", "Faltan datos obligatorios");
	    return "FRACASO";
	}

	Long tId = Long.parseLong(tripId);
	Long uId = Long.parseLong(userId);
	int valor = 0;
	try {
	    valor = Integer.parseInt(valoracion);
	} catch (NumberFormatException e) {
	    request.setAttribute("mensaje", "Valoración introducida no válida");
	    Log.error("Valoración introducida no válida: " + e.getMessage());
	    pasarValores(request, user, rd, td, ad, tId);
	    return "FRACASO";
	}

	if (user.getId().equals(uId)) {
	    request.setAttribute("mensaje",
		    "Un usuario no puede comentarse a si mismo");
	    Log.error("Un usuario no puede comentarse a si mismo");
	    pasarValores(request, user, rd, td, ad, tId);
	    return "FRACASO";
	}

	Rating rating = rd.findByAboutFrom(uId, tId, user.getId(), tId);

	if (rating != null) {
	    Log.error("Ya se ha realizado una valoración de este "
		    + "usuario en este viaje");
	    request.setAttribute("mensaje", "Ya se ha realizado "
		    + "una valoración de este usuario en este viaje");
	    pasarValores(request, user, rd, td, ad, tId);
	    return "FRACASO";
	}

	rating = new Rating();
	rating.setComment(comentarioSeat);
	rating.setValue(valor);
	rating.setSeatAboutTripId(tId);
	rating.setSeatAboutUserId(uId);
	rating.setSeatFromTripId(tId);
	rating.setSeatFromUserId(user.getId());

	rd.save(rating);

	pasarValores(request, user, rd, td, ad, tId);

	Log.debug("Asiento comentado con exito");
	request.setAttribute("mensaje", "Asiento comentado con exito");

	return "EXITO";
    }

    private void pasarValores(HttpServletRequest request, User user,
	    RatingDao rd, TripDao td, ApplicationDao ad, Long tId) {
	SeatDao sd = PersistenceFactory.newSeatDao();
	Trip trip = td.findById(tId);
	request.setAttribute("trip", trip);
	List<Seat> seats = sd.findByTrip(tId);
	request.setAttribute("seats", seats);
	List<Rating> ratings = rd.FindByTrip(tId);
	request.setAttribute("ratings", ratings);
	if (trip.getPromoterId() != user.getId()) {
	    Long[] ids = { user.getId(), tId };
	    Seat seat = sd.findById(ids);
	    request.setAttribute("seat", seat);
	} else {
	    List<Application> applications = ad.findByTripId(tId);
	    request.setAttribute("applications", applications);
	}
    }

}
