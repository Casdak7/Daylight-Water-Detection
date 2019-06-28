package pucpr.java.implementacoes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import pucpr.java.swing.janelas.GreennKmeans;

/**
 * Classe para os métodos(funcoes) para o calculo do Greenness, onde serão mantidos
 * , que serao usadas pelo resto do programa.
 * 
 * @author Flavia Mattos & Arthur Costa
 */
public class Greenness {

/**
 * Essa função é a implementação da método de GreennesskG = kG − (R + B)
 * onde K é o valor passado pelo usuário e o R,G e B são os valores obtido da imagem
 * 
 * @param img A imagem onde o filtro será aplicado
 * @param K O valor K da equação
 * @return retorna a imagem depois de aplicado o filtro
 */
public BufferedImage GreennKG(BufferedImage img, float K) {
    BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

    //COMEÇO NORMALIZAÇAO
    double min = 10000;
    double max = 0;
    double k = K;

    for (int i = 0; i < img.getWidth(); i++) {
        for (int j = 0; j < img.getHeight(); j++) {
            Color x = new Color(img.getRGB(i, j));
            double cor = k * x.getGreen() - x.getRed() - x.getBlue();

            if (cor < min) {
                min = cor;
            }
            if (cor > max) {
                max = cor;
            }
        }
    }
    //FINAL NORMALIZAÇÃO

    for (int i = 0; i < img.getWidth(); i++) {
        for (int j = 0; j < img.getHeight(); j++) {

            Color p = new Color(img.getRGB(i, j));
            double atual = k * p.getGreen() - p.getRed() - p.getBlue();
            double cor = 255 * ((atual - min) / (max - min));

            int corBK = (int) cor;

            Color novo = new Color(corBK, corBK, corBK);
            res.setRGB(i, j, novo.getRGB());
        }
    }
    return res;
}

/**
 * Funcao deteccao de agua Cassio
 * 
 * @param img
 * @param K
 * @return
 */
public BufferedImage WaterDetect(BufferedImage img, double maxVertical, double maxHorizontal, double minBrilho) {
    BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

    //COMEÇO NORMALIZAÇAO
    double min = 10000;
    double max = 0;

//    for (int i = 0; i < img.getWidth(); i++) {
//        for (int j = 0; j < img.getHeight(); j++) {
//            Color x = new Color(img.getRGB(i, j));
//            double cor = x.getGreen() - x.getRed() - x.getBlue();
//
//            if (cor < min) {
//                min = cor;
//            }
//            if (cor > max) {
//                max = cor;
//            }
//        }
//    }
//    //FINAL NORMALIZAÇÃO
    float[][][] corHSB = new float[img.getWidth()][img.getHeight()][3];
    
  for (int i = 0; i < img.getWidth(); i++) {
	  for (int j = 0; j < img.getHeight(); j++) {
	      Color x = new Color(img.getRGB(i, j));
	      corHSB[i][j] = Color.RGBtoHSB(x.getRed(), x.getGreen(), x.getBlue(), null);
	  }
	}
//FINAL NORMALIZAÇÃO
  
  double[][] razaoBrilhoSaturacao = new double[img.getWidth()][img.getHeight()];
    
    for (int i = 0; i < img.getWidth(); i++) {
        for (int j = 0; j < img.getHeight(); j++) {

            razaoBrilhoSaturacao[i][j] = corHSB[i][j][2] / corHSB[i][j][1];
            
//            System.out.println(razaoBrilhoSaturacao[i][j]);

        }
    }
    
    double[][] derivadaVertical = new double[img.getWidth()][img.getHeight()];
    
    for (int i = 1; i < img.getWidth() - 1; i++) {
        for (int j = 1; j < img.getHeight() - 1; j++) {

            derivadaVertical[i][j] =(corHSB[i+1][j][2] + corHSB[i+1][j-1][2] + corHSB[i+1][j+1][2]) - (corHSB[i-1][j][2] + corHSB[i-1][j-1][2] + corHSB[i-1][j + 1][2])  ;
            
            if(i == 1) {
            	derivadaVertical[i-1][j] = derivadaVertical[i][j];
            	if(j == 1) {
            		derivadaVertical[i-1][j-1] = derivadaVertical[i][j];
            	}
            }
            
            if(j == 1) {
            	derivadaVertical[i][j-1] = derivadaVertical[i][j];
            }
            
            if(i == img.getWidth() - 1) {
            	derivadaVertical[i+1][j] = derivadaVertical[i][j];
            	if(j == img.getHeight() - 1) {
            		derivadaVertical[i+1][j+1] = derivadaVertical[i][j];
            	}
            }
            
            if(j == img.getHeight() - 1) {
            	derivadaVertical[i][j+1] = derivadaVertical[i][j];
            }
            
            
//            System.out.println(derivadaVertical[i][j]);
            
        }
    }
    
double[][] derivadaHorizontal = new double[img.getWidth()][img.getHeight()];
    
    for (int i = 1; i < img.getWidth() - 1; i++) {
        for (int j = 1; j < img.getHeight() - 1; j++) {

            derivadaHorizontal[i][j] =(corHSB[i-1][j+1][2] + corHSB[i][j+1][2] + corHSB[i+1][j+1][2]) - (corHSB[i-1][j-1][2] + corHSB[i][j-1][2] + corHSB[i+1][j-1][2])  ;
            
            if(i == 1) {
            	derivadaHorizontal[i-1][j] = derivadaHorizontal[i][j];
            	if(j == 1) {
            		derivadaHorizontal[i-1][j-1] = derivadaHorizontal[i][j];
            	}
            }
            
            if(j == 1) {
            	derivadaHorizontal[i][j-1] = derivadaHorizontal[i][j];
            }
            
            if(i == img.getWidth() - 1) {
            	derivadaHorizontal[i+1][j] = derivadaHorizontal[i][j];
            	if(j == img.getHeight() - 1) {
            		derivadaHorizontal[i+1][j+1] = derivadaHorizontal[i][j];
            	}
            }
            
            if(j == img.getHeight() - 1) {
            	derivadaHorizontal[i][j+1] = derivadaHorizontal[i][j];
            }
            
            
//            System.out.println(derivadaHorizontal[i][j]);
            
        }
    }
    
    double mediaNegativa = 0;
    double mediaPositiva = 0;
    int pixelsNegativos = 0;
    int pixelsPositivos = 0;
    
    for (int i = 0; i < img.getWidth(); i++) {
        for (int j = 0; j < img.getHeight(); j++) {
            
            
            int derivadaNormatizada = (int) (255 * derivadaHorizontal[i][j]);
            
            if(derivadaNormatizada > 0) {
            	derivadaNormatizada = 0;
            }
            
            derivadaNormatizada = Math.abs(derivadaNormatizada);
            
            if(derivadaNormatizada < 0) {
            	derivadaNormatizada = 0;
            }
            
            if(derivadaNormatizada > 255) {
            	derivadaNormatizada = 255;
            }
//            System.out.println(derivadaNormatizada);
            
//            double[][] vizinhancaVariacao = new double[3][3];
//            if(i > 0 && j > 0 && i < img.getWidth() - 1 && j < img.getHeight() - 1) {
//	            vizinhancaVariacao[0][0] = derivadaHorizontal[i][j] - derivadaHorizontal[i-1][j-1];
//	            vizinhancaVariacao[0][1] = derivadaHorizontal[i][j] - derivadaHorizontal[i-1][j];
//	            vizinhancaVariacao[0][2] = derivadaHorizontal[i][j] - derivadaHorizontal[i-1][j+1];
//	            vizinhancaVariacao[1][0] = derivadaHorizontal[i][j] - derivadaHorizontal[i][j-1];
//	            vizinhancaVariacao[1][1] = derivadaHorizontal[i][j] - derivadaHorizontal[i][j];
//	            vizinhancaVariacao[1][2] = derivadaHorizontal[i][j] - derivadaHorizontal[i][j+1];
//	            vizinhancaVariacao[2][0] = derivadaHorizontal[i][j] - derivadaHorizontal[i+1][j-1];
//	            vizinhancaVariacao[2][1] = derivadaHorizontal[i][j] - derivadaHorizontal[i+1][j];
//	            vizinhancaVariacao[2][2] = derivadaHorizontal[i][j] - derivadaHorizontal[i+1][j+1];
//            }
//            double maiorVariacao = 0;
//            
////            Color novo = new Color(derivadaNormatizada, derivadaNormatizada, derivadaNormatizada);
//            
//            for(int k=0; k < 3; k++) {
//            	for(int l=0; l < 3; l++) {
//            		maiorVariacao = Math.max(maiorVariacao, vizinhancaVariacao[k][l]);
//            	}
//            }
            
//            double[][] regiao = new double[3][3];
//            if(i > 1 && j > 1 && i < img.getWidth() - 2 && j < img.getHeight() - 2) {
//	            regiao[0][0] = corHSB[i-1][j-1][2];
//	            regiao[0][1] = corHSB[i-1][j][2];
//	            regiao[0][2] = corHSB[i-1][j+1][2];
//	            regiao[1][0] = corHSB[i][j-1][2];
//	            regiao[1][1] = corHSB[i][j][2];
//	            regiao[1][2] = corHSB[i][j+1][2];
//	            regiao[2][0] = corHSB[i+1][j-1][2];
//	            regiao[2][1] = corHSB[i+1][j][2];
//	            regiao[2][2] = corHSB[i+1][j+1][2];
//            }
//            double mediaRegiao = 0;
//            
////            Color novo = new Color(derivadaNormatizada, derivadaNormatizada, derivadaNormatizada);
//            
//            for(int k=0; k < 3; k++) {
//            	for(int l=0; l < 3; l++) {
//            		mediaRegiao += regiao[k][l];
//            	}
//            }
//            
//            mediaRegiao = mediaRegiao / 9;
            
            double[][] regiaoVertical = new double[5][5];
            if(i > 2 && j > 2 && i < img.getWidth() - 3 && j < img.getHeight() - 3) {
	            regiaoVertical[0][0] = derivadaVertical[i-1][j-1];
	            regiaoVertical[0][1] = derivadaVertical[i-1][j];
	            regiaoVertical[0][2] = derivadaVertical[i-1][j+1];
	            regiaoVertical[0][3] = derivadaVertical[i-1][j-2];
	            regiaoVertical[0][4] = derivadaVertical[i-1][j+2];
	            
	            regiaoVertical[1][0] = derivadaVertical[i][j-1];
	            regiaoVertical[1][1] = derivadaVertical[i][j];
	            regiaoVertical[1][2] = derivadaVertical[i][j+1];
	            regiaoVertical[1][3] = derivadaVertical[i][j-2];
	            regiaoVertical[1][4] = derivadaVertical[i][j+2];
	            
	            regiaoVertical[2][0] = derivadaVertical[i+1][j-1];
	            regiaoVertical[2][1] = derivadaVertical[i+1][j];
	            regiaoVertical[2][2] = derivadaVertical[i+1][j+1];
	            regiaoVertical[2][3] = derivadaVertical[i+1][j-2];
	            regiaoVertical[2][4] = derivadaVertical[i+1][j+2];
	            
	            regiaoVertical[3][0] = derivadaVertical[i-2][j-1];
	            regiaoVertical[3][1] = derivadaVertical[i-2][j];
	            regiaoVertical[3][2] = derivadaVertical[i-2][j+1];
	            regiaoVertical[3][3] = derivadaVertical[i-2][j-2];
	            regiaoVertical[3][4] = derivadaVertical[i-2][j+2];
	            
	            regiaoVertical[4][0] = derivadaVertical[i+2][j-1];
	            regiaoVertical[4][1] = derivadaVertical[i+2][j];
	            regiaoVertical[4][2] = derivadaVertical[i+2][j+1];
	            regiaoVertical[4][3] = derivadaVertical[i+2][j-2];
	            regiaoVertical[4][4] = derivadaVertical[i+2][j+2];
            }
            double maiorVertical = 0;
            
//            Color novo = new Color(derivadaNormatizada, derivadaNormatizada, derivadaNormatizada);
            
            for(int k=0; k < 5; k++) {
            	for(int l=0; l < 5; l++) {
            		maiorVertical = Math.max(maiorVertical, Math.abs(regiaoVertical[k][l]));
            	}
            }
            
            double[][] regiaoHorizontal = new double[5][5];
            if(i > 2 && j > 2 && i < img.getWidth() - 3 && j < img.getHeight() - 3) {
            	regiaoHorizontal[0][0] = derivadaHorizontal[i-1][j-1];
            	regiaoHorizontal[0][1] = derivadaHorizontal[i-1][j];
            	regiaoHorizontal[0][2] = derivadaHorizontal[i-1][j+1];
            	regiaoHorizontal[0][3] = derivadaHorizontal[i-1][j-2];
            	regiaoHorizontal[0][4] = derivadaHorizontal[i-1][j+2];

            	regiaoHorizontal[1][0] = derivadaHorizontal[i][j-1];
            	regiaoHorizontal[1][1] = derivadaHorizontal[i][j];
            	regiaoHorizontal[1][2] = derivadaHorizontal[i][j+1];
            	regiaoHorizontal[1][3] = derivadaHorizontal[i][j-2];
            	regiaoHorizontal[1][4] = derivadaHorizontal[i][j+2];
            	
            	regiaoHorizontal[2][0] = derivadaHorizontal[i+1][j-1];
            	regiaoHorizontal[2][1] = derivadaHorizontal[i+1][j];
            	regiaoHorizontal[2][2] = derivadaHorizontal[i+1][j+1];
            	regiaoHorizontal[2][3] = derivadaHorizontal[i+1][j-2];
            	regiaoHorizontal[2][4] = derivadaHorizontal[i+1][j+2];
            	
            	regiaoHorizontal[3][0] = derivadaHorizontal[i-2][j-1];
            	regiaoHorizontal[3][1] = derivadaHorizontal[i-2][j];
            	regiaoHorizontal[3][2] = derivadaHorizontal[i-2][j+1];
            	regiaoHorizontal[3][3] = derivadaHorizontal[i-2][j-2];
            	regiaoHorizontal[3][4] = derivadaHorizontal[i-2][j+2];
            	
            	regiaoHorizontal[4][0] = derivadaHorizontal[i+2][j-1];
            	regiaoHorizontal[4][1] = derivadaHorizontal[i+2][j];
            	regiaoHorizontal[4][2] = derivadaHorizontal[i+2][j+1];
            	regiaoHorizontal[4][3] = derivadaHorizontal[i+2][j-2];
            	regiaoHorizontal[4][4] = derivadaHorizontal[i+2][j+2];
            }
            double maiorHorizontal = 0;
            
//            Color novo = new Color(derivadaNormatizada, derivadaNormatizada, derivadaNormatizada);
            
            for(int k=0; k < 5; k++) {
            	for(int l=0; l < 5; l++) {
            		maiorHorizontal = Math.max(maiorHorizontal, Math.abs(regiaoHorizontal[k][l]));
            	}
            }
            
            double[][] arredores = new double[5][5];
          if(i > 1 && j > 1 && i < img.getWidth() - 2 && j < img.getHeight() - 2) {
	            arredores[0][0] = corHSB[i-2][j-2][2];
	            arredores[0][1] = corHSB[i-2][j-1][2];
	            arredores[0][2] = corHSB[i-2][j][2];
	            arredores[0][3] = corHSB[i-2][j+1][2];
	            arredores[0][4] = corHSB[i-2][j+2][2];
	            arredores[1][0] = corHSB[i-1][j-2][2];
	            arredores[1][1] = 0;
	            arredores[1][2] = 0;
	            arredores[1][3] = 0;
	            arredores[1][4] = corHSB[i-1][j+2][2];
	            arredores[2][0] = corHSB[i][j-2][2];
	            arredores[2][1] = 0;
	            arredores[2][2] = 0;
	            arredores[2][3] = 0;
	            arredores[2][4] = corHSB[i+1][j+2][2];
	            arredores[3][0] = corHSB[i+1][j-2][2];
	            arredores[3][1] = 0;
	            arredores[3][2] = 0;
	            arredores[3][3] = 0;
	            arredores[3][4] = corHSB[i+1][j+2][2];
	            arredores[4][0] = corHSB[i+2][j-2][2];
	            arredores[4][1] = corHSB[i+2][j-1][2];
	            arredores[4][2] = corHSB[i+2][j][2];
	            arredores[4][3] = corHSB[i+2][j+1][2];
	            arredores[4][4] = corHSB[i+2][j+2][2];

          }
          double maiorIntensidade = 0;
          
//          Color novo = new Color(derivadaNormatizada, derivadaNormatizada, derivadaNormatizada);
          
          for(int k=0; k < 5; k++) {
          	for(int l=0; l < 5; l++) {
          		maiorIntensidade = Math.max(maiorIntensidade, arredores[k][l]);
          	}
          }
            
            Color novo = new Color(0, 0, 0);
            
            if(Math.abs(derivadaHorizontal[i][j]) < maxHorizontal  && Math.abs(derivadaVertical[i][j]) < maxVertical && maiorHorizontal < maxHorizontal && maiorVertical < maxVertical && corHSB[i][j][2] > minBrilho) {
            	novo = new Color(200, 0, 0);
            }
            	
            res.setRGB(i, j, novo.getRGB());
        }
    }
    
    for (int i = 32; i < img.getWidth() - 32; i++) {
        for (int j = 32; j < img.getHeight() - 32; j++) {
        	double[][] regiaoVertical = new double[5][5];
            if(i > 2 && j > 2 && i < img.getWidth() - 3 && j < img.getHeight() - 3) {
	            regiaoVertical[0][0] = derivadaVertical[i-1][j-1];
	            regiaoVertical[0][1] = derivadaVertical[i-1][j];
	            regiaoVertical[0][2] = derivadaVertical[i-1][j+1];
	            regiaoVertical[0][3] = derivadaVertical[i-1][j-2];
	            regiaoVertical[0][4] = derivadaVertical[i-1][j+2];
	            
	            regiaoVertical[1][0] = derivadaVertical[i][j-1];
	            regiaoVertical[1][1] = derivadaVertical[i][j];
	            regiaoVertical[1][2] = derivadaVertical[i][j+1];
	            regiaoVertical[1][3] = derivadaVertical[i][j-2];
	            regiaoVertical[1][4] = derivadaVertical[i][j+2];
	            
	            regiaoVertical[2][0] = derivadaVertical[i+1][j-1];
	            regiaoVertical[2][1] = derivadaVertical[i+1][j];
	            regiaoVertical[2][2] = derivadaVertical[i+1][j+1];
	            regiaoVertical[2][3] = derivadaVertical[i+1][j-2];
	            regiaoVertical[2][4] = derivadaVertical[i+1][j+2];
	            
	            regiaoVertical[3][0] = derivadaVertical[i-2][j-1];
	            regiaoVertical[3][1] = derivadaVertical[i-2][j];
	            regiaoVertical[3][2] = derivadaVertical[i-2][j+1];
	            regiaoVertical[3][3] = derivadaVertical[i-2][j-2];
	            regiaoVertical[3][4] = derivadaVertical[i-2][j+2];
	            
	            regiaoVertical[4][0] = derivadaVertical[i+2][j-1];
	            regiaoVertical[4][1] = derivadaVertical[i+2][j];
	            regiaoVertical[4][2] = derivadaVertical[i+2][j+1];
	            regiaoVertical[4][3] = derivadaVertical[i+2][j-2];
	            regiaoVertical[4][4] = derivadaVertical[i+2][j+2];
            }
            double maiorVertical = 0;
            
//            Color novo = new Color(derivadaNormatizada, derivadaNormatizada, derivadaNormatizada);
            
            for(int k=0; k < 5; k++) {
            	for(int l=0; l < 5; l++) {
            		maiorVertical = Math.max(maiorVertical, Math.abs(regiaoVertical[k][l]));
            	}
            }
            
            double[][] regiaoHorizontal = new double[5][5];
            if(i > 2 && j > 2 && i < img.getWidth() - 3 && j < img.getHeight() - 3) {
            	regiaoHorizontal[0][0] = derivadaHorizontal[i-1][j-1];
            	regiaoHorizontal[0][1] = derivadaHorizontal[i-1][j];
            	regiaoHorizontal[0][2] = derivadaHorizontal[i-1][j+1];
            	regiaoHorizontal[0][3] = derivadaHorizontal[i-1][j-2];
            	regiaoHorizontal[0][4] = derivadaHorizontal[i-1][j+2];

            	regiaoHorizontal[1][0] = derivadaHorizontal[i][j-1];
            	regiaoHorizontal[1][1] = derivadaHorizontal[i][j];
            	regiaoHorizontal[1][2] = derivadaHorizontal[i][j+1];
            	regiaoHorizontal[1][3] = derivadaHorizontal[i][j-2];
            	regiaoHorizontal[1][4] = derivadaHorizontal[i][j+2];
            	
            	regiaoHorizontal[2][0] = derivadaHorizontal[i+1][j-1];
            	regiaoHorizontal[2][1] = derivadaHorizontal[i+1][j];
            	regiaoHorizontal[2][2] = derivadaHorizontal[i+1][j+1];
            	regiaoHorizontal[2][3] = derivadaHorizontal[i+1][j-2];
            	regiaoHorizontal[2][4] = derivadaHorizontal[i+1][j+2];
            	
            	regiaoHorizontal[3][0] = derivadaHorizontal[i-2][j-1];
            	regiaoHorizontal[3][1] = derivadaHorizontal[i-2][j];
            	regiaoHorizontal[3][2] = derivadaHorizontal[i-2][j+1];
            	regiaoHorizontal[3][3] = derivadaHorizontal[i-2][j-2];
            	regiaoHorizontal[3][4] = derivadaHorizontal[i-2][j+2];
            	
            	regiaoHorizontal[4][0] = derivadaHorizontal[i+2][j-1];
            	regiaoHorizontal[4][1] = derivadaHorizontal[i+2][j];
            	regiaoHorizontal[4][2] = derivadaHorizontal[i+2][j+1];
            	regiaoHorizontal[4][3] = derivadaHorizontal[i+2][j-2];
            	regiaoHorizontal[4][4] = derivadaHorizontal[i+2][j+2];
            }
            double maiorHorizontal = 0;
            
//            Color novo = new Color(derivadaNormatizada, derivadaNormatizada, derivadaNormatizada);
            
            for(int k=0; k < 5; k++) {
            	for(int l=0; l < 5; l++) {
            		maiorHorizontal = Math.max(maiorHorizontal, Math.abs(regiaoHorizontal[k][l]));
            	}
            }
            
            boolean allAgua = true;
            Color testeCor;
            
            for(int p = -4; p < 4; p++) {
            	for(int t = -4; t < 4; t++) {
            		testeCor = new Color(res.getRGB(i+p, j+t));
            		if(testeCor.getRed() != 200) {
            			allAgua = false;
            		}
            	}
            }
            
            System.out.println(i + " - " + j + " - " + allAgua);
            
            if(allAgua == true) {
            	floodFill(i, j, derivadaHorizontal, maxHorizontal + 0.05, res, 1);
            }
        }
    }
    return res;
}

