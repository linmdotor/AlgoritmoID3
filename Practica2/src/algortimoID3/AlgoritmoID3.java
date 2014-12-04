package algortimoID3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import Constantes.*;
public class AlgoritmoID3 {

	/**
	 * Algoritmo ID3, que se explica en la pï¿½gina 21 de los apuntes "Tema 04 Aprendizaje II"
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
				System.out.println(comprobarPositivos(ejemplos));
				System.out.println(comprobarNegativos(ejemplos));
				if(comprobarPositivos(ejemplos)) //comprobar que todos los ejemplos son true
				{
					resultado = true;
					
				} 
				else if(comprobarNegativos(ejemplos)) //comprobar que todos los ejemplos son false
				{ 
					resultado = false;
					
				} 
				else 
				{
					double merito_final = Double.MAX_VALUE;
					int atributo_seleccionado = -1;
					
					for(int i=0; i<atributos.size(); i++)
					{
						double mer_aux = merito(atributos.get(i), i, ejemplos);
						if(mer_aux < merito_final)
						{
							merito_final = mer_aux;
							atributo_seleccionado = i;
						}
					}
					
					//seguir el pseudocï¿½digo del algortimo
					/*(1) llamar mejor al elemento a de lista-atributos que minimice mÃ©rito (a)
					(2) iniciar un Ã¡rbol cuya raÃ­z sea mejor:
						para cada valor v i de mejor
						* incluir en ejemplos-restantes los elementos de lista-ejemplos
						que tengan valor v i del atributo mejor.
						* dejar en atributos-restantes todos los elementos de lista-atributos
						excepto mejor.
						* devolver el valor de:
						ID3 (ejemplos-restantes, atributos-restantes)
						(llamada recursiva al algoritmo)*/
					
				}
			} 
			else 
			{
				System.out.println("La lista de atributos estï¿½ vacï¿½a, no se puede continuar.");
			}	
		} 
		else 
		{
			System.out.println("La lista de ejemplos estï¿½ vacï¿½a, no se puede continuar.");
		}
		
		
		return resultado;
	}


	
	/**
	 * Devuelve TRUE si TODOS los ejemplos tienen resultado POSITIVO
	 * @param ejemplos Ejemplos separados por comas
	 */
	private boolean comprobarPositivos(ArrayList<Ejemplo> ejemplos) {

		return comprobar(ejemplos, "no");
	}	
	
	
	/**
	 * Devuelve TRUE si TODOS los ejemplos tienen resultado NEGATIVO
	 * @param ejemplos Ejemplos separados por comas
	 */
	private boolean comprobarNegativos(ArrayList<Ejemplo> ejemplos) {

		return comprobar(ejemplos, "si");
	}	
	
	
	private boolean comprobar(ArrayList<Ejemplo> ejemplos, String valor) {

		for ( Ejemplo ej : ejemplos )
		{
			String atributo_si_no = ej.getEjemplo().get(ej.getEjemplo().size()-1);
			if(atributo_si_no.equalsIgnoreCase(valor))
			{
				return false;
			}
		}
		
		return true;
	}
	
	private double merito(String atributo, int numero_atributo, ArrayList<Ejemplo> ejemplos)
	{
		//1. HACEMOS UNA LISTA DEL NÚMERO DE OPCIONES DISTINTAS DEL ATRIBUTO
		ArrayList<String> opciones_atributo = new ArrayList<String>();
		//añadimos el primer elemento de la lista de ejemplos
		opciones_atributo.add(ejemplos.get(0).getEjemplo().get(0));
		
		for(Ejemplo ej : ejemplos)
		{
			boolean igual = false;
			String opcion = "";
			for(String opc : opciones_atributo)
			{
				if(opc.equalsIgnoreCase(ej.getEjemplo().get(numero_atributo)))
				{
					igual = true;
					opcion = opc;
				}
			}
			if(!igual && !opcion.equalsIgnoreCase(""))
			{
				opciones_atributo.add(opcion);
			}
		
		}
		
		/*
		 Almacenamos el número de positivos y negativos de la siguiente forma:
         A  N P		
		 xs 2 5
		 s  3 0
		 m  5 1
		 l  6 2
		 xl 9 3
		   ...
		 */
		int [][] positivos_negativos = new int[opciones_atributo.size()][2]; 
		
		
		//por cada tipo de atributo que sabemos que es distinto, calculamos cuantos son positivos y negativos
		for(String op : opciones_atributo)
		{
			int p = calcularPositivosNegativos(op, numero_atributo, ejemplos, "si");
			int n = calcularPositivosNegativos(op, numero_atributo, ejemplos, "no");
			
			positivos_negativos[opciones_atributo.indexOf(op)][Constantes.POSITIVO] = p;
			positivos_negativos[opciones_atributo.indexOf(op)][Constantes.NEGATIVO] = n;
			

			//calculamos la entropía (INFOR)
			double porcentaje_p = p/(p+n);
			double porcentaje_n = n/(p+n);
		
			//REVISAR A PARTIR DE AQUÍ; QUE LA FORMULA ES UN LIO Y HAY QUE VER
			//EN LA FORMULA DE ENTROPÍA SI ESTÁ BIEN, MAL O HAY QUE CAMBIAR LOS PORCENTAJES
			double entropia = -porcentaje_p * Math.log(porcentaje_p)/Math.log(2) - porcentaje_n * Math.log(porcentaje_n)/Math.log(2);
			
		}
		
		
		

		
		
		
		
		return numero_atributo;
		
	}
	
	
	
	private int calcularPositivosNegativos(String opcion_atributo, int numero_atributo,  ArrayList<Ejemplo>ejemplos, String opcion)
	{
		int correctos = 0;
		
		for(Ejemplo ej : ejemplos)
		{
			//si el ejemplo corresponde a la opción, valoramos si es si o no
			if(ej.getEjemplo().get(numero_atributo).equalsIgnoreCase(opcion_atributo))
			{
				String atributo_si_no = ej.getEjemplo().get(ej.getEjemplo().size()-1);
				if(atributo_si_no.equalsIgnoreCase(opcion))
				{
					correctos++;
				}
			}
		}
		
		return correctos;
		
	}
}
