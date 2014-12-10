/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 04.12.2014
 */
package data;

/**
 * @author
 *
 */
public class Spieler 
{
	private String name;
	private int punkte;
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{	
		if (!name.equals("") || (!name.equals(null)))
		{
			this.name = name;
		}
	}
	
	public void pinSetzen()
	{
		//TODO
	}
	
	public void pinLoeschen()
	{
		//TODO
	}
	
	public void erhoehePunkte()
	{
		//TODO
	}
	
	public void verringerePunkte()
	{
		//TODO STUFF
	}
	
	public int getPunkte()
	{
		return punkte;
	}
}
