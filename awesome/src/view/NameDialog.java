package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import data.Spieler;

/**
 * Das Dialogfenster, in dem die Spieler ihre Namen eingeben muessen bevor das Spiel beginnt. 
 * @author
 *
 */
public class NameDialog
{
	private Spieler[] spieler = new Spieler[2];
	private JTextField txtName1, txtName2;
	private JButton btnOK, btnAbort;
	private JDialog dialog;
	
	/**
	 * Hiermit wird das DialogFenster erstellt.
	 * @param frame - Das Elternfenster zu dem der Dialog gehoeren soll.
	 * @param spieler - Die Spieler
	 */
	public NameDialog(final Frame frame, final Spieler[] spieler)
	{
		this.spieler = spieler;
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
		Container centerPane = new Container();		// Die Ebene, in der die Eingabefelder liegen.
		Container bottomPane = new Container();		// Die Ebene, in der die Buttons liegen.
		btnOK = new JButton("OK");
		btnAbort = new JButton("Beenden");
		JLabel lblName1 = new JLabel("Spieler 1:    ");
		JLabel lblName2 = new JLabel("Spieler 2:    ");
		txtName1 = new JTextField(20);
		txtName2 = new JTextField(20);
		JLabel lblTitle = new JLabel("Bitte geben Sie die Spielernamen ein");
		
		lblTitle.setHorizontalAlignment(0);		// Der Text im TitelLabel soll horizontal zentriert dargestellt werden.
		
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();		// Die Bildschirmaufloesung wird erfasst.
		int x, y, width, height;
		
		width = 400;	// Fensterbreite
		height = 175;	// Fensterhoehe
		x = (screensize.width / 2) - (width / 2);		// Fenster horizontal mittig auf dem Bildschirm ausrichten.
		y = (screensize.height / 2) - (height / 2);		// Fenster vertikal mittig auf dem Bildschirm ausrichten.
				
		// Einstellungen des Dialogfensters initialisieren.
		dialog.setTitle("Namenseingabe");
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setBounds(x, y, width, height);
		dialog.setResizable(false);
		
		contentPane.add(lblTitle, BorderLayout.NORTH);	// TitelLabel zur hintersten Ebene im oberen Teil hinzufuegen.

		centerPane.setLayout(new GridBagLayout());		// Der mittleren Eben ein neues GridBagLayout zuweisen.
		GridBagConstraints cs = new GridBagConstraints();	// Diese Constraints sind zum steuern des Layouts im GridBagLayout.
		
		cs.gridx = 0;	// Einstellen der Spalte, in der das Label stehen soll.
		cs.gridy = 0;	// Einstellen der Zeile, in der das Label stehen soll.
		cs.ipady = 15;	// Der Abstand zwischen dieser Komponente und der Komponente eine Zeile darunter. 
		centerPane.add(lblName1, cs);	// Das Label zur mittleren Ebene hinzufuegen und die Constraints mitgeben.
		
		cs.gridx = 1;	// Das Textfeld soll rechts neben dem Label stehen.
		cs.gridy = 0;
		cs.ipady = 0;
		txtName1.addKeyListener(new EnterKeyListener());	// Das Textfeld beim KeyListener eintragen. (Um den Tastendruck auf die Entertaste zu erfassen.)
		centerPane.add(txtName1, cs);
		
		cs.gridx = 0;
		cs.gridy = 1;
		centerPane.add(lblName2, cs);
		
		cs.gridx = 1;
		cs.gridy = 1;
		txtName2.addKeyListener(new EnterKeyListener());
		centerPane.add(txtName2, cs);
		
		contentPane.add(centerPane, BorderLayout.CENTER);	// Die mittlere Ebene zur hinteren Ebene hinzufuegen.
		
		bottomPane.setLayout(new FlowLayout());
		
		btnOK.addActionListener(new ActionListener() // Den Button dem ActionListener hinzufuegen, um Mausklicks zu erfassen.
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				verifyNames();
				
			}
		});
		
		btnOK.addKeyListener(new EnterKeyListener());		// Den Button dem KeyListener hinzufuegen, um den Tastendruck auf Enter zu erfassen.
		bottomPane.add(btnOK);		// den Button der untersten Ebene hinzufuegen.
		
		btnAbort.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);		// Wird der Abbrechen-Button geklickt, wird das Programm beendet.
				
			}
		});
		
		btnAbort.addKeyListener(new EnterKeyListener());
		bottomPane.add(btnAbort);

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
				if (e.getSource().equals(btnAbort))		// ...wird zuerst ueberprueft, ob die Abbrechen-Taste das Event ausgeloest hat.
					System.exit(0);		// wenn ja wird das Programm beendet.
				else
					verifyNames();		// wenn nicht wird die Eingabe ueberprueft.
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e)
		{
			int maxLenght = 20;
			
			if (txtName1.getText().length() > maxLenght || txtName2.getText().length() > maxLenght)
			{
				cropText(txtName1, maxLenght);
				cropText(txtName2, maxLenght);
			}
		}
	}
	
	/**
	 * Diese Methode ueberprueft die Eingabe in den beiden Textfeldern.
	 */
	private void verifyNames()
	{
		String name1, name2, message;
		name1 = txtName1.getText();		// Die Eingabe wird aus den Textfeldern in Variablen geschrieben.
		name2 = txtName2.getText();
		
		if ((name1.length() != 0) && (name2.length() != 0))		// Wenn die Textfelder nicht leer sind...
		{
			if (name1.equals(name2))	// ...wird ueberprueft, ob die Namen identisch sind.
			{
				message = "Die beiden Namen dürfen nicht gleich sein.";
				JOptionPane.showMessageDialog(dialog, message, "Fehler", JOptionPane.ERROR_MESSAGE);	// Ein Fehlerdialog wird geoeffnet.
			}
			else	// Stimmen die Namen nicht ueberein werden die Namen in die Spieler geschrieben.
			{
				spieler[0] = new Spieler(txtName1.getText(), new Color(22,95,242));
				spieler[1] = new Spieler(txtName2.getText(), new Color(191,4,4));
			
				dialog.dispose();	// Das Dialogfenster wird geschlossen.
			}
		}
		else	// Sollten in mindestens einem der beiden Textfelder nichts stehen wird ebenfalls eine Fehlermeldung ausgegeben.
		{
			message = "Sie müssen zwei Namen eingeben!";
			JOptionPane.showMessageDialog(dialog, message, "Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cropText(JTextField textField, int maxLenght)
	{
		if (textField.getText().length() >= maxLenght)
		{
			String text = textField.getText();
			String sizedText = text.substring(0, maxLenght);
			
			textField.setText(sizedText);
		}
	}
}