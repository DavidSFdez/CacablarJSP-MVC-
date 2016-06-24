package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.model.Trip;
import uo.sdi.persistence.PersistenceFactory;

public class NavegaModificarViajeAction implements Accion {

    @Override
    public String execute(HttpServletRequest request,
	    HttpServletResponse response) {
	String tripId = request.getParameter("tripId");
	
	if(tripId==null || tripId.equals("")){
	    Log.error("Datos obligatorios no introducidos");
	    return "FRACASO";
	}
	
	Trip trip = PersistenceFactory.newTripDao().findById(Long.parseLong(tripId));
	
	request.setAttribute("trip", trip);
	
	return "EXITO";
    }

}
