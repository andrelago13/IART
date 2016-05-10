package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.xmlpull.v1.XmlPullParserException;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TouristGuide {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TouristGuide window = new TouristGuide();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws XmlPullParserException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public TouristGuide() throws FileNotFoundException, IOException, XmlPullParserException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws XmlPullParserException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private void initialize() throws FileNotFoundException, IOException, XmlPullParserException {		
		frame = new JFrame("Tourist Guide");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize);
		
		GraphPanel panel = new GraphPanel(frame);
		frame.getContentPane().add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		frame.getContentPane().add(panel_1, BorderLayout.WEST);
		panel_1.setPreferredSize(new Dimension((int) (frame.getWidth()*(1 - GraphPanel.WIDTH_PERCENTAGE)), frame.getHeight()));
		panel_1.setLayout(null);
		
		JButton btnPortoSmall = new JButton("Porto (Small)");
		btnPortoSmall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					panel.init("data/porto-small.osm");
				} catch (IOException | XmlPullParserException e1) {
					JOptionPane.showMessageDialog(null, "Unable to load map \"data/porto-small.osm\"", "Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		btnPortoSmall.setBounds(92, 11, 198, 23);
		panel_1.add(btnPortoSmall);
		
		JButton btnPortoLarge = new JButton("Porto (Large)");
		btnPortoLarge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					panel.init("data/porto-large.osm");
				} catch (IOException | XmlPullParserException e) {
					JOptionPane.showMessageDialog(null, "Unable to load map \"data/porto-large.osm\"", "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
		btnPortoLarge.setBounds(92, 43, 198, 23);
		panel_1.add(btnPortoLarge);
	}
}
