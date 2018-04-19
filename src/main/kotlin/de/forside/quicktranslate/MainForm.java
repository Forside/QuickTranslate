package de.forside.quicktranslate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainForm extends JFrame {

	private JPanel rootPanel;
	private JButton buttonTest;

	public MainForm(String title) {
		super(title);
		setContentPane(rootPanel);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
	}

}
