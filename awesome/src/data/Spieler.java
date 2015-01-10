/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 04.12.2014
 */
package data;

import java.awt.Color;
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
	private Color farbe;
	
	/**
	 * @param name
	 */
	public Spieler(final String name, final Color farbe)
	{
		this.name = name;
		this.farbe = farbe;
	}
	
	public Spieler (final String name)
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
	public boolean pinSetzen(Kasten[] kaesten, ArrayList<String> angebote)
	{
		boolean kastenGewonnen = false;
		boolean spielGewonnen = false;
		
		if (wuerfelErgebnis != 2)	// Pruefen, ob die 2 gewuerfelt wurde.
		{
			String eingabe = null;
			boolean falscheEingabe = false;
			
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
			
			kastenGewonnen = pruefeKasten(kaesten[kastenIndex]);
		}
		else
			pinLoeschen(kaesten);
		
		if (kastenGewonnen == true)
			spielGewonnen = pruefeSpielfeld(kaesten);
		
		return spielGewonnen;
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
	public ArrayList<String> bieteFelderAn(Kasten[] kaesten)
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
					feld.setHighlight(false);	// Setze die Variable vor der Pruefung zurueck
					
					if ((wuerfelErgebnis == 12) && (feld.getPin() == null))	// Wurde die 12 gewuerfelt, soll jedes freie Feld vorgeschlagen werden.
					{
						angebote.add(kastenIndex + "," + feldIndex);	// Die Indizes des Feldes werden der Liste als String hinzugefuegt.
						feld.setHighlight(true);	// Die Variable highlight wird im Feld auf true gesetzt, damit das Feld in der View gehighlightet werden kann.
						System.out.println("Kasten: " + (kastenIndex) + ", Feld: " + (feldIndex));
					}
					else if ((wuerfelErgebnis == feld.getFeldNummer()) && (feld.getPin() == null) || ((wuerfelErgebnis == k.getKastenNummer()) && (feld.getPin() == null)))
					// Wenn das Wuerfelergebnis mit der Feldnummer oder der kastenNummer �bereinstimmt und das Feld frei ist wird es vorgeschlagen.
					{
						if (feld.getFeldNummer() != 7)	// Das Feld 7 soll nicht vorgeschlagen werden...
						{
							angebote.add(kastenIndex + "," + feldIndex);	// Die Indizes des Feldes werden der Liste als String hinzugefuegt.
							feld.setHighlight(true);	// Die Variable highlight wird im Feld auf true gesetzt, damit das Feld in der View gehighlightet werden kann.
							System.out.println("Kasten: " + (kastenIndex) + ", Feld: " + (feldIndex));
						}
						else if ((wuerfelErgebnis == 7) && (feld.getFeldNummer() == 7))	// ...es sei denn es wurde die 7 gewuerfelt.
						{
							angebote.add(kastenIndex + "," + feldIndex);	// Die Indizes des Feldes werden der Liste als String hinzugefuegt.
							feld.setHighlight(true);	// Die Variable highlight wird im Feld auf true gesetzt, damit das Feld in der View gehighlightet werden kann.
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
	
	private boolean pruefeKasten(Kasten k)
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
		
		for (int i = 0; i <= 7; i++)
		{
			int x1,x2,x3;
			
			switch (i)
			{
				case 0:
				{
					x1 = 0;
					x2 = x1+1;
					x3 = x2+1;
					
					if ((pruefListe.contains(x1)) && (pruefListe.contains(x2)) && (pruefListe.contains(x3)))
					{
						k.setSpieler(this);
						kastenGewonnen = true;
					}
					break;
				}
				case 1:
				{
					x1 = 3;
					x2 = x1+1;
					x3 = x2+1;
					
					if ((pruefListe.contains(x1)) && (pruefListe.contains(x2)) && (pruefListe.contains(x3)))
					{
						k.setSpieler(this);
						kastenGewonnen = true;
					}
					break;
				}
				case 2:
				{
					x1 = 6;
					x2 = x1+1;
					x3 = x2+1;
					
					if ((pruefListe.contains(x1)) && (pruefListe.contains(x2)) && (pruefListe.contains(x3)))
					{
						k.setSpieler(this);
						kastenGewonnen = true;
						
					}
					break;
				}
				case 3:
				{
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
		
		return kastenGewonnen;
	}
	
	private boolean pruefeSpielfeld(Kasten[] kaesten)
	{
		ArrayList<Integer> pruefListe = new ArrayList<Integer>();
		boolean spielGewonnen = false;
		
		for (Kasten k : kaesten)
		{
			if (k.getSpieler() == this)
				pruefListe.add(k.getKastenNummer());
		}
		
		for (int i = 0; i <= 7; i++)
		{
			switch (i)
			{
				case 0:
				{
					if (pruefListe.contains(3) && pruefListe.contains(4) && pruefListe.contains(5))
						spielGewonnen = true;
				}
				case 1:
				{
					if (pruefListe.contains(6) && pruefListe.contains(7) && pruefListe.contains(8))
						spielGewonnen = true;
				}
				case 2:
				{
					if (pruefListe.contains(9) && pruefListe.contains(10) && pruefListe.contains(11))
						spielGewonnen = true;
				}
				case 3:
				{
					if (pruefListe.contains(3) && pruefListe.contains(6) && pruefListe.contains(9))
						spielGewonnen = true;
				}
				case 4:
				{
					if (pruefListe.contains(4) && pruefListe.contains(7) && pruefListe.contains(10))
						spielGewonnen = true;
				}
				case 5:
				{
					if (pruefListe.contains(5) && pruefListe.contains(8) && pruefListe.contains(11))
						spielGewonnen = true;
				}
				case 6:
				{
					if (pruefListe.contains(3) && pruefListe.contains(7) && pruefListe.contains(11))
						spielGewonnen = true;
				}
				case 7:
				{
					if (pruefListe.contains(5) && pruefListe.contains(7) && pruefListe.contains(9))
						spielGewonnen = true;
				}
			}
		}
		return spielGewonnen;
	}
	
	public Color getColor()
	{
		return farbe;
	}
}
