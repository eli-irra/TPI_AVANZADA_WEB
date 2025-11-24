package modelo;

public class LoginException extends Exception {
    
    public LoginException(String mensaje) {
        super(mensaje);
    }
    
    public LoginException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}


