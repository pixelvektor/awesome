/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 04.12.2014
 */
package data;

import control.Spiel;
import control.Wuerfel;
/**
 * @author
 *
 */
public class Spieler 
{
	private final String name;
	private int punkte;
	private int wuerfelErgebnis;
	
	/**
	 * @param name
	 */
	public Spieler(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
	
	public void pinSetzen()
	{
		//TODO
	}
	
	public void pinLoeschen()
	{
		//TODO
	}
	
	public void erhoehePunkte()
	{
		//TODO
	}
	
	public void verringerePunkte()
	{
		//TODO STUFF
	}
	
	public int getPunkte()
	{
		return punkte;
	}
	
	public int wuerfeln()
	{
		wuerfelErgebnis = Spiel.generateRandoms(2, 12);
		
		return wuerfelErgebnis;
	}
}
