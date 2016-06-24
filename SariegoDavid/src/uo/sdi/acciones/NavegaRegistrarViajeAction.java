package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Trip;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.TripDao;

public class NavegaRegistrarViajeAction implements Accion {

    @Override
    public String execute(HttpServletRequest request,
	    HttpServletResponse response) {

	TripDao td = PersistenceFactory.newTripDao();

	Long newIdTrip = td.findNewTripId();
	
	Trip trip = new Trip();
	trip.setId(newIdTrip);
	
	request.setAttribute("trip", trip);
	return "EXITO";
    }

}
