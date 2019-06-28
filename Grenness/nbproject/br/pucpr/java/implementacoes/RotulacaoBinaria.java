/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pucpr.java.implementacoes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import pucpr.java.infraBasica.ListaPixel;
import pucpr.java.infraBasica.Ponto;
import pucpr.java.swing.Menu;

/**
 *
 * @author Bruno
 */
public class RotulacaoBinaria {
    
    ListaPixel Imagem1 = new ListaPixel(); 
    ListaPixel rotulo = new ListaPixel();
    Color cores[] = new Color[120];
    /*
     * 
    L <- -1

    Varre a imagem da esquerda para direita e de cima para baixo para todo (x , y)
    |	Se g (x, y) >=0 então  insere (x, y)	
    |	 Enquanto lista não vazia faça
    |	 |	(s ,t)  remove()
    |	 |	para cada 4-vizinhança (u , v) de ( s , t ) faça
    |	 |	|	Se (u, v) está sem rotulo e g(u, v) = g(x, y) então
    |	 |	|	|	Insere (u, v)
    |	 | 	|	Fim_se
    |	 |	Fim_para
    |	Fim_enquanto
    |	L<- L-1 (Pega um novo rotulo)	
    Fim
    */
  
   
   private void initCores() {
                int r = 0;
                int g = 0;
                int b = 0;
                cores[0] = new Color(255,255,255);
                for (int i = 1; i < cores.length; i++) {                    
                    cores[i] = new Color(r,g,b);
                    r = i*23 % 255;
                    g = (i*71) % 255;
                    b = (i*11) % 255;
                }                
	}
   public BufferedImage rotulacao2(BufferedImage bufImg1){
        
        int w = bufImg1.getWidth();
        int h = bufImg1.getHeight();        
        
        short img[][] = new short[w][h];
        int rotuloImg[][]=new int[w][h];
        ArrayList<Ponto> listaP = new ArrayList<>();
        
        
        
        BufferedImage bufImgRes = new BufferedImage(w, h,bufImg1.getType());
        initCores();
        //inicializa matrizes de rotulo e imagem
        for (int x = 0; x < bufImg1.getWidth(); x++) {
                for (int y = 0; y < bufImg1.getHeight(); y++) {
                    rotuloImg[x][y]=0; //sem rotulo
                    Color c = new Color( bufImg1.getRGB( x, y ) );
                    if(c.getRed()<20 && c.getBlue()<20 && c.getGreen()<20)
                        img[x][y]=0;//cor preta
                    else
                        img[x][y]=1;                    
                }
         }
        int valorRotulo=1;
        for (int x = 0; x < bufImg1.getWidth(); x++) {
                for (int y = 0; y < bufImg1.getHeight(); y++) {
                    if(img[x][y]==0 && rotuloImg[x][y]==0){                        
                        listaP.add(new Ponto(0,0,0,x,y));
                        while(!listaP.isEmpty()){ // pega um ponto preto e vai embusca dos similares
                            Ponto uv = listaP.get(0); 
                            listaP.remove(0); //remove o primeiro da lista
                            int ux = uv.getX();
                            int vy = uv.getY();
                            
                            //vizinho superior
                            if(vy>0)
                                if(img[ux][vy-1]==0 && rotuloImg[ux][vy-1]==0){
                                    listaP.add(new Ponto(0,0,0,ux,vy-1));
                                    rotuloImg[ux][vy-1] = valorRotulo;
                                }
                            //vizinho inferior
                            if(vy<(h-1))
                                if(img[ux][vy+1]==0 && rotuloImg[ux][vy+1]==0){
                                    listaP.add(new Ponto(0,0,0,ux,vy+1));
                                    rotuloImg[ux][vy+1] = valorRotulo;
                                }
                            //vizinho esquerda
                            if(ux>0)
                                if(img[ux-1][vy]==0 && rotuloImg[ux-1][vy]==0){
                                    listaP.add(new Ponto(0,0,0,ux-1,vy));
                                    rotuloImg[ux-1][vy] = valorRotulo;
                                }
                            //vizinho direita
                             if(ux<(w-1))
                                if(img[ux+1][vy]==0 && rotuloImg[ux+1][vy]==0){
                                    listaP.add(new Ponto(0,0,0,ux+1,vy));
                                    rotuloImg[ux+1][vy] = valorRotulo;
                                }
                        }
                        valorRotulo++;
                    }                        
                }        
        }
        System.out.println("Qtd Rotulos: : "+valorRotulo);
        Menu.textoImg = " : " + valorRotulo;
        
         for (int x = 0; x < bufImg1.getWidth(); x++) {
                for (int y = 0; y < bufImg1.getHeight(); y++) {
                                int indice = 0;                                
                                indice = rotuloImg[x][y]%119;                                
				bufImgRes.setRGB(x, y, cores[indice].getRGB());
                              
		}
	}
         
        return bufImgRes;
   }
}
