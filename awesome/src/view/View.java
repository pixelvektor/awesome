/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik 1 (Awesome App)
 * (C) 2014 Matthias Ridder, Dominic von Zielinski, Adrian Schmidt, Fabian Schneider
 * 04.12.2014
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

import data.Kasten;

/**
 * @author 
 *
 */
public class View implements ContractView, ActionListener, ContainerListener
{
	JButton[] buttons = new JButton[81];
	
	@SuppressWarnings("deprecation")
	public void showWindow(Kasten[] kaesten)
	{
		JFrame frame = new JFrame();	// Das Fenster selbst
		Container contentPane = frame.getContentPane();	// Die ContentPane fuer das Spielfeld (evtl. nochmal ueberdenken).
		Container[] kastenContainer = new Container[9];	// Ein Container fuer die einzelnen Kaesten, damit diese richtig angeordnet werden.
		
		frame.setTitle("AWESOME");
		frame.setSize(500, 500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane.setLayout(new GridLayout(3,3,10,10));	// Das Layout fuer das gesamte Spielfeld.
		contentPane.setBackground(Color.GRAY);
		
		for (int i = 0; i < 9; i++)		// Eine Schleife, um jeden der 9 Container zu fuellen.
		{
			kastenContainer[i] = new Container();			// Ein neuer Container wird erstellt.
			kastenContainer[i].addContainerListener(this);	// Der Container wird einem Listener zugeordnet, der in dieser Klasse implementiert ist.
			fillContainer(kastenContainer[i], kaesten[i]);
			kastenContainer[i].setLayout(new GridLayout(3,3,5,5));	// Das Layout fuer einen einzelnen Kasten.
			
			contentPane.add(kastenContainer[i]);	// Der Kasten wird dem Spielfeld hinzugefuegt.
		}
		
		frame.show();
	}
	/** Fuellt einen Container mit 9 Buttons und den entsprechenden Feldnummern
	 * 
	 * @param c - Der Container, der mit Buttons gefuellt werden soll. 
	 * @param k - Der Kasten, aus dem die Feldnummern geholt werden sollen.
	 */
	public void fillContainer(Container c, Kasten k)
	{
		for (int x = 0; x < 9; x++)		// Diese Schleife durchlaeuft den gesamten Kasten.
		{
			buttons[x] = new JButton("" + k.getFelder()[x].getFeldNummer());	// Es wird ein neuer Button erzeugt und die Feldnummer aus dem Kasten geholt.
			c.add(buttons[x]);	// Der neue Button wird dem Container hinzugefuegt.
		}
	}
	
	public void show() {
		int diff = 0;
		for(int i=0; i<13; i++) {
			if(i%4==0) {
				printLine();
				diff++;
			}
			else
				printNumbers(i-diff);
		}
		
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
	
	/**
	 * Das ist der ActionListener, in dem steht was bei einem Buttonklick passieren soll.
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		System.out.println();
		System.out.println("Selected: " + e.getActionCommand());
	}
	
	/**
	 * Das ist eine Methode die fuer den ContainerListener implementiert werden muss.
	 * 
	 * Diese wird aufgerufen, wenn eine Komponente zum Container hinzugefuegt wird.
	 * Ist diese Komponente ein Button, wird ihm ein ActionListener zugeordnet. 
	 */
	@Override
	public void componentAdded(ContainerEvent e)
	{
		Component c = e.getChild();		// Aus dem ContainerEvent wird die Komponente herausgelesen.
		
        if (c instanceof JButton)	// Wenn diese Kompontente ein JButton ist...
        {
    		JButton b = (JButton) c;	// ...wird die Komponente zu einem JButton gecastet...
    		b.addActionListener(this);	// ...um einen ActionListener zugewiesen bekommen zu koennen.
        }
	}

	@Override
	public void componentRemoved(ContainerEvent e)
	{
		Component c = e.getChild();
		
        if (c instanceof JButton)
        {
        	JButton b = (JButton) c;
        	b.removeActionListener(this);
        }
	}
}