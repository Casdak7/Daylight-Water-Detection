/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pucpr.java.implementacoes;
import java.text.DecimalFormat; 

/**
 *
 * @author Flavia Mattos
 */
public class ColorSpaceConverter {
 
       /** 
    * reference white in XYZ coordinates 
    */ 
   public static double[]   D65 = {95.047, 100.000, 108.883}; 

   /** 
    * sRGB to XYZ conversion matrix 
    */ 
   public static double[][] M   = {{0.412424, 0.212656,  0.0193324}, 
                                   {0.357579, 0.715158,  0.119193 }, 
                                   {0.180464, 0.0721856, 0.950444 }}; 

   /** 
    * XYZ to sRGB conversion matrix 
    */ 
   public static double[][] Mi  = {{3.24071,  -0.969258,  0.0556352}, 
                                   {-1.53726,  1.87599,  -0.203996 }, 
                                   {-0.498571, 0.0415557, 1.05707  }}; 

   /** 
    * Main method for testing. 
    * @param args 
    * ColorSpaceConverter [-rgb R G B | -xyz X Y Z | -lab L a b] 
    */ 
   public static void main(String[] args) { 

     // local variables 
     ColorSpaceConverter csc = new ColorSpaceConverter(); 
     int R = 0, G = 0, B = 0; 
     double X = 0, Y = 0, Z = 0; 
     double L = 0, a = 0, b = 0; 
     double[] rf; 
     int[] ri; 

     DecimalFormat dfi = new DecimalFormat(" ##0;-##0"); 
     DecimalFormat dfd = new DecimalFormat(" #0.000;-#0.000"); 

     // for each argument 
     for (int i = 0; i < args.length; i++) { 
       try { 
         if (args[i].equalsIgnoreCase("-rgb")) { 
           i++; 
           R = new Integer(args[i]).intValue(); 
           i++; 
           G = new Integer(args[i]).intValue(); 
           i++; 
           B = new Integer(args[i]).intValue(); 
           rf = csc.RGBtoXYZ(R, G, B); 
           X = rf[0]; 
           Y = rf[1]; 
           Z = rf[2]; 
           rf = csc.XYZtoLAB(X, Y, Z); 
           L = rf[0]; 
           a = rf[1]; 
           b = rf[2]; 
         } 
         else if (args[i].equalsIgnoreCase("-xyz")) { 
           i++; 
           X = new Double(args[i]).doubleValue(); 
           i++; 
           Y = new Double(args[i]).doubleValue(); 
           i++; 
           Z = new Double(args[i]).doubleValue(); 
           rf = csc.XYZtoLAB(X, Y, Z); 
           L = rf[0]; 
           a = rf[1]; 
           b = rf[2]; 
           ri = csc.XYZtoRGB(X, Y, Z); 
           R = ri[0]; 
           G = ri[1]; 
           B = ri[2]; 
         } 
         else if (args[i].equalsIgnoreCase("-lab")) { 
           i++; 
           L = new Double(args[i]).doubleValue(); 
           i++; 
           a = new Double(args[i]).doubleValue(); 
           i++; 
           b = new Double(args[i]).doubleValue(); 
           rf = csc.LABtoXYZ(L, a, b); 
           X = rf[0]; 
           Y = rf[1]; 
           Z = rf[2]; 
           ri = csc.LABtoRGB(L, a, b); 
           R = ri[0]; 
           G = ri[1]; 
           B = ri[2]; 
         } 
       } 
       catch (ArrayIndexOutOfBoundsException e) { 
         System.err.println("Invalid input"); 
         e.printStackTrace(); 
         System.exit(-1); 
       } 
       System.out.println(""); 
       System.out.println("RGB: " + dfi.format(R) + ",\t" + dfi.format 
(G) 
           + ",\t" + dfi.format(B)); 
       System.out.println("XYZ: " + dfd.format(X) + ",\t" + dfd.format 
(Y) 
           + ",\t" + dfd.format(Z)); 
       System.out.println("Lab: " + dfd.format(L) + ",\t" + dfd.format 
(a) 
           + ",\t" + dfd.format(b)); 
     } 
   } // main 

   /** 
    * Convert LAB to RGB. 
    * @param L 
    * @param a 
    * @param b 
    * @return RGB values 
    */ 
   public int[] LABtoRGB(double L, double a, double b) { 
     return XYZtoRGB(LABtoXYZ(L, a, b)); 
   } 

