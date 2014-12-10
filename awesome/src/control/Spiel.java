/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 04.12.2014
 */
package control;

import data.Kasten;
import data.Pin;
import data.Spieler;

/**
 * @author 
 *
 */
public class Spiel 
{	
	private Kasten[] kaesten = new Kasten[9];
	private Spieler[] spieler = new Spieler[2];
	
	public void initialisiereSpiel()
	{
		for (int x = 3; x < 12; x++)
		{
			kaesten[x-3] = new Kasten(x);		//erzeugt die 9 kaesten mit den jeweiligen Nummern von 3 bis 11
			
			kaesten[x-3].setFelder(kaesten[x-3].erzeugeFeld());
			
		}
	}
	
	private void spielStarten()
	{
		
	}
	
	private void spielBeenden()
	{
		
	}
	
	public static int generateRandoms(int min, int max)
	{
		double random = (Math.random() * (max + 1 - min) + min);
		return (int) random;
	}
}
