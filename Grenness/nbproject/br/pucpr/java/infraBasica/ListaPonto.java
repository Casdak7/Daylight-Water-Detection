/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pucpr.java.infraBasica;

import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class ListaPonto {
    public ArrayList<Ponto> registros = new ArrayList();
    public boolean AtualizarClustrer = false;

    public double TotalR = 0;
    public double TotalG = 0;
    public double TotalB = 0;
    
    public int Cluster = 0;
           
    public void add(Ponto pPonto){
        if (AtualizarClustrer){
            pPonto.Cluster = this.Cluster;
        }
        this.registros.add(pPonto);

        this.TotalR = this.TotalR + pPonto.R; 
        this.TotalG = this.TotalG + pPonto.G;
        this.TotalB = this.TotalB + pPonto.B;
    }
    
    
    
    public Ponto get(int Indice){
        return this.registros.get(Indice);
    }
    
    public int size(){
        return this.registros.size();
    }
    
    public void InicializarLista(){
        this.registros.clear();
        this.TotalR = 0;
        this.TotalG = 0;
        this.TotalB = 0;
    }        
                    
    
    public PontoCentro GerarPontoCentroide(){
        double TotalR = 0;
        double TotalG = 0;
        double TotalB = 0;
        for (int i=0; i<this.registros.size(); i++){
            Ponto pPonto = this.registros.get(i);
            
            TotalR += pPonto.R;
            TotalG += pPonto.G;
            TotalB += pPonto.B;
        }

        double MediaR = TotalR / this.size();
        double MediaG = TotalG / this.size();
        double MediaB = TotalB / this.size();
        
        PontoCentro pRetorno = new PontoCentro(MediaR, MediaG, MediaB );
        
        return pRetorno;
    }
    
    
}
