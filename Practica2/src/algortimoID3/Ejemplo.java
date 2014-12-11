package algortimoID3;

import java.util.ArrayList;

import Constantes.Constantes;

public class Ejemplo implements Cloneable{

	//Hacemos una separación entre el ejemplo y el resultado porque así
	//nos serán más fáciles las comparaciones de resultados.

	ArrayList<String> ejemplo;	
	private boolean resultado;
	
	/*
	 * Inicializa un ejemplo mediante un String con sus atributos (separados por DELIMITADOR)
	 */
	public Ejemplo(String atributos)
	{
		String [] arrayAtributos = atributos.split(Constantes.DELIMITADOR);
		ejemplo = new ArrayList<String>();
		for (int i = 0;i< arrayAtributos.length;i++ )
		{
			ejemplo.add(arrayAtributos[i]);
		}
	}
	
	/*
	 * Crea un ejemplo inicializado a "cero" con guiones
	 */
	public Ejemplo(int num_atributos)
	{
		ejemplo = new ArrayList<String>();
		for (int i = 0; i<num_atributos; i++ )
		{
			ejemplo.add("-");
		}
	}
	
	/*
	 * constructor por copia
	 */
	public Ejemplo(Ejemplo ej)
	{
		this.ejemplo = new ArrayList<String>();
		for(String atr : ej.ejemplo)
		{
			this.ejemplo.add(atr);
		}
		this.resultado = ej.resultado;
		
	}
	
	public Ejemplo clone()
	{
		return new Ejemplo(this);
	}
	
	public void quitarAtributo(int num_atributo)
	{
		ejemplo.remove(num_atributo);
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
