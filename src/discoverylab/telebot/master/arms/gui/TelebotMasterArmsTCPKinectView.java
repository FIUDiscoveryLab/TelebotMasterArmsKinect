package discoverylab.telebot.master.arms.gui;

import java.awt.FlowLayout;
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
	
	public TelebotMasterArmsTCPKinectView()
	{
		JPanel viewPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500,  150);
		
		viewPanel.add(portLabel);
		viewPanel.add(port);
		viewPanel.add(connect);
		
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
	
	void addConnectListener(ActionListener e)
	{
		connect.addActionListener(e);
	}
	
	void displayErrorMessage(String error)
	{
		JOptionPane.showMessageDialog(this, error);
	}
	
}
