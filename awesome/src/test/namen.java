package test;


import java.awt.Color;

import data.Spieler;

public class namen
{
    String name;
    String name2;
    Color farbe;
    Color farbe2;
	public static void main(String[] args)
	{
		new namen();
	}
    public namen(){
        final Spieler[] spieler = new Spieler[2];
     
		name = "Erika";
		name2 = "Klaus";
		farbe = Color.RED;
		farbe2 = Color.BLUE;
		
		spieler[0] = new Spieler(name,farbe);
		spieler[1] = new Spieler(name2,farbe2);
		System.out.println("Name Spieler 1: " + spieler[0].getName());
		System.out.println("Name Spieler 2: " + spieler[1].getName());
		
	
		
		
    }
}
