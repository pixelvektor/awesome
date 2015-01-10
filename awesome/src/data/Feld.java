/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 10.12.2014
 */
package data;
/**
 * @author
 *
 */
public class Feld
{
	private Pin pin;
	private int feldNummer;
	private int feldIndex;
	private boolean highlight = false;
	
	public Feld(int feldNummer, int feldIndex)
	{
		this.feldNummer = feldNummer;
		this.feldIndex = feldIndex;
	}
	
	public Pin getPin()
	{
		return pin;
	}
	
	public void setPin(Pin pin)
	{
		this.pin = pin;
	}
	
	public int getFeldNummer()
	{
		return feldNummer;
	}
	
	public int getFeldIndex()
	{
		return feldIndex;
	}
	
	/**
	 * @return the highlight
	 */
	public boolean getHighlight()
	{
		return highlight;
	}

	/**
	 * @param highlight the highlight to set
	 */
	public void setHighlight(boolean highlight)
	{
		this.highlight = highlight;
	}
}
