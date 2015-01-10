/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 04.12.2014
 */
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.CustomButton;
import view.View;
import data.Feld;
import data.Kasten;
import data.Spieler;

/**
 * @author 
 *
 */
public class Spiel 
{	
	/** Spielfeld mit 9 Kaesten. */
	private Kasten[] kaesten = new Kasten[9];
	/** Die Spieler eines Spiels. */
	private final Spieler[] spieler = new Spieler[2];
	/** Fuer den Zug aktiver Spieler. */
	private Spieler activePlayer;
	/** Speichert die View. */
	private final View view;
	
	/**
	 * Erstellt ein Spiel mit einem Spielfeld.
	 */
	public Spiel(final View view)
	{
		this.view = view;
		
		for (int x = 3; x < 12; x++)
		{
			kaesten[x-3] = new Kasten(x);	// Erzeugt die 9 Kaesten mit den jeweiligen Nummern von 3 bis 11
			kaesten[x-3].setFelder(kaesten[x-3].erzeugeFeld());	// Erzeugt die Felder in den Kaesten
		}
		
		view.show(kaesten, spieler, new ButtonListener());	// Das Fenster wird dargestellt und bekommt das Spielfeld als Parameter mit.
		
		gameStart();	// Das Spiel wird gestartet.
	}
	
	private void gameStart()
	{
		
		System.out.println("Das Spiel beginnt!\r\nEs wird jetzt ausgewuerfelt welcher Spieler anfaengt.");
		
		do	// Beide Spieler wuerfeln solange, bis beide unterschiedliche Ergebnisse haben.
		{
			spieler[0].wuerfeln();
			spieler[1].wuerfeln();
		} while (spieler[0].getWuerfelErgebnis() == spieler[1].getWuerfelErgebnis());
		
		System.out.println(spieler[0].getName() + ": " + spieler[0].getWuerfelErgebnis() + ", " + spieler[1].getName() + ": " + spieler[1].getWuerfelErgebnis());
		
		// Umbau fuer die View
		activePlayer = spieler[0].getWuerfelErgebnis() > spieler[1].getWuerfelErgebnis() ? spieler[0] : spieler[1];
		System.out.println(activePlayer.getName() + " faengt an!");
		
		startRound(kaesten);
	}

	private void startRound(Kasten[] kaesten)
	{
		do 
		{
			System.out.println("\r\n" + activePlayer.getName() + " wuerfelt.");
			
			activePlayer.wuerfeln();	// Der derzeit aktive Spieler wuerfelt.
			
			System.out.println("Ergebnis: " + activePlayer.getWuerfelErgebnis() + "\r\n");
				
			activePlayer.bieteFelderAn(kaesten);
			
			view.updateButtons();
		} 
		while (view.allInactive());
	}
	
	private void placePin(Feld feld, int kastenIndex)
	{
		activePlayer.pinSetzen(kaesten, feld.getFeldIndex(), kastenIndex);
	}
	
	private void gameEnd()
	{
		
	}
	
	class ButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			CustomButton button = (CustomButton) e.getSource();
			
			button.setBackground(activePlayer.getColor());
			placePin(button.getFeld(), button.getKastenIndex());
			
			System.out.println(e.getActionCommand());
			
			boolean spielBeenden = false;	// Wird erst auf true gesetzt, wenn ein Spieler gewonnen hat.
			
			if ((activePlayer.equals(spieler[0])) && (activePlayer.getWuerfelErgebnis() != 2))	/* Hier werden die Spieler gewechselt und geprueft, 
																								   ob ein Spieler nochmal an der Reihe ist.*/
				activePlayer = spieler[1];
			else if ((activePlayer.equals(spieler[1])) && (activePlayer.getWuerfelErgebnis() != 2))
				activePlayer = spieler[0];
			else
				System.out.println(activePlayer.getName() + " ist nochmal an der Reihe.");
			
			startRound(kaesten);
		}
		
	}
}
