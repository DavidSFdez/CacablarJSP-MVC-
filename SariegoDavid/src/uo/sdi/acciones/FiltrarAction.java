package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Trip;
import uo.sdi.model.User;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.TripDao;

public class FiltrarAction implements Accion {

    @Override
    public String execute(HttpServletRequest request,
	    HttpServletResponse response) {
	User user = (User) request.getSession().getAttribute("user");
	String filtrado = request.getParameter("filtrar");
	
	List<Trip> trips;
	TripDao td = PersistenceFactory.newTripDao();
	
	if(filtrado==null || filtrado.equals("")){
	    if(user==null)
		    trips = td.findAllActive();
		else
		    trips = td.findAllActiveToUser(user.getId());
	    request.setAttribute("listaViajes", trips);
	    return "EXITO";
	}
	
	filtrado="%"+filtrado+"%";
	
	
	
	if(user==null)
	    trips = td.findAllActiveFiltrado(filtrado);
	else
	    trips = td.findAllActiveToUserFiltrado(user.getId(),filtrado);
	
	request.setAttribute("listaViajes", trips);
	return "EXITO";
    }

}
