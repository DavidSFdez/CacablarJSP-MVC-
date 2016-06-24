package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExitoAction implements Accion{

    @Override
    public String execute(HttpServletRequest request,
	    HttpServletResponse response) {
	
	return "EXITO";
    }

}
