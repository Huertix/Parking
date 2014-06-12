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
	 * @param Excepción
	 */
	public static void getMessage(Exception ex){
		String message = ex.getMessage();
		 if(message.contains("string")){
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
	 * 
	 */
	private static void tailMessage(){
		System.out.println("\nDebes introducir un valor Integer como argumento.");
		System.out.println("\nEjempo: 1234");
		System.out.println("\nFin de Programa.");
		
	}

}
