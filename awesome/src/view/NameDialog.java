package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import data.Spieler;

public class NameDialog
{
	private Spieler[] spieler = new Spieler[2];
	private JTextField txtName1, txtName2;
	private JDialog dialog;
	private Frame mainWindow;
	
	public NameDialog(Frame o, Spieler[] spieler)
	{
		this.spieler = spieler;
		mainWindow = o;
		showNameDialog(o);
	}
	
	@SuppressWarnings("deprecation")
	public void showNameDialog(Frame o)
	{
		dialog = new JDialog(o, true);
		Container contentPane = dialog.getContentPane();
		Container centerPane = new Container();
		Container bottomPane = new Container();
		JButton btnOK = new JButton("OK");
		JButton btnAbort = new JButton("Abbrechen");
		JLabel lblName1 = new JLabel("Spieler 1:    ");
		JLabel lblName2 = new JLabel("Spieler 2:    ");
		txtName1 = new JTextField(20);
		txtName2 = new JTextField(20);
		JLabel lblTitle = new JLabel("Bitte geben Sie die Spielernamen ein");
		
		lblTitle.setHorizontalAlignment(0);
		
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int x, y, width, height;
		
		width = 400;
		height = 150;
		x = (screensize.width / 2) - (width / 2);
		y = (screensize.height / 2) - (height / 2);
				
		dialog.setTitle("Namenseingabe");
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setBounds(x, y, width, height);
		
		contentPane.add(lblTitle, BorderLayout.NORTH);

		centerPane.setLayout(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();
		
		cs.gridx = 0;
		cs.gridy = 0;
		cs.ipady = 15;
		centerPane.add(lblName1, cs);
		
		cs.gridx = 1;
		cs.gridy = 0;
		cs.ipady = 0;
		centerPane.add(txtName1, cs);
		
		cs.gridx = 0;
		cs.gridy = 1;
		centerPane.add(lblName2, cs);
		
		cs.gridx = 1;
		cs.gridy = 1;
		centerPane.add(txtName2, cs);
		
		contentPane.add(centerPane, BorderLayout.CENTER);
		
		bottomPane.setLayout(new FlowLayout());
		
		btnOK.addActionListener(new OKButtonListener());
		bottomPane.add(btnOK);
		
		btnAbort.addActionListener(new AbortButtonListener());
		bottomPane.add(btnAbort);

		contentPane.add(bottomPane, BorderLayout.SOUTH);
		
		dialog.show();
	}
	
	class OKButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if ((txtName1.getText().length() != 0) && (txtName2.getText().length() != 0))
			{
				spieler[0] = new Spieler(txtName1.getText());
				spieler[1] = new Spieler(txtName2.getText());
				
				dialog.dispose();
			}
			else
			{
				String message = "Sie müssen zwei Namen eingeben!";
				JOptionPane.showMessageDialog(dialog, message, "Fehler", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	class AbortButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
		
	}
}