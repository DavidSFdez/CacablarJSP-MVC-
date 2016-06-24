package uo.sdi.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import uo.sdi.acciones.AceptarSolicitudAction;
import uo.sdi.acciones.CancelarPlazaAction;
import uo.sdi.acciones.CancelarSeatAction;
import uo.sdi.acciones.CancelarSolicitudAction;
import uo.sdi.acciones.CancelarSolicitudComoPromotorAction;
import uo.sdi.acciones.CancelarViajeAction;
import uo.sdi.acciones.CerrarSesionAction;
import uo.sdi.acciones.ComentarSeatAction;
import uo.sdi.acciones.ExitoAction;
import uo.sdi.acciones.FiltrarAction;
import uo.sdi.acciones.FiltrarRelacionadosAction;
import uo.sdi.acciones.ListarViajesAction;
import uo.sdi.acciones.ListarViajesRelacionadosAction;
import uo.sdi.acciones.ModificarDatosAction;
import uo.sdi.acciones.MostrarViajeAction;
import uo.sdi.acciones.NavegaModificarViajeAction;
import uo.sdi.acciones.NavegaRegistrarViajeAction;
import uo.sdi.acciones.RegistrarUsuarioAction;
import uo.sdi.acciones.RegistrarViajeAction;
import uo.sdi.acciones.SolicitarPlazaAction;
import uo.sdi.acciones.ValidarseAction;
import alb.util.log.Log;

public class Controlador extends javax.servlet.http.HttpServlet {

    private static final long serialVersionUID = 1L;
    private Map<String, Map<String, Accion>> mapaDeAcciones; // <rol, <opcion,
							     // objeto Accion>>
    private Map<String, Map<String, Map<String, String>>> mapaDeNavegacion; // <rol,
									    // <opcion,
									    // <resultado,
									    // JSP>>>

