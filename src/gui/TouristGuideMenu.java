package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;

import org.xmlpull.v1.XmlPullParserException;

import java.awt.Image;
import javax.swing.SwingUtilities;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.JTextPane;
import java.awt.Font;

public class TouristGuideMenu implements ProgressListener {

	private GraphPanel panel;
	private JFrame frame;
	private JProgressBar progressBar = new JProgressBar();
	private JPanel panel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TouristGuideMenu window = new TouristGuideMenu();
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
	public TouristGuideMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new GraphPanel(frame);
		frame.getContentPane().add(panel);
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(411, 573, 156, 67);
		frame.getContentPane().add(panel_1);
		
		JButton btnPortoLarge = new JButton("Porto (Large)");
		panel_1.add(btnPortoLarge);
		
		JButton btnPortosmall = new JButton("Porto (Small)");
		btnPortosmall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TouristGuide_PortoSmall.ScreenStart();
			}
		});
		panel_1.add(btnPortosmall);
		
		JLabel label = new JLabel("");
		label.setBounds(0, 0, 992, 725);
		frame.getContentPane().add(label);
		label.setIcon(new ImageIcon("data/background.jpg"));
		
		btnPortoLarge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TouristGuide_PortoLarge.ScreenStart();
			}
		});
	}
	
	private void setDefaultCloseOperation(int exitOnClose) {
		// TODO Auto-generated method stub
		
	}

	private void loadMap(String graphpath, String monumentspath, String backgroundPath, int x_pos, int y_pos, double x_scale, double y_scale) {	
		final ProgressListener pl = this;
		
		new Thread( new Runnable() {
		    @Override
		    public void run() {
		    	try {
					initProgressBar();
					panel.init(graphpath, pl, backgroundPath, x_pos, y_pos, x_scale, y_scale);
					updateProgress(90);
					if(monumentspath != null)
						panel.parseMonuments(monumentspath);
					updateProgress(100);
					removeProgressBar();
				} catch (IOException | XmlPullParserException e) {
					JOptionPane.showMessageDialog(null, "Unable to load map \"data/porto-large.osm\"", "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
		    }
		}).start();
	}
	
	private void initProgressBar() {
		updateProgress(0);
		frame.remove(progressBar);
		progressBar.setBounds(38, 79, 130, 14);
		progressBar.setForeground(Color.GREEN);
		panel_1.add(progressBar);
		frame.repaint();
		frame.revalidate();
	}
	
	private void removeProgressBar() {
		panel_1.remove(progressBar);
		frame.repaint();
		frame.revalidate();
	}

	@Override
	public void updateProgress(int progress) {
		progressBar.setValue(progress);
		frame.repaint();
		frame.revalidate();
	}
}