	public static void floodFill(int i, int j, double[][] derivada, double maxDerivada, BufferedImage res, int cont) {
		Color cor = new Color(res.getRGB(i, j));
		System.out.print(cont + " - " + i + " - " + j + " - ");
		if(Math.abs(derivada[i][j]) >= maxDerivada) {
			System.out.println("Not Fillable!");
			return;
		} else if(cor.getGreen() == 200) {
			System.out.println("Already Filled");
			return;
		} else if(i < 32 || j < 32 || i > res.getWidth() - 32 || j > res.getHeight() - 32) {
			System.out.println("Out of Bounds!");
			return;
		} else {
			System.out.println("Fill!");
			Color novo = new Color(0, 200, 0);
			res.setRGB(i, j, novo.getRGB());
			//Tentativa de deixar recursao mais eficiente
			cor = new Color(res.getRGB(i+1, j));
			if(!((Math.abs(derivada[i+1][j]) >= maxDerivada) || (cor.getGreen() == 200))) {
				floodFill(i+1, j, derivada, maxDerivada, res, cont+1);				
			}
			
			cor = new Color(res.getRGB(i, j+1));
			if(!((Math.abs(derivada[i][j+1]) >= maxDerivada) || (cor.getGreen() == 200))) {
				floodFill(i, j+1, derivada, maxDerivada, res, cont+1);			
			}
			
			cor = new Color(res.getRGB(i, j-1));
			if(!((Math.abs(derivada[i][j-1]) >= maxDerivada) || (cor.getGreen() == 200))) {
				floodFill(i, j-1, derivada, maxDerivada, res, cont+1);			
			}
			
			cor = new Color(res.getRGB(i-1, j));
			if(!(Math.abs(derivada[i-1][j]) >= maxDerivada || cor.getGreen() == 200)) {
				floodFill(i-1, j, derivada, maxDerivada, res, cont+1);				
			}

			return;
		}
	}

