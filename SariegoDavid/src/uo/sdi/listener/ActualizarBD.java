package uo.sdi.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import uo.sdi.model.Application;
import uo.sdi.model.Seat;
import uo.sdi.model.SeatStatus;
import uo.sdi.model.Trip;
import uo.sdi.model.TripStatus;
import uo.sdi.persistence.ApplicationDao;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.SeatDao;
import uo.sdi.persistence.TripDao;

public class ActualizarBD implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {}

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
	TripDao td = PersistenceFactory.newTripDao();
	SeatDao sd = PersistenceFactory.newSeatDao();
	ApplicationDao ad = PersistenceFactory.newApplicationDao();

	List<Trip> trips = td.findActiveTripsToUpdateStatus();

	for (Trip t : trips) {
	    List<Application> a = ad.findByTripId(t.getId());
	    for (Application ap : a) {
		Seat st = new Seat();
		st.setStatus(SeatStatus.SIN_PLAZA);
		st.setTripId(ap.getTripId());
		st.setUserId(ap.getUserId());
		sd.save(st);
	    }
	    ad.deleteUpdate(t.getId());
	    t.setStatus(TripStatus.CLOSED);
	    td.update(t);
	}

    }

}
