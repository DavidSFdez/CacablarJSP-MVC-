package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.model.Application;
import uo.sdi.model.Seat;
import uo.sdi.model.SeatStatus;
import uo.sdi.model.Trip;
import uo.sdi.model.TripStatus;
import uo.sdi.model.User;
import uo.sdi.persistence.ApplicationDao;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.SeatDao;
import uo.sdi.persistence.TripDao;

public class CancelarViajeAction implements Accion {

    @Override
    public String execute(HttpServletRequest request,
	    HttpServletResponse response) {
	String tripId = request.getParameter("tripId");
	
	if(tripId==null || tripId.equals(""))
	    return "FRACASO";
		
	User user = (User) request.getSession().getAttribute("user");
	TripDao td = PersistenceFactory.newTripDao();
	SeatDao sd = PersistenceFactory.newSeatDao();
	ApplicationDao ad = PersistenceFactory.newApplicationDao();
	
	Trip trip = td.findById(Long.parseLong(tripId));
	
	if (trip==null || !trip.isActive()){
	    Log.error("Viaje nulo o ya cerrado");
	    return "FRACASO";
	}

	
	if(!user.getId().equals(trip.getPromoterId())){
	    Log.error("Usuario conectado no es el promotor del viaje");
	    return "FRACASO";
	}
	
	trip.setStatus(TripStatus.CANCELLED);
	
	List<Seat> seats = sd.findByTrip(trip.getId());
	
	for(Seat s : seats){
	    s.setStatus(SeatStatus.EXCLUDED);
	    sd.update(s);
	}
	
	List<Application> applications = ad.findByTripId(trip.getId());
	
	Long[] ids = new Long[2];
	for(Application a : applications){
	   ids[0]= a.getUserId();
	   ids[1] = a.getTripId();
	    ad.delete(ids);
	}
	
	td.update(trip);
	
	List<Trip> trips = td.findPromotedTrips(user.getId());
	
	request.setAttribute("trips", trips);
	
	Log.debug("Viaje cancelado con exito");
	
	return "EXITO";
    }

}
