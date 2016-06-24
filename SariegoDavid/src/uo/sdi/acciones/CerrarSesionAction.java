package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;

public class CerrarSesionAction implements Accion {

    @Override
    public String execute(HttpServletRequest request,
	    HttpServletResponse response) {
	HttpSession sesion = request.getSession();
	if (!sesion.isNew()) {
	    sesion.invalidate();
	    Log.debug("Sesion cerrada");
	} else {
	    Log.error("No hay sesion abierta");
	}
	return "EXITO";
    }

}
