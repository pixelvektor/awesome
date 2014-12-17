/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 04.12.2014
 */
import control.Spiel;
import view.View;
/**
 * @author 
 *
 */
public class Awesome 
{
	private static Spiel spiel;
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		View view = new View();
		new Spiel(view);
	}
}
