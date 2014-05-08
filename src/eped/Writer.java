package eped;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.awt.Toolkit;


public class Writer {
	private static File fileOut;

	
	public Writer() throws IOException{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	Date date = new Date();
    	String dateLine =  dateFormat.format(date);
    	
    	fileOut = new File("output-"+date+".txt");
    
	
	
    
	//if (fileOut.exists())fileOut.delete();
    

		
	}
	

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
