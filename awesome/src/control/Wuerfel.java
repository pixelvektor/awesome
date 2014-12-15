/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 04.12.2014
 */
package control;

/**
 * @author 
 *
 */
public class Wuerfel 
{
	/**
	 * Erzeugt eine Zufallszahl zwischen 2 und 12
	 * @return Die Zufallszahl als Integer
	 */
	public static int wuerfeln() 
	{
		double random = ((Math.random() * 11) + 2);
		return (int) random;
	}
}
