package algortimoID3;

import fichero.Fichero;
import Constantes.Constantes;
import arbolStackOverflow.TreeNode;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Fichero f = new Fichero();
		String [] atributos = f.leerFichero(Constantes.RUTA_ATRIBUTOS);
		for(int i = 0; i < atributos.length;i++)
		{
			System.out.println(atributos[i]);
		}
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
