/*
 * Esta clase se encarga del control de los mensajes de erros producidos 
 * por interacciones con los argumentos a la hora de ejecutar la aplicación
 * 
 */ 


package eped.parking;


/**
 * @author David Huerta - 47489624Y - 1º EPED 2013-2014 - Las Tablas
 * @version Version 1
 */
public class ExceptionManager extends Exception {
	
	
	
	/**
	 * Sec colecta todas las errores de programa en tiempo de ejecucion para su tratamiento
	 * @param Excepcion
	 */
	public static void getMessage(Exception ex){
		String message = ex.getMessage();
		 if(message.contains("Fallo Argumentos")){
			 System.out.println("Argumento no valido.");//ex.printStackTrace();
			 tailMessage();
		 }
		 else if(message.contains("0")){
			 System.out.println("No se ha introducido argumento. ");
			 tailMessage();
		 }
		 else
			 System.out.println("Error en programa: \n"+ex.getMessage());	
	}
	
	/**
	 * Cola de mensaje.
	 */
	private static void tailMessage(){
		System.out.println("\nDebe introducir valores Integer como argumentos.");
		System.out.println("\nEjemplo: parking.jar 999 1234");
		System.out.println("\nFin de Programa.");
		
	}

}
