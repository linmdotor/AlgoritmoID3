package algortimoID3;

import java.util.ArrayList;

import fichero.Fichero;
import Constantes.Constantes;

public class Principal {

	public static void main(String[] args) {
		
		//OBTIENE LOS ATRIBUTOS Y LOS METE EN UN ArrayList
		ArrayList<String> atributos = new ArrayList<String>();
		
		Fichero f = new Fichero();
		String [] vector_atributos = f.leerFichero(Constantes.RUTA_ATRIBUTOS);
		String [] atributos_separados = vector_atributos[0].split(Constantes.SEPARACION);
		for(int i = 0; i < atributos_separados.length;i++)
		{
			atributos.add(atributos_separados[i]);
		}
		
		//OBTIENE LOS EJEMPLOS Y LOS METE EN UN ArrayList
		ArrayList<Ejemplo> ejemplos = new ArrayList<Ejemplo>();
		
		String [] vector_ejemplos = f.leerFichero(Constantes.RUTA_JUEGO);
		for(int i = 0; i < vector_ejemplos.length;i++)
		{
			ejemplos.add(new Ejemplo(vector_ejemplos[i]));
		}

		AlgoritmoID3 clasificacion = new AlgoritmoID3();
		clasificacion.aprenderID3(ejemplos, atributos, atributos, 0, new Ejemplo(atributos_separados.length-1));

		System.out.println();
		System.out.println(" --- TABLA DE REGLAS --- ");
		
		clasificacion.imprimirReglas();
		System.out.println();
		
		String [] test;
		Ejemplo ejemploConcreto;
		//Analizamos varios ejemplos concretos:
		test = f.leerFichero(Constantes.RUTA_TEST1);	
		ejemploConcreto = new Ejemplo(test[0]);
		System.out.println("RESULTADO TEST 1: " + clasificacion.resuelve(ejemploConcreto));
		
		test = f.leerFichero(Constantes.RUTA_TEST2);	
		ejemploConcreto = new Ejemplo(test[0]);
		System.out.println("RESULTADO TEST 2: " + clasificacion.resuelve(ejemploConcreto));
		
		test = f.leerFichero(Constantes.RUTA_TEST3);	
		ejemploConcreto = new Ejemplo(test[0]);
		System.out.println("RESULTADO TEST 3: " + clasificacion.resuelve(ejemploConcreto));
	}

}
