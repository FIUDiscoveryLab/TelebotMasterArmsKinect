package discoverylab.telebot.master.arms.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelebotMasterArmsTCPKinectView extends JFrame
{
	private JLabel portLabel = new JLabel("Port: ");
	private JTextField port = new JTextField("6666");
	private JButton connect = new JButton("Connect");
	
	private JLabel labelRightSh;
	private JLabel labelRightEl;
	private JLabel labelRightWr;
	private JLabel labelLeftSh;
	private JLabel labelLeftEl;
	private JLabel labelLeftWr;
	
	private JTextField textRightSh;
	private JTextField textRightEl;
	private JTextField textRightWr;
	private JTextField textLeftSh;
	private JTextField textLeftEl;
	private JTextField textLeftWr;
	
	public TelebotMasterArmsTCPKinectView()
	{
		JPanel viewPanel = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400,  400);
		
		labelRightSh = new JLabel("Right Shoulder: ");
		labelRightEl = new JLabel("Right Elbow: ");
		labelRightWr = new JLabel("Right Wrist: ");
		labelLeftSh = new JLabel("Left Shoulder: ");
		labelLeftEl = new JLabel("Left Elbow: ");
		labelLeftWr = new JLabel("Left Wrist: "); 
	
		textRightSh = new JTextField();
		textRightEl = new JTextField();
		textRightWr = new JTextField();
		textLeftSh = new JTextField();
		textLeftEl = new JTextField();
		textLeftWr = new JTextField();
		
		GridLayout gridLayout = new GridLayout(14, 2, 1, 1);

		viewPanel.setLayout(gridLayout);
		
		viewPanel.add(portLabel);
		viewPanel.add(port);
		viewPanel.add(connect);
		
		viewPanel.add(labelLeftSh);
		viewPanel.add(textLeftSh);
		
		viewPanel.add(labelLeftEl);
		viewPanel.add(textLeftEl);
		
		viewPanel.add(labelLeftWr);
		viewPanel.add(textLeftWr);
		
		viewPanel.add(labelRightSh);
		viewPanel.add(textRightSh);
		
		viewPanel.add(labelRightEl);
		viewPanel.add(textRightEl);
		
		viewPanel.add(labelRightWr);
		viewPanel.add(textRightWr);
		 
		
		this.add(viewPanel);
	}
	
	public int getPortNumber()
	{
		return Integer.parseInt(port.getText());
	}
	
	public void setPortNumber(int portNumber)
	{
		port.setText(Integer.toString(portNumber));
	}
	
	public String getTextRightSh() {
		return textRightSh.getText();
	}

	public void setTextRightSh(String jointType) {
		textRightSh.setText(jointType);
	}

	public String getTextRightEl() {
		return textRightEl.getText();
	}

	public void setTextRightEl(String jointType) {
		textRightEl.setText(jointType);
	}

	public String getTextRightWr() {
		return textRightWr.getText();
	}

	public void setTextRightWr(String jointType) {
		textRightWr.setText(jointType);
	}

	public String getTextLeftSh() {
		return textLeftSh.getText();
	}

	public void setTextLeftSh(String jointType) {
		textLeftSh.setText(jointType);
	}

	public String getTextLeftEl() {
		return textLeftEl.getText();
	}

	public void setTextLeftEl(String jointType) {
		textLeftEl.setText(jointType);
	}

	public String getTextLeftWr() {
		return textLeftWr.getText();
	}

	public void setTextLeftWr(String jointType) {
		textLeftWr.setText(jointType);
	}
	
	void addConnectListener(ActionListener e)
	{
		connect.addActionListener(e);
	}
	
	void displayErrorMessage(String error)
	{
		JOptionPane.showMessageDialog(this, error);
	}
	
}