    public void init() throws ServletException {
	crearMapaAcciones();
	crearMapaDeJSP();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
	    throws IOException, ServletException {

	String opcion, resultado, jspSiguiente;
	Accion accion;
	String rolAntes, rolDespues;

	try {
	    opcion = req.getServletPath().replace("/", "");

	    rolAntes = obtenerRolDeSesion(req);

	    accion = buscarAccionParaOpcion(rolAntes, opcion);

	    resultado = accion.execute(req, res);

	    rolDespues = obtenerRolDeSesion(req);

	    jspSiguiente = buscarJSPSegun(rolDespues, opcion, resultado);

	    req.setAttribute("jspSiguiente", jspSiguiente);

	} catch (Exception e) {

	    req.getSession().invalidate();

	    Log.error("Se ha producido alguna excepción no manejada [%s]", e);

	    jspSiguiente = "/login.jsp";
	}

	RequestDispatcher dispatcher = getServletContext()
		.getRequestDispatcher(jspSiguiente);

	dispatcher.forward(req, res);
    }

    private String obtenerRolDeSesion(HttpServletRequest req) {
	HttpSession sesion = req.getSession();
	if (sesion.getAttribute("user") == null)
	    return "PUBLICO";
	else
	    return "REGISTRADO";
    }

    // Obtiene un objeto accion en funci�n de la opci�n
    // enviada desde el navegador
    private Accion buscarAccionParaOpcion(String rol, String opcion) {

	Accion accion = mapaDeAcciones.get(rol).get(opcion);
	Log.debug("Elegida acción [%s] para opción [%s] y rol [%s]", accion,
		opcion, rol);
	return accion;
    }

    // Obtiene la p�gina JSP a la que habr� que entregar el
    // control el funci�n de la opci�n enviada desde el navegador
    // y el resultado de la ejecuci�n de la acci�n asociada
    private String buscarJSPSegun(String rol, String opcion, String resultado) {

	String jspSiguiente = mapaDeNavegacion.get(rol).get(opcion)
		.get(resultado);
	Log.debug(
		"Elegida página siguiente [%s] para el resultado [%s] tras realizar [%s] con rol [%s]",
		jspSiguiente, resultado, opcion, rol);
	return jspSiguiente;
    }

    private void crearMapaAcciones() {

	mapaDeAcciones = new HashMap<String, Map<String, Accion>>();

	Map<String, Accion> mapaPublico = new HashMap<String, Accion>();
	mapaPublico.put("validarse", new ValidarseAction());
	mapaPublico.put("listarViajes", new ListarViajesAction());
	mapaPublico.put("navegaRegistrarse", new ExitoAction());
	mapaPublico.put("registrarse", new RegistrarUsuarioAction());
	mapaPublico.put("mostrarViaje", new MostrarViajeAction());
	mapaPublico.put("navegaPrincipal", new ExitoAction());
	mapaPublico.put("filtrar", new FiltrarAction());
	mapaDeAcciones.put("PUBLICO", mapaPublico);

	Map<String, Accion> mapaRegistrado = new HashMap<String, Accion>();
	mapaRegistrado.put("listarViajes", new ListarViajesAction());
	mapaRegistrado.put("navegaMostrarViaje", new ExitoAction());
	mapaRegistrado.put("mostrarViaje", new MostrarViajeAction());
	mapaRegistrado.put("navegaModificarUsuario", new ExitoAction());
	mapaRegistrado.put("modificarUsuario", new ModificarDatosAction());
	mapaRegistrado.put("cerrarSesion", new CerrarSesionAction());
	mapaRegistrado.put("solicitarPlaza", new SolicitarPlazaAction());
	mapaRegistrado.put("cancelarPlaza", new CancelarPlazaAction());
	mapaRegistrado.put("cancelarSolicitud", new CancelarSolicitudAction());
	mapaRegistrado.put("cancelarSeat", new CancelarSeatAction());
	mapaRegistrado.put("aceptarSolicitud", new AceptarSolicitudAction());
	mapaRegistrado.put("navegaRegistrarViaje", new NavegaRegistrarViajeAction());
	mapaRegistrado.put("registrarViaje", new RegistrarViajeAction());
	mapaRegistrado.put("listarRelacionados", new ListarViajesRelacionadosAction());
	mapaRegistrado.put("navegaPrincipal", new ExitoAction());
	mapaRegistrado.put("navegaModificarViaje", new NavegaModificarViajeAction());
	mapaRegistrado.put("cancelarSolicitudComoPromotor", new CancelarSolicitudComoPromotorAction());
	mapaRegistrado.put("cancelarViaje", new CancelarViajeAction());
	mapaRegistrado.put("comentarSeat", new ComentarSeatAction());
	mapaRegistrado.put("filtrar",new FiltrarAction());
	mapaRegistrado.put("filtrarRelacionados", new FiltrarRelacionadosAction());
	mapaDeAcciones.put("REGISTRADO", mapaRegistrado);

    }

    private void crearMapaDeJSP() {

	mapaDeNavegacion = new HashMap<String, Map<String, Map<String, String>>>();

	// Crear mapas auxiliares vacíos
	Map<String, Map<String, String>> opcionResJSP = new HashMap<String, Map<String, String>>();
	Map<String, String> resJSP = new HashMap<String, String>();

	// Mapa de navegación del público
	resJSP.put("FRACASO", "/login.jsp");
	opcionResJSP.put("validarse", resJSP);
	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/listaViajes.jsp");
	opcionResJSP.put("listarViajes", resJSP);
	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/registrarUsuario.jsp");
	opcionResJSP.put("navegaRegistrarse", resJSP);
	resJSP.put("FRACASO", "/registrarUsuario.jsp");
	opcionResJSP.put("registrarse", resJSP);
	resJSP = new HashMap<String, String>();
	resJSP = new HashMap<String, String>();	
	resJSP.put("EXITO", "/mostrarViaje.jsp");
	opcionResJSP.put("mostrarViaje", resJSP);
	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/principal.jsp");
	opcionResJSP.put("cerrarSesion", resJSP);
	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/principal.jsp");
	opcionResJSP.put("navegaPrincipal", resJSP);

	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/listaViajes.jsp");
	resJSP.put("FRACASO", "/listaViajes.jsp");
	opcionResJSP.put("filtrar", resJSP);
	
	mapaDeNavegacion.put("PUBLICO", opcionResJSP);

	// Crear mapas auxiliares vacíos
	opcionResJSP = new HashMap<String, Map<String, String>>();
	resJSP = new HashMap<String, String>();

	// Mapa de navegación de usuarios registrados
	resJSP.put("EXITO", "/principal.jsp");
	opcionResJSP.put("validarse", resJSP);
	resJSP.put("EXITO", "/principal.jsp");
	opcionResJSP.put("registrarse", resJSP);
	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/principal.jsp");
	resJSP.put("FRACASO", "/modificarUsuario.jsp");
	opcionResJSP.put("modificarUsuario", resJSP);
	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/listaViajes.jsp");
	opcionResJSP.put("listarViajes", resJSP);
	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/mostrarViaje.jsp");
	opcionResJSP.put("mostrarViaje", resJSP);
	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/modificarUsuario.jsp");
	opcionResJSP.put("navegaModificarUsuario", resJSP);
	resJSP = new HashMap<String, String>();
	
	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/mostrarViaje.jsp");
	resJSP.put("FRACASO", "/mostrarViaje.jsp");
	opcionResJSP.put("solicitarPlaza", resJSP);
	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/mostrarViaje.jsp");
	resJSP.put("FRACASO", "/mostrarViaje.jsp");
	opcionResJSP.put("cancelarPlaza", resJSP);	
	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/mostrarViaje.jsp");
	resJSP.put("FRACASO", "/mostrarViaje.jsp");
	opcionResJSP.put("cancelarSolicitud", resJSP);	
	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/mostrarViaje.jsp");
	resJSP.put("FRACASO", "/mostrarViaje.jsp");
	opcionResJSP.put("cancelarSeat", resJSP);	
	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/mostrarViaje.jsp");
	resJSP.put("FRACASO", "/mostrarViaje.jsp");
	opcionResJSP.put("cancelarSeat", resJSP);
	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/mostrarViaje.jsp");
	resJSP.put("FRACASO", "/mostrarViaje.jsp");
	opcionResJSP.put("aceptarSolicitud", resJSP);
	resJSP.put("EXITO", "/mostrarViaje.jsp");
	resJSP.put("FRACASO", "/mostrarViaje.jsp");
	opcionResJSP.put("cancelarSolicitudComoPromotor", resJSP);

	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/registrarViaje.jsp");
	opcionResJSP.put("navegaRegistrarViaje", resJSP);
	resJSP = new HashMap<String, String>();
	
	resJSP.put("EXITO", "/principal.jsp");
	resJSP.put("FRACASO", "/registrarViaje.jsp");
	opcionResJSP.put("registrarViaje", resJSP);
	resJSP = new HashMap<String, String>();
	
	resJSP.put("EXITO", "/listarRelacionados.jsp");
	resJSP.put("FRACASO", "/principal.jsp");
	opcionResJSP.put("listarRelacionados", resJSP);
	
	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/principal.jsp");
	opcionResJSP.put("navegaPrincipal", resJSP);
	
	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/registrarViaje.jsp");
	resJSP.put("FRACASO", "/principal.jsp");
	opcionResJSP.put("navegaModificarViaje", resJSP);
	
	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/listarRelacionados.jsp");
	resJSP.put("FRACASO", "/listarRelacionados.jsp");
	opcionResJSP.put("cancelarViaje", resJSP);
	
	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/mostrarViaje.jsp");
	resJSP.put("FRACASO", "/mostrarViaje.jsp");
	opcionResJSP.put("comentarSeat", resJSP);
	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/listaViajes.jsp");
	resJSP.put("FRACASO", "/listaViajes.jsp");
	opcionResJSP.put("filtrar", resJSP);
	resJSP = new HashMap<String, String>();
	resJSP.put("EXITO", "/listarRelacionados.jsp");
	resJSP.put("FRACASO", "/principal.jsp");
	opcionResJSP.put("filtrarRelacionados", resJSP);
	
	mapaDeNavegacion.put("REGISTRADO", opcionResJSP);
    }
    
    public void doPost(HttpServletRequest req, HttpServletResponse res)
	    throws IOException, ServletException {

	doGet(req, res);
    }

}