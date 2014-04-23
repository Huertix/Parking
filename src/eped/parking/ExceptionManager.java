package eped.parking;


public class ExceptionManager extends Exception {
	
	
	
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
	
	private static void tailMessage(){
		System.out.println("\nDebes introducir un valor Integer como argumento.");
		System.out.println("\nEjempo: 1234");
		System.out.println("\nFin de Programa.");
		
	}

}
