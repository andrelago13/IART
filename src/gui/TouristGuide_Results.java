package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JTextArea;

import algorithm.Solution;
import java.awt.SystemColor;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;

public class TouristGuide_Results {

	private JFrame frame;
	private Solution solu;
	/**
	 * Launch the application.
	 */
	public static void Results(Solution sol) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TouristGuide_Results window = new TouristGuide_Results(sol);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TouristGuide_Results(Solution sol) {
		solu = sol;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.window);
		frame.setBounds(100, 100, 450, 300);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(new Dimension(1000, 700));
		frame.setLocation(screenSize.width/2-frame.getSize().width/2, screenSize.height/2-frame.getSize().height/2);
		frame.getContentPane().setLayout(null);
		
		JLabel lblResults = new JLabel("Results");
		lblResults.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblResults.setBounds(37, 30, 152, 16);
		frame.getContentPane().add(lblResults);
		
/*		JTextArea textArea = new JTextArea();
		textArea.setBounds(37, 83, 863, 381);
		textArea.append(solu.toString());
*/		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBounds(37, 83, 863, 500);
//		frame.getContentPane().add(editorPane);
		editorPane.setText(solu.toString());

		JScrollPane scrollPane = new JScrollPane(editorPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(37, 83, 863, 500);
		scrollPane.setVisible(true);
		frame.getContentPane().add(scrollPane);
		
		frame.revalidate();
		frame.repaint();
	
	}
}
