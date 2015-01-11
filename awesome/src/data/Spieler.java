/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 04.12.2014
 */
package data;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Der Spieler
 */
public class Spieler 
{
	/** Name des Spielers. */
	private final String name;
	/** Erzielte Punkte. */
	private int punkte = 0;
	/** Aktuell gewuerfelte Zahl. */
	private int wuerfelErgebnis;
	/** Farbe des Spielers. */
	private final Color farbe;
	
	/** Erstellt einen Spieler.
	 * @param name Name des Spielers. Nicht null.
	 * @param farbe Farbe des Spielers. Nicht null.
	 */
	public Spieler(final String name, final Color farbe)
	{
		this.name = name;
		this.farbe = farbe;
	}

	/** Gibt den Namen des Spielers zurueck.
	 * @return Der Name des Spielers.
	 */
	public String getName()
	{
		return name;
	}
	
	/** Gibt die erspielten Punkte zurueck.
	 * @return Punkteanzahl.
	 */
	public int getPunkte()
	{
		return punkte;
	}
	
	/** Gibt das Wuerfelergebnis zurueck.
	 * @return Wuerfelergebnis.
	 */
	public int getWuerfelErgebnis()
	{
		return wuerfelErgebnis;
	}
	
	/** Gibt die Farbe des Spielers zurueck.
	 * @return Die Farbe des Spielers.
	 */
	public Color getColor()
	{
		return farbe;
	}
	
	/** Fuer die Testmethoden, kann spaeter geloescht werden.
	 * 
	 * @param wuerfelErgebnis Zu setzendes Ergebnis.
	 */
	@Deprecated
	public void setWuerfelErgebnis(int wuerfelErgebnis)
	{
		this.wuerfelErgebnis=wuerfelErgebnis;
	}
	
	/**
	 * Setzt einen Pin auf ein freies Feld.
	 * @param kaesten - Das Spielfeld
	 * @param feldIndex - Der Feldindex des gewaehlten Feldes
	 * @param kastenIndex - Der Kastenindex des gewaehlten Feldes
	 * @return Gibt true zurueck, wenn das Spiel gewonnen wurde. Sonst false.
	 */
	public boolean pinSetzen(final Kasten[] kaesten, final int feldIndex, final int kastenIndex)
	{
		boolean kastenGewonnen = false;
		boolean spielGewonnen = false;
		
		kaesten[kastenIndex].getFelder()[feldIndex].setPin(new Pin(this));	// Setzt den Pin in das Feld.
		erhoehePunkte();
		
		kastenGewonnen = pruefeKasten(kaesten[kastenIndex]);	// Prueft, ob der Kasten gewonnen wurde.
		
		if (kastenGewonnen == true)		// Wenn der Kasten gewonnen wurde, wird geprueft, ob das Spiel gewonnen wurde.
			spielGewonnen = pruefeSpielfeld(kaesten);
		
		return spielGewonnen;
	}

	/** Loescht einen Pin
	 * 
	 * @param kaesten - Das Spielfeld
	 * @param kastenIndex - Der Kastenindex des gewaehlten Feldes
	 * @param feldIndex - Der Feldindex des gewaehlten Feldes
	 */
	public void pinLoeschen(final Kasten[] kaesten, final int feldIndex, final int kastenIndex)
	{
		System.out.println("pinLoeschen wurde aufgerufen.\r\n");
		
		kaesten[kastenIndex].getFelder()[feldIndex].getPin().getSpieler().verringerePunkte();
		kaesten[kastenIndex].getFelder()[feldIndex].setPin(null);
	}
	
	public void bieteLoeschFelderAn(final Kasten[] kaesten)
	{
		for (Kasten k : kaesten)
		{
			if (k.getSpieler() == null)
			{
				for (Feld f : k.getFelder())
				{
					f.setHighlightToDelete(false);
					f.setHighlight(false);
					
					if ((f.getPin() != null) && (f.getPin().getSpieler() != this))
					{
						f.setHighlightToDelete(true);
					}
				}
			}
		}
	}
	
	/**
	 * Erhoeht die Punkte um 1
	 */
	public void erhoehePunkte()
	{
		punkte++;
	}
	
	/**
	 * Verringert die Punkte um 1
	 */
	public void verringerePunkte()
	{
		punkte--;
	}
	
