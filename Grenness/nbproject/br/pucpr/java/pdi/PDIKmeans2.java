/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pucpr.java.pdi;

import pucpr.java.interfaces.ImageInterface;
import pucpr.java.swing.Conf;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import pucpr.java.implementacoes.FogoArtigoJETAE;
import pucpr.java.implementacoes.MediaParaCentroide;
import pucpr.java.infraBasica.MatrizMascara;
import pucpr.java.infraBasica.OrdenaCor;
import pucpr.java.infraBasica.Pixel;

/**
 *
 * @author Bruno
 */
public class PDIKmeans2 extends JComponent implements ImageInterface {

	private BufferedImage image;
        //maximo 10 classes
	private Color[] cores = new Color[10];
        
        //pixel como centroide e marcando como não fogo
        Pixel cAzulLonge = new Pixel(70,90,100);
        
        //multiplos tipos de cores...
        private float[][][] cors;  
        
        private float [][][]mecs;
        private float [][][]vacs;
        private float [][][]skcs;
        private float [][][]cscs;        
        
	boolean useColor = Conf.feature_color;
	boolean useMedia = Conf.feature_media;
	boolean useVariancia = Conf.feature_variancia;
	boolean useSkewness = Conf.feature_skewness;
	boolean useCurtose = Conf.feature_curtose;
        boolean useFogo = Conf.feature_centroidMediaFogo;
        int tamMatriz = Conf.tam_matriz;
	
