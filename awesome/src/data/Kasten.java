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
	
	public Kasten()
	{
	}
	
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
		int random;
		Feld[] feld = new Feld[9];
		ArrayList<Integer> pruefListe = new ArrayList<Integer>();
		
		
		feld[4] = new Feld(7);
		pruefListe.add(7);
		
		for (int x = 0; x < 9; x++)
		{
			if (x != 4)
			{
				do
				{
					random = Spiel.generateRandoms(3, 11);
				}
				while (pruefListe.contains(random));
				
				feld[x] = new Feld(random);
				pruefListe.add(random);
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
