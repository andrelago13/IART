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
	private JButton btnPortoSmall;
	private JButton btnPortoLarge;
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
		
		JButton btnNewButton = new JButton("Porto (Large)");
		panel_1.add(btnNewButton);
		
		JButton btnPortosmall = new JButton("Porto (Small)");
		btnPortosmall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TouristGuide_PortoSmall tourSmall;
				try {
					tourSmall = new TouristGuide_PortoSmall();
					tourSmall.Screen_Porto_Small();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (XmlPullParserException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		panel_1.add(btnPortosmall);
		
		JLabel label = new JLabel("");
		label.setBounds(0, 0, 992, 725);
		frame.getContentPane().add(label);
		label.setIcon(new ImageIcon("C:\\Users\\Asus\\Documents\\GitHub\\IART\\data\\background.jpg"));
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TouristGuide tour;
				try {
					tour = new TouristGuide();
					tour.Screen_Porto_Large();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (XmlPullParserException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnPortoSmall = new JButton("Porto (Small)");
		btnPortoSmall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnPortoSmall.setBounds(10, 11, 179, 23);
		
		btnPortoLarge = new JButton("Porto (Large)");
		btnPortoLarge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadMap("data/porto-large.osm", "data/porto-monuments.txt", "data/porto-large.png", 145, 173, 1.53, 1.58);
			}
		});
		btnPortoLarge.setBounds(10, 45, 179, 23);
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
