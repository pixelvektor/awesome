/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 04.12.2014
 */
package control;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import view.View;
import data.Kasten;
import data.Spieler;

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
			System.out.println("Kasten " + (x-3) + ":");
			System.out.println("---------");
			
			kaesten[x-3] = new Kasten(x);	// Erzeugt die 9 Kaesten mit den jeweiligen Nummern von 3 bis 11
			kaesten[x-3].setFelder(kaesten[x-3].erzeugeFeld());	// Erzeugt die Felder in den Kaesten
		}
		
		view.showWindow(kaesten);	// Das Fenster wird dargestellt und bekommt das Spielfeld als Parameter mit.
		
		for (int y = 0; y < spieler.length; y++) // Alle Spieler sollen einen Namen eingeben.
		{
			String eingabe;
			
			do
			{
				eingabe = input("Geben Sie bitte Ihren Namen ein Spieler " + (y+1) + ": ");
				
				if (!(eingabe.length() == 0))	// Eine leere Eingabe wird nicht akzeptiert.
				{
					spieler[y] = new Spieler(eingabe);	// Der Spieler wird erzeugt und bekommt den eingegebenen Namen als Parameter mit.
				}
				else
				{
					System.out.println("Sie muessen einen Namen eingeben!");	// Fehlerausgabe, wenn kein Name eingegeben wurde.
				}
			} while ((eingabe.length() == 0));	// Wenn ein Spieler keinen Namen eingegeben hat, dann wird er nochmal dazu aufgefordert.
			System.out.println();
		}
		
		System.out.println("Name Spieler 1: " + spieler[0].getName());	// Die Namen werden ausgegeben.
		System.out.println("Name Spieler 2: " + spieler[1].getName());
		
		System.out.println();
		
		gameStart();	// Das Spiel wird gestartet.
	}
	
	private void gameStart()
	{
		
		System.out.println("Das Spiel beginnt!\r\nEs wird jetzt ausgewuerfelt welcher Spieler anfaengt.");
		
		do	// Beide Spieler wuerfeln solange, bis beide unterschiedliche Ergebnisse haben.
		{
			spieler[0].wuerfeln();
			spieler[1].wuerfeln();
		} while (spieler[0].getWuerfelErgebnis() == spieler[1].getWuerfelErgebnis());
		
		System.out.println(spieler[0].getName() + ": " + spieler[0].getWuerfelErgebnis() + ", " + spieler[1].getName() + ": " + spieler[1].getWuerfelErgebnis());
		
		// Umbau fuer die View
		activePlayer = spieler[0].getWuerfelErgebnis() > spieler[1].getWuerfelErgebnis() ? spieler[0] : spieler[1];
		System.out.println(activePlayer.getName() + " faengt an!");
		
		// Testaufruf der View
		view.show();
		
		gameLoop();		// Die Hauptschleife des Spiels wird aufgerufen.
	}
	
	/** Die Hauptschleife des Spiels.
	 *  Hier laeuft der ueberwiegende Teil der Spiellogik ab.
	 */
	private void gameLoop()
	{
		
		boolean spielBeenden = false;	// Wird erst auf true gesetzt, wenn ein Spieler gewonnen hat.
		int loopCount = 0;				// Zaehlt die Schleifendurchlaeufe (nur zu Testzwecken)
		
		do
		{
			System.out.println("\r\n" + activePlayer.getName() + " wuerfelt.");
			
			activePlayer.wuerfeln();	// Der derzeit aktive Spieler wuerfelt.
			
			System.out.println("Ergebnis: " + activePlayer.getWuerfelErgebnis() + "\r\n");
			
			spielBeenden = activePlayer.pinSetzen(kaesten);	// Der derzeit aktive Spieler setzt einen Pin.
									
			if ((loopCount >= 3) && (activePlayer.getWuerfelErgebnis() != 2))
				spielBeenden = true;	// Das Spiel wird zu Testzwecken beendet, wenn jeder Spieler ein Zug gemacht hat.
			
			if (activePlayer.getWuerfelErgebnis() != 2)
				loopCount++;	// Ein Zaehler der zu Testzwecken die Durchlaeufe zaehlt und das Spiel beendet.
			
			if ((activePlayer.equals(spieler[0])) && (activePlayer.getWuerfelErgebnis() != 2))	/* Hier werden die Spieler gewechselt und geprueft, 
																								   ob ein Spieler nochmal an der Reihe ist.*/
				activePlayer = spieler[1];
			else if ((activePlayer.equals(spieler[1])) && (activePlayer.getWuerfelErgebnis() != 2))
				activePlayer = spieler[0];
			else
				System.out.println(activePlayer.getName() + " ist nochmal an der Reihe.");
			
			System.out.println("Durchlauf " + loopCount);
			
		} while (spielBeenden == false);	// Das Spiel laeuft, solange die Variable spielBeenden auf false steht.
		
		System.out.println("\r\nDas Spiel ist aus!");
	}
	
	private void gameEnd()
	{
		
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
