package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Trip;
import uo.sdi.model.User;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.TripDao;
import alb.util.log.Log;

public class ListarViajesAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		List<Trip> viajes = null;
		User user = (User) request.getSession().getAttribute("user");
		String orderBy = request.getParameter("orderBy");
		TripDao td = PersistenceFactory.newTripDao();
		try {
		    if(orderBy==null || orderBy.equals(""))
		    	if(user==null)
		    	    viajes=td.findAllActive();
			else
			    viajes = td.findAllActiveToUser(user.getId());
		    else
			if(user==null)
			    switch(orderBy){
			    case "Departure_City":
				 viajes=td.findAllActiveOrderByDepartureCity();
				break;
			    case "Destination_City":
				 viajes=td.findAllActiveOrderByDestinationCity();
				break;
			    }
			else
			    switch(orderBy){
			    case "Departure_City":
				viajes = td.findAllActiveToUserOrderByDepartureCity(user.getId());
				break;
			    case "Destination_City":
				viajes = td.findAllActiveToUserOrderByDestinationCity(user.getId());
				break;
			    }
			    
			request.setAttribute("listaViajes", viajes);
			Log.debug("Obtenida lista de viajes conteniendo [%d] viajes", viajes.size());
		}
		catch (Exception e) {
			Log.error("Algo ha ocurrido obteniendo lista de viajes: "+e);
		}
		return "EXITO";
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
