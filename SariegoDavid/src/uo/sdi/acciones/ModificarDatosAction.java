package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.model.User;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.UserDao;
import alb.util.log.Log;

public class ModificarDatosAction implements Accion {

    @Override
    public String execute(HttpServletRequest request,
	    HttpServletResponse response) {
	String login = request.getParameter("login");
	String name = request.getParameter("nombreUsuario");
	String surname = request.getParameter("apellidoUsuario");
	String email = request.getParameter("emailUsuario");
	String password = request.getParameter("password");
	HttpSession session = request.getSession();
	User usuario = ((User) session.getAttribute("user"));

	if (login == null || login.equals("") || name == null
		|| name.equals("") || surname == null || surname.equals("")
		|| email == null || email.equals("") || password == null
		|| password.equals("")){
	    Log.error("Datos obligatorios no introducidos");
	    return "FRACASO";
	}

	if (usuario.getPassword().equals(password)) {
	    usuario.setLogin(login);
	    usuario.setName(name);
	    usuario.setSurname(surname);
	    usuario.setEmail(email);

	    String passwordNueva = request.getParameter("passwordNueva");
	    String passwordNueva2 = request.getParameter("passwordNueva2");
	    if (!passwordNueva.equals(""))
		if (passwordNueva.equals(passwordNueva2)) {
		    usuario.setPassword(passwordNueva);
		    Log.debug("Contraseña cambiada");
		} else {
		    request.setAttribute("mensaje",
			    "La nueva contraseña introducida no coincide.");
		    Log.debug("La nueva contraseña introducida no coincide");
		    return "FRACASO";
		}
	} else {
	    request.setAttribute("mensaje", "Contraseña introducida incorrecta");
	    Log.debug("Contraseña introducida incorrecta");
	    return "FRACASO";
	}

	try {
	    UserDao dao = PersistenceFactory.newUserDao();
	    dao.update(usuario);
	    Log.debug("Datos modificados");
	} catch (Exception e) {
	    request.setAttribute("mensaje",
		    "Algo ha ocurrido actualizando los datos.");
	    Log.error("Algo ha ocurrido actualizando los datos de [%s]",
		    usuario.getLogin());
	    return "FRACASO";
	}
	return "EXITO";
    }

    @Override
    public String toString() {
	return getClass().getName();
    }

}
