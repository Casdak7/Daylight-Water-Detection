/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pucpr.java.implementacoes;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import pucpr.java.infraBasica.ListaPixel;
import pucpr.java.infraBasica.ManipulaArquivo;
import pucpr.java.infraBasica.Pixel;
import pucpr.java.infraBasica.PreProcessaImg;

/**
 *
 * @author Bruno
 */
public class GeraArquivoCSV {
    ManipulaArquivo mar = new ManipulaArquivo();
    ListaPixel imagem = new ListaPixel();
    public static void main(String[] args) {
        GeraArquivoCSV gs = new GeraArquivoCSV();
        gs.gerarArquivosCSV();
    }
    public void gerarArquivosCSV(){
        File f[] = mar.abrePasta();
        PreProcessaImg preImg = new PreProcessaImg();
        for(int i=1;i<2;i++)
         {
            try {
                //abre o arquivo de imagem...converte cada ponto em pixel e depois gera arquivo
                BufferedImage img = ImageIO.read(f[i]);
                imagem = preImg.leImg(img);                
                FileWriter arq = new FileWriter("D:\\TrabDoc\\csv\\"+f[i].getName()+"_.csv"); 
                BufferedWriter gravarArq = new BufferedWriter(arq);
                for (int j = 0; j < imagem.size(); j++) {
                    Pixel pxl = imagem.get(j);
                    gravarArq.append(pxl.imprimeCSV_YCbCr());
                }        
                gravarArq.close();               
                arq.close();
                imagem = new ListaPixel();
            } catch (IOException ex) {
                Logger.getLogger(GeraArquivoCSV.class.getName()).log(Level.SEVERE, null, ex);
            }
             System.out.println("ARQUIVO "+i);
         } 
    }
}
