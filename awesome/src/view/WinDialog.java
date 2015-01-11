package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import data.Spieler;

/**
 * Das Dialogfenster, in dem die Spieler ihre Namen eingeben muessen bevor das Spiel beginnt. 
 * @author Matthias
 *
 */
public class WinDialog
{
	private Spieler spieler;
	private JButton btnRestart, btnExit;
	private JDialog dialog;
	private View view;
	
	/**
	 * Hiermit wird das DialogFenster erstellt.
	 * @param frame - Das Elternfenster zu dem der Dialog gehoeren soll.
	 * @param spieler - Die Spieler
	 */
	public WinDialog(final Frame frame, final View view, final Spieler spieler)
	{
		this.spieler = spieler;
		this.view = view;
		showNameDialog(frame);
	}
	
	/**
	 * 
	 * @param o - Das Elternfenster zu dem der Dialog gehoeren soll.
	 */
	private void showNameDialog(final Frame o)
	{
		// Initialisierung der Ebenen (Container) und Komponenten des Dialogfensters.
		dialog = new JDialog(o, true);	// Der Dialog wird modal erzeugt.
		Container contentPane = dialog.getContentPane();	// Die hinterste Ebene des Fensters.
		Container bottomPane = new Container();		// Die Ebene, in der die Buttons liegen.
		btnRestart = new JButton("Neues Spiel");
		btnExit = new JButton("Beenden");
		JLabel winner = new JLabel(spieler.getName() + " hat gewonnen. Fein gemacht!");
		
		winner.setHorizontalAlignment(0);		// Der Gewinnertext soll horizontal zentriert dargestellt werden.
		
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();		// Die Bildschirmaufloesung wird erfasst.
		int x, y, width, height;
		
		width = 300;	// Fensterbreite
		height = 125;	// Fensterhoehe
		x = (screensize.width / 2) - (width / 2);		// Fenster horizontal mittig auf dem Bildschirm ausrichten.
		y = (screensize.height / 2) - (height / 2);		// Fenster vertikal mittig auf dem Bildschirm ausrichten.
				
		// Einstellungen des Dialogfensters initialisieren.
		dialog.setTitle("Gewonnen :)");
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setBounds(x, y, width, height);
		dialog.setResizable(false);
		
		contentPane.add(winner, BorderLayout.CENTER);	// Gewinner zur hintersten Ebene im oberen Teil hinzufuegen.
		
		bottomPane.setLayout(new FlowLayout());
		
		btnRestart.addActionListener(new ActionListener() // Den Button dem ActionListener hinzufuegen, um Mausklicks zu erfassen.
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.setRepeat(true);
				dialog.dispose();
			}
		});
		
		btnRestart.addKeyListener(new EnterKeyListener());		// Den Button dem KeyListener hinzufuegen, um den Tastendruck auf Enter zu erfassen.
		bottomPane.add(btnRestart);		// den Button der untersten Ebene hinzufuegen.
		
		btnExit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);		// Wird der Abbrechen-Button geklickt, wird das Programm beendet.
				
			}
		});
		
		btnExit.addKeyListener(new EnterKeyListener());
		bottomPane.add(btnExit);

		contentPane.add(bottomPane, BorderLayout.SOUTH);	// Die unterste Ebene der Hiintergrundebene im unteren Teil (SOUTH) hinzufuegen.
		
		dialog.setVisible(true);	// Das Dialogfenster anzeigen.
	}
	
	/**
	 * Ein KeyListener fuer die Enter-Taste.
	 * @author Matthias
	 *
	 */
	class EnterKeyListener extends KeyAdapter	// implementiert mit Hilfe eines Adapters fuer das Interface KeyListener
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_ENTER)	// Wenn das Event den KeyCode der Enter-Taste hat...
			{
				if (e.getSource().equals(btnExit))		// ...wird zuerst ueberprueft, ob die Beenden-Taste das Event ausgeloest hat.
					System.exit(0);		// wenn ja wird das Programm beendet.
				else {
					view.setRepeat(true);		// wenn nicht wird der Neustart vorbereitet.
					dialog.dispose();
				}
			}
		}
	}
}