	/** Wuerfelt fuer den Spieler eine Zufallszahl und speichert diese in der Instanzvariable wuerfelErgebnis.
	 * @return Das Wuerfelergebnis als Integer
	 */
	public int wuerfeln()
	{
		wuerfelErgebnis = (int) ((Math.random() * 11) + 2);
		return wuerfelErgebnis;
	}
	
	/** Die Methode vergleicht alle Felder mit dem Wuerfelergebnis und gibt die Indizes der Kaesten und der dazugehörigen Felder zurueck.
	 * @param kaesten Das Spielfeld
	 * @return String-ArrayList mit den Indizes der freien Felder
	 */
	public ArrayList<String> bieteFelderAn(final Kasten[] kaesten)
	{
		ArrayList<String> angebote = new ArrayList<String>();	// Diese Liste wird spaeter mit den Indizes gefuellt und ist der Rueckgabewert.
		int kastenIndex = 0;
		
		System.out.println("Freie Felder mit der Zahl " + wuerfelErgebnis + ":");
		
		for (Kasten k : kaesten)	// Eine Schleife durch alle Kaesten des Spielfeldes.
		{
			if (k.getSpieler() == null)
			{
				for (Feld feld : k.getFelder())	// Hier wird jedes Feld einzeln auf seinen Wert ueberprueft.
				{
					feld.setHighlight(false);	// Setze die Variablen vor der Pruefung zurueck
					feld.setHighlightToDelete(false);
					
					if ((wuerfelErgebnis == 12) && (feld.getPin() == null))	// Wurde die 12 gewuerfelt, soll jedes freie Feld vorgeschlagen werden.
					{
						angebote.add(kastenIndex + "," + feld.getFeldIndex());	// Die Indizes des Feldes werden der Liste als String hinzugefuegt.
						feld.setHighlight(true);	// Die Variable highlight wird im Feld auf true gesetzt, damit das Feld in der View gehighlightet werden kann.
						System.out.println("Kasten: " + (kastenIndex) + ", Feld: " + (feld.getFeldIndex()));
					}
					else if ((wuerfelErgebnis == feld.getFeldNummer()) && (feld.getPin() == null) || ((wuerfelErgebnis == k.getKastenNummer()) && (feld.getPin() == null)))
					// Wenn das Wuerfelergebnis mit der Feldnummer oder der kastenNummer �bereinstimmt und das Feld frei ist wird es vorgeschlagen.
					{
						if (feld.getFeldNummer() != 7)	// Das Feld 7 soll nicht vorgeschlagen werden...
						{
							angebote.add(kastenIndex + "," + feld.getFeldIndex());	// Die Indizes des Feldes werden der Liste als String hinzugefuegt.
							feld.setHighlight(true);	// Die Variable highlight wird im Feld auf true gesetzt, damit das Feld in der View gehighlightet werden kann.
							System.out.println("Kasten: " + (kastenIndex) + ", Feld: " + (feld.getFeldIndex()));
						}
						else if ((wuerfelErgebnis == 7) && (feld.getFeldNummer() == 7))	// ...es sei denn es wurde die 7 gewuerfelt.
						{
							angebote.add(kastenIndex + "," + feld.getFeldIndex());	// Die Indizes des Feldes werden der Liste als String hinzugefuegt.
							feld.setHighlight(true);	// Die Variable highlight wird im Feld auf true gesetzt, damit das Feld in der View gehighlightet werden kann.
							System.out.println("Kasten: " + (kastenIndex) + ", Feld: " + (feld.getFeldIndex()));
						}
					}
				}
			}
			kastenIndex++;
		}
		return angebote;
	}
	
