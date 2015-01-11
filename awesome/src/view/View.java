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

import control.Spiel.RestartListener;
import data.Kasten;
import data.Pin;
import data.Spieler;

/**
 * @author 
 *
 */
public class View implements ContainerListener
{
	private CustomButton[] buttons = new CustomButton[81];
	private JLabel lbl_playerName = new JLabel();
	private JLabel lbl_ergebnisAusgabe = new JLabel();;
	private JFrame frame;
	private ActionListener buttonListener;
	private ActionListener restartListener;
	private boolean repeat;
	
	public void show(Kasten[] kaesten, Spieler[] spieler, ActionListener buttonListener, ActionListener restartListener)
	{
		this.buttonListener = buttonListener;
		this.restartListener = restartListener;
		
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
		lbl_ergebnisAusgabe.setBounds(550, 295, 130, 35);
		lbl_ergebnisAusgabe.setText("4");
		lbl_ergebnisAusgabe.setHorizontalAlignment(JLabel.CENTER);
		lbl_activePlayer.setBounds(550, 200, 130, 35);
		lbl_activePlayer.setText("Aktueller Spieler:");
		lbl_activePlayer.setHorizontalAlignment(JLabel.CENTER);
		lbl_playerName.setBounds(550, 220, 130, 35);
		lbl_playerName.setText("Spieler 1");
		lbl_playerName.setHorizontalAlignment(JLabel.CENTER);
		
		fieldPane.setLayout(new GridLayout(3, 3, 10, 10));	// Das Layout fuer das gesamte Spielfeld.
		fieldPane.setBackground(new Color(176, 176, 176));	// Hintergrundfarbe einstellen.
		fieldPane.setBounds(15, 15, 500, 500);
		
		contentPane.add(lbl_playerName);
		contentPane.add(lbl_activePlayer);
		contentPane.add(lbl_wuerfelErgebnis);
		contentPane.add(lbl_ergebnisAusgabe);
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
	private void fillContainer(Container c, Kasten kasten, int kastenIndex)
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
	
	public void updateButtons()
	{
		for (CustomButton button : buttons)
		{
			button.highlightButton();
		}
	}
	
	public boolean allInactive()
	{
		boolean inactive = true;
		for (CustomButton b : buttons)
		{
			if (b.isEnabled())
			{
				inactive = false;
				break;
			}
		}
		return inactive;
	}
	
	public void setPlayerLabel(String spielerName)
	{
		lbl_playerName.setText(spielerName);
	}
	
	public void setWuerfelLabel(int wuerfelErgebnis)
	{
		lbl_ergebnisAusgabe.setText("" + wuerfelErgebnis);
	}
	
	public void fillAllButtons(Spieler spieler)
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
}