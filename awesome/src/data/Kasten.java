/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 10.12.2014
 */
package data;

import java.util.ArrayList;
import java.util.Collections;
/**
 * @author
 *
 */
public class Kasten
{
	/** 9 Felder pro Kasten. */
	private Feld[] felder = new Feld[9];
	/** Die ID-Nummer des Kastens zur leichteren Adressierung. */
	private int kastenNummer;
	
	public Kasten(int kastenNummer)
	{
		this.kastenNummer = kastenNummer;
	}
	
	public Feld[] getFelder()
	{
		return felder;
	}
	
	public void setFelder(Feld[] felder)
	{
		if (felder.length == 9)
		{
			this.felder = felder;
		}
	}
	/**
	 * Erzeugt ein Feld[] mit Zufallszahlen und gibt es zurueck
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
				feld[y] = new Feld(shuffleListe.get(y));
		}
		
		for (int z = 0; z < 9; z++)
		{
			System.out.println("Feld " + z + ": " + feld[z].getFeldNummer());
		}
		System.out.println();
		
		return feld;
	}
	
	public int getKastenNummer()
	{
		return kastenNummer;
	}
}
