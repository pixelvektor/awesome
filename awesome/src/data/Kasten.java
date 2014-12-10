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
public class Kasten
{
	private Feld[] felder = new Feld[9];
	private int kastenNummer;
	
	public Kasten()
	{
	}
	
	public Kasten(int kastenNummer)
	{
		this.kastenNummer = kastenNummer;
	}
	
	public Feld[] getFelder()
	{
		return felder;
	}
	
	public void setFelder(Feld[] felder)
	{
		if (felder.length == 9)
		{
			this.felder = felder;
		}
	}
	
	public int getKastenNummer()
	{
		return kastenNummer;
	}
}
