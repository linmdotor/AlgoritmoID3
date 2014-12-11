package algortimoID3;

import java.util.ArrayList;

import fichero.Fichero;
import Constantes.Constantes;
import arbolStackOverflow.TreeNode;

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
		clasificacion.aprenderID3(ejemplos, atributos, 0, new Ejemplo(atributos_separados.length-1));

		Ejemplo ejemploConcreto = new Ejemplo("lluvioso,templado,alta,verdad,??");
		clasificacion.resuelve(ejemploConcreto);
		
		
		
		
		
		/*
		TreeNode<String> root = new TreeNode<String>("raiz");
		{
		    TreeNode<String> node0 = root.addChild("node0");
		    TreeNode<String> node1 = root.addChild("node1");
		    TreeNode<String> node2 = root.addChild("node2");
		    {
		        TreeNode<String> node20 = node2.addChild(null);
		        TreeNode<String> node21 = node2.addChild("node21");
		        {
		            TreeNode<String> node210 = node20.addChild("node210");
		        }
		    }
		}*/

	}

}
