/*
 * Esta clase se encarga del registro de la información a fichero.
 * En dicho fichero se incluira los eventos de salida y entrada al parking,
 * al igual que se muestra en consola.
 */ 


package eped;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author David Huerta - 47489624Y - 1º EPED 2013-2014 - Las Tablas
 * @version Version 1
 */
public class Writer {
	private static File fileOut;

	
	/**
	 * Dentro del m�todo write se utiliza la clase Date para nombrar el fichero de salida
	 * @throws IOException
	 */
	public Writer() throws IOException{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	Date date = new Date();
    	String dateLine =  dateFormat.format(date);
    	
    	fileOut = new File("output-"+date+".txt");	
	}
	

	/**
	 * @param String a escribir en fichero
	 */
	public void write(String line){
        try{  
	        FileWriter w = new FileWriter(fileOut,true); 
	        w.write(line+"\n");
	        w.close();
        }catch(IOException e){
            e.printStackTrace();
        }      
    }		
}
