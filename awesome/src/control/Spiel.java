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
import data.Kasten;
import data.Spieler;

/**
 * Das Spiel selbst
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
	private View view;
	/** true solange das Spiel laeuft. */
	private boolean isRunning = true;
	
	/**
	 * Erstellt ein Spiel mit einem Spielfeld.
	 */
	public Spiel(final View view)
	{
		this.view = view;
		
		initGame();
	}
	
	/**
	 * Initialisiert das Spielfeld, zeigt danach das Hauptfenster an und startet das Spiel.
	 */
	private void initGame()
	{
		for (int x = 0; x < 9; x++)
		{
			kaesten[x] = new Kasten(x+3);	// Erzeugt die 9 Kaesten mit den jeweiligen Nummern von 3 bis 11
			kaesten[x].setFelder(kaesten[x].erzeugeFeld());	// Erzeugt die Felder in den Kaesten
		}
		
		view.show(kaesten, spieler, new ButtonListener(), new RestartListener());	// Das Fenster wird dargestellt und bekommt das Spielfeld als Parameter mit.
		
		gameStart();	// Das Spiel wird gestartet.
	}
	
	/**
	 * Bestimmt zufaellig, welcher Spieler beginnen soll und startet danach den ersten Zug.
	 */
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
	
	/**
	 * Startet den Zug, wuerfelt fuer den aktuellen Spieler und bietet ihm die moeglichen Felder an.
	 * @param kaesten - Das Spielfeld
	 */
	private void startRound(final Kasten[] kaesten)
	{
		if (!isRunning)		// Wenn ein Spieler gewonnen hat, dann wird gameEnd() aufgerufen.
			gameEnd();
		else
		{
			do 
			{
				view.setPlayerLabel(activePlayer);
				System.out.println("\r\n" + activePlayer.getName() + " wuerfelt.");
				
				activePlayer.wuerfeln();	// Der derzeit aktive Spieler wuerfelt.
				
				view.setWuerfelLabel(activePlayer.getWuerfelErgebnis());
				System.out.println("Ergebnis: " + activePlayer.getWuerfelErgebnis() + "\r\n");
					
				if (activePlayer.getWuerfelErgebnis() != 2)		// Wenn das Wuerfelergebnis nicht 2 ist...
					activePlayer.bieteFelderAn(kaesten);		// ...werden dem Spieler Felder angeboten, in die er setzen kann...
				else
					activePlayer.bieteLoeschFelderAn(kaesten);	// ...ansonsten werden Felder zum loeschen angeboten. 
				
				view.updateButtons();	// Die Buttons in der View werden aktualisiert, sie entsprechend hervorzuheben.
			} 
			while (view.allInactive());		// Die Schleife wird solange wiederholt, bis Felder zum setzen vorhanden sind.
		}
	}
	
	/**
	 * Fuellt alle Buttons mit der Farbe des Gewinners und ruft ein Dialogfenster auf.
	 */
	private void gameEnd()
	{
		System.out.println("Das Spiel ist zu Ende");
		view.fillAllButtons(activePlayer);
		if(view.shouldRepeat())
			restartGame();
	}
	
	/**
	 * Startet das Spiel neu.
	 */
	private void restartGame() {
		isRunning = true;			// Die Variable wird zurueckgesetzt, um nach dem Neustart wieder in die Spielschleife zu kommen
		view.closeWindow();			
		kaesten = new Kasten[9];	// Erstellen der neuen Kaesten fuer ein neues Spiel
		initGame();
	}
	
	/**
	 * Der Listener fuer einen Button mit dem das Spiel neu gestartet werden kann.
	 */
	class RestartListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			restartGame();
		}
		
	}
	
	/**
	 * Der Listener fuer alle Buttons die zum Spielfeld gehoeren.
	 */
	class ButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			CustomButton button = (CustomButton) e.getSource();
			
			if (activePlayer.getWuerfelErgebnis() != 2)		// Wenn keine 2 gewuerfelt wurde wird ein Pin gesetzt.
			{
				button.setBackground(activePlayer.getColor());	// Der Button bekommt die Farbe des Spielers.
				button.setOpaque(true);		// Muss auf true gesetzt werden, um Anzeigefehler auf MacOS zu verhindern.
				
				
				isRunning = !activePlayer.pinSetzen(kaesten, button.getFeld().getFeldIndex(), button.getKastenIndex());
				
				if (kaesten[button.getKastenIndex()].getSpieler() != null)
				{
					view.fuelleKasten(button.getKastenIndex(), activePlayer.getColor());
				}
			}
			else
			{
				button.setDefaultBackground();
				activePlayer.pinLoeschen(kaesten, button.getFeld().getFeldIndex(), button.getKastenIndex());
			}
			
			if (isRunning)
			{
				if ((activePlayer.equals(spieler[0])) && (activePlayer.getWuerfelErgebnis() != 2))	/* Hier werden die Spieler gewechselt und geprueft, 
																									   ob ein Spieler nochmal an der Reihe ist.*/
					activePlayer = spieler[1];
				else if ((activePlayer.equals(spieler[1])) && (activePlayer.getWuerfelErgebnis() != 2))
					activePlayer = spieler[0];
				else
					System.out.println(activePlayer.getName() + " ist nochmal an der Reihe.");
			}
			
			view.updatePoints(spieler);
			
			startRound(kaesten);
		}	
	}
}
