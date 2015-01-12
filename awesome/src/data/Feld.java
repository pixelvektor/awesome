/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 10.12.2014
 */
package data;
/**
 * Ein Feld, dass der Spieler gewinnnen kann.
 * Drei Felder in Reihe lassen einen Kasten gewinnen.
 * @author
 *
 */
public class Feld
{
	/** Pin der in dem Feld gesetzt wird. */
	private Pin pin;
	/** Spiel- / Wuerfelnummer des Feldes. */
	private int feldNummer;
	/** Indes des Feldes. */
	private int feldIndex;
	/** Markierung als verfuegbar zum setzen wenn true. */
	private boolean highlight = false;
	/** Markierung als verfuegbar zum loeschen wenn true. */
	private boolean highlightToDelete = false;
	
	/** Erstellt ein Feld.
	 * @param feldNummer Spielnummer des Feldes. Nicht null.
	 * @param feldIndex Index des Feldes. Nicht null.
	 */
	public Feld(int feldNummer, int feldIndex)
	{
		this.feldNummer = feldNummer;
		this.feldIndex = feldIndex;
	}
	
	/** Gibt den Pin des Feldes zurueck.
	 * @return Gibt den Pin zurueck.
	 */
	public Pin getPin()
	{
		return pin;
	}
	
	/** Setzt den Pin des Feldes.
	 * @param pin Zu setzender Pin.
	 */
	public void setPin(final Pin pin)
	{
		this.pin = pin;
	}
	
	/** Gibt die Spielnummer des Feldes zurueck.
	 * @return Spielnummer des Feldes.
	 */
	public int getFeldNummer()
	{
		return feldNummer;
	}
	
	/** Gibt den Index des Feldes zurueck.
	 * @return Index des Feldes.
	 */
	public int getFeldIndex()
	{
		return feldIndex;
	}
	
	/** Gibt zurueck ob ein Feld besetzbar ist.
	 * @return true wenn besetzbar.
	 */
	public boolean getHighlight()
	{
		return highlight;
	}

	/** Setzt ein Feld als besetzbar.
	 * @param highlight true wenn besetzbar.
	 */
	public void setHighlight(final boolean highlight)
	{
		this.highlight = highlight;
	}

	/** Gibt zurueck ob ein Feld loeschbar ist.
	 * @return true wenn loeschbar.
	 */
	public boolean getHighlightToDelete()
	{
		return highlightToDelete;
	}

	/** Setzt ein Feld als loeschbar.
	 * @param highlightToDelete true wenn loeschbar.
	 */
	public void setHighlightToDelete(final boolean highlightToDelete)
	{
		this.highlightToDelete = highlightToDelete;
	}
}
