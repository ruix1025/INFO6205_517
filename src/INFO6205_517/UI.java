package INFO6205_517;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class UI extends JFrame {

	private static JPanel mainPanel = null;
	private JPanel northPanel = null;
	private JPanel showPanel = null;
	
	private JLabel title;

	
	// show different graphs in the panel to show the process of GA
	private JLabel target = new JLabel();  // target graph
	private JLabel bi1 = new JLabel();     // survival 1,2,3
	private JLabel bi2 = new JLabel();
	private JLabel bi3 = new JLabel();
	private JLabel last = new JLabel();    // the last survival
	private JLabel last2 = new JLabel();
	private JLabel last3 = new JLabel();
	private JLabel last4 = new JLabel();
	private JLabel last5 = new JLabel();
	
	
	public UI() {
		System.out.println("App");
		JFrame f = new JFrame("GA - 517");
		f.setSize(800, 600);
//		f.setLocation(500, 300);
		f.add(getMainPanel(), BorderLayout.CENTER);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	
	}
	
	public JPanel getMainPanel() {
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
//		mainPanel.setBackground(Color.WHITE);
		mainPanel.add(BorderLayout.NORTH, getNorthPanel());
		mainPanel.add(BorderLayout.CENTER, getShowPanel());
		
		// set canvas
//		bgPanel = new BGCanvas();
//		setSwitchBtn(); // Set some components on the canvas for executing Dynamic rule
//		mainPanel.add(BorderLayout.CENTER, bgPanel);
//		mainPanel.add(BorderLayout.SOUTH, getcolorPanel());
//		
		return mainPanel;
	}
	
	public JPanel getNorthPanel() {
		// Create the North Panel and set the layout and background color
		northPanel = new JPanel();
		northPanel.setBackground(Color.WHITE);
		title = new JLabel("GA Process");
		Font f = new Font("Tahoma", Font.BOLD, 15);
		title.setFont(f);
		title.setPreferredSize(new Dimension(200, 40));
		
		northPanel.add(title);
		
		
		return northPanel;
	}
	
	
	public JPanel getShowPanel() {
		showPanel = new JPanel();
		showPanel.setBackground(Color.WHITE);
		
		// Show target
		setLabel(target, "test3.jpg");
		showPanel.add(target);
		
		// Show last Survival
		setLabel(last, "test3.jpg");
		showPanel.add(last);
		
		setLabel(bi1, "testtest.jpg");
		showPanel.add(bi1);
		
		setLabel(last2, "test3.jpg");
		setLabel(last3, "test3.jpg");
		setLabel(last4, "test3.jpg");
		setLabel(last5, "test3.jpg");
		showPanel.add(last2);
		showPanel.add(last3);
		showPanel.add(last4);
		showPanel.add(last5);

		
		return showPanel;
	}

	
	
	// Transfer graphs to labels
	public void setLabel(JLabel l, String name) {
        ImageIcon i = new ImageIcon(name);
        //设置ImageIcon
        l.setIcon(i);
        //label的大小设置为ImageIcon,否则显示不完整
        l.setPreferredSize(new Dimension(i.getIconWidth()*2, i.getIconHeight()*2));
	}
	
	
	public static void main(String[] args) {
		UI ui = new UI();
	}
}
