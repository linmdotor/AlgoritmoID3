package algortimoID3;

import java.util.ArrayList;

import fichero.Fichero;
import Constantes.Constantes;

public class Principal {

	/**
	 * Clase principal
	 * IMPORTANTE llamar al principal pasándole como argumentos en este orden:
	 * - ruta del fichero de ATRIBUTOS
	 * - ruta del fichero de JUEGO (casos de entrenamiento)
	 * - rutas de los ficheros de TEST (todas las que se deseen)
	 * 
	 * por ejemplo: java Principal AtributosJuego.txt Juego.txt Test1Juego.txt Test2Juego.txt Test3Juego.txt
	 */
	public static void main(String[] args) {
		
		//OBTIENE LOS ATRIBUTOS Y LOS METE EN UN ArrayList
		ArrayList<String> atributos = new ArrayList<String>();
		
		Fichero f = new Fichero();
		String [] vector_atributos = f.leerFichero(args[0]);
		String [] atributos_separados = vector_atributos[0].split(Constantes.DELIMITADOR);
		for(int i = 0; i < atributos_separados.length;i++)
		{
			atributos.add(atributos_separados[i]);
		}
		
		//OBTIENE LOS EJEMPLOS Y LOS METE EN UN ArrayList
		ArrayList<Ejemplo> ejemplos = new ArrayList<Ejemplo>();
		
		String [] vector_ejemplos = f.leerFichero(args[1]);
		for(int i = 0; i < vector_ejemplos.length;i++)
		{
			ejemplos.add(new Ejemplo(vector_ejemplos[i]));
		}

		//Realiza el arbol de decision
		System.out.println(" ------- ARBOL DE DECISION ------- ");
		AlgoritmoID3 clasificacion = new AlgoritmoID3();
		clasificacion.aprenderID3(ejemplos, atributos, atributos, 0, new Ejemplo(atributos_separados.length-1));

		System.out.println();
		System.out.println();
		System.out.println(" ------- TABLA DE REGLAS ------- ");
		
		clasificacion.imprimirReglas();
		System.out.println();
		System.out.println();
		
		
		//Analizamos los ejemplos concretos pasados como parámetro:
		System.out.println(" ------- TEST DE CORRECION ------- ");
		String [] test;
		Ejemplo ejemploConcreto;
		
		for(int i=2; i<args.length; i++)
		{
			test = f.leerFichero(args[i]);	
			ejemploConcreto = new Ejemplo(test[0]);
			System.out.println("RESULTADO " + args[i] + " : " + clasificacion.resuelve(ejemploConcreto));
		}

	}

}
