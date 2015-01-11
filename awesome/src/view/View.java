/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 04.12.2014
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import data.Kasten;
import data.Pin;
import data.Spieler;

/**
 * @author 
 *
 */
public class View implements ContainerListener
{
	private CustomButton[] buttons = new CustomButton[81];	// Dieses Array haelt alle Buttons fuer das Spielfeld.
	private JLabel lblPlayerName = new JLabel();
	private JLabel lblErgebnisAusgabe = new JLabel();
	private JLabel lbl_player1Points = new JLabel();
	private JLabel lbl_player2Points = new JLabel();
	private JFrame frame;
	private ActionListener buttonListener;					// Der ActionListener fuer die Buttons auf dem Spielfeld.
	private boolean repeat;
	
	/**
	 * Initialisiert das Hauptfenster und zeigt den Dialog fuer die Namenseingabe an.
	 * @param kaesten - Das Spielfeld.
	 * @param spieler - Die Spieler.
	 * @param buttonListener - Der ActionListener fuer die Buttons des Spielfeldes.
	 * @param restartListener - Der ActionListener fuer den Neustart-Button.
	 */
	public void show(final Kasten[] kaesten, final Spieler[] spieler, final ActionListener buttonListener, final ActionListener restartListener)
	{
		this.buttonListener = buttonListener;
		
		frame = new JFrame();	// Das Fenster selbst
		Container contentPane = frame.getContentPane();
		Container fieldPane = new Container();	// Die ContentPane fuer das Spielfeld (evtl. nochmal ueberdenken).
		Container[] kastenContainer = new Container[9];	// Ein Container fuer die einzelnen Kaesten, damit diese richtig angeordnet werden.
		
		new NameDialog(frame, spieler);		// Der EingabeDialog wird als modaler Dialog aufgerufen.
			
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();	// Die Aufloesung des Bildschirms wird erfasst.
		
		int width = 720;	// Fensterbreite
		int height = 560;	// Fensterhoehe
		int x = (screensize.width / 2) - (width / 2); 		// Fenster horizontal mittig ausrichten.
		int y = (screensize.height / 2) - (height / 2);		// Fenster vertikal mittig ausrichten.
		
		frame.setTitle("AWESOME");				// Fenstertitel angegeben.
		frame.setBounds(x, y, width, height);	// Fenstergroesse und Position festlegen.
		frame.setResizable(false);		// Das Fenster kann nicht vom Benutzer skaliert werden.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// Das Fenster wird mit einem Klick auf das X geschlossen.
		
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(90, 90, 90));
		
		JButton closeButton = new JButton("Beenden");
		JButton restartButton = new JButton("Neustart");
		JLabel lbl_wuerfelErgebnis = new JLabel();
		JLabel lbl_activePlayer = new JLabel();
		
		closeButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);		// Wird der Beenden-Button geklickt, wird das Programm beendet.
				
			}
		});
		
		restartButton.addActionListener(restartListener);
		
		closeButton.setBounds(550, 472, 130, 35);
		restartButton.setBounds(550, 420, 130, 35);
		lbl_wuerfelErgebnis.setBounds(550, 275, 130, 35);
		lbl_wuerfelErgebnis.setText("Würfelergebnis:");
		lbl_wuerfelErgebnis.setHorizontalAlignment(JLabel.CENTER);
		lblErgebnisAusgabe.setBounds(550, 295, 130, 35);
		lblErgebnisAusgabe.setText("4");
		lblErgebnisAusgabe.setHorizontalAlignment(JLabel.CENTER);
		lbl_activePlayer.setBounds(550, 200, 130, 35);
		lbl_activePlayer.setText("Aktueller Spieler:");
		lbl_activePlayer.setHorizontalAlignment(JLabel.CENTER);
		lblPlayerName.setBounds(550, 220, 130, 35);
		lblPlayerName.setText("Spieler 1");
		lblPlayerName.setHorizontalAlignment(JLabel.CENTER);
		lbl_player1Points.setBounds(550, 150, 65, 35);
		lbl_player1Points.setText("0");
		lbl_player1Points.setHorizontalAlignment(JLabel.CENTER);
		lbl_player2Points.setBounds(615, 150, 65, 35);
		lbl_player2Points.setText("0");
		lbl_player2Points.setHorizontalAlignment(JLabel.CENTER);
		
		fieldPane.setLayout(new GridLayout(3, 3, 10, 10));	// Das Layout fuer das gesamte Spielfeld.
		fieldPane.setBackground(new Color(176, 176, 176));	// Hintergrundfarbe einstellen.
		fieldPane.setBounds(15, 15, 500, 500);
		
		// Die Komponenten werden der ContenPane hinzugefuegt.
		contentPane.add(lbl_player1Points);
		contentPane.add(lbl_player2Points);
		contentPane.add(lblPlayerName);
		contentPane.add(lbl_activePlayer);
		contentPane.add(lbl_wuerfelErgebnis);
		contentPane.add(lblErgebnisAusgabe);
		contentPane.add(closeButton);
		contentPane.add(restartButton);
		contentPane.add(fieldPane);
		
		for (int i = 0; i < 9; i++)		// Eine Schleife, um jeden der 9 Container zu fuellen.
		{
			kastenContainer[i] = new Container();			// Ein neuer Container wird erstellt.
			kastenContainer[i].addContainerListener(this);	// Der Container wird einem Listener zugeordnet, der in dieser Klasse implementiert ist.
			fillContainer(kastenContainer[i], kaesten[i], i);
			kastenContainer[i].setLayout(new GridLayout(3,3,5,5));	// Das Layout fuer einen einzelnen Kasten.
			
			fieldPane.add(kastenContainer[i]);	// Der Kasten wird dem Spielfeld hinzugefuegt.
		}
		
		frame.setVisible(true);	// Fenster anzeigen.
	}
	
	public boolean shouldRepeat() {
		return repeat;
	}
	
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}
	
	/** Fuellt einen Container mit 9 Buttons und den entsprechenden Feldnummern
	 * 
	 * @param c - Der Container, der mit Buttons gefuellt werden soll. 
	 * @param k - Der Kasten, aus dem die Feldnummern geholt werden sollen.
	 * @param kastenIndex 
	 */
	private void fillContainer(final Container c, Kasten kasten, int kastenIndex)
	{
		int multiplikator = kasten.getKastenNummer() - 3;	// 
		int offset = multiplikator * 9;
		
		for (int x = 0; x < 9; x++)		// Diese Schleife durchlaeuft den gesamten Kasten.
		{
			buttons[x + offset] = new CustomButton(kasten.getFelder()[x], kastenIndex);	// Es wird ein neuer Button erzeugt und die Feldnummer aus dem Kasten geholt.
			c.add(buttons[x + offset]);	// Der neue Button wird dem Container hinzugefuegt.
		}
	}
	
	/** Das ist eine Methode die fuer den ContainerListener implementiert werden muss.
	 * 
	 * Diese wird aufgerufen, wenn eine Komponente zum Container hinzugefuegt wird.
	 * Ist diese Komponente ein Button, wird ihm ein ActionListener zugeordnet.
	 * @param e
	 */
	@Override
	public void componentAdded(ContainerEvent e)
	{
		Component c = e.getChild();		// Aus dem ContainerEvent wird die Komponente herausgelesen.
		
        if (c instanceof JButton)	// Wenn diese Kompontente ein JButton ist...
        {
    		JButton b = (JButton) c;	// ...wird die Komponente zu einem JButton gecastet...
    		b.addActionListener(buttonListener);	// ...um einen ActionListener zugewiesen bekommen zu koennen.
        }
	}

	@Override
	public void componentRemoved(ContainerEvent e)
	{
		Component c = e.getChild();
		
        if (c instanceof JButton)
        {
        	JButton b = (JButton) c;
        	b.removeActionListener(buttonListener);
        }
	}
	
	/**
	 * Das Aussehen der Buttons wird aktualisiert.
	 * Nicht moegliche Felder werden deaktiviert, mögliche Felder werden hervorgehoben.
	 */
	public void updateButtons()
	{
		for (CustomButton button : buttons)		// Fuer alle Buttons wird die Methode highlightButton() aufgerufen.
		{
			button.highlightButton();
		}
	}
	
	/**
	 * Der aktuelle Punktestand der Spieler wird mit der View synchronisiert.
	 * @param spieler - Die beiden Spieler
	 */
	public void updatePoints(final Spieler[] spieler)
	{
		lbl_player1Points.setText("" + spieler[0].getPunkte());
		lbl_player2Points.setText("" + spieler[1].getPunkte());
	}
	
	/**
	 * Es wird geprueft, ob alle Buttons des Spielfeldes deaktiviert sind.
	 * @return Gibt true zurueck, wenn alle Buttons inaktiv sind.
	 */
	public boolean allInactive()
	{
		boolean inactive = true;		// Der Rueckgabewert wird mit true initialisiert.
		for (CustomButton b : buttons)
		{
			if (b.isEnabled())		// Sobald ein Button aktiv ist...
			{
				inactive = false;	// ...wird der Rueckgabewert auf false gesetzt...
				break;				// ...und die Schleife wird verlassen.
			}
		}
		return inactive;
	}
	
	/**
	 * Der Text des PlayerLabels wird auf den Namen des aktuellen Spieler gesetzt.
	 * @param spielerName - Der Name des aktuellen Spielers.
	 */
	public void setPlayerLabel(final String spielerName)
	{
		lblPlayerName.setText(spielerName);
	}
	
	/**
	 * Der Text des WuerfelLabels wird auf die aktuell gewuerfelte Zahl gesetzt.
	 * @param wuerfelErgebnis - Das aktuelle wuerfelErgebnis.
	 */
	public void setWuerfelLabel(final int wuerfelErgebnis)
	{
		lblErgebnisAusgabe.setText("" + wuerfelErgebnis);
	}
	
	/**
	 * Alle Buttons in einem gewonnenen Kasten werden mit der Farbe des Spielers gefuellt, der ihn gewonnen hat. 
	 * @param kastenIndex - Der Index des gewonnenen Kastens.
	 * @param color - Die Farbe des Spieler.
	 */
	public void fuelleKasten(final int kastenIndex, final Color color)
	{
		int startValue = kastenIndex * 9;	// Als Startwert wird der kastenIndex mit der Anzahl der Buttons im Kasten multipliziert.
											// Da kastenIndex die Werte 0 bis 8 annehmen kann ergibt sich hierbei fuer startValue
											// als minimaler Wert 0 und als maximaler Wert 72.
		
		for (int x = startValue; x < (startValue + 9); x++)	// Eine Schleife vom Startwert an ueber die 8 naechsten Felder 
		{
			buttons[x].setBackground(color);	// Die Farbe des Buttons wird auf die Spielerfarbe gesetzt.
			buttons[x].setOpaque(true);			// Dieser Wert muss auf true gesetzt werden, sonst gibt es Anzeigeprobleme auf MacOS
		}
	}
	
	/** Fuellen aller Felder mit der Farbe des aktuellen Spielers.
	 * Wird bei einem Gewinn benoetigt.
	 * @param spieler Aktueller Spieler.
	 */
	public void fillAllButtons(final Spieler spieler)
	{
		for (CustomButton b : buttons)
		{
			b.setOpaque(true);
			b.setEnabled(false);
			b.setBackground(spieler.getColor());
			b.getFeld().setPin(new Pin(spieler));
			b.getFeld().setHighlight(false);
			b.highlightButton();
		}
		new WinDialog(frame, this, spieler);
	}
	
	/** Schliessen des Frame.
	 * 
	 */
	public void closeWindow() {
		frame.setVisible(false);
		frame.dispose();
	}
}