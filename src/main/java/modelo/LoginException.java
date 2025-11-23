// Archivo: modelo/LoginException.java
package modelo;

/**
 * Excepción personalizada para manejar errores de validación
 * y negocio durante el proceso de Inicio de Sesión.
 */
public class LoginException extends Exception {
    
    public LoginException(String mensaje) {
        super(mensaje);
    }
    
    public LoginException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}


