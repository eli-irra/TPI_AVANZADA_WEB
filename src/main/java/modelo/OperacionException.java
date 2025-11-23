package modelo;

public class OperacionException extends Exception { 
    
    public OperacionException(String mensaje) {
        
        super(mensaje); 
    }
    
    public OperacionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
