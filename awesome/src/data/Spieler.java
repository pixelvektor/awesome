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
		/*
		  getFeld[]
		  getWuerfel
		  pruefeErgebnis
		     wenn (Ergebnis=2)
		      dann oeffne pinLoeschen()
		     alles andere 
		       bieteFelderAn
		       übergebeAusgewaehltesFeldAnFeld
		  */
	}
	
	public void pinLoeschen()
	{
		/*
		  bieteFelderZumLoeschenAn
		  uebergebeAusgewaehltesFeldAnFeld
		 */
	}
	
	public void erhoehePunkte()
	{
		/*
		  bei jedem gesetzten Pin Counter+1
		  wenn Kasten gewonnen 
		       dann entferne gegnerische Pins
		            Counter+dazugewonneneFelder
		 */
	}
	
	public void verringerePunkte()
	{
		/*
		  wenn (pin geloescht)
		   dann Counter-1
		 */
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
