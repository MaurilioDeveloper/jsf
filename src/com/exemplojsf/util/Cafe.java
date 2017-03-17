package com.exemplojsf.util;

import java.util.Random;

/** 
 * 
 * Regras
 * 
 * 1 - Jogar pela manhã: Os dois que tirarem o maior numero vão para o café, o perdedor automaticamente está incluido para o café da tarde.
 * 
 * 2 - O perdedor: O perdedor da tarde, automaticamente irá para o café no outro dia de manhã
 * 
 * */

public class Cafe {
    public static void main(String[] args) {
        Random random = new Random();
        String[] nomes = new String[] { "FARUK", "MATHEUS", "ROBINSON" };
        String[] nomesUtilizados = new String[3];
        Integer[] posicao = new Integer[3];
        boolean encontrou = false;
        for (int i = 0; i < nomes.length; i++) {
            do {
                encontrou = false;
                String nome = nomes[random.nextInt(3)];
                for (int j = 0; j < nomesUtilizados.length; j++) {
                    if (nome.equals(nomesUtilizados[j])) {
                        encontrou = true;
                        break;
                    }
                }
                Integer score = random.nextInt(10);
                for (int k = 0; k < posicao.length; k++) {
                    if (score.equals(posicao[k])) {
                        encontrou = true;
                        break;
                    }
                }
                if (!encontrou) {
                    posicao[i] = score;
                    nomesUtilizados[i] = nome;
                    System.out.println(nome + ": " + score);
                    encontrou = false;
                }
            }
            while (encontrou);
        }
    }
}
