package uo.sdi.tests;

import org.junit.*;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

public class Sesion4Tests {

    @Before
    public void prepare() {
        setBaseUrl("http://localhost:8280/SariegoDavid");
        
    }

    private void loguearse(String userpass){
	// Rellenando el formulario HTML
        beginAt("/");  // Navegar a la URL
        setTextField("nombreUsuario", userpass); // Rellenar primer campo de formulario
        setTextField("password", userpass);
        submit(); // Enviar formulario
    }
    
    private void cerrarSesion(){
	 assertLinkPresent("cerrarSesion");  // Comprobar que existe el hipervínculo
	 clickLink("cerrarSesion");
    }
    
    private void cancelarViaje() {
   	loguearse("user1");
   	
   	 assertLinkPresent("listarRelacionados");  // Comprobar que existe el hipervínculo
   	 clickLink("listarRelacionados");
   	 
   	 assertButtonPresent("cancelarViaje_ciudadTest");
   	 submit("cancelarViaje_ciudadTest");
       }
    
    private void solicitarPlaza() {
   	loguearse("user1");
   	
   	 assertLinkPresent("listarViajes");  // Comprobar que existe el hipervínculo
   	 clickLink("listarViajes");
   	 
   	 assertLinkPresent("42");  // Comprobar que existe el hipervínculo
   	 clickLink("42");
   	 
   	 assertButtonPresent("solicitarPlaza");
   	 submit("solicitarPlaza");
       }
    
    private void cancelarSolicitud() {
	loguearse("user1");
	
	 assertLinkPresent("listarRelacionados");  // Comprobar que existe el hipervínculo
	 clickLink("listarRelacionados");
	 
	 assertLinkPresent("42");  // Comprobar que existe el hipervínculo
	 clickLink("42");
	 
	 assertButtonPresent("cancelarSolicitud");
	 submit("cancelarSolicitud");
    }
    
    private void comoPromotorCancelarSolicitud() {
	loguearse("user3");
	
	 assertLinkPresent("listarRelacionados");  // Comprobar que existe el hipervínculo
	 clickLink("listarRelacionados");
	 
	 assertLinkPresent("42");  // Comprobar que existe el hipervínculo
	 clickLink("42");
	 
	 assertButtonPresent("cancelarSeat_306");
	 submit("cancelarSeat_306");
    }
    
    private void comoPromotorAceptarSolicitud() {
	loguearse("user3");
	
	 assertLinkPresent("listarRelacionados");  // Comprobar que existe el hipervínculo
	 clickLink("listarRelacionados");
	 
	 assertLinkPresent("42");  // Comprobar que existe el hipervínculo
	 clickLink("42");
	 
	 assertButtonPresent("aceptarSolicitud_306");
	 submit("aceptarSolicitud_306");
    }
    
    private void comoPromotorCancelarSeat() {
  	loguearse("user3");
  	
  	 assertLinkPresent("listarRelacionados");  // Comprobar que existe el hipervínculo
  	 clickLink("listarRelacionados");
  	 
  	 assertLinkPresent("42");  // Comprobar que existe el hipervínculo
  	 clickLink("42");
  	 
  	 assertButtonPresent("cancelarSeat_306");
  	 submit("cancelarSeat_306");	
      }
    
    private void comoUsuarioCancelarSeat() {
   	loguearse("user1");
   	
   	assertLinkPresent("listarRelacionados");  // Comprobar que existe el hipervínculo
   	clickLink("listarRelacionados");
   	 
   	assertLinkPresent("42");  // Comprobar que existe el hipervínculo
   	clickLink("42");
   	 
   	assertButtonPresent("cancelarPlaza_306");
   	submit("cancelarPlaza_306");
       }
       
    
    @Test
    public void testListarViajes() {
        beginAt("/");  // Navegar a la URL
        assertLinkPresent("listarViajes");  // Comprobar que existe el hipervínculo
        clickLink("listarViajes"); // Seguir el hipervínculo

        assertTitleEquals("ShareMyTrip - Listado de viajes");  // Comprobar título de la página

        // La base de datos contiene 2 viajes tal y como se entrega
        assertElementPresent("item_0"); // Comprobar elemento presente en la página
        assertElementPresent("item_1"); // Comprobar elemento presente en la página
    }

    @Test
    public void testIniciarSesionConExito() {
    	
	loguearse("user1");
        assertTitleEquals("ShareMyTrip - Menu");  // Comprobar título de la página
       
    }

    @Test
    public void testIniciarSesionConExitoConQueryString() {
    	// Rellenando el formulario HTML
        beginAt("/validarse?nombreUsuario=user2&password=user2");  // Navegar a la URL
        assertTitleEquals("ShareMyTrip - Menu");  // Comprobar título de la página
    }
    
