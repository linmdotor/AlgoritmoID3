import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Id3{

	private String[] atributos;
	private int indiceAtributo=-1;
	private Set<String> valoresAtributo = new HashSet<String>();

	public Arbol algoritmo(String entrada, String atributo, String separador) throws IOException{

		Arbol arbol = new Arbol();
		BufferedReader lector = new BufferedReader(new FileReader(entrada));
		String linea=lector.readLine();
		atributos = linea.split(separador);
		int[] atributosRestantes = new int[atributos.length-1]; //este array contiene todos los atributos excepto el atributo objetivo
		int j=0; //segundo contador para las posiciones

		for(int i=0;i<atributos.length;i++){
			if(atributos[i].equals(atributo)){
				indiceAtributo=i; //guardamos posicion del atributoObjetivo
			}else{
				atributosRestantes[j++] =i;
			}
		}

		//guardamos las lineas del archivo en un ArrayList por simplicidad
		List<String[]> objetos = new ArrayList<String[]>();
		while((linea=lector.readLine())!=null){
				if(linea.isEmpty()==true){
					continue;
				}

			String[] separarLinea = linea.split(separador);
			objetos.add(separarLinea);
			valoresAtributo.add(separarLinea[indiceAtributo]);
		}
			lector.close(); //segun san Gil cerramos el reader con el archivo si no pasan cosas feas :S
			//recursion FTW
			arbol.raiz=id3(atributosRestantes,objetos);
			arbol.atributos=atributos;
			return arbol;
	}

	private Nodo id3(int[] atributosRestantes, List<String[]> objetos){
		if(atributosRestantes.length==0){
			Map<String,Integer> frecuenciaDeValores = calcularFrecuenciaValores(objetos,indiceAtributo);
			int cuenta=0;
			String nombre="";
			for(Entry<String,Integer> entrada :frecuenciaDeValores.entrySet()){ //usando un for each para obtener valores y nombres
				if(entrada.getValue()>cuenta){
					cuenta=entrada.getValue();
					nombre=entrada.getKey();
				}
			}
			NodoClase nodoClase = new NodoClase();
			nodoClase.nombreClase=nombre;
			return nodoClase;
		}
		Map<String,Integer> frecuenciaDeValores = calcularFrecuenciaValores(objetos,indiceAtributo);
		if(frecuenciaDeValores.entrySet().size()==1){
			NodoClase nodoClase = new NodoClase();
			nodoClase.nombreClase= (String) frecuenciaDeValores.keySet().toArray()[0];
			return nodoClase;
		}

		double entropia=0;
		for(String valor: valoresAtributo){
			Integer frecuencia= frecuenciaDeValores.get(valor);
				if(frecuencia!=null){
					double frecuencia2=frecuencia/(double) objetos.size();
					entropia-=frecuencia2 * Math.log(frecuencia2)/Math.log(2);
				}
		}
		int base=0;
		double maximo=0;
		for(int atributo:atributosRestantes){
			double a=aumento(atributo,objetos,entropia);
				if(a>=-9999){
					maximo=a;
					base=atributo;
				}
		}
		if(maximo==0){
			NodoClase nodoClase = new NodoClase();
			int maxFre= 0;
			String nom=null;
			for(Entry<String, Integer> entrada:frecuenciaDeValores.entrySet()){
				if(entrada.getValue()>maxFre){
					maxFre=entrada.getValue();
					nom=entrada.getKey();
				}
			}
			nodoClase.nombreClase = nom;
			return nodoClase;
		}
		NodoDecision nod = new NodoDecision();
		nod.atributo=base;
		int[] arregloRestantes = new int[atributosRestantes.length-1];
		int p=0;
		for(int i=0;i<atributosRestantes.length;i++){
			if(atributosRestantes[i] != base){
				arregloRestantes[p++] = atributosRestantes[i];
			}
		}
		Map<String, List<String[]>> t = new HashMap<String, List<String[]>>();
		for (String[] var : objetos) {
			String valor = var[base];
			List<String[]> objetos2 = t.get(valor);
			if (objetos2 == null) {
				objetos2 = new ArrayList<String[]>();
				t.put(valor, objetos2);
			}
			objetos2.add(var);
		}
		nod.nodos = new Nodo[t.size()];
		nod.valoresAtributo = new String[t.size()];
		int ind = 0;
		for (Entry<String, List<String[]>> t2 : t.entrySet()) {
			nod.valoresAtributo[ind] = t2.getKey();
			nod.nodos[ind] = id3(arregloRestantes,
					t2.getValue()); // recursive call
			ind++;
		}
		return nod;
	}

	private Map<String,Integer> calcularFrecuenciaValores(List<String[]> objetos,int indiceAtributo){
		Map<String,Integer> frecuencia = new HashMap<String,Integer>();

		for(String[] var:objetos){
			String objetivo=var[indiceAtributo];
			if(frecuencia.get(objetivo) == null){
				frecuencia.put(objetivo,1);
			}else{
				frecuencia.put(objetivo,frecuencia.get(objetivo)+1);
			}
		}

		return frecuencia;
	}


	private double aumento(int pos, List<String[]> objetos, double entropia) {
				Map<String, Integer> mapa= calcularFrecuenciaValores(objetos, pos);
				double y = 0;
			for (Entry<String, Integer> entrada : mapa.entrySet()) {
				y += entrada.getValue()/ ((double) objetos.size())* calcularEntropia(objetos, pos,entrada.getKey());
			}
			double aumento=entropia-y;
			return aumento;
	}


	private double calcularEntropia(List<String[]> objetos,int condicion, String valor) {
			int a = 0;
			Map<String, Integer> valores= new HashMap<String, Integer>();
			for (String[] var : objetos) {
				if (var[condicion].equals(valor)) {
					String objetivo = var[indiceAtributo];
					if (valores.get(objetivo) == null) {
						valores.put(objetivo, 1);
					} else {
						valores.put(objetivo,valores.get(objetivo) + 1);
					}
					a++;
				}
			}

			double entropia = 0;
			for (String var2: valoresAtributo) {
				// get the frequency
				Integer cuenta = valores.get(var2);
				// if the frequency is not null
				if (cuenta != null) {
					// update entropy according to the formula
					double frecuencia = cuenta / (double) a;
					entropia -= frecuencia * Math.log(frecuencia) / Math.log(2);
				}
			}
			return entropia;
	}

	public void print() {
		System.out.println("Target attribute = " + atributos[indiceAtributo]);
		System.out.print("Other attributes = ");
		for (String var : atributos) {
			if (!var.equals(atributos[indiceAtributo])) {
				System.out.print(var + " ");
			}
		}
		System.out.println();
	}


}