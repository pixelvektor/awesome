/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 04.12.2014
 */
package view;

/**
 * @author 
 *
 */
public class View implements ContractView
{
	public void show(final String activePlayer, final int pip) {
		int diff = 0;
		for(int i=0; i<13; i++) {
			if(i%4==0) {
				printLine();
				diff++;
			}
			else
				printNumbers(i-diff);
		}
		printPlayer(activePlayer, pip);
		
	}
	
	private void printLine() {
		System.out.println("----------------------------");
	}
	
	private void printNumbers(final int i) {
		System.out.println("| 3  9 11| 3  9 11| 3  9 11|");
	}
	
	private void printPlayer(final String player, final int pip) {
		System.out.println("\n" + player + " wuerfelt die " + pip);
	}
}
