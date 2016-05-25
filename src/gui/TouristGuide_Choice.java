package gui;

import graph.Monument;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;

import org.xmlpull.v1.XmlPullParserException;

public class TouristGuide_Choice implements ProgressListener {
	protected String frame_title = "";
	protected String graph_path = "";
	protected String monuments_path = "";
	protected String background_path = "";
	protected int bckgrd_x_pos = 0;
	protected int bckgrd_y_pos = 0;
	protected double bckgrd_x_scale = 1;
	protected double bckgrd_y_scale = 1;
	
	protected JFrame frame;
	
	private GraphPanel panel;
	private JPanel panel_1;
	private JProgressBar progressBar = new JProgressBar();
	private JButton btnPortoLarge;
	
	public static void ScreenStart() {}

	/**
	 * Create the application.
	 * @throws XmlPullParserException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public TouristGuide_Choice() throws FileNotFoundException, IOException, XmlPullParserException {}

	/**
	 * Initialize the contents of the frame.
	 * @throws XmlPullParserException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void initialize() throws FileNotFoundException, IOException, XmlPullParserException {		
		frame = new JFrame(frame_title);
		frame.setResizable(false);
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
		

		btnPortoLarge = new JButton("Run");
		btnPortoLarge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadMap();
			}
		});
		btnPortoLarge.setBounds(12, 13, 179, 23);
		panel_1.add(btnPortoLarge);
		
		panel_1.setVisible(true);

}
	
	private void loadMap() {	
		final ProgressListener pl = this;
		
		new Thread( new Runnable() {
		    @Override
		    public void run() {
		    	try {
					initProgressBar();
					panel.init(graph_path, pl, background_path, bckgrd_x_pos, bckgrd_y_pos, bckgrd_x_scale, bckgrd_y_scale);
					updateProgress(90);
					if(monuments_path != null)
						panel.parseMonuments(monuments_path);
					updateProgress(100);
					removeProgressBar();
					onMapLoaded();
				} catch (IOException | XmlPullParserException e) {
					JOptionPane.showMessageDialog(null, "Unable to load map \"" + graph_path + "\"", "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
		    }
		}).start();
	}
	
	private void onMapLoaded() {
		JComboBox<Monument> comboBox = new JComboBox<Monument>();
		comboBox.setBounds(12, 47, 177, 20);
		comboBox.addItem(new Monument(0, "test1", 0, null, 0));
		comboBox.addItem(new Monument(0, "test2", 0, null, 0));
		comboBox.addItem(new Monument(0, "test3", 0, null, 0));
		comboBox.addItem(new Monument(0, "test4", 0, null, 0));
		comboBox.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        System.out.println(comboBox.getSelectedItem());
		    }
		});
		panel_1.add(comboBox);
		comboBox.setVisible(true);
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

	@Override
	public void updateProgress(int progress) {
		progressBar.setValue(progress);
		frame.repaint();
		frame.revalidate();
	}
}
