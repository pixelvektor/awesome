package view;

import javax.swing.*;
import javax.swing.border.Border;

import data.Feld;

/**
 * Ein spezieller JButton, der das Feld speichern kann zu dem er gehoert.
 * @author Matthias
 *
 */
public class CustomButton extends JButton
{
	private static final long serialVersionUID = 1L;
	private Feld feld;		// Das Feld zu dem der Button gehoert.
	private Border defaultBorder;
	
	/**
	 * Ein Spezialkonstruktor, dem zusätzlich zum Text ein Feld übergeben wird.
	 * @param s - Der Text, der auf dem Button stehen soll. Wird ein leerer String übergeben, dann steht die Feldnummer auf dem Button.
	 * @param f - Das Feld, zu dem der Button gehoeren soll.
	 */
	public CustomButton(String s, Feld f)
	{
		feld = f;
		defaultBorder = this.getBorder();	// Der Standardrahmen des Buttons wird zur spaeteren Verwendung in eine Instanzvariable geschrieben.
		
		if ( s.length() == 0)	// Wenn der String leer ist steht die Feldnummer als Text im Button.
			this.setText("" + f.getFeldNummer());
		else
			this.setText(s);	// Ansonsten wird der Text auf den Button geschrieben.
	}
	
	/**
	 *  Der Standardkonstruktor eines Buttons.
	 * @param s - Der Text, den der Button haben soll.
	 */
	public CustomButton(String s)
	{
		defaultBorder = this.getBorder();
		this.setText(s);
	}
	
	public Feld getFeld()
	{
		return feld;
	}
	
	/**
	 * Diese Methode aendert den Rahmen des Buttons, um ihn hervorzuheben.
	 */
	public void setHighlight()
	{
		Border highlight = BorderFactory.createLineBorder(java.awt.Color.ORANGE, 3);	// Hier wird die Farbe und Dicke des Rahmens eingestellt.
		this.setBorder(highlight);	// Hier wird der Rahmen uebernommen.
	}
	
	/**
	 * Hiermit wird der Standardrahmen des Buttons wiederhergestellt.
	 */
	public void setNormal()
	{
		this.setBorder(defaultBorder);
	}
}
