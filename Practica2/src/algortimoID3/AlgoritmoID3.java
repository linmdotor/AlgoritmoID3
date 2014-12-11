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

	private ArrayList<Ejemplo> reglas = new ArrayList<Ejemplo>();
	
	/**
	 * Algoritmo ID3, que se explica en la pï¿½gina 21 de los apuntes "Tema 04 Aprendizaje II"
	 * @param ejemplos
	 * @param atributos
	 * @return
	 */
	public boolean aprenderID3(ArrayList<Ejemplo> ejemplos, ArrayList<String> atributos, ArrayList<String> atributos_originales, int nivel, Ejemplo regla)
	{
		boolean resultado = false;
		
		if(!ejemplos.isEmpty())
		{
			if(!atributos.isEmpty())
			{
				if(comprobarPositivos(ejemplos)) //comprobar que todos los ejemplos son true
				{
					for(int i =0;i<nivel;i++)
						System.out.print(" ");
					System.out.println("TODOS son +");
					//pone a negativo y almacena en el array de reglas
					regla.setResultado(true);	
					reglas.add(regla);
				} 
				else if(comprobarNegativos(ejemplos)) //comprobar que todos los ejemplos son false
				{ 
					for(int i =0;i<nivel;i++)
						System.out.print(" ");
					System.out.println("TODOS son -");
					//pone a positivo y almacena en el array de reglas
					regla.setResultado(false);
					reglas.add(regla);
				} 
				else 
				{
					double merito_final = Double.MAX_VALUE;
					int atributo_seleccionado = -1;
					
					for(int i=0; i<atributos.size()-1; i++)
					{
						double mer_aux = merito(i, ejemplos);
						//System.out.println("MERITO DE " + atributos.get(i) + ": " + mer_aux);
						if(mer_aux < merito_final)
						{
							merito_final = mer_aux;
							atributo_seleccionado = i;
						}
					}
					
					for(int i =0;i<nivel;i++)
						System.out.print(" ");
					System.out.println("ATRIBUTO SELECCIONADO: " + atributos.get(atributo_seleccionado));
					
					//miramos cuantas opciones hay, para llamar a la recursión de cada una
					ArrayList<String> opciones_atributo = calcularOpcionesDistintas(atributo_seleccionado, ejemplos);
					
					
					for(String op : opciones_atributo)	
					{
						//crearse una lista distinta, para cada opcion distinta
						ArrayList<Ejemplo> ejemplos_aux = new ArrayList<Ejemplo>();
						
						//añadimos el ejemplo (fila) si coincide con la opción actual
						for(Ejemplo ej : ejemplos)
						{
							if(ej.getEjemplo().get(atributo_seleccionado).equalsIgnoreCase(op))
							{
								ej.quitarAtributo(atributo_seleccionado);
								ejemplos_aux.add(ej);
							}
						}
						for(int i =0;i<nivel;i++)
							System.out.print(" ");	
						System.out.println(op + "- ");
						
						//Imprimimos cada una de las tablas intermedias
						/*System.out.println("TABLA NUEVA de -" + op + "- para la RECURSIÓN ");
						System.out.println("------------------");
						for(Ejemplo ej2 : ejemplos_aux)
						{
							for(int i=0; i<ej2.getEjemplo().size(); i++)
							System.out.print(ej2.getEjemplo().get(i) + ", ");
							System.out.println();
						}
						System.out.println("------------------");*/
						
						//llamar a la recursión, con la lista reducida de esa opción
						// ¡¡¡SIN LA COLUMNA DEL ATRIBUTO SELECCCCCCIONADO!!!
						//modificamos la regla
						Ejemplo regla_aux = (Ejemplo)regla.clone();
						int indice_original_atributo_seleccionado = calcularIndiceAtributo(atributos.get(atributo_seleccionado), atributos_originales);
						regla_aux.ejemplo.remove(indice_original_atributo_seleccionado);
						regla_aux.ejemplo.add(indice_original_atributo_seleccionado, op);
						
						//modificamos la lista "atributos"
						ArrayList<String> atributos_aux = (ArrayList<String>)atributos.clone();
						atributos_aux.remove(atributo_seleccionado);
						aprenderID3(ejemplos_aux, atributos_aux, atributos_originales, nivel+5, regla_aux);
					}
					
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


	/*
	 * Devuelve la posición en la que se encuentra un atributo dentro de un Array
	 */
	private int calcularIndiceAtributo(String atributo, ArrayList<String> atributos_originales) {
		int indice_final = 0;

		for(int i=0; i<atributos_originales.size(); i++)
		{
			if(atributos_originales.get(i).equalsIgnoreCase(atributo))
				indice_final = i;
		}

		return indice_final;
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
	
	private double merito(int numero_atributo, ArrayList<Ejemplo> ejemplos)
	{
		
		//Llamamos a un método para que nos de todas las opciones distintas de este atributo
		ArrayList<String> opciones_atributo = calcularOpcionesDistintas(numero_atributo, ejemplos);
		
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
		double [] infor = new double[opciones_atributo.size()];

		//por cada tipo de atributo que sabemos que es distinto, calculamos cuantos son positivos y negativos
		for(String op : opciones_atributo)	
		{
			int p = calcularPositivosNegativos(op, numero_atributo, ejemplos, "si");
			int n = calcularPositivosNegativos(op, numero_atributo, ejemplos, "no");
			
			positivos_negativos[opciones_atributo.indexOf(op)][Constantes.POSITIVO] = p;
			positivos_negativos[opciones_atributo.indexOf(op)][Constantes.NEGATIVO] = n;
			

			//calculamos la entropía (INFOR)

			double porcentaje_p = (double)p/(double)(p+n);
			double porcentaje_n = (double)n/(double)(p+n);

			//Tomammoen en cuenta la excepción de calcular un infor(p,n) en el que p o n sean 0
			//Como no se puede calcular el logaritmo de 0, decirmos que infor, por defecto es 0
			if(porcentaje_p != 0 && porcentaje_n != 0)
			{
				infor[opciones_atributo.indexOf(op)] = -porcentaje_p * Math.log(porcentaje_p)/Math.log(2) - porcentaje_n * Math.log(porcentaje_n)/Math.log(2);
				
			}
			else
			{
				infor[opciones_atributo.indexOf(op)] = 0;
			}
		}
		
		double merito = 0;
		for(String op : opciones_atributo)	
		{
			int num_elementos_opcion = positivos_negativos[opciones_atributo.indexOf(op)][Constantes.POSITIVO] + positivos_negativos[opciones_atributo.indexOf(op)][Constantes.NEGATIVO];
			merito = merito + (((double)num_elementos_opcion/(double)ejemplos.size()) * infor[opciones_atributo.indexOf(op)]);				
		}
		
		return merito;
		
	}
	
	
	
	private ArrayList<String> calcularOpcionesDistintas(int numero_atributo, ArrayList<Ejemplo> ejemplos) {
		
		//1. HACEMOS UNA LISTA DEL NÚMERO DE OPCIONES DISTINTAS DEL ATRIBUTO
		ArrayList<String> opciones_atributo = new ArrayList<String>();
		//añadimos el primer elemento de la lista de ejemplos
		opciones_atributo.add(ejemplos.get(0).getEjemplo().get(numero_atributo));
		
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
				else
				{
					opcion = ej.getEjemplo().get(numero_atributo);
				}
			}
			if(!igual && !opcion.equalsIgnoreCase(""))
			{
				opciones_atributo.add(opcion);
			}
		
		}
		
		return opciones_atributo;
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



	public boolean resuelve(Ejemplo ejemplo)
	{
		//valoramos regla por regla
		for(Ejemplo reg : reglas)
		{	
			boolean todos_coinciden = true;
			int indice_atributo = 0;
			//cada uno de los atributos debe coincidir
			for(String atrib : reg.ejemplo)
			{
				if(!atrib.equalsIgnoreCase("-"))
				{
					if(!ejemplo.getEjemplo().get(indice_atributo).equalsIgnoreCase(atrib))
					{
						//no todos coinciden, así que tiene que ser otra regla
						todos_coinciden = false;						
					}
				}
				
				indice_atributo++;
			}
			
			if(todos_coinciden)
			{
				return reg.isResultado();
			}
			
		}
		
		System.out.println("EL EJEMPLO NO COINCIDE CON NINGUNA REGLA. NO SE PUEDE CLASIFICAR.");
		return false;
		
	}


	public void imprimirReglas() {
		for(Ejemplo reg : reglas)
		{
			for(int i=0; i< reg.getEjemplo().size(); i++)
			{
				System.out.print(reg.getEjemplo().get(i) + "  ");
			}
			System.out.print("| resultado: " + reg.isResultado());
			System.out.println();
		}		
	}
}
