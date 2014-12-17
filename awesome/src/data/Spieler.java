/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 04.12.2014
 */
package data;

import java.util.ArrayList;
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
		if (wuerfelErgebnis != 2)	// Pruefen, ob die 2 gewuerfelt wurde.
		{
			String eingabe = null;
			boolean falscheEingabe = false;			
			ArrayList<String> angebote = new ArrayList<String>();	// ArrayList erstellen, die die angebotenen Felder enthalten soll.
			
			angebote = bieteFelderAn(kaesten);	// Angegbote durch die Methode einholen.
			System.out.println(angebote.size() + " Elemente: " + angebote);
			
			do
			{
				falscheEingabe = false;
				eingabe = Spiel.input("Bitte wählen Sie ein freies Feld aus, in das Sie Ihren Pin setzen wollen. ('Kastennummer','Feldnummer'): ");
				
				if (!angebote.contains(eingabe))
				{
					falscheEingabe = true;
					System.out.println("Bitte überprüfen Sie Ihre Eingabe");
				}
			} while (falscheEingabe == true);
			
			String[] koordinaten = eingabe.split(",");
			int kastenIndex = Integer.parseInt(koordinaten[0]);
			int feldIndex = Integer.parseInt(koordinaten[1]);
			System.out.println("Kasten: " + kastenIndex + ", Feld: " + feldIndex);
			Feld[] zielFeld = kaesten[kastenIndex].getFelder();
			zielFeld[feldIndex].setPin(new Pin(this));
		}
		else
			pinLoeschen(kaesten);
		
		/*
		  getFeld[]
		  getWuerfel
		  pruefeErgebnis
		     wenn (Ergebnis=2)
		      dann oeffne pinLoeschen()
		     alles andere 
		       bieteFelderAn
		       ï¿½bergebeAusgewaehltesFeldAnFeld
		  */
	}
	
	public void pinLoeschen(Kasten[] kaesten)
	{
		System.out.println("pinLoeschen wurde aufgerufen.\r\n");
		
		ArrayList<String> angebote = new ArrayList<String>();
		int kastenIndex = 0;
		
		for (Kasten k : kaesten)
		{
			int feldIndex = 0;
			
			for (Feld f : k.getFelder())
			{
				if ((f.getPin() != null) && (f.getPin().getSpieler() != this))
				{
					System.out.println("Kasten: " + kastenIndex + ", Feld: " + feldIndex + ", Spieler: " + f.getPin().getSpieler().getName());
					
					angebote.add(kastenIndex + "," + feldIndex);
				}
				feldIndex++;
			}
			kastenIndex++;
		}
		
		if (angebote.size() == 0)
			System.out.println("Es sind leider keine gegnerischen Pins auf dem Feld.");
		else
		{
			boolean falscheEingabe;
			String eingabe = null;
			System.out.println("\r\n" + angebote.size() + " Elemente: " + angebote);
			
			do
			{
				falscheEingabe = false;
				eingabe = Spiel.input("Bitte wählen Sie ein Feld aus, dass Sie löschen möchten('Kastennummer','Feldnummer'): ");
				
				if (!angebote.contains(eingabe))
				{
					falscheEingabe = true;
					System.out.println("Bitte überprüfen Sie Ihre Eingabe.");
				}
			} while (falscheEingabe == true);
			
			String[] koordinaten = eingabe.split(",");
			int kastenIndex1 = Integer.parseInt(koordinaten[0]);
			int feldIndex = Integer.parseInt(koordinaten[1]);
			System.out.println("Kasten: " + kastenIndex1 + ", Feld: " + feldIndex);
			Feld[] zielFeld = kaesten[kastenIndex1].getFelder();
			zielFeld[feldIndex].setPin(null);
		}
			
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
	 * Wuerfelt fï¿½r den Spieler eine Zufallszahl und 
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
	 * und gibt die Indizes der Kaesten und der dazugehï¿½rigen Felder zurï¿½ck
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
			
			for (Feld feld : k.getFelder())	// Hier wird jedes Feld einzeln auf seinen Wert ueberprueft.
			{
				if ((wuerfelErgebnis == 12) && (feld.getPin() == null))	// Wurde die 12 gewuerfelt, soll jedes freie Feld vorgeschlagen werden.
				{
					angebote.add(kastenIndex + "," + feldIndex);	// Die Indizes des Feldes werden der Liste als String hinzugefuegt.
					System.out.println("Kasten: " + (kastenIndex) + ", Feld: " + (feldIndex));
				}
				else if ((wuerfelErgebnis == feld.getFeldNummer()) || ((wuerfelErgebnis) == k.getKastenNummer()) && (feld.getPin() == null))	
				// Wenn das Wuerfelergebnis mit der Feldnummer oder der kastenNummer ï¿½bereinstimmt und das Feld frei ist wird es vorgeschlagen.
				{
					if (feld.getFeldNummer() != 7)	// Das Feld 7 soll nicht vorgeschlagen werden...
					{
						angebote.add(kastenIndex + "," + feldIndex);	// Die Indizes des Feldes werden der Liste als String hinzugefuegt.
						System.out.println("Kasten: " + (kastenIndex) + ", Feld: " + (feldIndex));
					}
					else if ((wuerfelErgebnis == 7) && (feld.getFeldNummer() == 7))	// ...es sei denn es wurde die 7 gewuerfelt.
					{
						angebote.add(kastenIndex + "," + feldIndex);	// Die Indizes des Feldes werden der Liste als String hinzugefuegt.
						System.out.println("Kasten: " + (kastenIndex) + ", Feld: " + (feldIndex));
					}
				}
				feldIndex++;
			}
			kastenIndex++;
		}
		return angebote;
	}
}
