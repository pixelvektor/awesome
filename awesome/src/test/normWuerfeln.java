package test;


import data.Kasten;
import data.Spieler;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class normWuerfeln
{
	public static void main(String[] args)
	{
		new normWuerfeln();
	}
 public normWuerfeln()
 {
	 String name;
	 Color farbe;
	 int zaehler = 0;
	 int kastenIndex = 0;
	 int feldIndex = 0;
	
	while(zaehler < 20){
		String eingabe = input("Geben sie die Zahl ein die sie testen wollen:");
		int tipp=Integer.parseInt(eingabe);
	 final Spieler[] spieler = new Spieler[2];
	  Kasten[] kaesten = new Kasten[9];
	  for (int x = 3; x < 12; x++)
		{
			System.out.println("Kasten " + (x-3) + ":");
			System.out.println("---------");
			
			kaesten[x-3] = new Kasten(x);	// Erzeugt die 9 Kaesten mit den jeweiligen Nummern von 3 bis 11
			kaesten[x-3].setFelder(kaesten[x-3].erzeugeFeld());	// Erzeugt die Felder in den Kaesten
		}
		    name = "Erika";
		    farbe = Color.RED;
			
			spieler[0] = new Spieler(name,farbe);
			
			System.out.println("Name Spieler 1: " + spieler[0].getName());
			
	spieler[0].setWuerfelErgebnis(tipp);
	System.out.println(spieler[0].getWuerfelErgebnis());
	spieler[0].pinSetzen(kaesten,kastenIndex,feldIndex);
	zaehler++;
	} 
 }
 public static String input(String ausgabe)
 {
     System.out.print(ausgabe);
     BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
     try 
     {
         return br.readLine();            
     } 
     catch (Exception e)
     {
         return "";
     }
 }
}
