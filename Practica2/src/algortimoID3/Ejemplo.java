package algortimoID3;

import java.util.ArrayList;
import Constantes.Constantes;

public class Ejemplo {

	//Hacemos una separaci�n entre el ejemplo y el resultado porque as�
	//nos ser�n m�s f�ciles las comparaciones de resultados.
	//private String ejemplos;
	ArrayList<String> ejemplo;
	
	private boolean resultado;
	
	public Ejemplo(String atributos)
	{
		String [] arrayAtributos = atributos.split(Constantes.SEPARACION);
		ejemplo = new ArrayList<String>();
		for (int i = 0;i< arrayAtributos.length;i++ )
		{
			ejemplo.add(arrayAtributos[i]);
		}
	}

	public ArrayList<String> getEjemplo() {
		return ejemplo;
	}

	public void setEjemplo(ArrayList<String> ejemplo) {
		this.ejemplo = ejemplo;
	}

	public boolean isResultado() {
		return resultado;
	}

	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}
	
	
}