    @Test
    public void testIniciarSesionSinExito() {
    	// Rellenando el formulario HTML
        beginAt("/");  // Navegar a la URL
        setTextField("nombreUsuario", "yoNoExisto"); // Rellenar primer campo de formulario
        setTextField("password", "yoNoExisto"); // Rellenar primer campo de formulario
        submit(); // Enviar formulario
        assertTitleEquals("ShareMyTrip - Inicie sesión");  // Comprobar título de la página
    }
    @Test
    public void testRegistrarseSinExito(){
	beginAt("/navegaRegistrarse");
        setTextField("login", "nombre1"); // Rellenar primer campo de formulario
        setTextField("nombreUsuario", "nombre1");
        setTextField("apellidoUsuario", "apellido1"); // Rellenar primer campo de formulario
        setTextField("emailUsuario", "email1");
        setTextField("password", "password1"); // Rellenar primer campo de formulario
        setTextField("password2", "password2");
        submit();
        assertTextInElement("mensaje","Las contraseñas introducidas no coinciden");
        
    }
    
    @Test
    public void testIniciarSesionContraseñaIncorrecta() {
    	// Rellenando el formulario HTML
        beginAt("/");  // Navegar a la URL
        setTextField("nombreUsuario", "user1"); // Rellenar primer campo de formulario
        setTextField("password", "user"); // Rellenar primer campo de formulario
        submit(); // Enviar formulario
        assertTextInElement("mensaje","Login o contraseña incorrectos");
    }
    
    @Test
    public void testRegistrarViajeConExito() {
    	// Rellenando el formulario HTML
	 loguearse("user1");
	
	 assertLinkPresent("navegaRegistrarViaje");  // Comprobar que existe el hipervínculo
	 clickLink("navegaRegistrarViaje"); // Seguir el hipervínculo

	 setTextField("calleSalida", "a");
	 setTextField("ciudadSalida", "ciudadTest");
	 setTextField("provinciaSalida", "a");
	 setTextField("paisSalida", "a");
	 setTextField("cpSalida", "12345");

	 setTextField("fechaSalida", "2017-04-04 10:00:00");
	 
	 setTextField("calleDestino", "a");
	 setTextField("ciudadDestino", "a");
	 setTextField("provinciaDestino", "a");
	 setTextField("paisDestino", "a");
	 setTextField("cpDestino", "12345");
	 
	 setTextField("fechaDestino", "2017-04-05 10:00:00");
	 setTextField("fechaLimite", "2017-04-04 09:00:00");
	 
	 setTextField("coste", "30");
	 setTextField("descripcion", "fda");
	 setTextField("nPlazasMax", "3");
	 
        submit("Registrar"); // Enviar formulario
                
        assertTextInElement("mensaje", "Viaje registrado con exito"); 
    }
    
    @Test
    public void testCancelarViaje(){
	cancelarViaje();
	 
	 assertButtonNotPresent("cancelarViaje_ciudadTest");
	 
	 cerrarSesion();
    }
    
    @Test
    public void testSolicitarPlaza(){
	solicitarPlaza();
	 
	 assertButtonPresent("cancelarSolicitud");
	 
	 cerrarSesion();
	 
	 cancelarSolicitud();
    }

   
    
    @Test
    public void testCancelarSolicitud(){
	solicitarPlaza();
	
	cerrarSesion();
	
	cancelarSolicitud();
	 
	 assertButtonPresent("solicitarPlaza");
	 
    }

    
    
    @Test
    public void testComoPromotorCancelarSolicitud(){
	solicitarPlaza();
	cerrarSesion();
	
	comoPromotorCancelarSolicitud();
	 
	assertButtonNotPresent("cancelarSeat_306");
    }

    
    
    @Test
    public void testComoPromotorAceptarSolicitud(){
	solicitarPlaza();
	cerrarSesion();
	
	comoPromotorAceptarSolicitud();
	 
	assertButtonNotPresent("aceptarSolicitud_306");
	
	comoPromotorCancelarSeat();
    }


    @Test
    public void testComoPromotorCancelarSeat(){
	solicitarPlaza();
	
	cerrarSesion();
	
	comoPromotorAceptarSolicitud();
	
	cerrarSesion();
	
	comoPromotorCancelarSeat();
	 
	assertButtonNotPresent("cancelarSeat_306");
    }
    
    @Test
    public void testComoUsuarioCancelarSeat(){
	solicitarPlaza();
	
	cerrarSesion();
	
	comoPromotorAceptarSolicitud();
	
	cerrarSesion();
	
	comoUsuarioCancelarSeat();
	 
	assertButtonNotPresent("cancelarPlaza_306");
	
	
    }

//    @Test
//    public void testComoUsuarioComentarPlaza(){
//	loguearse("user1");
//	
//	assertLinkPresent("listarRelacionados");
//	clickLink("listarRelacionados");
//	
//	assertLinkPresent("31");
//	clickLink("31");
//	
//	setTextField("comentarioSeat_309", "Muy salao"); 
//	setTextField("valoracionSeat_309", "8");
//        
//        submit("comentarioSeat_309");
//	
//        assertTextInElement("mensaje","Asiento comentado con exito");
//    }
//   

}