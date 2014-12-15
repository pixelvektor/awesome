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
	/** Spielfeld mit 9 Kaesten. */
	private Kasten[] kaesten = new Kasten[9];
	/** Die Spieler eines Spiels. */
	private final Spieler[] spieler = new Spieler[2];
	/** Legt fest, welcher Spieler gerade an der Reihe ist. */
	private int aktiverSpieler = 0;
	
	/**
	 * Erstellt ein Spiel mit einem Spielfeld.
	 */
	public Spiel()
	{
		for (int x = 3; x < 12; x++)
		{
			System.out.println("Kasten " + (x-3) + ":");
			System.out.println("---------");
			
			kaesten[x-3] = new Kasten(x);	// Erzeugt die 9 Kaesten mit den jeweiligen Nummern von 3 bis 11
			kaesten[x-3].setFelder(kaesten[x-3].erzeugeFeld());	// Erzeugt die Felder in den Kaesten
		}
		
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
		System.out.println("Das Spiel beginnt!\r\nEs wird jetzt ausgewürfelt welcher Spieler anfängt.");
		
		do	// Beide Spieler wuerfeln solange, bis beide unterschiedliche Ergebnisse haben.
		{
			spieler[0].wuerfeln();
			spieler[1].wuerfeln();
		} while (spieler[0].getWuerfelErgebnis() == spieler[1].getWuerfelErgebnis());
		
		System.out.println(spieler[0].getName() + ": " + spieler[0].getWuerfelErgebnis() + ", " + spieler[1].getName() + ": " + spieler[1].getWuerfelErgebnis());
		
		if (spieler[0].getWuerfelErgebnis() > spieler[1].getWuerfelErgebnis())
		{
			aktiverSpieler = 0;	// Spieler 0 fängt an, wenn sein Ergebnis hoeher war.
			System.out.println(spieler[0].getName() + " fängt an!");
		}
		else
		{
			aktiverSpieler = 1;	// Spieler 1 fängt an, wenn sein Ergebnis hoeher war.
			System.out.println(spieler[1].getName() + " fängt an!");
		}
		
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
			System.out.println("\r\n" + spieler[aktiverSpieler].getName() + " würfelt.");
			
			spieler[aktiverSpieler].wuerfeln();	// Der derzeit aktive Spieler wuerfelt.
			
			System.out.println("Ergebnis: " + spieler[aktiverSpieler].getWuerfelErgebnis() + "\r\n");
			
			spieler[aktiverSpieler].pinSetzen(kaesten);	// Der derzeit aktive Spieler setzt einen Pin.
			
			if (aktiverSpieler == 0)	// Hier wird der Spieler gewechselt, nachdem sein Zug abschlossen ist.
				aktiverSpieler = 1;
			else
				aktiverSpieler = 0;
			
			if (loopCount >= 1)			// Das Spiel wird zu Testzwecken beendet, wenn jeder Spieler ein Zug gemacht hat.
				spielBeenden = true;
			
			loopCount++;
			
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
