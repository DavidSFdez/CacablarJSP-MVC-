package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.model.Trip;
import uo.sdi.model.User;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.TripDao;

public class FiltrarRelacionadosAction implements Accion {

    @Override
    public String execute(HttpServletRequest request,
	    HttpServletResponse response) {
	User user = (User) request.getSession().getAttribute("user");
	String filtrado = request.getParameter("filtrar");
	
	List<Trip> trips;
	TripDao td = PersistenceFactory.newTripDao();
	
	if(filtrado==null || filtrado.equals("")){
	    if(user!=null)
		 trips = td.findRelatedTrips(user.getId());
	    else{
		Log.error("No hay usuario registrado");
		return "FRACASO";
	    }
	    request.setAttribute("listaViajes", trips);
	    return "EXITO";
	}
	
	filtrado="%"+filtrado+"%";

	if(user!=null)
		 trips = td.findRelatedTripsFiltrado(user.getId(),filtrado);
	    else{
		Log.error("No hay usuario registrado");
		return "FRACASO";
	    }
	
	request.setAttribute("listaViajes", trips);
	return "EXITO";
    }

}
