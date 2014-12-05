/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 04.12.2014
 */
package data;

/**
 * @author
 *
 */
public class Pin {
	private Spieler spieler;
	private int feld;
	private int kasten;
	
	public Pin(Spieler spieler)
	{
		this.spieler = spieler;
	}

	public Spieler getSpieler()
	{
		return spieler;
	}

	public void setSpieler(Spieler spieler)
	{
		this.spieler = spieler;
	}
	
}
