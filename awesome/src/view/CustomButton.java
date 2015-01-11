package view;

import java.awt.Color;

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
	private int kastenIndex;
	private final Border defaultBorder;
	private final Color defaultColor;
	
	/**
	 * Ein Spezialkonstruktor, dem anstatt dem Text ein Feld übergeben wird.
	 * @param feld - Das Feld, zu dem der Button gehoeren soll.
	 * @param kastenIndex - Der Index des Kastens in dem sich das Feld befindet
	 */
	public CustomButton(Feld feld, int kastenIndex)
	{
		this.feld = feld;
		defaultBorder = this.getBorder();	// Der Standardrahmen des Buttons wird zur spaeteren Verwendung in eine Instanzvariable geschrieben.
		defaultColor = this.getBackground();	// Die Standardfarbe des Buttons wird zur spaeteren Verwendung in eine Instanzvariable geschrieben.
		
		this.setText("" + feld.getFeldNummer());
		this.kastenIndex = kastenIndex;
	}
	
	/**
	 *  Der Standardkonstruktor eines Buttons.
	 * @param s - Der Text, den der Button haben soll.
	 */
	public CustomButton(String s)
	{
		defaultBorder = this.getBorder();
		defaultColor = this.getBackground();
		this.setText(s);
	}
	
	public Feld getFeld()
	{
		return feld;
	}
	
	public int getKastenIndex()
	{
		return kastenIndex;
	}
	
	/**
	 * Diese Methode aendert den Rahmen des Buttons, um ihn hervorzuheben.
	 */
	private void setHighlight()
	{
		Border highlight = BorderFactory.createLineBorder(new Color(34,166,26), 3);	// Hier wird die Farbe und Dicke des Rahmens eingestellt.
		this.setBorder(highlight);	// Hier wird der Rahmen uebernommen.
	}
	
	private void setDeleteHighlight()
	{
		Border deleteHighlight = BorderFactory.createLineBorder(new Color(30,36,38), 3);
		this.setBorder(deleteHighlight);
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
		if (feld.getHighlight())
		{
			this.setEnabled(true);
			setHighlight();
		}
		else if (feld.getHighlightToDelete())
		{
			this.setEnabled(true);
			setDeleteHighlight();
		}
		else
		{
			setNormal();
			this.setEnabled(false);
		}
	}
	/**
	 * Die Standardhintergrundfarbe wird wiederhergestellt.
	 */
	public void setDefaultBackground()
	{
		this.setBackground(defaultColor);
	}
}
