package test;

import gui.GraphPanel;
import gui.ProgressListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.xmlpull.v1.XmlPullParserException;

import graph.Monument;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JSlider;


public class TestGUI implements ProgressListener {

	private JFrame frame;
	
	private GraphPanel panel;
	private JPanel panel_1;
	private JProgressBar progressBar = new JProgressBar();;
	private JButton btnPortoSmall;
	private JSlider slider;
	private int sliderValue;
	private JTextField textField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestGUI window = new TestGUI();
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
	public TestGUI() throws FileNotFoundException, IOException, XmlPullParserException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws FileNotFoundException, IOException, XmlPullParserException {		
		frame = new JFrame("Tourist Guide Porto (Small)");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(new Dimension(1000, 700));
		frame.setLocation(screenSize.width/2-frame.getSize().width/2, screenSize.height/2-frame.getSize().height/2);
		
		panel = new GraphPanel(frame);
		frame.getContentPane().add(panel);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		frame.getContentPane().add(panel_1, BorderLayout.WEST);
		panel_1.setPreferredSize(new Dimension((int) (frame.getWidth()*(1 - GraphPanel.WIDTH_PERCENTAGE)), frame.getHeight()));
		panel_1.setLayout(null);
	
		btnPortoSmall = new JButton("Run");
		btnPortoSmall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadMap("data/porto-small.osm", "data/porto-monuments.txt", "data/porto-small.png", 145, 213, 1.38, 1.62);
			}
		});
		btnPortoSmall.setBounds(10, 11, 179, 23);
		panel_1.add(btnPortoSmall);
		
		slider = new JSlider();
		slider.setMinorTickSpacing(2);
		slider.setMajorTickSpacing(100);
		slider.setBounds(10, 140, 133, 42);
		slider.setPaintLabels(true);
		sliderValue = slider.getValue();
		panel_1.add(slider);
        
        if (!slider.getValueIsAdjusting())
            System.out.println(slider.getValue());
	    
		textField = new JTextField();
		textField.setBounds(155, 142, 32, 22);
		panel_1.add(textField);
		textField.setColumns(10);
		textField.setText("" + sliderValue);
		
		

		panel_1.setVisible(true);

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
					onMapLoaded();
				} catch (IOException | XmlPullParserException e) {
					JOptionPane.showMessageDialog(null, "Unable to load map \"data/porto-small.osm\"", "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
		    }
		}).start();
	}
	
	private void onMapLoaded() {
		JComboBox<Monument> comboBox = new JComboBox<Monument>();
		comboBox.setBounds(12, 47, 177, 20);
		ArrayList<Monument> monuments = panel.getMonuments();
		for(int i = 0; i < monuments.size(); ++i) {
			comboBox.addItem(monuments.get(i));
		}
		
		comboBox.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        System.out.println(comboBox.getSelectedItem());
		    }
		});
		panel_1.add(comboBox);
		comboBox.setVisible(true);
		
		slider = new JSlider();
		slider.setMinorTickSpacing(2);
		slider.setMajorTickSpacing(10);
		slider.setBounds(10, 79, 179, 26);
		slider.setValue(0);
		panel_1.add(slider);
		
		panel_1.revalidate();
		panel_1.repaint();
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

	public void updateProgress(int progress) {
		progressBar.setValue(progress);
		frame.repaint();
		frame.revalidate();
	}
}