	public PDIKmeans2(BufferedImage img) {
		initCores();
		initCaracteristicas(img, Conf.color_chanels.length);
		image = processaKmeans(img, Conf.kmeans_classes, Conf.color_chanels.length);
	}
	//n = numero de classes
        //c = numero de canais de cores
	private BufferedImage processaKmeans(BufferedImage image, int nClass, int c) {
		BufferedImage out = new BufferedImage(image.getWidth(), image.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		
		// ************************************
		// *** Declarações e inicializações ***
		// ************************************
		
		// *** Declarando e definindo os primeiros centróides
		float[][] centroc = new float[c][nClass];		
                float[][] centroMecs = new float[c][nClass];
                float[][] centroVacs = new float[c][nClass];
                float[][] centroSkcs = new float[c][nClass];
                float[][] centroCscs = new float[c][nClass];
                
                int px = (int) (image.getWidth() / (nClass + 1));
                int py = (int) (image.getHeight() / (nClass + 1));
                                                
                
                /*
                FogoArtigoJETAE fire = new FogoArtigoJETAE();
                if(useFogo){                    
                    fire.DetectaFogo(image);
                    * int tm = fire.naofogo2.size();
                    OrdenaCor oc[] = new OrdenaCor[c];
                    for(int i=0;i<c;i++){
                        oc[i] = new OrdenaCor(tm);
                        //pega o espaço de cor exato nas confs
                        int ec = Conf.color_chanels[i];
                        for(int j=0;j<tm;j++){
                            oc[i].insere(fire.naofogo2.get(j).getCor(ec));
                        }
                    }                    
                    
                    double rR = fire.fogo2.getMediaR();
                    double gG = fire.fogo2.getMediaG();
                    double bB = fire.fogo2.getMediaB();
                    int red = (int)rR;
                    int green=(int)gG;
                    int blue = (int)bB;
                    //System.out.println("CORES>> "+red+" - "+green+" - "+blue);
                    Pixel p = new Pixel(red,green,blue);
                    //p.imprime();
                    //centroide da classe 0 será a classe do fogo.
                    for(int j=0;j<c;j++){
                        int ec = Conf.color_chanels[j];
                        float col=p.getCor(ec);                        
                        //System.out.println("COR --> "+col);
                        centroc[j][0] = col;
                        centroMecs[j][0] = col;
                        centroVacs[j][0] = col;
                        centroSkcs[j][0] = col;
                        centroCscs[j][0] = col;
                        
                        float cor2 = oc[j].mediana();
                        centroc[j][1] = cor2;
                        centroMecs[j][1] = cor2;
                        centroVacs[j][1] = cor2;
                        centroSkcs[j][1] = cor2;
                        centroCscs[j][1] = cor2;
                    }

                    for (int i = 2; i < nClass; i++) {                                               
                        for(int j=0;j<c;j++){
                                int xi = (i + 1) * px;
                                int yi = (i + 1) * py;                            
                                if (useColor) {                                                                
                                    centroc[j][i] = cors[xi][yi][j];				
                                }
                                if (useMedia) {                                
                                        centroMecs[j][i] = mecs[xi][yi][j];				
                                }
                                if (useVariancia) {                                
                                        centroVacs[j][i] = vacs[xi][yi][j];				
                                }
                                if (useSkewness) {                                
                                        centroSkcs[j][i] = skcs[xi][yi][j];								
                                }
                                if (useCurtose) {                                
                                        centroCscs[j][i] = cscs[xi][yi][j];				
                                }                                                                                                                           
                    }
                }
                }*/
    
            if(useFogo){
                //Definindo Centroide de Fogo                      
                MediaParaCentroide mc = new MediaParaCentroide();
                Pixel cFire = mc.mediaFogo();
                
                //define o centroide inicial do fogo
                for(int j=0;j<c;j++){
                        int ec = Conf.color_chanels[j];
                        float col=cFire.getCor(ec);                        
                        centroc[j][0] = col;
                        centroMecs[j][0] = col;
                        centroVacs[j][0] = col;
                        centroSkcs[j][0] = col;
                        centroCscs[j][0] = col;
                        
                        float corP = cAzulLonge.getCor(ec);
                        centroc[j][1] = corP;
                        centroMecs[j][1] = corP;
                        centroVacs[j][1] = corP;
                        centroSkcs[j][1] = corP;
                        centroCscs[j][1] = corP;
                        
                }                                
                for (int i = 2; i < nClass; i++) {                                               
                        for(int j=0;j<c;j++){
                                int xi = (i + 1) * px;
                                int yi = (i + 1) * py;                            
                                if (useColor) {                                                                
                                    centroc[j][i] = cors[xi][yi][j];				
                                }
                                if (useMedia) {                                
                                        centroMecs[j][i] = mecs[xi][yi][j];				
                                }
                                if (useVariancia) {                                
                                        centroVacs[j][i] = vacs[xi][yi][j];				
                                }
                                if (useSkewness) {                                
                                        centroSkcs[j][i] = skcs[xi][yi][j];								
                                }
                                if (useCurtose) {                                
                                        centroCscs[j][i] = cscs[xi][yi][j];				
                                }                                                                                                                           
                    }
                }
                    
            }//if                
            else{                                                       
                for (int i = 0; i < nClass; i++) {
                        if (useColor) {
                            for(int j=0;j<c;j++)
                                centroc[j][i] = cors[(i + 1) * px][(i + 1) * py][j];				
                        }
                        if (useMedia) {
                             for(int j=0;j<c;j++)
                                centroMecs[j][i] = mecs[(i + 1) * px][(i + 1) * py][j];				
                        }
                        if (useVariancia) {
                            for(int j=0;j<c;j++)
                                centroVacs[j][i] = vacs[(i + 1) * px][(i + 1) * py][j];				
                        }
                        if (useSkewness) {
                            for(int j=0;j<c;j++)
                                centroSkcs[j][i] = skcs[(i + 1) * px][(i + 1) * py][j];								
                        }
                        if (useCurtose) {
                            for(int j=0;j<c;j++)
                                centroCscs[j][i] = cscs[(i + 1) * px][(i + 1) * py][j];				
                        }
                }
            }//else
		// *** Declarando e inicializando as estruturas que calcularão os novos centróides
		float[][] somaNovoCentroc = new float[c][nClass];
                
                float[][] somaNovoCentroMecs = new float[c][nClass];
                float[][] somaNovoCentroVacs = new float[c][nClass];
                float[][] somaNovoCentroSkcs = new float[c][nClass];
                float[][] somaNovoCentroCscs = new float[c][nClass];
                
		int[] count = new int[nClass];
		for (int i = 0; i < nClass; i++) {
                        for (int j = 0; j < c; j++){                              
                            somaNovoCentroc[j][i]= 0;
                            somaNovoCentroMecs[j][i]= 0;
                            somaNovoCentroVacs[j][i]= 0;
                            somaNovoCentroSkcs[j][i]= 0;
                            somaNovoCentroCscs[j][i]= 0;
                            
                        }                    
			count[i] = 0;
		}
		// *** Declarando e inicializando os vetores de classe
		int[][] k = new int[image.getWidth()][image.getHeight()];
		int[][] oldk = new int[image.getWidth()][image.getHeight()];
		int changek = 0;
		
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				k[x][y] = 0;
				oldk[x][y] = 0;
			}
		}
		
