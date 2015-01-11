package view;

import java.awt.Color;

import javax.swing.*;
import javax.swing.border.Border;

import data.Feld;
import data.Kasten;

/**
 * Ein spezieller JButton, der das Feld speichern kann zu dem er gehoert.
 * @author
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
	public CustomButton(final Feld feld, final int kastenIndex)
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
	 * Aendert den Rahmen des Buttons, um ihn zum setzen hervorzuheben.
	 */
	private void setHighlight()
	{
		Border highlight = BorderFactory.createLineBorder(new Color(34,166,26), 3);	// Hier wird die Farbe und Dicke des Rahmens eingestellt.
		this.setBorder(highlight);	// Hier wird der Rahmen uebernommen.
	}
	
	/**
	 * Aendert den Rahmen des Buttons, um ihn zum loeschen hervorzuheben.
	 */
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
	
	/**
	 * Es wird geprueft, ob der Button hervorgehoben werden soll und wenn ja in welcher Form.
	 */
	public void highlightButton()
	{
		if (feld != null)	// Es wird zur Sicherheit abgefragt, ob der Button ein Feld kennt.
		{
			if (feld.getHighlight())	// Wenn der Button zum setzen hervorgehoben werden soll.
			{
				this.setEnabled(true);	// Macht den Button klickbar.
				setHighlight();
			}
			else if (feld.getHighlightToDelete())	// Wenn der Button zum loeschen hervorgehoben werden soll.
			{
				this.setEnabled(true);
				setDeleteHighlight();
			}
			else	// Wenn der Button nicht hervorgehoben werden soll.
			{
				this.setEnabled(false);		// Deaktiviert den Button, um eine falsche Auswahl automatisch zu verhindern.
				setNormal();
			}
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
