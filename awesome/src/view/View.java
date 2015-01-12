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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import data.Kasten;
import data.Pin;
import data.Spieler;

/** Ist fuer die Darstellung des Spiels auf dem Bildschirm verantwortlich.
 * @author 
 *
 */
public class View implements ContainerListener, ContractView
{
	private CustomButton[] buttons = new CustomButton[81];	// Dieses Array haelt alle Buttons fuer das Spielfeld.
	private JLabel lbl_PlayerName = new JLabel();
	private JLabel lbl_ErgebnisAusgabe = new JLabel();
	private JLabel lbl_player1Points = new JLabel();
	private JLabel lbl_player2Points = new JLabel();
	private JFrame frame;
	private ActionListener buttonListener;					// Der ActionListener fuer die Buttons auf dem Spielfeld.
	private boolean repeat;
	
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
		contentPane.setBackground(new Color(80, 80, 80));
		
		JButton closeButton = new JButton("Beenden");
		JButton restartButton = new JButton("Neustart");
		JButton rulesButton = new JButton("Spielregeln");
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
		
		rulesButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				showRulesDialog();
			}
		});
		
		restartButton.addActionListener(restartListener);
		
		// Steuer- und Anzeigeelemente initialisieren
		Border buttonBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		Border playerPointsBorder = BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.BLACK);
		Color buttonColor = Color.LIGHT_GRAY;
		
		closeButton.setBounds(550, 472, 130, 35);
		closeButton.setBackground(buttonColor);
		closeButton.setForeground(Color.BLACK);
		closeButton.setBorder(buttonBorder);
		restartButton.setBounds(550, 420, 130, 35);
		restartButton.setBackground(buttonColor);
		restartButton.setForeground(Color.BLACK);
		restartButton.setBorder(buttonBorder);
		rulesButton.setBounds(550, 368, 130, 35);
		rulesButton.setBackground(buttonColor);
		rulesButton.setForeground(Color.BLACK);
		rulesButton.setBorder(buttonBorder);
		lbl_wuerfelErgebnis.setBounds(550, 198, 130, 35);
		lbl_wuerfelErgebnis.setText("Würfelergebnis:");
		lbl_wuerfelErgebnis.setForeground(Color.BLACK);
		lbl_wuerfelErgebnis.setHorizontalAlignment(JLabel.CENTER);
		lbl_ErgebnisAusgabe.setBounds(550, 218, 130, 35);
		lbl_ErgebnisAusgabe.setText("4");
		lbl_ErgebnisAusgabe.setHorizontalAlignment(JLabel.CENTER);
		lbl_activePlayer.setBounds(550, 123, 130, 35);
		lbl_activePlayer.setText("Aktueller Spieler:");
		lbl_activePlayer.setForeground(Color.BLACK);
		lbl_activePlayer.setHorizontalAlignment(JLabel.CENTER);
		lbl_PlayerName.setBounds(550, 153, 130, 20);
		lbl_PlayerName.setText("Spieler 1");
		lbl_PlayerName.setHorizontalAlignment(JLabel.CENTER);
		lbl_player1Points.setBorder(playerPointsBorder);
		lbl_player1Points.setBounds(547, 17, 65, 25);
		lbl_player1Points.setText("0");
		lbl_player1Points.setForeground(Color.WHITE);
		lbl_player1Points.setBackground(spieler[0].getColor());
		lbl_player1Points.setHorizontalAlignment(JLabel.CENTER);
		lbl_player1Points.setOpaque(true);
		lbl_player2Points.setBorder(playerPointsBorder);
		lbl_player2Points.setBounds(618, 17, 65, 25);
		lbl_player2Points.setText("0");
		lbl_player2Points.setForeground(Color.WHITE);
		lbl_player2Points.setBackground(spieler[1].getColor());
		lbl_player2Points.setHorizontalAlignment(JLabel.CENTER);
		lbl_player2Points.setOpaque(true);
		
		fieldPane.setLayout(new GridLayout(3, 3, 10, 10));	// Das Layout fuer das gesamte Spielfeld.
		fieldPane.setBackground(new Color(176, 176, 176));	// Hintergrundfarbe einstellen.
		fieldPane.setBounds(15, 15, 500, 500);
		
		// Die Komponenten werden der ContenPane hinzugefuegt.
		contentPane.add(lbl_player1Points);
		contentPane.add(lbl_player2Points);
		contentPane.add(lbl_PlayerName);
		contentPane.add(lbl_activePlayer);
		contentPane.add(lbl_wuerfelErgebnis);
		contentPane.add(lbl_ErgebnisAusgabe);
		contentPane.add(closeButton);
		contentPane.add(restartButton);
		contentPane.add(rulesButton);
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
	
	public void setRepeat(final boolean repeat) {
		this.repeat = repeat;
	}
	
	/** Fuellt einen Container mit 9 Buttons und den entsprechenden Feldnummern.
	 * @param c - Der Container, der mit Buttons gefuellt werden soll. 
	 * @param k - Der Kasten, aus dem die Feldnummern geholt werden sollen.
	 * @param kastenIndex 
	 */
	private void fillContainer(final Container c, final Kasten kasten, final int kastenIndex)
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
	 * Diese wird aufgerufen, wenn eine Komponente zum Container hinzugefuegt wird.
	 * Ist diese Komponente ein Button, wird ihm ein ActionListener zugeordnet.
	 * @param e Das zu uebergebende ContainerEvent.
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

	/** Das ist eine Methode die fuer den ContainerListener implementiert werden muss.
	 * Diese wird aufgerufen, wenn eine Komponente vom Container entfernt wird.
	 * Ist diese Komponente ein Button, wird ihm ein ActionListener entfernt.
	 * @param e Das zu uebergebende ContainerEvent.
	 */
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
	
	public void updateButtons()
	{
		for (CustomButton button : buttons)		// Fuer alle Buttons wird die Methode highlightButton() aufgerufen.
		{
			button.highlightButton();
		}
	}
	
	public void updatePoints(final Spieler[] spieler)
	{
		lbl_player1Points.setText("" + spieler[0].getPunkte());
		lbl_player2Points.setText("" + spieler[1].getPunkte());
	}
	
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
	
	public void setPlayerLabel(final Spieler spieler)
	{
		Border border = BorderFactory.createLoweredSoftBevelBorder();
		
		lbl_PlayerName.setBackground(spieler.getColor());
		lbl_PlayerName.setForeground(Color.LIGHT_GRAY);
		lbl_PlayerName.setText(spieler.getName());
		lbl_PlayerName.setBorder(border);
		lbl_PlayerName.setOpaque(true);
	}
	
	public void setWuerfelLabel(final int wuerfelErgebnis)
	{
		lbl_ErgebnisAusgabe.setForeground(Color.BLACK);
		lbl_ErgebnisAusgabe.setText("" + wuerfelErgebnis);
	}
	
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
	
	private void showRulesDialog()
	{
		String message = "Dies sind die Spielregeln\r\n\r\n"
				+ "Das Spiel wird mit zwei Spielern gespielt. Zu Anfang wird ausgewürfelt, welcher Spieler anfängt.\r\n"
				+ "Es beginnt der Spieler mit dem höchsten Ergebnis. Zu Beginn jedes Zuges wird eine Zahl zwischen 2 und 12 gewürfelt.\r\n"
				+ "Die gewürfelte Zahl bestimmt darüber, wo Sie einen Pin setzen bzw. löschen dürfen.\r\n"
				+ "Wenn Sie beispielsweise eine 3 würfeln, dürfen Sie Ihren Pin in jedes Feld mit der Zahl 3 setzen,\r\n"
				+ "sowie in alle Felder im Kasten 3 außer dem Mittleren.\r\n"
				+ "Die Kästen sind von links oben nach rechts unten von 3 bis 11 durchnummeriert.\r\n\r\n"
				+ "Darüber hinaus gibt es einige Sonderregeln:\r\n"
				+ "2 - Sie dürfen einen gegnerischen Pin löschen, danach sind Sie noch einmal am Zug. (Gegnerische Felder werden schwarz umrandet)\r\n"
				+ "7 - Sie dürfen einen Pin in ein freies Feld mit der Zahl 7 oder im mittleren Kasten setzen.\r\n"
				+ "12 - Sie dürfen einen Pin in ein freies Feld auf dem gesamten Spielfeld setzen\r\n\r\n"
				+ "Ein Kasten gilt als gewonnen, wenn ein Spieler 3 Felder horizontal, vertikal oder diagonal in einer Reihe gewinnt.\r\n"
				+ "Das Spiel gewinnt der Spieler, der zuerst 3 Kästen horizontal, vertikal oder diagonal in einer Reihe gewinnt.\r\n"
				+ "Wurden alle Kästen gewonnen, aber kein Spieler konnte eine Reihe bilden, gewinnt der Spieler mit der höchsten Punktzahl.";
		
		JOptionPane.showMessageDialog(frame, message, "Spielregeln", JOptionPane.DEFAULT_OPTION);
	}
	
	public void closeWindow()
	{
		frame.setVisible(false);
		frame.dispose();
	}
}