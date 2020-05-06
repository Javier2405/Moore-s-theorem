
//VICTOR JAVIER AGUAYO MENDOZA
//A01229260

import java.util.Queue;
import java.util.LinkedList; 

public class LeyMoore {
	
	private String[][]matrizQ1,
						matrizQ2;
	
	public LeyMoore(String[][] matrizQ1, String[][] matrizQ2) {
		this.matrizQ1 = matrizQ1;
		this.matrizQ2 = matrizQ2;
	}
	
	
	public boolean procedimiento() {
		
		//Obtener los simbolos del alfabeto de matriz Q1
		String[] alfabetoQ1 = new String[this.matrizQ1[0].length-1] ;
		for(int i = 1; i<=this.matrizQ1[0].length-1;i++) {
			alfabetoQ1[i-1]=(this.matrizQ1[0][i]);
		}
				
		//Obtener los simbolos del alfabeto de matriz Q2
		String[] alfabetoQ2 = new String[this.matrizQ2[0].length-1] ;
		for(int i = 1; i<=this.matrizQ2[0].length-1;i++) {
			alfabetoQ2[i-1]=(this.matrizQ2[0][i]);
		}
				
		//Comparar los alfabetos de matrizQ1 y matrizQ2. Primer filtro
		
		if(alfabetoQ1.length == alfabetoQ2.length) {
			for(int i =0; i<alfabetoQ1.length;i++) {
				boolean igual = false;
				for(int j=0; j<alfabetoQ2.length;j++) {
					if(alfabetoQ1[i]==alfabetoQ2[j]) {
						igual = true;
					}
				}
				if(!igual) {
					System.out.println("Los automatas no son equivalentes, ya que no tienen el mismo alfabeto");
					return false;
				}
			}
		}else {
			System.out.println("Los automatas no son equivalentes, ya que no tienen el mismo alfabeto");
			return false;
		}
		
		//Una vez que verificamos que matrizQ1 y matrizQ2 tienen el mismo alfabeto
					
		//Crear los estados de matrizQ1
		Estado[] estadosQ1 = new Estado[this.matrizQ1.length-1];
		Estado estadoAux; 
		for(int i = 1; i<this.matrizQ1.length;i++) {
			estadoAux = new Estado(this.matrizQ1[i],alfabetoQ1);
			estadosQ1[i-1]=estadoAux;
		}
		
		//Crear los estados de matrizQ2
		Estado[] estadosQ2 = new Estado[this.matrizQ2.length-1];
		for(int i = 1; i<this.matrizQ2.length;i++) {
			estadoAux = new Estado(this.matrizQ2[i],alfabetoQ2);
			estadosQ2[i-1]=estadoAux;
		}
		
		//Crear queue de pares ordenados pendientes por procesar
		Queue<ParOrdenado> pendientes = new LinkedList<>();
		ParOrdenado parInicial = new ParOrdenado(estadosQ1[0],estadosQ2[0]);
		pendientes.add(parInicial);
		//Crear linked list de pares ordenados revisados
		LinkedList<ParOrdenado> revisados = new LinkedList<ParOrdenado>();
		
		//Antes de entrar a la funcion recursiva, verificamos si todo esta correcto
		
		//Queue pendientes
		//System.out.println(pendientes.peek().getQ1().getNombre());
		//System.out.println(pendientes.peek().getQ2().getNombre());
		
		//System.out.println("--------------------------------");
		//AlfabetoQ1
		//for(int i =0; i<alfabetoQ1.length;i++) {
			//System.out.print(alfabetoQ1[i]);
		//}
		
		//System.out.println("\n--------------------------------");
		
		//EstadosQ1
		//System.out.println("Q1");
		//for(int i =0; i<estadosQ1.length;i++) {
			//System.out.println("--------transiciones-----------" + estadosQ1[i].getNombre());
			//for(int j =0;j<alfabetoQ1.length;j++) {
				//System.out.println(alfabetoQ1[j] + ": " + estadosQ1[i].getConexion(alfabetoQ1[j]));
			//}
		//}
		
		//System.out.println("--------------------------------");
		
		//System.out.println("Q2");
		//EstadosQ2
		//for(int i =0; i<estadosQ2.length;i++) {
			//System.out.println("--------transiciones-----------" + estadosQ2[i].getNombre());
			//for(int j =0;j<alfabetoQ1.length;j++) {
				//System.out.println(alfabetoQ1[j] + ": " + estadosQ2[i].getConexion(alfabetoQ1[j]));
			//}
		//}
		
		//Llamar la funcion recursiva que retorna un boolean
		return paresOrdenados(pendientes,alfabetoQ1, estadosQ1, estadosQ2, revisados);				
	}
	
	//Funcion para convertir un string con el nombre de un estado, en un estado, solo si este existe
	public Estado StringToEstado(Estado[] estados, String Sestado) {
		for(int i =0; i<estados.length; i++) {
			if(estados[i].getNombre().equals(Sestado)) {
				return estados[i];
			}
		}
		return null;
	}
	
