/*
 * Esta clase se encarga de realizar una escritura en fichero del String pasado por argumento
 * al método write. Se utiliza para registrar las actividades de entra y salida
 * de los vehículos en el parking.
 */

package eped;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Date;


/**
 * @author David Huerta - 47489624Y - 1º EPED 2013-2014 - Las Tablas
 * @version Version 1
 */
public class Writer {
	private static File fileOut;

	
	/**
	 * @throws IOException
	 */
	public Writer() throws IOException{
	
    	fileOut = new File("47489624Y-output-EPED-"+new Date()+".txt");	
	}
	

	/**
	 * @param line. String to print
	 */
	public void write(String line){
        try{
        	
	        FileWriter w = new FileWriter(fileOut,true); 
	        w.write(line+"\n");
	        w.close(); // Cierra el fichero.
	        
         
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }	
	
}