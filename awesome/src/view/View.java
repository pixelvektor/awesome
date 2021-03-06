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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
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
 */
public class View implements ContainerListener, ContractView
{
	/** Dieses Array haelt alle Buttons fuer das Spielfeld. */
	private CustomButton[] buttons = new CustomButton[81];
	/** Leinwand im Hinterdrund des Spielfelds. */
	private PaintingComponent paintingComponent = new PaintingComponent();
	/** Label fuer den Spielernamen. */
	private JLabel lbl_PlayerName = new JLabel();
	/** Label fuer die Ergebnisausgabe. */
	private JLabel lbl_ErgebnisAusgabe = new JLabel();
	/** Label fuer die Punkte des Spieler 1. */
	private JLabel lbl_player1Points = new JLabel();
	/** Label fuer die Punkte des Spieler 1. */
	private JLabel lbl_player2Points = new JLabel();
	/** Frame fuer die View. */
	private JFrame frame;
	/** Der ActionListener fuer die Buttons auf dem Spielfeld.*/
	private ActionListener buttonListener;
	/** True wenn das Spiel wiederholt werden soll. */
	private boolean repeat = false;
	
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
		
		contentPane.setLayout(null);	// Ein Null-Layout festlegen, um die Komponenten frei positionieren und skalieren zu koennen.
		contentPane.setBackground(new Color(80, 80, 80));	// Die Hintergrundfarbe einstellen.
		
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
				showRulesDialog();	// Wird dieser Button geklickt wird das Dialogfenster mit den Spielregeln geoeffnet.
			}
		});
		
		restartButton.addActionListener(restartListener);
		
		generateControlElements(spieler, closeButton, restartButton, rulesButton, lbl_wuerfelErgebnis, lbl_activePlayer);
		
		fieldPane.setLayout(new GridLayout(3, 3, 10, 10));	// Das Layout fuer das gesamte Spielfeld.
		fieldPane.setBackground(new Color(176, 176, 176));	// Hintergrundfarbe einstellen.
		fieldPane.setBounds(15, 15, 500, 500);
		
		// Ein neues abgerundetes Rechteck erzeugen und Groesse und Position festlegen.
		RoundRectangle2D rahmen = new RoundRectangle2D.Float(8f, 8f, 514f, 514f, 10f, 10f);
		
		paintingComponent.setSize(new Dimension(600, 600));	// Die Groesse der Zeichenflaeche festlegen.
		paintingComponent.setColor(new Color(50, 50, 50));	// Farbe des Rechtecks festlegen.
		paintingComponent.setShape(rahmen);	// Das Rechteck an die Zeichenflaeche uebergeben.
		paintingComponent.repaint();	// Java zum neu zeichnen zwingen.
		
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
		contentPane.add(paintingComponent);
		
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

	/** Initialisiert die Steuer und Anzeigeelemente.
	 * @param spieler Array mit den Spielern.
	 * @param closeButton Schliessen Button.
	 * @param restartButton Neustart Button.
	 * @param rulesButton Regel Button.
	 * @param lbl_wuerfelErgebnis Das Label des Wuerfelergebnis.
	 * @param lbl_activePlayer Das Label des aktiven Spielers.
	 */
	private void generateControlElements(final Spieler[] spieler,
			final JButton closeButton, final JButton restartButton,
			final JButton rulesButton, final JLabel lbl_wuerfelErgebnis,
			final JLabel lbl_activePlayer)
	{
		Border buttonBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		Border playerPointsBorder = BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.BLACK);
		Color buttonColor = Color.LIGHT_GRAY;
		
		closeButton.setBounds(550, 472, 130, 35);	// Groesse und Position festlegen.
		closeButton.setBackground(buttonColor);		// Hintergrundfarbe festlegen.
		closeButton.setForeground(Color.BLACK);		// Textfarbe festlegen. (Effekt dieser Methode kann je nach Komponente varieren).
		closeButton.setBorder(buttonBorder);		// Den Rahmen festlegen.
		restartButton.setBounds(550, 420, 130, 35);
		restartButton.setBackground(buttonColor);
		restartButton.setForeground(Color.BLACK);
		restartButton.setBorder(buttonBorder);
		rulesButton.setBounds(550, 368, 130, 35);
		rulesButton.setBackground(buttonColor);
		rulesButton.setForeground(Color.BLACK);
		rulesButton.setBorder(buttonBorder);
		lbl_wuerfelErgebnis.setBounds(550, 198, 130, 35);
		lbl_wuerfelErgebnis.setText("W�rfelergebnis:");
		lbl_wuerfelErgebnis.setForeground(Color.BLACK);
		lbl_wuerfelErgebnis.setHorizontalAlignment(JLabel.CENTER);	// Den Text des Labels horizontal zentrieren.
		lbl_ErgebnisAusgabe.setBounds(590, 228, 50, 20);
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
		lbl_player1Points.setOpaque(true);		// Wird auf true gesetzt, damit das Label mit der Hintergrundfarbe ausgefuellt wird.
		lbl_player2Points.setBorder(playerPointsBorder);
		lbl_player2Points.setBounds(618, 17, 65, 25);
		lbl_player2Points.setText("0");
		lbl_player2Points.setForeground(Color.WHITE);
		lbl_player2Points.setBackground(spieler[1].getColor());
		lbl_player2Points.setHorizontalAlignment(JLabel.CENTER);
		lbl_player2Points.setOpaque(true);
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
	 * @param kastenIndex - Der Index das Kastens
	 */
	private void fillContainer(final Container c, final Kasten kasten, final int kastenIndex)
	{
		int offset = kastenIndex * 9;	// Es wird ein Startwert berechnet, damit an der richtigen Stelle begonnen wird das Array zu fuellen.
		
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
		Border border = BorderFactory.createLoweredSoftBevelBorder();
		
		lbl_ErgebnisAusgabe.setBackground(Color.LIGHT_GRAY);
		lbl_ErgebnisAusgabe.setForeground(Color.BLACK);
		lbl_ErgebnisAusgabe.setText("" + wuerfelErgebnis);
		lbl_ErgebnisAusgabe.setBorder(border);
		lbl_ErgebnisAusgabe.setOpaque(true);
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
	
	/**
	 * Erstellt ein Dialogfenster mit den Spielregeln in Textform
	 */
	private void showRulesDialog()
	{
		String message = "Dies sind die Spielregeln\r\n\r\n"
				+ "Das Spiel wird mit zwei Spielern gespielt. Zu Anfang wird ausgew�rfelt, welcher Spieler anf�ngt.\r\n"
				+ "Es beginnt der Spieler mit dem h�chsten Ergebnis. Zu Beginn jedes Zuges wird eine Zahl zwischen 2 und 12 gew�rfelt.\r\n"
				+ "Die gew�rfelte Zahl bestimmt dar�ber, wo Sie einen Pin setzen bzw. l�schen d�rfen.\r\n"
				+ "Wenn Sie beispielsweise eine 3 w�rfeln, d�rfen Sie Ihren Pin in jedes Feld mit der Zahl 3 setzen,\r\n"
				+ "sowie in alle Felder im Kasten 3 au�er dem Mittleren.\r\n"
				+ "Die K�sten sind von links oben nach rechts unten von 3 bis 11 durchnummeriert.\r\n\r\n"
				+ "Dar�ber hinaus gibt es einige Sonderregeln:\r\n"
				+ "2 - Sie d�rfen einen gegnerischen Pin l�schen, danach sind Sie noch einmal am Zug. (Gegnerische Felder werden golden umrandet)\r\n"
				+ "7 - Sie d�rfen einen Pin in ein freies Feld mit der Zahl 7 oder im mittleren Kasten setzen.\r\n"
				+ "12 - Sie d�rfen einen Pin in ein freies Feld auf dem gesamten Spielfeld setzen\r\n\r\n"
				+ "Ein Kasten gilt als gewonnen, wenn ein Spieler 3 Felder horizontal, vertikal oder diagonal in einer Reihe gewinnt.\r\n"
				+ "Das Spiel gewinnt der Spieler, der zuerst 3 K�sten horizontal, vertikal oder diagonal in einer Reihe gewinnt.\r\n"
				+ "Wurden alle K�sten gewonnen, aber kein Spieler konnte eine Reihe bilden, gewinnt der Spieler mit der h�chsten Punktzahl.";
		
		JOptionPane.showMessageDialog(frame, message, "Spielregeln", JOptionPane.DEFAULT_OPTION);
	}
	
	public void closeWindow()
	{
		frame.setVisible(false);
		frame.dispose();
	}
	
	/**
	 * Zum Zeichnen von geometrischen Formen
	 */
	class PaintingComponent extends JComponent
	{
		private static final long serialVersionUID = 1L;
		private Shape shape;
		private Color color;
		
		/**
		 * Darf nicht vom Programmierer aufgerufen werden! Java ruft diese Methode bei bedarf selbst auf.
		 */
		@Override
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);	// Bevor gezeichnet wird, wird die Zeichenfl�che geleert.
			
			// Da die Methode automatisch aufgerufen wird muss ueberprueft werden, ob die Instanzvariablen gefuellt wurden.
			if (shape != null && color != null) 
			{
				Graphics2D g2d = (Graphics2D) g;	// Das Graphics-Objekt wird in ein Graphics2D Objekt gecastet, um mehr zu ermoeglichen.
				g2d.setColor(color);	// Die Farbe des zu zeichnenden Objekts festlegen.
				g2d.fill(shape);	// Die Form gefuellt zeichnen.
			}
		}
		
		/**
		 * Die Form bestimmen
		 * @param shape - Die geometrische Grundform
		 */
		public void setShape (Shape shape)
		{
			this.shape = shape;
		}
		
		/**
		 * Die Farbe bestimmen
		 * @param color - Die Farbe, die das Objekt haben soll
		 */
		public void setColor (Color color)
		{
			this.color = color;
		}
	}
}