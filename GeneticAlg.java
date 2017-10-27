package ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class GeneticAlg {

	public Random r = new Random();

	public final int REPRESENTACAO = 44;
	public final int GERACOES = 400;
	public final int POPULACAO_MAX = 100;
	public final double TAXA_CROSSOVER = 0.95f;
	public final double TAXA_MUTACAO = 0.008f;
	public final static int DOMINIO_MAX = 2;
	public final static int DOMINIO_MIN = -2; // 01100100

	public List<Solucao> populacao = new ArrayList<>();
	public List<Solucao> descendentes = new ArrayList<>();

	public void inicializar() {
		// cria o individuo aleatorio e adiciona a populacao
		for (int i = 0; i < POPULACAO_MAX; i++) {
			int[] individual = new int[REPRESENTACAO];
			for (int j = 0; j < REPRESENTACAO ; j++) {
					individual[j] = r.nextInt(2);
			}
			populacao.add(new Solucao(individual, DOMINIO_MAX, DOMINIO_MIN));
		}

	}

	public void repopular() {
		// adiciona a descendencia a solucao
		for (Solucao solution : descendentes)
			this.populacao.add(solution);
		// ordena em melhores resultados
		Collections.sort(this.populacao);
		// remove os piores
		for (int i = this.populacao.size() - 1; i >= this.POPULACAO_MAX; i--)
			this.populacao.remove(i);

		this.descendentes.clear();

	}

	public void cruzamento() {

		// ajustando para um numero par de pais
		int size = (int) (POPULACAO_MAX / 2 * TAXA_CROSSOVER) * 2;

		double soma = 0;
		double roleta[] = new double[POPULACAO_MAX];
		int contador = 0;
		for (Solucao s : populacao) {
			soma += s.fitness;
			roleta[contador++] = soma-populacao.get(0).fitness;
		}

		for (int i = 0; i < size; i += 2) {
			ArrayList<Solucao> sons = this.combinacao(
					populacao.get(sorteio(soma, roleta)),
					populacao.get(sorteio(soma, roleta)));
			this.descendentes.add(sons.get(0));
			this.descendentes.add(sons.get(1));
		}

	}

	private int sorteio(double soma, double roleta[]) {
		double sorteio = r.nextDouble()*soma;
		int i = 0;
		while (roleta[i] > sorteio) {
			i++;
		}
		return i;
	}

	//um ponto
	public ArrayList<Solucao> combinacao(Solucao parent_a, Solucao parent_b) {

		ArrayList<Solucao> sons = new ArrayList<>();

		int cut_point = REPRESENTACAO /2;
		int[] son_a = new int[REPRESENTACAO];
		int[] son_b = new int[REPRESENTACAO];

		for (int i = 0; i < REPRESENTACAO; i++) {
			if (i <= cut_point) {
				son_a[i] = parent_a.chromosome[i];
				son_b[i] = parent_b.chromosome[i];
			} else {
				son_a[i] = parent_b.chromosome[i];
				son_b[i] = parent_a.chromosome[i];
			}
		}

		sons.add(new Solucao(son_a, DOMINIO_MAX, DOMINIO_MIN));
		sons.add(new Solucao(son_b, DOMINIO_MAX, DOMINIO_MIN));

		return sons;
}
	

	// bit-flip
	public void mutacao() {
		for (Solucao solution : descendentes) {
			for (int i = 0; i < solution.chromosome.length; i++) {
				double prob = r.nextDouble();
				if (prob <= TAXA_MUTACAO)
					if (solution.chromosome[i] == 0)
						solution.chromosome[i] = 1;
					else
						solution.chromosome[i] = 0;
				solution.evaluate();
			}
		}
	}

	public void imprimir(Solucao s) {

		System.out.println("\n--------------------");
		for (int gene : s.chromosome)
			System.out.print("|" + gene);

		System.out.println("\n--------------------");
		System.out.println(s.fitness);

		double x = 0;
		double y = 0;
		int[] bits = populacao.get(POPULACAO_MAX-1).chromosome;

		for (int i = 0; i < bits.length / 2; i++)
			x += bits[i] * Math.pow(2, bits.length - i - 1);

		int zerador = bits.length / 2;
		for (int i = bits.length / 2; i < bits.length; i++)
			y += bits[i] * Math.pow(2, bits.length - (i - zerador) - 1);

		System.out.println("\n--------------------");
		System.out.print("x " + x);
		System.out.print("  y " + y);

	}

	public float mediaGeracaoFitness() {
		float avg = 0;
		for (Solucao solution : populacao) 
			avg += solution.fitness;
		return avg / populacao.size();
}
	
}
