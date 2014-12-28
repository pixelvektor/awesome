/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 12.12.2014
 */
package view;

import data.Kasten;
import data.Spieler;

/**
 * @author fabian
 *
 */
public interface ContractView
{
	/**
	 * Loest die Ausgabe des Spiels aus.
	 * @param kaesten - Das Spielfeld
	 * @param spieler - Die Spieler
	 */
	public void show(Kasten[] kaesten, Spieler[] spieler);
}
