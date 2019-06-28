/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pucpr.java.implementacoes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import pucpr.java.infraBasica.ListaPixel;
import pucpr.java.infraBasica.ManipulaArquivo;
import pucpr.java.infraBasica.Pixel;

/**
 *
 * @author Bruno
 */
public class MediaParaCentroide {
     ListaPixel pixelsFogo = new ListaPixel();          
     
     public Pixel processaMedia(BufferedImage bufImg1[]){
         Pixel p;
         p=new Pixel();
         int rIgnorar = 192;
         int gIgnorar = 192;
         int bIgnorar = 192;
         for (int img=0;img<bufImg1.length;img++){
            for (int h = 0; h < bufImg1[img].getHeight(); h++) {
                for (int w = 0; w < bufImg1[img].getWidth(); w++) {
                       Color c = new Color( bufImg1[img].getRGB( w, h ) ); 
                       int r = c.getRed();
                       int g = c.getGreen();
                       int b = c.getBlue(); 
                       if(r!=rIgnorar && g!=gIgnorar && b!=bIgnorar){
                           Pixel pxl = new Pixel(r, g, b);
                           pixelsFogo.add(pxl);
                       }                                                      
                }                         
            }            
         }
         for (int i = 0; i < 17; i++) {
             p.setCor(i+1, pixelsFogo.getMediaCor(i));                     
         }
         p.imprime();
         /*
         int rr = (int) pixelsFogo.getMediaR();
         int gg = (int)pixelsFogo.getMediaG();
         int bb = (int)pixelsFogo.getMediaB();
         Pixel p2 = new Pixel(rr, gg, bb);
         p2.imprime();
         */ 
         return p; 
     }
     public Pixel mediaFogo(){
         
                   /* 
                    MEDIA PARA FOGO NOS VEICULOS!
                    RGB >> 0.86821175 0.73940104 0.66209054
                    YCbCr >> 0.72325265 0.4489163 0.5640544
                    HSV >> 0.3908529 0.2775678 0.8682267
                    HSL >> 0.0 0.2775678 0.72944283
                    YUV >> 0.76910204 -0.44335353 -0.0076803584
                    HSI >> 0.0 0.444078 0.7565678                                                                               
                 */
                Pixel cFire = new Pixel();
                cFire.setCor(1,0.86821175f);
                cFire.setCor(2,0.73940104f);
                cFire.setCor(3,0.66209054f);
                //YCbCr
                cFire.setCor(4,0.72325265f);
                cFire.setCor(5,0.4489163f);
                cFire.setCor(6,0.5640544f);
                //HSV
                cFire.setCor(7,0.3908529f);
                cFire.setCor(8,0.2775678f);
                cFire.setCor(9,0.8682267f);
                //HSL - SL
                cFire.setCor(10,0.2775678f);
                cFire.setCor(11,0.72944283f);
                //YUV
                cFire.setCor(12,0.76910204f);
                cFire.setCor(13,-0.44335353f);
                cFire.setCor(14,-0.0076803584f);
                //HSI               
                cFire.setCor(15,0.86821175f);
                cFire.setCor(16,0.86821175f);
                cFire.setCor(17,0.86821175f);
                
                return cFire;
     }
     public Pixel processaMediana(BufferedImage bufImg1){
         Pixel p;
         p=new Pixel();
         return p;
     }
}
