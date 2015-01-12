package view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

import data.Feld;

/** Ein spezieller JButton, der das Feld speichern kann zu dem er gehoert.
 * @author
 *
 */
public class CustomButton extends JButton
{
	/** UID, welche Java haben moechte. */
	private static final long serialVersionUID = 1L;
	/** Das Feld zu dem der Button gehoert. */
	private Feld feld;
	/** Index des Kasten zu dem der Button gehoert. */
	private int kastenIndex;
	/** Speichert die Standardborder. */
	private final Border defaultBorder;
	/** Speichert die Standardfarbe. */
	private final Color defaultColor;
	
	/** Ein Spezialkonstruktor, dem anstatt dem Text ein Feld uebergeben wird.
	 * @param feld Das Feld, zu dem der Button gehoeren soll.
	 * @param kastenIndex Der Index des Kastens in dem sich das Feld befindet.
	 */
	public CustomButton(final Feld feld, final int kastenIndex)
	{
		this.setOpaque(true);
		this.feld = feld;
		defaultBorder = this.getBorder();	// Der Standardrahmen des Buttons wird zur spaeteren Verwendung in eine Instanzvariable geschrieben.
		defaultColor = this.getBackground();	// Die Standardfarbe des Buttons wird zur spaeteren Verwendung in eine Instanzvariable geschrieben.
		
		this.setText("" + feld.getFeldNummer());
		this.kastenIndex = kastenIndex;
	}
	
	/** Der Standardkonstruktor eines Buttons.
	 * @param s Der Text, den der Button haben soll.
	 */
	public CustomButton(String s)
	{
		this.setOpaque(true);
		defaultBorder = this.getBorder();
		defaultColor = this.getBackground();
		this.setText(s);
	}
	
	/** Gibt das Feld des Button zurueck.
	 * @return Das Feld des Button.
	 */
	public Feld getFeld()
	{
		return feld;
	}
	
	/** Gibt den Index des Kasten zurueck in dem das Feld des Button ist.
	 * @return Des Kastenindex zum Button.
	 */
	public int getKastenIndex()
	{
		return kastenIndex;
	}
	
	/** Aendert den Rahmen des Buttons, um ihn zum Setzen hervorzuheben.
	 */
	private void setHighlight()
	{
		Border highlight = BorderFactory.createLineBorder(new Color(34,166,26), 3);	// Hier wird die Farbe und Dicke des Rahmens eingestellt.
		this.setBorder(highlight);	// Hier wird der Rahmen uebernommen.
	}
	
	/** Aendert den Rahmen des Buttons, um ihn zum loeschen hervorzuheben.
	 */
	private void setDeleteHighlight()
	{
		Border deleteHighlight = BorderFactory.createLineBorder(new Color(212, 165, 32), 3);
		this.setBorder(deleteHighlight);
	}
	
	/** Es wird geprueft, ob der Button hervorgehoben werden soll und wenn ja in welcher Form.
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
				this.setBorder(defaultBorder);
			}
		}
	}
	/** Die Standardhintergrundfarbe wird wiederhergestellt.
	 */
	public void setDefaultBackground()
	{
		this.setBackground(defaultColor);
	}
}
