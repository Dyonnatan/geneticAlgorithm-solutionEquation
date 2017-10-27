package ga;

import java.util.Collections;

public class Execute {
	public static void main(String[] args) {

		GeneticAlg ag = new GeneticAlg();

		ag.inicializar();
		Collections.sort(ag.populacao);

		int generation = 0;
		while (generation < ag.GERACOES) {

			ag.cruzamento();
			ag.mutacao();
			ag.repopular();

			Collections.sort(ag.populacao);

			System.out.println(ag.mediaGeracaoFitness());

			generation++;
		}

		ag.imprimir(ag.populacao.get(0));
		
		ag.imprimir(ag.populacao.get(ag.POPULACAO_MAX-1));
	}
	

//    Procedimento AG
//        { t = 0;
//        inicia_população (P, t)
//        avaliação (P, t);
//        repita até (t = d) 
//
//            { t = t +1;
//            seleção_dos_pais (P,t);
//            recombinação (P, t);
//            mutação (P, t);
//            avaliação (P, t);
//            sobrevivem (P, t)
//            } 
//        } 
}
