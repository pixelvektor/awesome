package view;

import javax.swing.*;
import javax.swing.border.Border;

import data.Feld;

public class CustomButton extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Feld feld;
	private Border defaultBorder;
	
	public CustomButton(String s, Feld f)
	{
		feld = f;
		defaultBorder = this.getBorder();
		
		if ( s.length() == 0)
			this.setText("" + f.getFeldNummer());
		else
			this.setText(s);
	}
	
	public CustomButton(String s)
	{
		defaultBorder = this.getBorder();
		this.setText(s);
	}
	
	public Feld getFeld()
	{
		return feld;
	}
	
	public void setHighlight()
	{
		Border highlight = BorderFactory.createLineBorder(java.awt.Color.ORANGE, 3);
		this.setBorder(highlight);
	}
	
	public void setNormal()
	{
		this.setBorder(defaultBorder);
	}
}
