
public class ParOrdenado {
	
	private Estado Q1,
					Q2;
	
	public ParOrdenado(Estado q1, Estado q2) {
			this.Q1 = q1;
			this.Q2 = q2;
	}
	
	public Estado getQ1() {
		return this.Q1;	
	}
	
	public Estado getQ2() {
		return this.Q2;	
	}
}
