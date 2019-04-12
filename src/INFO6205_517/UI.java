package INFO6205_517;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

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
	
	private JComboBox<String> selectionBox;
	
	// show different graphs in the panel to show the process of GA
	private JLabel target = new JLabel();  // target graph
	private JLabel bi1 = new JLabel();     // survival 1,2,3
	private JLabel bi2 = new JLabel();
	private JLabel bi3 = new JLabel();
	private JLabel last = new JLabel();    // the last survival
	
	
	public UI() {
		System.out.println("App");
		JFrame f = new JFrame("GA - 517");
		f.setSize(1200, 1000);
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
		
		return mainPanel;
	}
	
	public JPanel getNorthPanel() {
		// Create the North Panel and set the layout and background color
		northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout());
		northPanel.setBackground(Color.WHITE);
		title = new JLabel("Please See the GA process!");
		
		// Create ComboBox 可以用于选择不同的图片，这个还没有实现，最后完善
		selectionBox = new JComboBox<String>();
		selectionBox.addItem("Select a Graph");
		selectionBox.addItem("Pic1");
		selectionBox.addItem("Pic2");
		selectionBox.addItem("Pic3");
//		selectionBox.addItemListener(this);
		selectionBox.setEnabled(true);
//		selectionBox.addActionListener(this);
		
		// Add components on panel
		northPanel.add(selectionBox);
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
		
		setLabel(bi1, "test3.jpg");
		showPanel.add(bi1);
		
		setLabel(bi2, "panda2.jpg");
		showPanel.add(bi2);
		
		return showPanel;
	}

	
	
	// Transfer graphs to labels
	public void setLabel(JLabel l, String name) {
        ImageIcon i = new ImageIcon(name);
        //设置ImageIcon
        l.setIcon(i);
        //label的大小设置为ImageIcon,否则显示不完整
        l.setBounds(50, 50, i.getIconWidth(), i.getIconHeight());
	}
	
	
	public static void main(String[] args) {
		UI ui = new UI();
	}
}
