package uo.sdi.acciones;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.AddressPoint;
import uo.sdi.model.Seat;
import uo.sdi.model.SeatStatus;
import uo.sdi.model.Trip;
import uo.sdi.model.TripStatus;
import uo.sdi.model.User;
import uo.sdi.model.Waypoint;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.TripDao;

public class RegistrarViajeAction implements Accion {

    @Override
    public String execute(HttpServletRequest request,
	    HttpServletResponse response) {

	String resultado = "EXITO";
	User user = (User) request.getSession().getAttribute("user");

	try {

	    String tripId = request.getParameter("idTrip");
	    String calleSalida = request.getParameter("calleSalida");
	    String ciudadSalida = request.getParameter("ciudadSalida");
	    String provinciaSalida = request.getParameter("provinciaSalida");
	    String paisSalida = request.getParameter("paisSalida");
	    String cpSalida = request.getParameter("cpSalida");
	    String coordenadasLatSalida = request
		    .getParameter("coordenadasLatSalida");
	    String coordenadasLonSalida = request
		    .getParameter("coordenadasLonSalida");
	    String fSalida = request.getParameter("fechaSalida");
	    
	    String calleDestino = request.getParameter("calleDestino");
	    String ciudadDestino = request.getParameter("ciudadDestino");
	    String provinciaDestino = request.getParameter("provinciaDestino");
	    String paisDestino = request.getParameter("paisDestino");
	    String cpDestino = request.getParameter("cpDestino");
	    String coordenadasLatDestino = request
		    .getParameter("coordenadasLatDestino");
	    String coordenadasLonDestino = request
		    .getParameter("coordenadasLonDestino");
	    String fDestino = request.getParameter("fechaDestino");

	    String fLimite = request.getParameter("fechaLimite");

	    String c = request.getParameter("coste");

	    String descripcion = request.getParameter("descripcion");
	    String plazas = request.getParameter("nPlazasMax");

	    if (calleSalida == null || ciudadSalida == null
		    || provinciaSalida == null || paisSalida == null
		    || cpSalida == null || fSalida == null
		    || calleDestino == null || ciudadDestino == null
		    || provinciaDestino == null || paisDestino == null
		    || cpDestino == null || fDestino == null || fLimite == null
		    || c == null || descripcion == null || plazas == null)
		return "FRACASO";

	    if (calleSalida.equals("") || ciudadSalida.equals("")
		    || provinciaSalida.equals("") || paisSalida.equals("")
		    || cpSalida.equals("") || fSalida.equals("")
		    || calleDestino.equals("") || ciudadDestino.equals("")
		    || provinciaDestino.equals("") || paisDestino.equals("")
		    || cpDestino.equals("") || fDestino.equals("")
		    || fLimite.equals("") || c.equals("")
		    || descripcion.equals("") || plazas.equals(""))
		return "FRACASO";

	    int nPlazasMax = Integer.parseInt(plazas);
	    if (nPlazasMax <= 0) {
		request.setAttribute("mensaje",
			"Ha de haber al menos una plaza disponible");
		return "FRACASO";
	    }

	    int nPlazas = nPlazasMax;

	    double coste = Double.parseDouble(c);

	    SimpleDateFormat lFormatter = null;
	    Date fechaSalida = null;
	    try {
		lFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",
			Locale.ENGLISH);
		fechaSalida = lFormatter.parse(fSalida);
	    } catch (ParseException e1) {
		lFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		fechaSalida = (Date) lFormatter.parse(fSalida);
	    }

	    Date fechaDestino = null;
	    try {
		lFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",
			Locale.ENGLISH);
		fechaDestino = lFormatter.parse(fDestino);
	    } catch (ParseException e1) {
		lFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		fechaDestino = (Date) lFormatter.parse(fDestino);
	    }

	    Date fechaLimite = null;

	    try {
		lFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",
			Locale.ENGLISH);
		fechaLimite = lFormatter.parse(fLimite);
	    } catch (ParseException e1) {
		lFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		fechaLimite = (Date) lFormatter.parse(fLimite);
	    }

	    Waypoint coordSalida = new Waypoint(0D, 0D);
	    Waypoint coordDestino = new Waypoint(0D, 0D);
	    if (!coordenadasLatSalida.trim().equals("")
		    && !coordenadasLonSalida.trim().equals("")) {
		double coordLatSalida = Double.valueOf(coordenadasLatSalida);
		double coordLonSalida = Double.valueOf(coordenadasLonSalida);
		coordSalida = new Waypoint(coordLatSalida, coordLonSalida);
	    }
	    if (!coordenadasLatDestino.trim().equals("")
		    && !coordenadasLonDestino.trim().equals("")) {
		double coordLatDestino = Double.valueOf(coordenadasLatDestino);
		double coordLonDestino = Double.valueOf(coordenadasLonDestino);
		coordDestino = new Waypoint(coordLatDestino, coordLonDestino);
	    }

	    if (fechaDestino.before(fechaSalida)) {
		request.setAttribute("mensaje",
			"La fecha de llegada no puede ser anterior a la de salida");
		resultado = "FRACASO";
	    } else if (fechaLimite.after(fechaSalida)) {
		request.setAttribute("mensaje",
			"La fecha límite para solicitar plaza no puede ser después de la de salida");
		return "FRACASO";
	    } else if (nPlazas > nPlazasMax) {
		request.setAttribute("mensaje",
			"El número de plazas totales no puede ser menor que el de plazas disponibles");
		return "FRACASO";
	    } else {

		Trip trip = new Trip();
		trip.setId(Long.parseLong(tripId));
		trip.setArrivalDate(fechaDestino);
		trip.setAvailablePax(nPlazas);
		trip.setClosingDate(fechaLimite);
		trip.setComments(descripcion);

		AddressPoint departure = new AddressPoint(calleSalida,
			ciudadSalida, provinciaSalida, paisSalida, cpSalida,
			coordSalida);
		trip.setDeparture(departure);

		trip.setDepartureDate(fechaSalida);

		AddressPoint destination = new AddressPoint(calleDestino,
			ciudadDestino, provinciaDestino, paisDestino,
			cpDestino, coordDestino);
		trip.setDestination(destination);

		trip.setEstimatedCost(coste);
		trip.setMaxPax(nPlazasMax);
		trip.setStatus(TripStatus.OPEN);
		trip.setPromoterId(user.getId());

		TripDao td = PersistenceFactory.newTripDao();
		if (td.findById(trip.getId()) != null) {
		    td.update(trip);
		    request.setAttribute("mensaje",
			    "Viaje modificado con exito");
		} else {
		    td.save(trip);
		    Seat seat = new Seat();
		    request.setAttribute("mensaje",
			    "Viaje registrado con exito");
		}

	    }

	} catch (ParseException e) {

	    e.printStackTrace();
	}

	return resultado;
    }

}
