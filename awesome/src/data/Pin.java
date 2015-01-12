/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 04.12.2014
 */
package data;

/** Ein Pin, der einen Spieler halten kann.
 */
public class Pin 
{
	/** Spieler dem der Pin gehoert. */
	private Spieler spieler;
	
	/** Erstellt einen Pin fuer einen Spieler.
	 * @param spieler Spieler dem der Pin gehoert. nicht null.
	 */
	public Pin(final Spieler spieler)
	{
		this.spieler = spieler;
	}

	/** Gibt den Spieler des Pin zurueck.
	 * @return Spieler des Pin.
	 */
	public Spieler getSpieler()
	{
		return spieler;
	}
}
