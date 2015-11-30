package discoverylab.telebot.master.arms.test;

import static discoverylab.util.logging.LogUtils.*;
import discoverylab.telebot.master.arms.gui.TelebotMasterArmsTCPKinectController;
import discoverylab.telebot.master.arms.gui.TelebotMasterArmsTCPKinectView;

public class TelebotMasterArmsTCPKinectTest {

	public static String TAG = makeLogTag("TelebotMasterArmsTCPKinectTest");
	
	public static void main(String args []) {
		
		TelebotMasterArmsTCPKinectView view = new TelebotMasterArmsTCPKinectView();
		TelebotMasterArmsTCPKinectController controller = new TelebotMasterArmsTCPKinectController(view);
		
		view.setVisible(true);
		

	}
}
