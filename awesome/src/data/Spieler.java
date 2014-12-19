/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 04.12.2014
 */
package data;

import java.util.ArrayList;
import control.Spiel;

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
	public Spieler(final String name)
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

				eingabe = Spiel.input("Bitte w�hlen Sie ein freies Feld aus, in das Sie Ihren Pin setzen wollen. ('Kastennummer','Feldnummer'): ");
				
				if (!angebote.contains(eingabe))
				{
					falscheEingabe = true;
					System.out.println("Bitte �berpr�fen Sie Ihre Eingabe");
				}
			} while (falscheEingabe == true);
			
			String[] koordinaten = eingabe.split(",");
			int kastenIndex = Integer.parseInt(koordinaten[0]);
			int feldIndex = Integer.parseInt(koordinaten[1]);
			System.out.println("Kasten: " + kastenIndex + ", Feld: " + feldIndex);
			Feld[] zielFeld = kaesten[kastenIndex].getFelder();
			zielFeld[feldIndex].setPin(new Pin(this));
			
			pruefeKasten(kaesten[kastenIndex]);
		}
		else
			pinLoeschen(kaesten);
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
				eingabe = Spiel.input("Bitte w�hlen Sie ein Feld aus, dass Sie l�schen m�chten('Kastennummer','Feldnummer'): ");
				
				if (!angebote.contains(eingabe))
				{
					falscheEingabe = true;
					System.out.println("Bitte �berpr�fen Sie Ihre Eingabe.");
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
	 * Wuerfelt f�r den Spieler eine Zufallszahl und 
	 * speichert diese in der Instanzvariable wuerfelErgebnis
	 * @return Das Wuerfelergebnis als Integer
	 */
	public int wuerfeln()
	{
		double random = ((Math.random() * 11) + 2);
		wuerfelErgebnis = (int) random;
		return (int) random;
	}
	
	public int getWuerfelErgebnis()
	{
		return wuerfelErgebnis;
	}
	public void setWuerfelErgebnis(int wuerfelErgebnis) //fuer die testmethoden, kann spaeter geloescht werden
	{
		this.wuerfelErgebnis=wuerfelErgebnis;
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
			
			if (k.getSpieler() == null)
			{
				for (Feld feld : k.getFelder())	// Hier wird jedes Feld einzeln auf seinen Wert ueberprueft.
				{
					if ((wuerfelErgebnis == 12) && (feld.getPin() == null))	// Wurde die 12 gewuerfelt, soll jedes freie Feld vorgeschlagen werden.
					{
						angebote.add(kastenIndex + "," + feldIndex);	// Die Indizes des Feldes werden der Liste als String hinzugefuegt.
						System.out.println("Kasten: " + (kastenIndex) + ", Feld: " + (feldIndex));
					}
					else if ((wuerfelErgebnis == feld.getFeldNummer()) && (feld.getPin() == null) || ((wuerfelErgebnis == k.getKastenNummer()) && (feld.getPin() == null)))
					// Wenn das Wuerfelergebnis mit der Feldnummer oder der kastenNummer �bereinstimmt und das Feld frei ist wird es vorgeschlagen.
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
			}
			kastenIndex++;
		}
		return angebote;
	}
	
	private void pruefeKasten(Kasten k)
	{
		System.out.println("pruefeKasten wurde für den Kasten " + k.getKastenNummer() + " aufgerufen.");
		
		ArrayList<Integer> pruefListe = new ArrayList<Integer>();
		int pruefIndex = 0;
		boolean kastenGewonnen = false;
		
		for (Feld f : k.getFelder())
		{
			if (f.getPin() != null)
			{
				if (f.getPin().getSpieler() == this)
				{
					pruefListe.add(pruefIndex);
				}
			}
			pruefIndex++;
		}
		
		for (int i = 0; i <= 8; i++)
		{
			switch (i)
			{
				case 0:
				{
					int x1, x2, x3;
					x1 = 0;
					x2 = x1++;
					x3 = x2++;
					
					if ((pruefListe.contains(x1)) && (pruefListe.contains(x2)) && (pruefListe.contains(x3)))
					{
						k.setSpieler(this);
						kastenGewonnen = true;
					}
					break;
				}
				case 1:
				{
					int x1, x2, x3;
					x1 = 3;
					x2 = x1++;
					x3 = x2++;
					
					if ((pruefListe.contains(x1)) && (pruefListe.contains(x2)) && (pruefListe.contains(x3)))
					{
						k.setSpieler(this);
						kastenGewonnen = true;
					}
					break;
				}
				case 2:
				{
					int x1, x2, x3;
					x1 = 0;
					x2 = x1++;
					x3 = x2++;
					
					if ((pruefListe.contains(x1)) && (pruefListe.contains(x2)) && (pruefListe.contains(x3)))
					{
						k.setSpieler(this);
						kastenGewonnen = true;
					}
					break;
				}
				case 3:
				{
					int x1, x2, x3;
					x1 = 0;
					x2 = x1+3;
					x3 = x2+3;
					
					if ((pruefListe.contains(x1)) && (pruefListe.contains(x2)) && (pruefListe.contains(x3)))
					{
						k.setSpieler(this);
						kastenGewonnen = true;
					}
					break;
				}
				case 4:
				{
					int x1, x2, x3;
					x1 = 1;
					x2 = x1+3;
					x3 = x2+3;
					
					if ((pruefListe.contains(x1)) && (pruefListe.contains(x2)) && (pruefListe.contains(x3)))
					{
						k.setSpieler(this);
						kastenGewonnen = true;
					}
					break;
				}
				case 5:
				{
					int x1, x2, x3;
					x1 = 2;
					x2 = x1+3;
					x3 = x2+3;
					
					if ((pruefListe.contains(x1)) && (pruefListe.contains(x2)) && (pruefListe.contains(x3)))
					{
						k.setSpieler(this);
						kastenGewonnen = true;
					}
					break;
				}
				case 6:
				{
					int x1, x2, x3;
					x1 = 0;
					x2 = x1+4;
					x3 = x2+4;
					
					if ((pruefListe.contains(x1)) && (pruefListe.contains(x2)) && (pruefListe.contains(x3)))
					{
						k.setSpieler(this);
						kastenGewonnen = true;
					}
					break;
				}
				case 7:
				{
					int x1, x2, x3;
					x1 = 2;
					x2 = x1+2;
					x3 = x2+2;
					
					if ((pruefListe.contains(x1)) && (pruefListe.contains(x2)) && (pruefListe.contains(x3)))
					{
						k.setSpieler(this);
						kastenGewonnen = true;
					}
					break;
				}
			}
			
			if (kastenGewonnen == true)
			{
				System.out.println("Herzlichen Glückwunsch "  + this.getName() + "! Sie haben Kasten " + k.getKastenNummer() + " gewonnen!");
				break;
			}
		}
	}
}