	/**
	 * Prueft, ob der Kasten gewonnen wurde
	 * @param k - zu pruefender Kasten
	 * @return true, wenn der Kasten gewonnen wurde. Sonst false.
	 */
	private boolean pruefeKasten(final Kasten k)
	{
		System.out.println("pruefeKasten wurde für den Kasten " + k.getKastenNummer() + " aufgerufen.");
		
		ArrayList<Integer> pruefListe = new ArrayList<Integer>();	// In diese Liste kommen die Indizes der gewonnenen Felder.
		int pruefIndex = 0;		// Gibt den Index eines Feldes an. Wird nach jedem Durchlauf um 1 erhoeht.
		boolean kastenGewonnen = false;		// Wird auf true gesetzt, wenn der Kasten gewonnen wurde.
		
		for (Feld f : k.getFelder())	// Durchlaeuft jedes Feld im Kasten.
		{
			if (f.getPin() != null)		// Wenn das Feld eine Pin besitzt...
			{
				if (f.getPin().getSpieler() == this)	// ...wird geprueft, ob es der Pin des aktuellen Spielers ist.
				{
					pruefListe.add(pruefIndex);		// wenn das der Fall ist, wird der pruefIndex zur pruefListe hinzugefuegt.
				}
			}
			pruefIndex++;	// Nach einem Durchlauf wird der pruefIndex um 1 erhoeht.
		}
		
		for (int i = 0; i <= 7; i++)	// Diese Schleife durchlaeuft (wenn noetig) alle 8 moeglichen Faelle, in denen ein Kasten gewonnen ist.
		{
			int x1,x2,x3;	// Deklaration der Indexvariablien mit denen geprueft wird.
			
			switch (i)
			{
				case 0:		// Waagerecht 1. Zeile.
				{
					x1 = 0;
					x2 = x1+1;
					x3 = x2+1;
					
					kastenGewonnen = checkList(k, pruefListe, x1, x2, x3);
					break;
				}
				case 1:		// Waagerecht 2. Zeile.
				{
					x1 = 3;
					x2 = x1+1;
					x3 = x2+1;
					
					kastenGewonnen = checkList(k, pruefListe, x1, x2, x3);
					break;
				}
				case 2:		// Waagerecht 3. Zeile.
				{
					x1 = 6;
					x2 = x1+1;
					x3 = x2+1;
					
					kastenGewonnen = checkList(k, pruefListe, x1, x2, x3);
					break;
				}
				case 3:		// Senkrecht linke Spalte.
				{
					x1 = 0;
					x2 = x1+3;
					x3 = x2+3;
					
					kastenGewonnen = checkList(k, pruefListe, x1, x2, x3);
					break;
				}
				case 4:		// Senkrecht mittlere Spalte.
				{
					x1 = 1;
					x2 = x1+3;
					x3 = x2+3;
					
					kastenGewonnen = checkList(k, pruefListe, x1, x2, x3);
					break;
				}
				case 5:		// Senkrecht rechte Spalte.
				{
					x1 = 2;
					x2 = x1+3;
					x3 = x2+3;
					
					kastenGewonnen = checkList(k, pruefListe, x1, x2, x3);
					break;
				}
				case 6:		// Diagonal von oben links nach unten rechts.
				{
					x1 = 0;
					x2 = x1+4;
					x3 = x2+4;
					
					kastenGewonnen = checkList(k, pruefListe, x1, x2, x3);
					break;
				}
				case 7:		// Diagonal von oben rechts nach unten links.
				{
					x1 = 2;
					x2 = x1+2;
					x3 = x2+2;
					
					kastenGewonnen = checkList(k, pruefListe, x1, x2, x3);
					break;
				}
			}
			
			if (kastenGewonnen == true)		// Wenn ein Kasten gewonnen wurde...
			{
				k.setSpieler(this);		// ...wird dem Kasten zuerst der Spieler uebergeben.

				for (Feld f : k.getFelder())	// Die Schleife durchlaeuft jeden Feld im Kasten.
				{
					if ((f.getPin() != null) && (f.getPin().getSpieler() != this))	// Pruefen, ob das aktuelle Feld dem Gegner gehoert.
					{
						f.getPin().getSpieler().verringerePunkte();		// Die gegnerischen Punkte verringern.
						erhoehePunkte();	// Die eigenen Punkte erhoehen.
					}
					
					if (f.getPin() == null)		// Wenn das aktuelle Feld noch niemandem gehoert...
					{
						erhoehePunkte();		// ...werden nur die eigenen Punkte erhoeht.
					}
						
					f.setHighlight(false);		// Das Feld soll ich mehr hervorgehoben werden.
					f.setPin(new Pin(this));	// Des Spieler setzt seinen Pin in das Feld.
				}
				
				System.out.println("Herzlichen Glückwunsch "  + this.getName() + "! Sie haben Kasten " + k.getKastenNummer() + " gewonnen!");
				break;		// Sobald ein Kasten gewonnen wurde, wird die for-Schleife verlassen.
			}
		}
		
		return kastenGewonnen;
	}

