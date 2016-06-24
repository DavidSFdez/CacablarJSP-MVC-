package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.model.Trip;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.TripDao;

public class ModificarViajeAction implements Accion {

    @Override
    public String execute(HttpServletRequest request,
	    HttpServletResponse response) {
	String tripId = request.getParameter("tripId");

	if (tripId == null || tripId.equals("")){
	    Log.error("Datos obligatorios no introducidos");
	    return "FRACASO";
	}
	
	TripDao td = PersistenceFactory.newTripDao();
	Trip trip = td.findById(Long.parseLong(tripId));
	request.setAttribute("trip", trip);
	Log.debug("Datos del viaje cargados con exito");
	
	return "EXITO";
    }

}