 /**
 * Essa função é a implementação da método de GreennessMin = G − min(R + B)
 * onde o R,G e B são os valores obtido da imagem
 * 
 * @param img A imagem onde o filtro será aplicado
 * @return retorna a imagem depois de aplicado o filtro
 */
    public BufferedImage GreennMin(BufferedImage img) {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        //COMEÇO NORMALIZAÇAO
        double min = 10000;
        double max = 0;
        double menor ;

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color x = new Color(img.getRGB(i, j));
                
                if(x.getRed() < x.getBlue()){
                    menor = x.getRed();
                }else{
                    menor = x.getBlue();
                }
                
                double cor = x.getGreen() - menor;

                if (cor < min) {
                    min = cor;
                }
                if (cor > max) {
                    max = cor;
                }
            }
        }
        //FINAL NORMALIZAÇÃO

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                
                Color p = new Color(img.getRGB(i, j));
                
                if(p.getRed() < p.getBlue()){
                    menor = p.getRed();
                }else {
                    menor = p.getBlue();
                }                
                
                
                double atual = p.getGreen() - menor;
                double cor = 255 * ((atual - min) / (max - min));

                int corB31 = (int) cor;

                Color novo = new Color(corB31, corB31, corB31);
                res.setRGB(i, j, novo.getRGB());
                
            }
        }
        return res;
    }
    
    /**
     * Essa função é a implementação da método de GreennessG−R = (G + R)/(G - R)
     * onde o R,G e B são os valores obtido da imagem
     * @param img A imagem onde o filtro será aplicado
     * @return retorna a imagem depois de aplicado o filtro
     */
    public BufferedImage GreennGmenR(BufferedImage img) {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        //COMEÇO NORMALIZAÇÃO
        double min = 10000;
        double max = 0;
        int zero;
        double cor = 0;
        
        try {
            
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color x = new Color(img.getRGB(i, j));

                    zero = x.getGreen() + x.getRed();

                    if(zero != 0){

                        cor = (x.getGreen() - x.getRed()) / ((x.getGreen() + x.getRed())); //Oq fazer se tiver um pixel preto?

                    }else{

                        cor = 0;

                    }

                    if (cor < min) {
                        min = cor;
                    }
                    if (cor > max) {
                        max = cor;
                    }
                }
            }

            
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color p = new Color(img.getRGB(i, j));

                    zero = p.getGreen() + p.getRed();
                    if(zero != 0){

                        double atual = (p.getGreen() - p.getRed()) / ((p.getGreen() + p.getRed()));
                        cor = 255 * ((atual - min) / (max - min));

                    }else{

                        cor = 0;

                    }

                    int corB31 = (int) cor;

                    Color novo = new Color(corB31, corB31, corB31);
                    res.setRGB(i, j, novo.getRGB());
                }
            }
        } catch (java.lang.ArithmeticException e) {
            
            JOptionPane.showMessageDialog(null, "Divisão por Zero", "Error", 0);
            
        }
        //FINAL NORMALIZAÇÃO

        
    return res;
    }
    
    /**
     * Essa função é a implementação da método de GreennessG−R = (G − R)/(G + R)
     * onde K é o valor passado pelo usuário e o R,G e B são os valores obtido da imagem
     * @param img A imagem onde o filtro será aplicado
     * @return retorna a imagem depois de aplicado o filtro
     */
    public BufferedImage GreennGmaisR(BufferedImage img) {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        //COMEÇO NORMALIZAÇÃO
        double min = 10000;
        int zero;
        double max = 0;
        double cor = 0;

        try {
            

            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color x = new Color(img.getRGB(i, j));

                    zero = x.getGreen() - x.getRed();

                    if(zero != 0){

                        cor = (x.getGreen() + x.getRed() / (x.getGreen() - x.getRed()));

                    }else{

                        cor = 0;

                    }

                        if (cor < min) {
                            min = cor;
                        }
                        if (cor > max) {
                            max = cor;
                        }
                    }
            }
       
        //FINAL NORMALIZAÇÃO

       
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color p = new Color(img.getRGB(i, j));

                    zero = p.getGreen() - p.getRed();

                    if(zero != 0){

                            double atual = (p.getGreen() + p.getRed() / (p.getGreen() - p.getRed()));
                            cor = 255 * ((atual - min) / (max - min));

                    }else{

                        cor = 0;

                    }

                        int corB31 = (int) cor;

                        Color novo = new Color(corB31, corB31, corB31);
                        res.setRGB(i, j, novo.getRGB());
                }
            }
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Divisão por Zero", "Error", 0);
            
        }
        
        
    return res;
    }

    /**
     * Essa função é a implementação da método de Greenness = (G)/(R + G + B)
     * onde o R,G e B são os valores obtido da imagem
     * @param img
     * @return 
     */
    public BufferedImage Greenn(BufferedImage img) {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        //COMEÇO NORMALIZAÇÃO
        double min = 10000;
        double max = 0;

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color x = new Color(img.getRGB(i, j));

                double cor = x.getGreen() - (x.getRed() + x.getGreen() + x.getBlue());

                if (cor < min) {
                    min = cor;
                }
                if (cor > max) {
                    max = cor;
                }
            }
        }
        //FINAL NORMALIZAÇÃO

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color p = new Color(img.getRGB(i, j));

                double atual = p.getGreen() - (p.getRed() + p.getGreen() + p.getBlue() );

                double cor = 255 * ((atual - min) / (max - min));

                int corB32 = (int) cor;
                Color novo = new Color(corB32, corB32, corB32);
                res.setRGB(i, j, novo.getRGB());
            }
        }
        return res;
    }

    
    /**
     * Essa função é a implementação da método de GreennessSmolka = (G − Max{R,B})^2/G
     * onde o R,G e B são os valores obtido da imagem
     * @param img A imagem onde o filtro será aplicado
     * @return retorna a imagem depois de aplicado o filtro
     */
    public BufferedImage GreennSmolka(BufferedImage img) {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        //COMEÇO NORMALIZAÇÃO
        double min = 10000;
        int zero;
        double max = 0;
        double cor = 0;
        double maior;

        try {
            

            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color x = new Color(img.getRGB(i, j));

                    zero = x.getGreen();

                    if(zero != 0){

                        if(x.getRed() > x.getBlue()){
                            maior = x.getRed();
                        }else{
                            maior = x.getBlue();
                        }

                        cor = x.getGreen() - Math.pow((maior),9) / x.getGreen();

                    }else{

                        cor = 0;

                    }

                        if (cor < min) {
                            min = cor;
                        }
                        if (cor > max) {
                            max = cor;
                        }
                    }
            }
       
        //FINAL NORMALIZAÇÃO

       
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color p = new Color(img.getRGB(i, j));

                    zero = p.getGreen();

                    if(zero != 0){

                        if(p.getRed() > p.getBlue()){
                            maior = p.getRed();
                        }else{
                            maior = p.getBlue();
                        }

                        double atual = p.getGreen() - Math.pow(( maior),9) / p.getGreen();
                        cor = 255 * ((atual - min) / (max - min));
                        
                    }else{

                        cor = 0;

                    }

                            
                        int corB31 = (int) cor;

                        Color novo = new Color(corB31, corB31, corB31);
                        res.setRGB(i, j, novo.getRGB());
                }
            }
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Divisão por Zero", "Error", 0);
            
        }
        
    return res;
    }

    /**
    * Essa função é a implementação da método de GreennessG2 = (G^2 )/(B^2 + R^2 + k)
    * onde K é o valor passado pelo usuário e o R,G e B são os valores obtido da imagem
    * 
    * @param img A imagem onde o filtro será aplicado
    * @param K O valor K da equação
    * @return retorna a imagem depois de aplicado o filtro
    */
    public BufferedImage GreennG2(BufferedImage img) {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        //COMEÇO NORMALIZAÇAO
        double min = 10000;
        double max = 0;
        double var = 14;
        double cor = 0;
        double maior;
        double zero;

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                
                Color x = new Color(img.getRGB(i, j));
                
                zero = Math.pow(x.getBlue(),2) + Math.pow(x.getRed(),2) + var;

                if(zero != 0){

                    cor = Math.pow(x.getGreen(),2) / zero;

                }else{

                    cor = 0;

                }

                if (cor < min) {
                    min = cor;
                }
                if (cor > max) {
                    max = cor;
                }
            }
        }
        //FINAL NORMALIZAÇÃO

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {

                Color p = new Color(img.getRGB(i, j));
                
                zero = Math.pow(p.getBlue(),2) + Math.pow(p.getRed(),2) + var;
                
                if(zero != 0){
                    
                    double atual = Math.pow(p.getGreen(),2) / zero;
                    cor = 255 * ((atual - min) / (max - min));

                }else{

                    cor = 0;

                }

                if (cor < min) {
                    min = cor;
                }
                if (cor > max) {
                    max = cor;
                }
                
                int corBK = (int) cor;

                Color novo = new Color(corBK, corBK, corBK);
                res.setRGB(i, j, novo.getRGB());
            }
        }
        return res;
    }
    
    /**
     * Essa função é a implementação da método de GreennessIPCA = I P CA = 0.7582|R − B| − 0.1168|R − G| + 0.6414|G − B|
     * onde o R,G e B são os valores obtido da imagem
     * @param img
     * @return 
     */
    public BufferedImage GreennIPCA(BufferedImage img) {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        //COMEÇO NORMALIZAÇÃO
        double min = 10000;
        double max = 0;
        double RB, RG, GB;
        
        

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color x = new Color(img.getRGB(i, j));
                
                RB = x.getRed() - x.getBlue();
                RG = x.getRed() - x.getGreen();
                GB = x.getGreen() - x.getBlue();

                //Colocar a função IPCA
                double cor = 0.7582*(Math.abs(RB)) - 0.1168*(Math.abs(RG)) + 0.6414*(Math.abs(GB));

                if (cor < min) {
                    min = cor;
                }
                if (cor > max) {
                    max = cor;
                }
            }
        }
        //FINAL NORMALIZAÇÃO

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color p = new Color(img.getRGB(i, j));

                RB = p.getRed() - p.getBlue();
                RG = p.getRed() - p.getGreen();
                GB = p.getGreen() - p.getBlue();
                
                double atual = 0.7582*(Math.abs(RB)) - 0.1168*(Math.abs(RG)) + 0.6414*(Math.abs(GB));

                double cor = 255 * ((atual - min) / (max - min));

                int corB32 = (int) cor;
                Color novo = new Color(corB32, corB32, corB32);
                res.setRGB(i, j, novo.getRGB());
            }
        }
        return res;
    }
    
    public BufferedImage BIEspacoX(BufferedImage img) {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        //COMEÇO NORMALIZAÇÃO
        double min = 100000;
        double max = 0;

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
               Color x = new Color(img.getRGB(i, j));
               
                //Conversão de RGB para Espaço de cor XYZ
                double Xx = x.getRed() * 0.4124 + x.getGreen() * 0.3575 + x.getBlue() * 0.1804;
                double Yx = x.getRed() * 0.2126 + x.getGreen() * 0.7156 + x.getBlue() * 0.0721;
                double Zx = x.getRed() * 0.0193 + x.getGreen() * 0.1191 + x.getBlue() * 0.9502;

                //Conversão de XYZ para L* a* b*
                double L = (116 * (Yx / 100) - 16);
                double a = 500 * ((Xx / 95.047) - (Yx / 100));
                double b = 200 * ((Yx / 100) - (Zx / 108.883));

                double P = (a + (1.75 * L)) / ((5.465 * L) + a - (3.012 * b));
                double cor = (100 * (P - 0.31)) / 0.17;

                if (cor < min) {
                    min = cor;
                }
                if (cor > max) {
                    max = cor;
                }
            }
        }
        //FINAL NORMALIZAÇÃO

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color p = new Color(img.getRGB(i, j));
                //Conversão de RGB para Espaço de cor XYZ
                double X = p.getRed() * 0.4124 + p.getGreen() * 0.3575 + p.getBlue() * 0.1804;
                double Y = p.getRed() * 0.2126 + p.getGreen() * 0.7156 + p.getBlue() * 0.0721;
                double Z = p.getRed() * 0.0193 + p.getGreen() * 0.1191 + p.getBlue() * 0.9502;

                //Conversão de XYZ para L* a* b*
                double L = (116 * (Y / 100) - 16);
                double a = 500 * ((X / 95.047) - (Y / 100));
                double b = 200 * ((Y / 100) - (Z / 108.883));

                double P = (a + 1.75 * L) / (5.465 * L + a - 3.012 * b);
                double atual = (100 * (P - 0.31)) / 0.17;

                double cor = 255 * ((atual - min) / (max - min));

                int corBX = (int) cor;

                Color novo = new Color(corBX, corBX, corBX);
                res.setRGB(i, j, novo.getRGB());

            }
        }
        return res;
    }

    public BufferedImage BIEspacoI(BufferedImage img) {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        //COMEÇO NORMALIZAÇÃO
        double min = 100000;
        double max = 0;

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color x = new Color(img.getRGB(i, j));
                
                //Conversão de RGB para Espaço de cor XYZ
                double Xx = x.getRed() * 0.4124 + x.getGreen() * 0.3575 + x.getBlue() * 0.1804;
                double Yx = x.getRed() * 0.2126 + x.getGreen() * 0.7156 + x.getBlue() * 0.0721;
                double Zx = x.getRed() * 0.0193 + x.getGreen() * 0.1191 + x.getBlue() * 0.9502;

                //Conversão de XYZ para L* a* b*
                double L = (116 * (Yx / 1) - 16);

                double cor = 100 - L;

                if (cor < min) {
                    min = cor;
                }
                if (cor > max) {
                    max = cor;
                }
            }
        }
        //FINAL NORMALIZAÇÃO

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color p = new Color(img.getRGB(i, j));

                //Conversão de RGB para Espaço de cor XYZ
                double X = p.getRed() * 0.4124 + p.getGreen() * 0.3575 + p.getBlue() * 0.1804;
                double Y = p.getRed() * 0.2126 + p.getGreen() * 0.7156 + p.getBlue() * 0.0721;
                double Z = p.getRed() * 0.0193 + p.getGreen() * 0.1191 + p.getBlue() * 0.9502;

                //Conversão de XYZ para L* a* b*
                double L = (116 * (Y / 1) - 16);
                double atual = 100 - L;
                double cor = 255 * ((atual - min) / (max - min));

                int corBII = (int) cor;
                
                Color novo = new Color(corBII, corBII, corBII);
                res.setRGB(i, j, novo.getRGB());

            }
        }
        return res;
    }
}
