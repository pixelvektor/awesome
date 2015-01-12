package test;


import java.awt.Color;

import data.Kasten;
import data.Spieler;

public class KastGew
{
	public static void main(String[] args)
	{
		new KastGew();
	}
 public KastGew()
 {
	 String name;
	 //String name2;
	 Color farbe;
		
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
			
			//name2 = "Klaus";
			spieler[0] = new Spieler(name,farbe);
			//spieler[1] = new Spieler(name2);
			System.out.println("Name Spieler 1: " + spieler[0].getName());
			//System.out.println("Name Spieler 2: " + spieler[1].getName());
	spieler[0].setWuerfelErgebnis(3);
	System.out.println(spieler[0].getWuerfelErgebnis());
	spieler[0].pinSetzen(kaesten,0,0);
	spieler[0].setWuerfelErgebnis(3);
	System.out.println(spieler[0].getWuerfelErgebnis());
	spieler[0].pinSetzen(kaesten,1,0);
	spieler[0].setWuerfelErgebnis(3);
	System.out.println(spieler[0].getWuerfelErgebnis());
	spieler[0].pinSetzen(kaesten,2,0);
	
 }
}
