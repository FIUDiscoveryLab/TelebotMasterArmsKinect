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
	
	class ConnectListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				int portNumber = view.getPortNumber();
				
				telebotMasterArms = new TelebotMasterArmsTCPKinectComponent(portNumber);
				
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
