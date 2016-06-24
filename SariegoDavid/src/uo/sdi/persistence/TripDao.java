package uo.sdi.persistence;

import java.util.Date;
import java.util.List;

import uo.sdi.model.Trip;
import uo.sdi.persistence.util.GenericDao;

public interface TripDao extends GenericDao<Trip, Long> {

	Trip findByPromoterIdAndArrivalDate(Long id, Date arrivalDate);

	List<Trip> findPromotedTrips(Long id);

	Long findNewTripId();

	List<Trip> findAllActive();

	List<Trip> findAllActiveToUser(Long id);

	List<Trip> findRelatedTrips(Long id);

	List<Trip> findAllActiveOrderByDepartureCity();

	List<Trip> findAllActiveOrderByDestinationCity();

	List<Trip> findAllActiveToUserOrderByDepartureCity(Long id);

	List<Trip> findAllActiveToUserOrderByDestinationCity(Long id);

	List<Trip> findAllActiveFiltrado(String filtrado);

	List<Trip> findAllActiveToUserFiltrado(Long id, String filtrado);

	List<Trip> findRelatedTripsOrderByDepartureCity(Long userId);

	List<Trip> findRelatedTripsOrderByDestinationCity(Long userId);

	List<Trip> findRelatedTripsFiltrado(Long id, String filtrado);

	List<Trip> findActiveTripsToUpdateStatus();


}
