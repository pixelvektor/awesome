/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 04.12.2014
 */
package data;

import java.util.ArrayList;

import control.Spiel;
import control.Wuerfel;
/**
 * @author
 *
 */
public class Spieler 
{
	private final String name;
	private int punkte;
	private int wuerfelErgebnis;
	
	/**
	 * @param name
	 */
	public Spieler(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
	
	/** Die Methode setzt einen Pin auf ein freies Feld
	 * 
	 * @param kaesten Das Spielfeld
	 */
	public void pinSetzen(Kasten[] kaesten)
	{
		ArrayList<String> angebote = new ArrayList<String>();
		angebote = bieteFelderAn(kaesten);
		
		System.out.println(angebote);
		/*
		  getFeld[]
		  getWuerfel
		  pruefeErgebnis
		     wenn (Ergebnis=2)
		      dann oeffne pinLoeschen()
		     alles andere 
		       bieteFelderAn
		       übergebeAusgewaehltesFeldAnFeld
		  */
	}
	
	public void pinLoeschen()
	{
		/*
		  bieteFelderZumLoeschenAn
		  uebergebeAusgewaehltesFeldAnFeld
		 */
	}
	
	public void erhoehePunkte()
	{
		/*
		  bei jedem gesetzten Pin Counter+1
		  wenn Kasten gewonnen 
		       dann entferne gegnerische Pins
		            Counter+dazugewonneneFelder
		 */
	}
	
	public void verringerePunkte()
	{
		/*
		  wenn (pin geloescht)
		   dann Counter-1
		 */
	}
	
	public int getPunkte()
	{
		return punkte;
	}
	
	/**
	 * Wuerfelt für den Spieler eine Zufallszahl und 
	 * speichert diese in der Instanzvariable wuerfelErgebnis
	 * @return Das Wuerfelergebnis als Integer
	 */
	public int wuerfeln()
	{
		wuerfelErgebnis = Wuerfel.wuerfeln();
		
		return wuerfelErgebnis;
	}
	
	public int getWuerfelErgebnis()
	{
		return wuerfelErgebnis;
	}
	
	/** Die Methode vergleicht alle Felder mit dem Wuerfelergebnis
	 * und gibt die Indizes der Kaesten und der dazugehörigen Felder zurück
	 * @param kaesten Das Spielfeld
	 * @return String-ArrayList mit den Indizes der freien Felder
	 */
	private ArrayList<String> bieteFelderAn(Kasten[] kaesten)
	{
		ArrayList<String> angebote = new ArrayList<String>();	// Diese Liste wird spaeter mit den Indizes gefuellt und ist der Rueckgabewert.
		int kastenIndex = 0;
		
		System.out.println("Freie Felder mit der Zahl " + wuerfelErgebnis + ":");
		
		for (Kasten k : kaesten)	// Eine Schleife durch alle Kaesten des Spielfeldes.
		{
			int feldIndex = 0;
			Feld[] felder = k.getFelder();	// Die Methode holt sich alle Felder aus dem aktuellen Kasten. 
			
			for (Feld feld : felder)	// Hier wird jedes Feld einzeln auf seinen Wert ueberprueft.
			{
				if ((wuerfelErgebnis == feld.getFeldNummer()) && (feld.getPin() == null))	/* Wenn das Feld frei ist und dieselbe Nummer hat
				 																			wie die gewuerfelt Zahl wird es der Liste hinzugefügt.*/
				{
					angebote.add(kastenIndex + "," + feldIndex);	// Die Indizes des Feldes werden der Liste als String hinzugefuegt.
					System.out.println("Kasten: " + (kastenIndex) + ", Feld: " + (feldIndex));
				}
				feldIndex++;
			}
			kastenIndex++;
		}
		
		return angebote;
	}
}