   /** 
    * @param Lab 
    * @return RGB values 
    */ 
   public int[] LABtoRGB(double[] Lab) { 
     return XYZtoRGB(LABtoXYZ(Lab)); 
   } 

   /** 
    * Convert LAB to XYZ. 
    * @param L 
    * @param a 
    * @param b 
    * @return XYZ values 
    */ 
   public double[] LABtoXYZ(double L, double a, double b) { 
     double[] result = new double[3]; 

     double y = (L + 16.0) / 116.0; 
     double y3 = Math.pow(y, 3.0); 
     double x = (a / 500.0) + y; 
     double x3 = Math.pow(x, 3.0); 
     double z = y - (b / 200.0); 
     double z3 = Math.pow(z, 3.0); 

     if (y3 > 0.008856) { 
       y = y3; 
     } 
     else { 
       y = (y - (16.0 / 116.0)) / 7.787; 
     } 
     if (x3 > 0.008856) { 
       x = x3; 
     } 
     else { 
       x = (x - (16.0 / 116.0)) / 7.787; 
     } 
     if (z3 > 0.008856) { 
       z = z3; 
     } 
     else { 
       z = (z - (16.0 / 116.0)) / 7.787; 
     } 

     result[0] = x * D65[0]; 
     result[1] = y * D65[1]; 
     result[2] = z * D65[2]; 

     return result; 
   } 

   /** 
    * Convert LAB to XYZ. 
    * @param Lab 
    * @return XYZ values 
    */ 
   public double[] LABtoXYZ(double[] Lab) { 
     return LABtoXYZ(Lab[0], Lab[1], Lab[2]); 
   } 

   /** 
    * @param R 
    * @param G 
    * @param B 
    * @return Lab values 
    */ 
   public double[] RGBtoLAB(int R, int G, int B) { 
     return XYZtoLAB(RGBtoXYZ(R, G, B)); 
   } 

   /** 
    * @param RGB 
    * @return Lab values 
    */ 
   public double[] RGBtoLAB(int[] RGB) { 
     return XYZtoLAB(RGBtoXYZ(RGB)); 
   } 

   /** 
    * Convert RGB to XYZ 
    * @param R 
    * @param G 
    * @param B 
    * @return XYZ in double array. 
    */ 
   public double[] RGBtoXYZ(int R, int G, int B) { 
     double[] result = new double[3]; 

     // convert 0..255 into 0..1 
     double r = R / 255.0; 
     double g = G / 255.0; 
     double b = B / 255.0; 

     // assume sRGB 
     if (r <= 0.04045) { 
       r = r / 12.92; 
     } 
     else { 
       r = Math.pow(((r + 0.055) / 1.055), 2.4); 
     } 
     if (g <= 0.04045) { 
       g = g / 12.92; 
     } 
     else { 
       g = Math.pow(((g + 0.055) / 1.055), 2.4); 
     } 
     if (b <= 0.04045) { 
       b = b / 12.92; 
     } 
     else { 
       b = Math.pow(((b + 0.055) / 1.055), 2.4); 
     } 

     r *= 100.0; 
     g *= 100.0; 
     b *= 100.0; 

     // [X Y Z] = [r g b][M] 
     result[0] = (r * M[0][0]) + (g * M[1][0]) + (b * M[2][0]); 
     result[1] = (r * M[0][1]) + (g * M[1][1]) + (b * M[2][1]); 
     result[2] = (r * M[0][2]) + (g * M[1][2]) + (b * M[2][2]); 

     return result; 
   } 

   /** 
    * Convert RGB to XYZ 
    * @param RGB 
    * @return XYZ in double array. 
    */ 
   public double[] RGBtoXYZ(int[] RGB) { 
     return RGBtoXYZ(RGB[0], RGB[1], RGB[2]); 
   } 

   /** 
    * @param x 
    * @param y 
    * @param Y 
    * @return XYZ values 
    */ 
   public double[] xyYtoXYZ(double x, double y, double Y) { 
     double[] result = new double[3]; 
     if (y == 0) { 
       result[0] = 0; 
       result[1] = 0; 
       result[2] = 0; 
     } 
     else { 
       result[0] = (x * Y) / y; 
       result[1] = Y; 
       result[2] = ((1 - x - y) * Y) / y; 
     } 
     return result; 
   } 

