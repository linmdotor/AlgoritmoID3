public class Arbol{
	String[] atributos;
	Nodo raiz=null;

	public String predecirValor(String[] a){
		return predecir(raiz,a);
	}

	private String predecir(Nodo nodoActual, String[] b){
		if(nodoActual instanceof NodoClase){
			NodoClase nodo= (NodoClase) nodoActual;
			return nodo.nombreClase;
		}else{
			NodoDecision nodo=(NodoDecision) nodoActual;
			String valor = b[nodo.atributo];
			for(int i=0;i<nodo.valoresAtributo.length;i++){
				if(nodo.valoresAtributo[i].equals(valor)){
					return predecir(nodo.nodos[i],b);
				}
			}
		}
		return null;
	}
}