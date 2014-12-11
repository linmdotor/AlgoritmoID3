package fichero;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Constantes.Constantes;

public class Fichero 
{

	public String [] leerFichero(String ruta)
	{
		//cargamos los atributos		
		 String cad = "";
	       File fichIn = new File(ruta);  
	        if(fichIn != null){
	            try {
	                //Cargar fichero
	                FileReader fr = new FileReader(fichIn);
	                BufferedReader br = new BufferedReader(fr);
	               
	                	while(br.ready())
	                	{
	                		cad = cad +br.readLine()+ "\n" ;
	                		//System.out.println(cad);
	                	}
	                    
	            } catch (FileNotFoundException ex) {

	            } catch (IOException e) {

					e.printStackTrace();
				} 
	            finally
	            {
	            	
	            }
	        }
	        return cad.split("\n");
	}
	
	public String [] dividirLinea(String linea, String separacion)
	{
		return linea.split(separacion);
	}
}