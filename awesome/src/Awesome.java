/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 04.12.2014
 * GitHub: https://github.com/pixelvektor/awesome (verfuegbar bis: 28.02.2015)
 */
import control.Spiel;
import view.View;
/**
 * @author 
 *
 */
public class Awesome 
{
	/** Startet das Spiel.
	 * @param args Argumente der Kommandozeile beim Aufruf. Nicht benoetigt.
	 */
	public static void main(String[] args)
	{
		View view = new View();
		new Spiel(view);
	}
}
