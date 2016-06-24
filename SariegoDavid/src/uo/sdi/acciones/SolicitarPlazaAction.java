package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Application;
import uo.sdi.model.Seat;
import uo.sdi.model.Trip;
import uo.sdi.model.User;
import uo.sdi.persistence.ApplicationDao;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.SeatDao;
import uo.sdi.persistence.TripDao;
import alb.util.log.Log;

public class SolicitarPlazaAction implements Accion {

    @Override
    public String execute(HttpServletRequest request,
	    HttpServletResponse response) {
	String tripId = request.getParameter("tripId");
	User user = (User) request.getSession().getAttribute("user");

	Long tId = null;

	if (tripId != null && !tripId.equals("") && user != null) {
	    tId = Long.parseLong(tripId);
	} else {
	    pasarValores(request, tId, user.getId());
	    Log.error("Datos obligatorios no introducidos o usuario no conectado");
	    return "FRACASO";
	}

	TripDao td = PersistenceFactory.newTripDao();
	
	Trip trip = td.findById(tId);
	
	if (trip == null || !trip.isActive()) {
	    Log.error("Viaje nulo o ya cerrado");
	    pasarValores(request, tId, user.getId());
	    return "FRACASO";
	}
	
	if(user.getId() == trip.getPromoterId()){
	    Log.error("El promotor del viaje no puede solicitar plaza");
	    pasarValores(request, tId, user.getId());
	    return "FRACASO";
	}
	
	
	if(trip.getAvailablePax()<=0){
	    request.setAttribute("mensaje", "No quedan plazas disponibles");
	    Log.error("No quedan plazas disponibles");
	    return "FRACASO";
	}
	
	ApplicationDao ad = PersistenceFactory.newApplicationDao();

	Application application = new Application();

	Long[] ids = { user.getId(), tId };

	if (ad.findById(ids) != null) {
	    Log.error("La solicitud ya ha sido realizada");
	    request.setAttribute("mensaje", "La solicitud ya ha sido realizada");
	    pasarValores(request, tId, user.getId());
	    return "FRACASO";
	}
	application.setTripId(tId);
	application.setUserId(user.getId());

	// En caso de que haya una entrada asiento creada cancelada, la borro
	// para que no entre en conflicto
	// con la nueva solicitud
	SeatDao sd = PersistenceFactory.newSeatDao();

	Seat seat = sd.findById(ids);

	if (seat != null && !seat.getStatus().isAccepted())
	    sd.delete(ids);

	ad.save(application);

	pasarValores(request, tId, user.getId());

	Log.debug("Solicitud realizada con exito");
	
	return "EXITO";
    }

    private void pasarValores(HttpServletRequest request, Long tId, Long userId) {
	Trip trip = PersistenceFactory.newTripDao().findById(tId);
	ApplicationDao ad = PersistenceFactory.newApplicationDao();
	Long[] ids = { userId, tId };
	Application application = ad.findById(ids);

	request.setAttribute("application", application);
	request.setAttribute("trip", trip);
    }

}