	/**
	 * Ausgelagerte Hilfsmethode fuer die Methode pruefeKasten().
	 * Prueft anhand der uebergebenen Indizes, ob ein Kasten gewonnen wurde.
	 * @param k - Der Kasten, der geprueft werden soll.
	 * @param pruefListe - Die Liste mit den Indizes der Feld, die der Spieler gewonnen hat.
	 * @param x1 - Index 1
	 * @param x2 - Index 2
	 * @param x3 - Index 3
	 * @return true, wenn der Kasten gewonnen wurde. Sonst false.
	 */
	private boolean checkList(final Kasten k, final ArrayList<Integer> pruefListe,
			int x1, int x2, int x3)
	{
		boolean kastenGewonnen = false;
		if ((pruefListe.contains(x1)) && (pruefListe.contains(x2)) && (pruefListe.contains(x3)))	// wenn die Liste alle 3 Indizes enthaelt...
			kastenGewonnen = true;	// wird der Rueckgabewert auf true gesetzt.
		
		return kastenGewonnen;
	}
	
	/** Prueft ob der Spieler das Spiel gewonnen hat.
	 * 
	 * @param kaesten Das Spielfeld. Nicht null.
	 * @return true wenn gewonnen. Sonst false.
	 */
	private boolean pruefeSpielfeld(final Kasten[] kaesten)
	{
		ArrayList<Integer> pruefListe = new ArrayList<Integer>();	// Eine Liste, der die Nummern der gewonnenen Kaesten hinzugefuegt werden. 
		boolean spielGewonnen = false;
		
		for (Kasten k : kaesten)	// Die Schleife durchlaeuft jeden Kasten im Spielfeld.
		{
			if (k.getSpieler() == this)		// Wenn dem Spieler der Kasten gehoert...
				pruefListe.add(k.getKastenNummer());	// ...wird die Nummer des Kastens zur Liste hinzugefuegt.
		}
		
		for (int i = 0; i <= 7; i++)	// Es werden (wenn noetig) alle 8 moeglichen Faelle zum gewinnen des Spiels geprueft.
		{
			switch (i)
			{
				case 0:		// Waagerecht 1. Zeile.
				{
					if (pruefListe.contains(3) && pruefListe.contains(4) && pruefListe.contains(5))
						spielGewonnen = true;
					break;
				}
				case 1:		// Waagerecht 2. Zeile.
				{
					if (pruefListe.contains(6) && pruefListe.contains(7) && pruefListe.contains(8))
						spielGewonnen = true;
					break;
				}
				case 2:		// Waagerecht 2. Zeile.
				{
					if (pruefListe.contains(9) && pruefListe.contains(10) && pruefListe.contains(11))
						spielGewonnen = true;
					break;
				}
				case 3:		// Senkrecht linke Spalte.
				{
					if (pruefListe.contains(3) && pruefListe.contains(6) && pruefListe.contains(9))
						spielGewonnen = true;
					break;
				}
				case 4:		// Senkrecht mittlere Spalte.
				{
					if (pruefListe.contains(4) && pruefListe.contains(7) && pruefListe.contains(10))
						spielGewonnen = true;
					break;
				}
				case 5:		// Senkrecht rechte Spalte.
				{
					if (pruefListe.contains(5) && pruefListe.contains(8) && pruefListe.contains(11))
						spielGewonnen = true;
					break;
				}
				case 6:		// Diagonal von oben links nach unten rechts.
				{
					if (pruefListe.contains(3) && pruefListe.contains(7) && pruefListe.contains(11))
						spielGewonnen = true;
					break;
				}
				case 7:		// Diagonal von oben rechts nach unten links.
				{
					if (pruefListe.contains(5) && pruefListe.contains(7) && pruefListe.contains(9))
						spielGewonnen = true;
				}
			}
			
			if (spielGewonnen == true)
			{
				System.out.println(this.getName() + " hat das Spiel gewonnen!");
				break;
			}
		}

		return spielGewonnen;
	}
}
