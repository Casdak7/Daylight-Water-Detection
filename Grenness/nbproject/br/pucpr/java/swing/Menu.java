package pucpr.java.swing;

import pucpr.java.swing.janelas.TelaKmeansGray;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import pucpr.java.implementacoes.Greenness;
import pucpr.java.swing.JImageWindow.Tipo;
import pucpr.java.swing.janelas.TelaComparaLote;
import pucpr.java.swing.janelas.AguaLote;
import pucpr.java.swing.janelas.GreennKmeans;
import pucpr.java.swing.janelas.MorfologiaB;
import pucpr.java.swing.janelas.GreenningLote;

@SuppressWarnings("serial")
public class Menu extends JMenuBar {

    private JMainFrame mainFrame;
    static public String textoImg = "";

    //********************
    // Cria o Menu
    //********************
    /**
     * Cria o Menu
     * @param mainFrame 
     */
    Menu(JMainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.add(newMenu(1, "Opções do Arquivo"));
        this.add(newMenu(2, "K-Means Imagem Cinza"));
        this.add(newMenu(3, "Método de Greenness Index"));
        //this.add(newMenu(4, "APAGAR - Greenness Index em Lote"));
        this.add(newMenu(5, "Greenness Index K-means em Lote"));
        this.add(newMenu(6, "Comparação em Lote"));
        this.add(newMenu(7, "Detec��o de �gua em lote"));
    }

    //********************
    // CRIA SUB-MENU
    //********************
    /**
     * Cria o Sub-Menu
     * 
     * @param pos
     * @param titulo
     * @return 
     */
    public JMenu newMenu(int pos, String titulo) {

        JMenu menu = new JMenu(titulo);
        

        switch (pos) {
            case 1:
                menu.add(itemAbrir());
                menu.add(itemSalvar());
                menu.addSeparator();
                menu.add(itemFechar());
                break;
            case 2:
                menu.add(KMeansGray());
                menu.setEnabled(false);
                break;
            case 3:
                menu.add(itemGreennKG());
                menu.add(itemGreenn());
                menu.add(itemGreennMin());
                menu.add(itemGreennGmaisR());
                menu.add(itemGreennGmenR());
                menu.add(itemGreennSmolka());
                menu.add(itemGreennG2());
                menu.add(itemGreennIPCA());
                //menu.add(itemBIEspacoX());
                //menu.add(itemBIEspacoI());
                menu.setEnabled(false);
                break;
            case 4:
                menu.add(itemProcessaBI());               
                menu.setEnabled(true);
                break;
            case 5:
                menu.add(GreennessEmLote());                
                menu.setEnabled(true);
                break;
            case 6:
                menu.add(itemLoteComparaGT());
                menu.setEnabled(true);
                break;
            case 7:
            	menu.add(AguaEmLote());                
                menu.setEnabled(true);
            	break;
        }
        return menu;
    }

    //********************
    // Itens Menu Arquivo
    //********************
    public JImageWindow geraJanela(BufferedImage imagem, Tipo t, String titulo) {
        JImageWindow imgWindow = new JImageWindow(imagem, t);
        imgWindow.setTitle(titulo);
        imgWindow.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent e) {
                mainFrame.setSelected((JImageWindow) e.getSource());
                alterarMenu(true);
            }

            ;
						
