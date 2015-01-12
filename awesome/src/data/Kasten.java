/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 10.12.2014
 */
package data;

import java.util.ArrayList;
import java.util.Collections;

/** Kasten, der gewonnen werden kann.
 * Drei Kaesten in Reihe fuer einen Spieler gewinnen das Spiel.
 * @author
 */
public class Kasten
{
	/** 9 Felder pro Kasten. */
	private Feld[] felder = new Feld[9];
	/** Die ID-Nummer des Kastens zur leichteren Adressierung. */
	private int kastenNummer;
	/** Diese Variable wird gesetzt sobald ein Spieler einen Kasten für sich entschieden hat. */
	private Spieler spieler = null;
	
	/** Erstellt einen Kasten.
	 * @param kastenNummer Nummer des Kastens im Spiel.
	 */
	public Kasten(int kastenNummer)
	{
		this.kastenNummer = kastenNummer;
	}
	
	/** Gibt die beinhalteten Felder des Kastens zurueck.
	 * @return Gibt die Felder zurueck.
	 */
	public Feld[] getFelder()
	{
		return felder;
	}
	
	/** Setzt die Felder eines Kastens.
	 * @param felder Felder gesetzt werden sollen. Muessen genau neun (9) sein.
	 */
	public void setFelder(final Feld[] felder)
	{
		if (felder.length == 9)
		{
			this.felder = felder;
		}
	}
	
	/** Gibt die Kastennummer zurueck.
	 * @return Die Kastennummer.
	 */
	public int getKastenNummer()
	{
		return kastenNummer;
	}

	/** Gibt den Spieler zurueck welchem der Kasten gehoert.
	 * @return Spieler, dem der Kasten gehoert.
	 */
	public Spieler getSpieler()
	{
		return spieler;
	}

	/** Setzt den Spieler der Kasten gewonnen hat.
	 * @param spieler Der Spieler, der den Kasten gewonnen hat.
	 */
	public void setSpieler(final Spieler spieler)
	{
		this.spieler = spieler;
	}
	
	/** Erzeugt ein Feld[] mit Zufallszahlen und gibt es zurueck.
	 */
	public Feld[] erzeugeFeld()
	{
		Feld[] feld = new Feld[9];	//die Rueckgabevariable
		ArrayList<Integer> shuffleListe = new ArrayList<Integer>();	/* eine Liste, die nach und nach mit den
																	bereits verwendeten Zahlen gefaellt wird */
		
		for (int x = 3; x < 12; x++)
		{
			if (x != 7)
				shuffleListe.add(x);
		}
		
		Collections.shuffle(shuffleListe);
		shuffleListe.add(4, 7);
		
		for (int y = 0; y < 9; y++)
		{
				feld[y] = new Feld(shuffleListe.get(y), y);
		}
		
		/*
		for (int z = 0; z < 9; z++)
		{
			System.out.println("Feld " + z + ": " + feld[z].getFeldNummer());
		}
		System.out.println();
		*/
		return feld;
	}
}
