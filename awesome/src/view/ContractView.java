/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 04.12.2014
 */
package view;

import java.awt.Color;
import java.awt.event.ActionListener;

import data.Kasten;
import data.Spieler;

/** Interface der Klasse View.
 * @author
 *
 */
public interface ContractView
{
	/** Initialisiert das Hauptfenster und zeigt den Dialog fuer die Namenseingabe an.
	 * @param kaesten Das Spielfeld.
	 * @param spieler Die Spieler.
	 * @param buttonListener Der ActionListener fuer die Buttons des Spielfeldes.
	 * @param restartListener Der ActionListener fuer den Neustart-Button.
	 */
	public void show(Kasten[] kaesten, Spieler[] spieler, ActionListener buttonListener, ActionListener restartListener);
	
	/** Gibt zurueck ob ein neues Spiel gewuenscht ist.
	 * @return true wenn ein neues Spiel gewuenscht ist. Sonst false.
	 */
	public boolean shouldRepeat();
	
	/** Setzt den Wunsch auf ein neues Spiel.
	 * @param repeat true wenn ein neues Spiel gewuenscht ist. Sonst false.
	 */
	public void setRepeat(boolean repeat);
	
	/** Das Aussehen der Buttons wird aktualisiert.
	 * Nicht moegliche Felder werden deaktiviert, mögliche Felder werden hervorgehoben.
	 */
	public void updateButtons();
	
	/**
	 * Der aktuelle Punktestand der Spieler wird mit der View synchronisiert.
	 * @param spieler Die beiden Spieler
	 */
	public void updatePoints(final Spieler[] spieler);

	/**
	 * Es wird geprueft, ob alle Buttons des Spielfeldes deaktiviert sind.
	 * @return Gibt true zurueck, wenn alle Buttons inaktiv sind.
	 */
	public boolean allInactive();
	
	/**
	 * Der Text des PlayerLabels wird auf den Namen des aktuellen Spieler gesetzt.
	 * @param spieler - Der aktuelle Spieler.
	 */
	public void setPlayerLabel(final Spieler spieler);

	/**
	 * Der Text des WuerfelLabels wird auf die aktuell gewuerfelte Zahl gesetzt.
	 * @param wuerfelErgebnis - Das aktuelle wuerfelErgebnis.
	 */
	public void setWuerfelLabel(final int wuerfelErgebnis);

	/**
	 * Alle Buttons in einem gewonnenen Kasten werden mit der Farbe des Spielers gefuellt, der ihn gewonnen hat. 
	 * @param kastenIndex - Der Index des gewonnenen Kastens.
	 * @param color - Die Farbe des Spieler.
	 */
	public void fuelleKasten(final int kastenIndex, final Color color);

	/** Fuellen aller Felder mit der Farbe des aktuellen Spielers.
	 * Wird bei einem Gewinn benoetigt.
	 * @param spieler Aktueller Spieler.
	 */
	public void fillAllButtons(Spieler spieler);

	/** Schliessen des Frame.
	 */
	public void closeWindow();
}