
public class Estado {
	
	private String nombreEstado;
	
	private boolean QFinal;
	
	private Simbolo[] conexiones;
						
	private String[] alfabeto;
	
	public Estado(String[] conexiones, String[] alfabeto) {
		if(conexiones[0].substring(0,1).equals("*")) {
			this.QFinal = true;
			this.nombreEstado = conexiones[0].substring(1);
			//System.out.println("creando estado final: " + this.nombreEstado);
		}else {
			this.nombreEstado = conexiones[0];
			this.QFinal = false;
			//System.out.println("creando estado: " + this.nombreEstado);
		}
		this.alfabeto = alfabeto;
		this.conexiones = new Simbolo[alfabeto.length];
		for(int i =0; i<alfabeto.length;i++) {
			Simbolo simbAux = new Simbolo(this.alfabeto[i],conexiones[i+1]);
			this.conexiones[i]=simbAux;
		}
		
	}
	
	public String getNombre() {
		return this.nombreEstado;
	}
	
	public boolean getFinal() {
		return this.QFinal;
	} 
	
	//Retorna el String del estado resultante de la transicion con el simbolo obtenido
	public String getConexion(String simboloBuscado) {
		for(int i = 0; i<this.conexiones.length;i++) {
			if(this.conexiones[i].getValor()==simboloBuscado) {
				return this.conexiones[i].getDestino();
			}
		}
		return "error";
	}
	
}
