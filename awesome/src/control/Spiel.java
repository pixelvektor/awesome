/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 04.12.2014
 */
package control;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
			kaesten[x-3] = new Kasten(x);	//erzeugt die 9 Kästen mit den jeweiligen Nummern von 3 bis 11
			
			kaesten[x-3].setFelder(kaesten[x-3].erzeugeFeld());	//erzeugt die Felder in den Kästen
			
		}
		
		for (int y = 0; y < spieler.length; y++)
		{
			spieler[y] = new Spieler();
			String eingabe;
			
			do
			{
				eingabe = input("Geben Sie bitte Ihren Namen ein Spieler " + (y+1) + ": ");
				
				if (!(eingabe.length() == 0))
				{
					spieler[y].setName(eingabe);
				}
				else
				{
					System.out.println("Sie müssen einen Namen eingeben!");
				}
			} while ((eingabe.length() == 0));
			System.out.println();
		}
		
		System.out.println("Name Spieler 1: " + spieler[0].getName());
		System.out.println("Name Spieler 2: " + spieler[1].getName());
		
		System.out.println();
		
		spielStarten();
	}
	
	private void spielStarten()
	{
		int e1, e2;
		
		System.out.println("Das Spiel beginnt!\r\nEs wird jetzt ausgewürfelt welcher Spieler anfängt.");
		
		do
		{
			e1 = spieler[0].wuerfeln();
			e2 = spieler[1].wuerfeln();
		} while (e1 == e2);
		
		System.out.println(spieler[0].getName() + ": " + e1 + ", " + spieler[1].getName() + ": " + e2);
		
		if (e1 > e2)
			System.out.println(spieler[0].getName() + " fängt an!");
		else
			System.out.println(spieler[1].getName() + " fängt an!");
	}
	
	private void spielBeenden()
	{
		
	}
	
	public static int generateRandoms(int min, int max)
	{
		double random = (Math.random() * (max + 1 - min) + min);
		return (int) random;
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
