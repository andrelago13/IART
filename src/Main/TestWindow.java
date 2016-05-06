package Main;

import gui.GraphPanel;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.xmlpull.v1.XmlPullParserException;

import java.awt.BorderLayout;
import java.io.IOException;

public class TestWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestWindow window = new TestWindow();
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
	public TestWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GraphPanel panel;
		try {
			panel = new GraphPanel("data/porto-small.osm", frame);
			frame.getContentPane().add(panel, BorderLayout.CENTER);
		} catch (IOException | XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