	//Funcion para verificar si el parOrdenado ya ha sido revisado o no
	public boolean inRevisado(LinkedList<ParOrdenado> revisados, ParOrdenado par) {
		for(int j=0;j<revisados.size();j++) {
			if((revisados.get(j).getQ1().getNombre().equals(par.getQ1().getNombre())) && (revisados.get(j).getQ2().getNombre().equals(par.getQ2().getNombre()))) {
				return true;	
			}
		}
		//System.out.println("no esta en revisados");
		return false;
	}
	
	//Funcion recursiva que analiza los pares ordenados de la fila de pendientes
	//En la queue pendientes, se guardan todos los pares ordenados por revisar
	//En la linked list revisados, se almacenan todos los pares ordenados que ya fueron revisados
	public boolean paresOrdenados(Queue<ParOrdenado> pendientes, String[] alfabeto, Estado[] estadosQ1, Estado[] estadosQ2, LinkedList<ParOrdenado> revisados) {
		//System.out.println("quedan por revisar " + pendientes.size() + " pares ordenados");
		if(pendientes.size()==0) {
			System.out.println("Los automatas son equivalentes");
			return true;
		}else {
			revisados.add(pendientes.peek());
			ParOrdenado parAux = pendientes.peek();
			//System.out.println("Revisando " + pendientes.peek().getQ1().getNombre() + " , " + pendientes.peek().getQ2().getNombre());
			pendientes.remove();
			if(parAux.getQ1().getFinal() == parAux.getQ2().getFinal()) {
				for(int i = 0; i<alfabeto.length; i++) {
					Estado Q1 = StringToEstado(estadosQ1,parAux.getQ1().getConexion(alfabeto[i]));
					Estado Q2 = StringToEstado(estadosQ2,parAux.getQ2().getConexion(alfabeto[i]));
					ParOrdenado parAux2 = new ParOrdenado(Q1,Q2);
					//System.out.println("Quiero agregar: "+ parAux2.getQ1().getNombre() + " , " + parAux2.getQ2().getNombre());
					if(inRevisado(revisados,parAux2) == false) {
						//System.out.println("Agrego: "+ parAux2.getQ1().getNombre() + " , " + parAux2.getQ2().getNombre());
						pendientes.add(parAux2);
						parAux2 = null;
					}
				}
				return paresOrdenados(pendientes,alfabeto,estadosQ1,estadosQ2,revisados);
			}else {
				//System.out.println(parAux.getQ1().getNombre());
				//System.out.println(parAux.getQ2().getNombre());
				System.out.println("Los automatas no son equivalentes");
				return false;
			}
		}
	}
	
				
	public static void main(String[] args) {
		
		//String[][] matrix = {{"Q","x","y"},{"q1","q2","q4"},{"*q2","q4","q4"},{"q4","q1","q1"}};
		//String[][] matrix2 = {{"Q","x","y"},{"q1","q2","q4"},{"*q2","q4","q4"},{"q4","q1","q1"}};
		
		//PRUEBA 1
		//String[][] matrix = {{"Q","a","b"},{"q0","q1","q2"},{"*q1","q1","q2"},{"q2","q2","q2"}};
		//String[][] matrix2 = {{"Q","a","b"},{"q0","q1","q3"},{"*q1","q2","q3"},{"*q2","q1","q3"},{"q3","q3","q2"}};
		
		//PRUEBA 2
		//String[][] matrix = {{"Q","a","b"},{"q0","q1","q1"},{"*q1","q2","q2"},{"q2","q2","q2"}};
		//String[][] matrix2 = {{"Q","a","b"},{"q0","q1","q2"},{"*q1","q3","q3"},{"*q2","q3","q3"},{"q3","q3","q3"}};
		
		//PRUEBA 3
		String[][] matrix = {{"Q","a","b","c","d","e"},{"q0","q6","q1","q2","q3","q4"},{"q1","q6","q6","q5","q5","q5"},{"q2","q5","q6","q6","q6","q6"},{"q3","q5","q6","q6","q6","q6"},{"q4","q5","q6","q6","q6","q6"},{"*q5","q6","q6","q6","q6","q6"},{"q6","q6","q6","q6","q6","q6"}};
		String[][] matrix2 = {{"Q","a","b","c","d","e"},{"q0","q4","q2","q1","q1","q1"},{"q1","q3","q4","q4","q4","q4"},{"q2","q4","q4","q3","q3","q3"},{"*q3","q4","q4","q4","q4","q4"},{"q4","q4","q4","q4","q4","q4"}};
		
		//String[][] matrix = {{"Q","a","b"},{"*q0","q1","0"},{"q1","q2","q3"},{"q2","0","q0"},{"*q3","q0q1","0"},{"*q0q1","q1q2","q3"},{"q1q2","q2","q3"},{"0","0","0"}};
		//String[][] matrix2 = {{"Q","a","b"},{"*q0","q1q2q4","0"},{"q1q2q4","q3","q0q5"},{"q3","0","q0"},{"0","0","0"},{"*q0q5","q0q1q2q4","0"},{"*q0q1q2q4","q1q2q3q4","q0q5"},{"q1q2q3q4","q3","q0q5"}};
		
		
		LeyMoore Moore = new LeyMoore(matrix,matrix2);
		System.out.println(Moore.procedimiento());
		
	}

}