   /** 
    * @param xyY 
    * @return XYZ values 
    */ 
   public double[] xyYtoXYZ(double[] xyY) { 
     return xyYtoXYZ(xyY[0], xyY[1], xyY[2]); 
   } 

   /** 
    * Convert XYZ to LAB. 
    * @param X 
    * @param Y 
    * @param Z 
    * @return Lab values 
    */ 
   public double[] XYZtoLAB(double X, double Y, double Z) { 

     double x = X / D65[0]; 
     double y = Y / D65[1]; 
     double z = Z / D65[2]; 

     if (x > 0.008856) { 
       x = Math.pow(x, 1.0 / 3.0); 
     } 
     else { 
       x = (7.787 * x) + (16.0 / 116.0); 
     } 
     if (y > 0.008856) { 
       y = Math.pow(y, 1.0 / 3.0); 
     } 
     else { 
       y = (7.787 * y) + (16.0 / 116.0); 
     } 
     if (z > 0.008856) { 
       z = Math.pow(z, 1.0 / 3.0); 
     } 
     else { 
       z = (7.787 * z) + (16.0 / 116.0); 
     } 

     double[] result = new double[3]; 

     result[0] = (116.0 * y) - 16.0; 
     result[1] = 500.0 * (x - y); 
     result[2] = 200.0 * (y - z); 

     return result; 
   } 

   /** 
    * Convert XYZ to LAB. 
    * @param XYZ 
    * @return Lab values 
    */ 
   public double[] XYZtoLAB(double[] XYZ) { 
     return XYZtoLAB(XYZ[0], XYZ[1], XYZ[2]); 
   } 

   /** 
    * Convert XYZ to RGB. 
    * @param X 
    * @param Y 
    * @param Z 
    * @return RGB in int array. 
    */ 
   public int[] XYZtoRGB(double X, double Y, double Z) { 
     int[] result = new int[3]; 

     double x = X / 100.0; 
     double y = Y / 100.0; 
     double z = Z / 100.0; 

     // [r g b] = [X Y Z][Mi] 
     double r = (x * Mi[0][0]) + (y * Mi[1][0]) + (z * Mi[2][0]); 
     double g = (x * Mi[0][1]) + (y * Mi[1][1]) + (z * Mi[2][1]); 
     double b = (x * Mi[0][2]) + (y * Mi[1][2]) + (z * Mi[2][2]); 

     // assume sRGB 
     if (r > 0.0031308) { 
       r = ((1.055 * Math.pow(r, 1.0 / 2.4)) - 0.055); 
     } 
     else { 
       r = (r * 12.92); 
     } 
     if (g > 0.0031308) { 
       g = ((1.055 * Math.pow(g, 1.0 / 2.4)) - 0.055); 
     } 
     else { 
       g = (g * 12.92); 
     } 
     if (b > 0.0031308) { 
       b = ((1.055 * Math.pow(b, 1.0 / 2.4)) - 0.055); 
     } 
     else { 
       b = (b * 12.92); 
     } 

     r = (r < 0) ? 0 : r; 
     g = (g < 0) ? 0 : g; 
     b = (b < 0) ? 0 : b; 

     // convert 0..1 into 0..255 
     result[0] = (int) Math.round(r * 255); 
     result[1] = (int) Math.round(g * 255); 
     result[2] = (int) Math.round(b * 255); 

     return result; 
   } 

   /** 
    * Convert XYZ to RGB 
    * @param XYZ in a double array. 
    * @return RGB in int array. 
    */ 
   public int[] XYZtoRGB(double[] XYZ) { 
     return XYZtoRGB(XYZ[0], XYZ[1], XYZ[2]); 
   } 

   /** 
    * @param X 
    * @param Y 
    * @param Z 
    * @return xyY values 
    */ 
   public double[] XYZtoxyY(double X, double Y, double Z) { 
     double[] result = new double[3]; 
     if ((X + Y + Z) == 0) { 
       result[0] = D65[0]; 
       result[1] = D65[1]; 
       result[2] = D65[2]; 
     } 
     else { 
       result[0] = X / (X + Y + Z); 
       result[1] = Y / (X + Y + Z); 
       result[2] = Y; 
     } 
     return result; 
   } 
   /** 
    * @param XYZ 
    * @return xyY values 
    */ 
   public double[] XYZtoxyY(double[] XYZ) { 
     return XYZtoxyY(XYZ[0], XYZ[1], XYZ[2]); 
   } 

}

