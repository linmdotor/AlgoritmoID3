package algortimoID3;

import java.util.ArrayList;

public class AlgoritmoID3 {

	/**
	 * Algoritmo ID3, que se explica en la p�gina 21 de los apuntes "Tema 04 Aprendizaje II"
	 * @param ejemplos
	 * @param atributos
	 * @return
	 */
	public boolean aprenderID3(ArrayList<Ejemplo> ejemplos, ArrayList<String> atributos)
	{
		boolean resultado = false;
		
		if(!ejemplos.isEmpty())
		{
			if(!atributos.isEmpty())
			{
				if(comprobarPositivos(ejemplos, atributos.size())) //comprobar que todos los ejemplos son true
				{
					resultado = true;
					
				} else if(comprobarNegativos(ejemplos, atributos.size())) { //comprobar que todos los ejemplos son false
					resultado = false;
					
				} else {
					
					//seguir el pseudoc�digo del algortimo
					
					
				}
			} else {
				System.out.println("La lista de atributos est� vac�a, no se puede continuar.");
			}	
		} else {
			System.out.println("La lista de ejemplos est� vac�a, no se puede continuar.");
		}
		
		
		return resultado;
	}



	/**
	 * Devuelve TRUE si TODOS los ejemplos tienen resultado POSITIVO
	 * Se le debe pasar el n�mero de atributos para poder separar el String en partes, 
	 * 	y quedarse con el �ltimo, que ser� el resultado
	 * @param ejemplos Ejemplos separados por comas
	 * @param num_atributos
	 * @return
	 */
	private boolean comprobarPositivos(ArrayList<Ejemplo> ejemplos, int num_atributos) {
		// TODO Auto-generated method stub
		for ( Ejemplo ej : ejemplos )
		{
			
		}
		return false;
	}
	
	/**
	 * Devuelve TRUE si TODOS los ejemplos tienen resultado NEGATIVO
	 * Se le debe pasar el n�mero de atributos para poder separar el String en partes, 
	 * 	y quedarse con el �ltimo, que ser� el resultado
	 * @param ejemplos Ejemplos separados por comas
	 * @param num_atributos
	 * @return
	 */
	private boolean comprobarNegativos(ArrayList<Ejemplo> ejemplos, int num_atributos) {
		// TODO Auto-generated method stub
		for ( Ejemplo ej : ejemplos )
		{
			
		}
		return false;
	}
}
