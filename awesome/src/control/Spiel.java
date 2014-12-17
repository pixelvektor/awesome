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
import view.View;

/**
 * @author 
 *
 */
public class Spiel 
{	
	/** Spielfeld mit 9 Kaesten. */
	private Kasten[] kaesten = new Kasten[9];
	/** Die Spieler eines Spiels. */
	private final Spieler[] spieler = new Spieler[2];
	/** Fuer den Zug aktiver Spieler. */
	private Spieler activePlayer;
	/** Speichert die View. */
	private final View view;
	
	/**
	 * Erstellt ein Spiel mit einem Spielfeld.
	 */
	public Spiel(final View view)
	{
		this.view = view;
		
		for (int x = 3; x < 12; x++)
		{
			kaesten[x-3] = new Kasten(x);	// Erzeugt die 9 Kaesten mit den jeweiligen Nummern von 3 bis 11
			kaesten[x-3].setFelder(kaesten[x-3].erzeugeFeld());	// Erzeugt die Felder in den Kaesten
			
		}
		
		for (int y = 0; y < spieler.length; y++)
		{
			String eingabe;
			
			do
			{
				eingabe = input("Geben Sie bitte Ihren Namen ein Spieler " + (y+1) + ": ");
				
				if (!(eingabe.length() == 0))
				{
					spieler[y] = new Spieler(eingabe);
				}
				else
				{
					System.out.println("Sie muessen einen Namen eingeben!");
				}
			} while ((eingabe.length() == 0));
			System.out.println();
		}
		
		System.out.println("Name Spieler 1: " + spieler[0].getName());
		System.out.println("Name Spieler 2: " + spieler[1].getName());
		
		System.out.println();
		
		gameStart();
	}
	
	private void gameStart()
	{
		int e1, e2;
		
		System.out.println("Das Spiel beginnt!\r\nEs wird jetzt ausgewuerfelt welcher Spieler anfaengt.");
		
		do
		{
			e1 = spieler[0].wuerfeln();
			e2 = spieler[1].wuerfeln();
		} while (e1 == e2);
		
		System.out.println(spieler[0].getName() + ": " + e1 + ", " + spieler[1].getName() + ": " + e2);
		
		// Umbau fuer die View
		activePlayer = e1 > e2 ? spieler[0] : spieler[1];
		System.out.println(activePlayer.getName() + " faengt an!");
		
		// Testaufruf der View
		view.show(activePlayer.getName(),activePlayer.wuerfeln());
	}
	
	private void gameEnd()
	{
		
	}
	
	public static int generateRandoms(int min, int max)
	{
		double random = (Math.random() * (max + 1 - min) + min);
		return (int) random;
	}
	
	private String input(String ausgabe)
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
