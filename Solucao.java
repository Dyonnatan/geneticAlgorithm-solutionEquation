package ga;

public class Solucao implements Comparable<Solucao>{

	public int[] chromosome;
	public double fitness;
	public static int max;
	public static int min;

	public Solucao(int[] bits, int max, int min) {
		this.chromosome = bits;
		Solucao.max=max;
		Solucao.min=min;
		this.fitness = evaluate(chromosome);
	}

	// fitness function
	public static double evaluate(int[] bits) {
		double x = 0;
		double y = 0;

		for (int i = 0; i < bits.length/2; i++)
			x += bits[i] * Math.pow(2, (bits.length/2-1)-i);

		int zerador = bits.length/2;
		for (int i = bits.length/2; i < bits.length; i++)
			y += bits[i] * Math.pow(2, (bits.length)-i );
		
		x = (x*(GeneticAlg.DOMINIO_MAX-GeneticAlg.DOMINIO_MIN)/(Math.pow(2, bits.length/2)-1))+GeneticAlg.DOMINIO_MIN;
		y = (y*(GeneticAlg.DOMINIO_MAX-GeneticAlg.DOMINIO_MIN)/(Math.pow(2, bits.length/2)-1))+GeneticAlg.DOMINIO_MIN;

		 double retorno= (0.5 + ( Math.pow(	Math.sin( Math.sqrt( x*x+(y*y) ) ),2) - 0.5)/
				(1+0.001*(x*x + y*y))*(1+0.001*(x*x + y*y)) -1);
		 if(retorno<0){
			 return -1*retorno;
		 }else{
			 return retorno;
		 }
	}

	@Override
	public int compareTo(Solucao o) {
		
		if(this.fitness < o.fitness)
			return -1;
		if(this.fitness > o.fitness)
			return 1;
		
		return 0;
	}

	public void evaluate() {
		this.fitness = evaluate(this.chromosome);
}

}
