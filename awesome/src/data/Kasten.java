/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 10.12.2014
 */
package data;

import control.Spiel;
import java.util.*;
/**
 * @author
 *
 */
public class Kasten
{
	private Feld[] felder = new Feld[9];
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
	
	public Feld[] erzeugeFeld()
	{
		int random;	//in diese Variable kommt die Zufallszahl
		Feld[] feld = new Feld[9];	//die Rueckgabevariable
		ArrayList<Integer> pruefListe = new ArrayList<Integer>();	/* eine Liste, die nach und nach mit den
																	bereits verwendeten Zahlen gefaellt wird */
		
		feld[4] = new Feld(7);	//in die Mitte des Feldes wird die Zahl 7 geschrieben
		pruefListe.add(7);		//und danach zur Pruefliste hinzugefuegt
		
		for (int x = 0; x < feld.length; x++)		//diese Schleife durchlueuft das gesamte Feld
		{
			if (x != 4)
			{
				do
				{
					random = Spiel.generateRandoms(3, 11);	//hier wird eine neue Zufallszahl generiert...
				} while (pruefListe.contains(random));	//...immer, wenn die Zahl bereits in der Liste ist
				
				feld[x] = new Feld(random);		//Zufallszahl zum Feld hinzufuegen...
				pruefListe.add(random);			//...und auch zur Pruefliste hinzufuegen
				System.out.println(x + ".: " + random);
			}
			else 
			{
				System.out.println(x + ".: " + feld[x].getFeldNummer());
			}
		}
		System.out.println();
		
		return feld;
	}
	
	public int getKastenNummer()
	{
		return kastenNummer;
	}
}
