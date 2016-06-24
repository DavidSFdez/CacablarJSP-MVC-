package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.model.Trip;
import uo.sdi.model.User;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.TripDao;

public class ListarViajesRelacionadosAction implements Accion {

    @Override
    public String execute(HttpServletRequest request,
	    HttpServletResponse response) {
	TripDao td = PersistenceFactory.newTripDao();
	String orderBy = request.getParameter("orderBy");
	User user = (User) request.getSession().getAttribute("user");
	List<Trip> trips = null;
	if (orderBy == null || orderBy.equals(""))
	    if (user != null) {
		trips = td.findRelatedTrips(user.getId());
		request.setAttribute("listaViajes", trips);
	    } else {
		Log.error("No hay usuario conectado");
		return "FRACASO";
	    }
	else if (user != null) {
	    switch(orderBy){
	    case "Departure_City":
		trips=td.findRelatedTripsOrderByDepartureCity(user.getId());
		break;
	    case "Destination_City":
		trips=td.findRelatedTripsOrderByDestinationCity(user.getId());
		break;
	    }
	    request.setAttribute("listaViajes", trips);
	} else {
	    Log.error("No hay usuario conectado");
	    return "FRACASO";
	}

	Log.debug("Viajes relacionados listados con exito");

	return "EXITO";
    }
}
