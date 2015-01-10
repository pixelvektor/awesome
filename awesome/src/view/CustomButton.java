package view;

import javax.swing.*;
import javax.swing.border.Border;

import data.Feld;
import data.Kasten;

/**
 * Ein spezieller JButton, der das Feld speichern kann zu dem er gehoert.
 * @author Matthias
 *
 */
public class CustomButton extends JButton
{
	private static final long serialVersionUID = 1L;
	private Feld feld;		// Das Feld zu dem der Button gehoert.
	private Kasten[] kaesten;
	private int kastenIndex;
	private Border defaultBorder;
	
	/**
	 * Ein Spezialkonstruktor, dem zusätzlich zum Text ein Feld übergeben wird.
	 * @param string - Der Text, der auf dem Button stehen soll. Wird ein leerer String übergeben, dann steht die Feldnummer auf dem Button.
	 * @param feld - Das Feld, zu dem der Button gehoeren soll.
	 * @param kaesten - Das Spielfeld
	 * @param kastenIndex - Der Index des Kastens in dem sich das Feld befindet
	 */
	public CustomButton(Feld feld, Kasten[] kaesten, int kastenIndex)
	{
		this.feld = feld;
		defaultBorder = this.getBorder();	// Der Standardrahmen des Buttons wird zur spaeteren Verwendung in eine Instanzvariable geschrieben.
		
		this.setText("" + feld.getFeldNummer());
		this.kaesten = kaesten;
		this.kastenIndex = kastenIndex;
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
	private void setHighlight()
	{
		Border highlight = BorderFactory.createLineBorder(java.awt.Color.ORANGE, 3);	// Hier wird die Farbe und Dicke des Rahmens eingestellt.
		this.setBorder(highlight);	// Hier wird der Rahmen uebernommen.
	}
	
	/**
	 * Hiermit wird der Standardrahmen des Buttons wiederhergestellt.
	 */
	private void setNormal()
	{
		this.setBorder(defaultBorder);
	}
	
	public void highlightButton()
	{
		if (feld.getHighlight() == true)
		{
			this.setEnabled(true);
			setHighlight();
		}
		else
		{
			setNormal();
			this.setEnabled(false);
		}
	}
}