            @Override
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                JImageWindow closed = (JImageWindow) e.getSource();
                mainFrame.getDesktopPane().remove(closed);
                if (mainFrame.getDesktopPane().getComponentCount() == 0) {
                    alterarMenu(false);
                }
            }
        ;
        });
                            imgWindow.setVisible(true);
        if (mainFrame.getSelected() != null) {
            imgWindow.setLocation(mainFrame.getSelected().getLocation().x + 50,
                    mainFrame.getSelected().getLocation().y + 50);
        }
        mainFrame.getDesktopPane().add(imgWindow);
        try {
            imgWindow.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imgWindow;
    }

    public JMenuItem itemLoteComparaGT() {
        JMenuItem item = new JMenuItem("Lote de Comparação");

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaComparaLote tc = new TelaComparaLote(mainFrame);
                tc.setVisible(true);

            }
        });
        return item;
    }

    public JMenuItem itemMorfoBinaria() {
        JMenuItem item = new JMenuItem("Morfologia Binaria");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MorfologiaB m = new MorfologiaB(mainFrame);
                m.setVisible(true);
            }
        });
        return item;
    }
    
    public JMenuItem itemProcessaBI() {
        JMenuItem item = new JMenuItem("Processamento Greenness Index");

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GreenningLote PW = new GreenningLote(mainFrame);
                PW.setVisible(true);

            }
        });
        return item;
    }
    
    public JMenuItem GreennessEmLote() {
        JMenuItem item = new JMenuItem("Lote");

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GreennKmeans Wt = new GreennKmeans(mainFrame);
                Wt.setVisible(true);

            }
        });
        return item;
    }
    
    public JMenuItem AguaEmLote() {
        JMenuItem item = new JMenuItem("Lote");

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AguaLote Wt = new AguaLote(mainFrame);
                Wt.setVisible(true);

            }
        });
        return item;
    }
    
    public JMenuItem KMeansGray() {
        JMenuItem item = new JMenuItem("Selecionar K-Means");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaKmeansGray tc = new TelaKmeansGray(mainFrame);
                tc.setVisible(true);
            }
        });
        return item;
    }

    public JMenuItem itemAbrir() {

        JMenuItem item = new JMenuItem("Abrir");

        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser("D:\\TrabDoc\\");
                if (chooser.showOpenDialog(mainFrame) != JFileChooser.APPROVE_OPTION) {
                    return;
                }

                try {
                    BufferedImage imagem = ImageIO.read(chooser.getSelectedFile());
                    JImageWindow imgWindow = geraJanela(imagem, Tipo.NORMAL, chooser.getSelectedFile().getName());
                    imgWindow.setSelected(true);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(mainFrame, "Falha ao carregar a imagem");
                    e1.printStackTrace();
                }
            }
        });

        return item;
    }

    public JMenuItem itemSalvar() {

        JMenuItem item = new JMenuItem("Salvar");

        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                File file = null;
                JFileChooser chooser = new JFileChooser();
                JavaFilter filter = new JavaFilter();
                chooser.setFileFilter(filter);
                if (chooser.showSaveDialog(mainFrame) != JFileChooser.APPROVE_OPTION) {
                    return;
                }

                try {
                    file = new File(chooser.getSelectedFile().getAbsolutePath() + ".png");
                    ImageIO.write(mainFrame.getSelected().getImage(), "PNG", file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        item.setEnabled(false);

        return item;
    }

    public JMenuItem itemFechar() {

        JMenuItem item = new JMenuItem("Fechar");

        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        return item;
    }

    //******Greenness Index******
    /**
     * Cria um subitem itemGreennKG() para ser colocado na parte superior da tela
     * @return 
     */
    public JMenuItem itemGreennKG() {

        //JMenuItem item = new JMenuItem("Whiteness Método de ASTME313");
        JMenuItem item = new JMenuItem("GreennessK");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    Greenness white = new Greenness();
                    //BufferedImage imagem = white.whiteNessASTME313(mainFrame.getSelected().getImage());
                    String k = JOptionPane.showInputDialog(null, "Entre com um valor para K: ");
                    float converteK = Float.parseFloat(k);
                    BufferedImage imagem = white.GreennKG(mainFrame.getSelected().getImage(), converteK);
                    //geraJanela(imagem, Tipo.NORMAL, "ASTME313");
                    geraJanela(imagem, Tipo.NORMAL, "GreennessK");
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(mainFrame, "Falha ao carregar a imagem");
                    e1.printStackTrace();
                }

            }

        });
        return item;
    }

     /**
     * Cria um subitem itemGreennGmenR() para ser colocado na parte superior da tela
     * @return 
     */
    public JMenuItem itemGreennGmenR() {
        

        JMenuItem item = new JMenuItem("Greenness G−R");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    Greenness white = new Greenness();
                    BufferedImage imagem = white.GreennGmenR(mainFrame.getSelected().getImage());
                    geraJanela(imagem, Tipo.NORMAL, "Greenness G−R");

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(mainFrame, e1);
                    e1.printStackTrace();
                }

            }

        });
        return item;
    }

    /**
     * Cria um subitem itemGreennGmaisR() para ser colocado na parte superior da tela
     * @return 
     */
    public JMenuItem itemGreennGmaisR() {

        JMenuItem item = new JMenuItem("Greenness G+R");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    Greenness white = new Greenness();
                    BufferedImage imagem = white.GreennGmaisR(mainFrame.getSelected().getImage());
                    geraJanela(imagem, Tipo.NORMAL, "Greenness G+R");

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(mainFrame, "Falha ao carregar a imagem");
                    e1.printStackTrace();
                }

            }

        });
        return item;
    }

    /**
     * Cria um subitem itemBI3ind3() para ser colocado na parte superior da tela
     * @return 
     */
    public JMenuItem itemGreennMin() {

        JMenuItem item = new JMenuItem("GreennessMin");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    Greenness white = new Greenness();
                    BufferedImage imagem = white.GreennMin(mainFrame.getSelected().getImage());
                    geraJanela(imagem, Tipo.NORMAL, "GreennessMin");

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(mainFrame, "Falha ao carregar a imagem");
                    e1.printStackTrace();
                }

            }

        });
        return item;
    }
    
        public JMenuItem itemGreennSmolka() {

        JMenuItem item = new JMenuItem("GreennessSmolka");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    Greenness white = new Greenness();
                    BufferedImage imagem = white.GreennSmolka(mainFrame.getSelected().getImage());
                    geraJanela(imagem, Tipo.NORMAL, "GreennessSmolka");

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(mainFrame, "Falha ao carregar a imagem");
                    e1.printStackTrace();
                }

            }

        });
        return item;
    }    
    
    public JMenuItem itemGreenn(){

        JMenuItem item = new JMenuItem("GreennessG/RGB");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    Greenness white = new Greenness();
                    BufferedImage imagem = white.Greenn(mainFrame.getSelected().getImage());
                    geraJanela(imagem, Tipo.NORMAL, "GreennessG/RGB");

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(mainFrame, "Falha ao carregar a imagem");
                    e1.printStackTrace();
                }

            }

        });
        return item;
    }
    
     public JMenuItem itemGreennG2() {

        //JMenuItem item = new JMenuItem("Whiteness Método de ASTME313");
        JMenuItem item = new JMenuItem("GreennessG2");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    Greenness white = new Greenness();
                    //BufferedImage imagem = white.whiteNessASTME313(mainFrame.getSelected().getImage());
                    BufferedImage imagem = white.GreennG2(mainFrame.getSelected().getImage());
                    //geraJanela(imagem, Tipo.NORMAL, "ASTME313");
                    geraJanela(imagem, Tipo.NORMAL, "GreennessG2");
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(mainFrame, "Falha ao carregar a imagem");
                    e1.printStackTrace();
                }

            }

        });
        return item;
    }
     
    public JMenuItem itemGreennIPCA(){

        JMenuItem item = new JMenuItem("GreennessIPCA");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    Greenness white = new Greenness();
                    BufferedImage imagem = white.Greenn(mainFrame.getSelected().getImage());
                    geraJanela(imagem, Tipo.NORMAL, "GreennessIPCA");

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(mainFrame, "Falha ao carregar a imagem");
                    e1.printStackTrace();
                }

            }

        });
        return item;
    }
    /**
     * Cria um subitem itemBIEspacoX() para ser colocado na parte superior da tela
     * @return 
     */
    public JMenuItem itemBIEspacoX() {

        JMenuItem item = new JMenuItem("Espaço X");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    Greenness white = new Greenness();
                    BufferedImage imagem = white.BIEspacoX(mainFrame.getSelected().getImage());
                    geraJanela(imagem, Tipo.NORMAL, "Espaço X");
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(mainFrame, "Falha ao carregar a imagem");
                    e1.printStackTrace();
                }

            }

        });
        return item;
    }

    /**
     * Cria um subitem itemBIEspacoI() para ser colocado na parte superior da tela
     * @return 
     */
    public JMenuItem itemBIEspacoI() {

        JMenuItem item = new JMenuItem("Espaço I");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    Greenness white = new Greenness();
                    BufferedImage imagem = white.BIEspacoI(mainFrame.getSelected().getImage());
                    geraJanela(imagem, Tipo.NORMAL, "Espaço I");
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(mainFrame, "Falha ao carregar a imagem");
                    e1.printStackTrace();
                }

            }

        });
        return item;
    }

    public void alterarMenu(boolean completo) {
        if (completo) {
            for (int i = 1; i < Menu.this.getMenuCount(); i++) {
                Menu.this.getMenu(i).setEnabled(true);
            }
            Menu.this.getMenu(0).getItem(1).setEnabled(true);
        } else {
            for (int i = 1; i < Menu.this.getMenuCount(); i++) {
                Menu.this.getMenu(i).setEnabled(true);
            }
            Menu.this.getMenu(0).getItem(1).setEnabled(false);

        }
    }

    public class JavaFilter extends javax.swing.filechooser.FileFilter {

        @Override
        public boolean accept(File f) {
            return f.getName().toLowerCase().endsWith(".png")
                    || f.isDirectory();
        }

        @Override
        public String getDescription() {
            return "PNG Files (*.png)";
        }
    }
}
