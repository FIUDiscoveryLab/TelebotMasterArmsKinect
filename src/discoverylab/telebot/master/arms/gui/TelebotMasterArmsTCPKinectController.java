package discoverylab.telebot.master.arms.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import TelebotDDSCore.Source.Java.Generated.master.arms.TMasterToArms;
import TelebotDDSCore.Source.Java.Generated.master.arms.TOPIC_MASTER_TO_SLAVE_ARMS;
import discoverylab.telebot.master.arms.TelebotMasterArmsTCPKinectComponent;

public class TelebotMasterArmsTCPKinectController 
{
	private TelebotMasterArmsTCPKinectView view;
	private TelebotMasterArmsTCPKinectComponent telebotMasterArms;
	
	public TelebotMasterArmsTCPKinectController(TelebotMasterArmsTCPKinectView view)
	{
		this.view = view;
		this.view.addConnectListener(new ConnectListener());
	}
	
	public class DataListener
	{
		public void changeText(String joint, String data)
		{
			String jointType = joint;
			String jointData = data;
			

			if(jointType.equals("left_shoulder"))
			{
				view.setTextLeftSh(jointData);
			}
			else if(jointType.equals("left_elbow"))
			{
				view.setTextLeftEl(jointData);
			}
			else if(jointType.equals("left_wrist"))
			{
				view.setTextLeftWr(jointData);
			}
			else if(jointType.equals("right_shoulder"))
			{
				view.setTextRightSh(jointData);
			}
			else if(jointType.equals("right_elbow"))
			{
				view.setTextRightEl(jointData);
			}
			else if(jointType.equals("right_wrist"))
			{
				view.setTextRightWr(jointData);
			}
		}
	}
	
	class ConnectListener implements ActionListener
	{
		DataListener listener = new DataListener();
		
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				int portNumber = view.getPortNumber();
				
				telebotMasterArms = new TelebotMasterArmsTCPKinectComponent(listener, portNumber);
				
				//Initiate Slave Component Device
				telebotMasterArms.initiate();
				
				//Initiate Transmission Protocol
				telebotMasterArms.initiateTransmissionProtocol(TOPIC_MASTER_TO_SLAVE_ARMS.VALUE, TMasterToArms.class);
				
				//Initiate DataWriter
				telebotMasterArms.initiateDataWriter();
			}
			catch(NumberFormatException exception)
			{
				view.displayErrorMessage("Please enter a valid port number.");
			}
		}
	}
	
}