		// **************************
		// *** Corpo do Algoritmo ***
		// **************************
		//fire=null;
		// *** Cálculo das distâncias euclidianas ***
		for (int itera = 0; itera < 500; itera++) {
			for (int y = 0; y < image.getHeight(); y++) {
				for (int x = 0; x < image.getWidth(); x++) {
					float maiorDist = 32765;					
					for (int i = 0; i < nClass; i++) {
						float soma = 0;
						if (useColor) {
                                                    for (int j = 0; j < c; j++) 
                                                    	soma += Math.pow(cors[x][y][j] - centroc[j][i], 2.0);														
						}
						if (useMedia) {
                                                    for (int j = 0; j < c; j++)
							soma += Math.pow(mecs[x][y][j] - centroMecs[j][i], 2.0);							
						}
						if (useVariancia) {
                                                    for (int j = 0; j < c; j++)
							soma += Math.pow(vacs[x][y][j] - centroVacs[j][i], 2.0);							
						}
						if (useSkewness) {
                                                    for (int j = 0; j < c; j++)
							soma += Math.pow(skcs[x][y][j] - centroSkcs[j][i], 2.0);							
						}
						if (useCurtose) {
                                                    for (int j = 0; j < c; j++)
							soma += Math.pow(cscs[x][y][j] - centroCscs[j][i], 2.0);							
						}
						float dist = (float) Math.sqrt(soma);
						
						if (dist < maiorDist) {
							maiorDist = dist;
							k[x][y] = i;
						}
					}
					
					if (k[x][y] != oldk[x][y]) {
						changek++;
						oldk[x][y] = k[x][y];
					}
					
					// Somando para o cálculo dos novos centróides
					if (useColor) {
                                            for (int j = 0; j < c; j++)                                                                                            
                                                somaNovoCentroc[j][k[x][y]] += cors[x][y][j];						
					}
					if (useMedia) {
                                            for (int j = 0; j < c; j++)
						somaNovoCentroMecs[j][k[x][y]] += mecs[x][y][j];						
					}
					if (useVariancia) {
                                            for (int j = 0; j < c; j++)
						somaNovoCentroVacs[j][k[x][y]] += vacs[x][y][j];						
					}
					if (useSkewness) {
                                            for (int j = 0; j < c; j++)
						somaNovoCentroSkcs[j][k[x][y]] += skcs[x][y][j];						
					}
					if (useCurtose) {
                                            for (int j = 0; j < c; j++)
						somaNovoCentroCscs[j][k[x][y]] += cscs[x][y][j];						
					}
					count[k[x][y]]++;
				}
			}
			// *** Cálculo dos novos centróides
			for (int i = 0; i < nClass; i++) {
				if (useColor) {
                                    for (int j = 0; j < c; j++)   
					centroc[j][i] = somaNovoCentroc[j][i] / count[i];					
				}
				if (useMedia) {
                                    for (int j = 0; j < c; j++)
					centroMecs[j][i] = somaNovoCentroMecs[j][i] / count[i];					
				}
				if (useVariancia) {
                                    for (int j = 0; j < c; j++)
					centroVacs[j][i] = somaNovoCentroVacs[j][i] / count[i];					
				}
				if (useSkewness) {
                                    for (int j = 0; j < c; j++)
					centroSkcs[j][i] = somaNovoCentroSkcs[j][i] / count[i];					
				}
				if (useCurtose) {
                                     for (int j = 0; j < c; j++)
					centroCscs[j][i] = somaNovoCentroCscs[j][i] / count[i];					
				}
			}
			for (int i = 0; i < nClass; i++) {
                                for (int j = 0; j < c; j++){   
                                    somaNovoCentroc[j][i]= 0;
                                    somaNovoCentroMecs[j][i]= 0;
                                    somaNovoCentroVacs[j][i]= 0;
                                    somaNovoCentroSkcs[j][i]= 0;
                                    somaNovoCentroCscs[j][i]= 0;
                                }
				count[i] = 0;
			}
			//System.out.println("Changes: " + changek);
			if (changek < image.getWidth()*image.getHeight()*0.001)
				break;
			changek = 0;
		}
		System.out.println("Imagem Processada...");
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				out.setRGB(x, y, cores[k[x][y]].getRGB());
			}
		}		
		Conf.classes = new int[image.getWidth()][image.getHeight()];
		Conf.classes = k;
		
		return out;                
	}
	
	private void initCores() {
                cores[0] = Color.BLACK;
		cores[1] = Color.WHITE;	
                if(useFogo){
                    cores[2] = Color.WHITE;
                    cores[3] = Color.WHITE;
                    cores[4] = Color.WHITE;
                    cores[5] = Color.WHITE;
                    cores[6] = Color.WHITE;
                    cores[7] = Color.WHITE;
                    cores[8] = Color.WHITE;
                    cores[9] = Color.WHITE;
                }else{
                    
                
                    cores[2] = Color.BLUE;
                    cores[3] = Color.YELLOW;
                    cores[4] = Color.CYAN;
                    cores[5] = Color.MAGENTA;
                    cores[6] = Color.ORANGE;
                    cores[7] = Color.GREEN;
                    cores[8] = Color.DARK_GRAY;
                    cores[9] = Color.RED;
                }
	}
	
	private void initCaracteristicas(BufferedImage image, int espCor) {		
		cors = new float[image.getWidth()][image.getHeight()][espCor];		
		if (useMedia || useVariancia || useSkewness || useCurtose) {                    
			mecs = new float[image.getWidth()][image.getHeight()][espCor];
		}
		if (useVariancia) {
			vacs = new float[image.getWidth()][image.getHeight()][espCor];                        		
		}
		if (useSkewness) {
			skcs = new float[image.getWidth()][image.getHeight()][espCor];						
		}
		if (useCurtose) {
			cscs = new float[image.getWidth()][image.getHeight()][espCor];	                       			
		}                
                for(int i1=0;i1<espCor;i1++){
                    for (int y = 0; y < image.getHeight(); y++) {
                        for (int x = 0; x < image.getWidth(); x++) {    
                    
                            int ec = Conf.color_chanels[i1];  
                            Color c = new Color(image.getRGB(x, y));  
                            Pixel p1;
                            if(useFogo){
                                if(c.getRed()>c.getBlue() && c.getRed()>c.getGreen())
                                    p1 = new Pixel (c.getRed(),c.getGreen(),c.getBlue());                                
                                else
                                    p1 = cAzulLonge;                                
                            }
                            else
                                p1 = new Pixel (c.getRed(),c.getGreen(),c.getBlue());
                                
                            //float[] hsv = new float[3];
                            switch(ec) {                              
                                case 1: //R                      
                                        cors[x][y][i1] = p1.getRGB()[0];//R
                                        break;
                                case 2: //G                           					
                                        cors[x][y][i1] = p1.getRGB()[1];//G
                                        break;
                                case 3: //B  					
                                        cors[x][y][i1] = p1.getRGB()[2];//B
                                        break;
                                case 4://Y					
                                        cors[x][y][i1] =  p1.getYCbCr()[0];//Y
                                        break;    
                                case 5: //Cb					
                                        cors[x][y][i1] = p1.getYCbCr()[1];//Cb
                                        break;  
                                case 6: //Cr					
                                        cors[x][y][i1] =  p1.getYCbCr()[2];//Cr
                                        break;                          
                                case 7: //H                                        
                                        cors[x][y][i1] = p1.getHSV()[0];//H
                                        break;   
                                case 8: //S                                                                        
                                        cors[x][y][i1] = p1.getHSV()[1];  //S
                                        break;     
                                case 9: //V                                        
                                        cors[x][y][i1] = p1.getHSV()[2];  //V
                                        break;
                                case 10://S (HSL)                                                                                                           
                                        cors[x][y][i1] = p1.getHSL()[1];                                                  		
                                        break;                            
                                case 11://L (HSL)
                                        cors[x][y][i1] = p1.getHSL()[2];
                                        break;
                                    //Y -- YUV
                                case 12:				
                                        cors[x][y][i1] = p1.getYUV()[0];  
                                        break;
                                     //U - YUV
                                case 13:
                                        cors[x][y][i1] = p1.getYUV()[1];  
                                        break;
                                     //V - YUV
                                case 14:
                                        cors[x][y][i1] = p1.getYUV()[2];  
                                        break;
                                case 15: //H - HSI
                                        cors[x][y][i1] = p1.getHSI()[0];                                        
                                    break;
                                case 16: //S - HSI
                                    cors[x][y][i1] = p1.getHSI()[1];
                                    break;
                                case 17: //I - HSI
                                    cors[x][y][i1] = p1.getHSI()[2];
                                    break;
                            }//switch 
                        }//for x
                    }//for y
                }//for i1
                for (int y = 0; y < image.getHeight(); y++) {
                    for (int x = 0; x < image.getWidth(); x++) {
                        MatrizMascara mm = new MatrizMascara();
                        int ordem=Conf.tam_matriz;
                        
                        if (useMedia||useVariancia||useSkewness||useCurtose) {
                            for(int i2=0;i2<espCor;i2++){
                                mecs[x][y][i2] = mm.mediaMatr(ordem, x, y, i2, cors);
                            }                           
                        }//media
                        if (useVariancia) {
                            for(int i2=0;i2<espCor;i2++){
                                vacs[x][y][i2] = mm.variMatr(ordem, x, y, i2, cors,mecs);
                            }                          
                        }//variancia
                        if (useSkewness) {
                            for(int i2=0;i2<espCor;i2++){
                                skcs[x][y][i2] = mm.skewMatr(ordem, x, y, i2, cors, mecs);
                            }                          
                        }//skewness
                        if (useCurtose) {
                            for(int i2=0;i2<espCor;i2++){
                                cscs[x][y][i2] = mm.curtMatr(ordem, x, y, i2, cors, mecs);
                            }                       
                        }//curtose
                     }//for x 
                }//for y
                    
	}//init caracte
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(image.getWidth(), image.getHeight());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	public BufferedImage getImage() {
		return image;
	}
	
}