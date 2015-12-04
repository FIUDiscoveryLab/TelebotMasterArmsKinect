package discoverylab.telebot.master.arms;

import static discoverylab.util.logging.LogUtils.*;
import discoverylab.telebot.master.arms.configurations.MasterArmsConfig;
import discoverylab.telebot.master.arms.configurations.SensorConfig;
import discoverylab.telebot.master.arms.gui.TelebotMasterArmsTCPKinectController;
import discoverylab.telebot.master.arms.gui.TelebotMasterArmsTCPKinectController.DataListener;
import discoverylab.telebot.master.arms.mapper.KinectDataMapper;
import discoverylab.telebot.master.arms.model.KinectDataModel;

import com.rti.dds.infrastructure.InstanceHandle_t;

import TelebotDDSCore.Source.Java.Generated.master.arms.TMasterToArms;
import TelebotDDSCore.Source.Java.Generated.master.arms.TMasterToArmsDataWriter;
import discoverylab.telebot.master.arms.parser.KinectDataParser;
import discoverylab.telebot.master.core.component.CoreMasterTCPComponent;
import discoverylab.telebot.master.core.socket.CoreServerSocket;


public class TelebotMasterArmsTCPKinectComponent extends CoreMasterTCPComponent implements CoreServerSocket.SocketEventListener{
	
	public static String TAG = makeLogTag(TelebotMasterArmsTCPKinectComponent.class);
	
	private KinectDataParser parser;
	private KinectDataMapper mapper;
	private DataListener listener;
	
	private int defaultSpeed = 100;
	
	private int[] jointPositions;
	
	private TMasterToArmsDataWriter writer;
	TMasterToArms instance = new TMasterToArms();
	InstanceHandle_t instance_handle = InstanceHandle_t.HANDLE_NIL;
	
	public TelebotMasterArmsTCPKinectComponent(DataListener listener, int portNumber) {
		super(portNumber);
		parser = new KinectDataParser();
		mapper = new KinectDataMapper();
		this.listener = listener;
		
		jointPositions = new int[14];
		
		for(int i = 0; i < jointPositions.length; i++)
		{
			jointPositions[i] = -1;
		}
	}
	
	/**
	 * Cast the Writer to our Arms DataWriter
	 * This allows us to publish the appropriate Topic data
	 */
	public void initiateDataWriter(){
		writer = (TMasterToArmsDataWriter) getDataWriter();
	}


	@Override
	public void callback(String data) { //try synchronized
		
		LOGI(TAG, "DATA: " + data );
		KinectDataModel kinectDataInstance = (KinectDataModel) parser.parse(data);
		
		String jointType = kinectDataInstance.getJointType();
		
		listener.changeText(jointType, data);
		
		int x, y, z;
		int servoOnePosition, servoTwoPosition;
		
		if(jointType.equals("head"))
		{
			x = kinectDataInstance.getX();
			y = kinectDataInstance.getY();
			
			servoOnePosition = mapper.map(
					x, 
					SensorConfig.HEAD_X_MAX, 
					SensorConfig.HEAD_X_MIN, 
					MasterArmsConfig.HEAD_PITCH_MAX, 
					MasterArmsConfig.HEAD_PITCH_MIN
					);
			
			servoTwoPosition = mapper.map(
					y, 
					SensorConfig.HEAD_Y_MAX, 
					SensorConfig.HEAD_Y_MIN, 
					MasterArmsConfig.HEAD_YAW_MAX, 
					MasterArmsConfig.HEAD_YAW_MIN
					);
			
			if(servoOnePosition != jointPositions[0])
			{
				System.out.println("HEYYYYYY!");
				instance.servoId = 10;
				instance.servoPositon = servoOnePosition;
				instance.servoSpeed = defaultSpeed;
				writer.write(instance, instance_handle);
				jointPositions[0] = servoOnePosition;
			}

			if(servoTwoPosition != jointPositions[1])
			{
				instance.servoId = 11;
				instance.servoPositon = servoTwoPosition;
				instance.servoSpeed = defaultSpeed;
				writer.write(instance, instance_handle);
				jointPositions[1] = servoTwoPosition;
			}
		}
		else if(jointType.equals("left_shoulder"))
		{
			x = kinectDataInstance.getX();
			y = kinectDataInstance.getY();
			
			servoOnePosition = mapper.map( 
					y, 
					SensorConfig.SHOULDER_LEFT_Y_MAX, 
					SensorConfig.SHOULDER_LEFT_Y_MIN, 
					MasterArmsConfig.ARM_ROLL_LEFT_MAX, 
					MasterArmsConfig.ARM_ROLL_LEFT_MIN
					);
			
			servoTwoPosition = mapper.map( 
					x, 
					SensorConfig.SHOULDER_LEFT_X_MAX, 
					SensorConfig.SHOULDER_LEFT_X_MIN, 
					MasterArmsConfig.ARM_PITCH_LEFT_MAX, 
					MasterArmsConfig.ARM_PITCH_LEFT_MIN
					);
			
			if(servoTwoPosition != jointPositions[2])
			{
				instance.servoId = 20;
				instance.servoPositon = servoTwoPosition;
				instance.servoSpeed = defaultSpeed;
				writer.write(instance, instance_handle);
				jointPositions[2] = servoTwoPosition;
			}
			
			if(servoOnePosition != jointPositions[3])
			{
				instance.servoId = 21; 
				instance.servoPositon = servoOnePosition;
				instance.servoSpeed = defaultSpeed;
				writer.write(instance, instance_handle);
				jointPositions[3] = servoOnePosition;
			}
		}
		else if(jointType.equals("left_elbow"))
		{
			x = kinectDataInstance.getX();
			y = kinectDataInstance.getY();
			
			servoOnePosition = mapper.map( 
					y, 
					SensorConfig.ELBOW_LEFT_Y_MAX, 
					SensorConfig.ELBOW_LEFT_Y_MIN, 
					MasterArmsConfig.ELBOW_ROLL_LEFT_MAX, 
					MasterArmsConfig.ELBOW_ROLL_LEFT_MIN
					);
			
			servoTwoPosition = mapper.map( 
					x, 
					SensorConfig.ELBOW_LEFT_X_MAX, 
					SensorConfig.ELBOW_LEFT_X_MIN, 
					MasterArmsConfig.ARM_YAW_LEFT_MAX, 
					MasterArmsConfig.ARM_YAW_LEFT_MIN
					);
			
			if(servoTwoPosition != jointPositions[4])
			{
				instance.servoId = 22;
				instance.servoPositon = servoTwoPosition;
				instance.servoSpeed = defaultSpeed;
				writer.write(instance, instance_handle);
				jointPositions[4] = servoTwoPosition;
			}

			if(servoOnePosition != jointPositions[5])
			{
				instance.servoId = 23;
				instance.servoPositon = servoOnePosition;
				instance.servoSpeed = defaultSpeed;
				writer.write(instance, instance_handle);
				jointPositions[5] = servoOnePosition;
			}
		}
		else if(jointType.equals("left_wrist"))
		{
			x = kinectDataInstance.getX();
			z = kinectDataInstance.getZ();
			
			servoOnePosition = mapper.map( 
					-z, //x
					SensorConfig.WRIST_LEFT_Y_MAX, 
					SensorConfig.WRIST_LEFT_Y_MIN, 
					MasterArmsConfig.WRIST_ROLL_LEFT_MAX, 
					MasterArmsConfig.WRIST_ROLL_LEFT_MIN
					);
			
			servoTwoPosition = mapper.map( 
					x, //100-y
					SensorConfig.WRIST_LEFT_X_MAX, 
					SensorConfig.WRIST_LEFT_X_MIN, 
					MasterArmsConfig.FOREARM_YAW_LEFT_MAX, 
					MasterArmsConfig.FOREARM_YAW_LEFT_MIN
					);
			
			if(servoTwoPosition != jointPositions[6])
			{
				instance.servoId = 24;
				instance.servoPositon = servoTwoPosition;
				instance.servoSpeed = defaultSpeed;
				writer.write(instance, instance_handle);
				jointPositions[6] = servoTwoPosition;
			}

			if(servoOnePosition != jointPositions[7])
			{
				instance.servoId = 25;
				instance.servoPositon = servoOnePosition;
				instance.servoSpeed = defaultSpeed;
				writer.write(instance, instance_handle);
				jointPositions[7] = servoOnePosition;
			}
		}
		else if(jointType.equals("right_shoulder"))
		{
			x = kinectDataInstance.getX();
			y = kinectDataInstance.getY();
			
			servoOnePosition = mapper.map( 
					y, 
					SensorConfig.SHOULDER_RIGHT_Y_MAX, 
					SensorConfig.SHOULDER_RIGHT_Y_MIN, 
					MasterArmsConfig.ARM_ROLL_RIGHT_MAX, 
					MasterArmsConfig.ARM_ROLL_RIGHT_MIN
					);
			
			servoTwoPosition = mapper.map(
					120 - x, 
					SensorConfig.SHOULDER_RIGHT_X_MAX, 
					SensorConfig.SHOULDER_RIGHT_X_MIN, 
					MasterArmsConfig.ARM_PITCH_RIGHT_MAX, 
					MasterArmsConfig.ARM_PITCH_RIGHT_MIN
					);
			
			if(servoTwoPosition != jointPositions[8])
			{
				instance.servoId = 30;
				instance.servoPositon = servoTwoPosition;
				instance.servoSpeed = defaultSpeed;
				writer.write_untyped(instance, instance_handle);
				jointPositions[8] = servoTwoPosition;
			}

			if(servoOnePosition != jointPositions[9])
			{
				instance.servoId = 31;
				instance.servoPositon = servoOnePosition;
				instance.servoSpeed = defaultSpeed;
				writer.write_untyped(instance, instance_handle);
				jointPositions[9] = servoOnePosition;
			}			
		}
		else if(jointType.equals("right_elbow"))
		{
			x = kinectDataInstance.getX();
			y = kinectDataInstance.getY();
			
			servoOnePosition = mapper.map( 
					-x, //50 - x
					SensorConfig.ELBOW_RIGHT_X_MAX, 
					SensorConfig.ELBOW_RIGHT_X_MIN, 
					MasterArmsConfig.ELBOW_ROLL_RIGHT_MAX, 
					MasterArmsConfig.ELBOW_ROLL_RIGHT_MIN
					);
			
			servoTwoPosition = mapper.map( 
					y, //130 - y
					SensorConfig.ELBOW_RIGHT_Y_MAX, 
					SensorConfig.ELBOW_RIGHT_Y_MIN, 
					MasterArmsConfig.ARM_YAW_RIGHT_MAX, 
					MasterArmsConfig.ARM_YAW_RIGHT_MIN
					);
			
			if(servoOnePosition != jointPositions[10])
			{
				instance.servoId = 32;
				instance.servoPositon = servoOnePosition;
				instance.servoSpeed = defaultSpeed;
				writer.write_untyped(instance, instance_handle);
				jointPositions[10] = servoOnePosition;
			}

			if(servoTwoPosition != jointPositions[11])
			{
				instance.servoId = 33;
				instance.servoPositon = servoTwoPosition;
				instance.servoSpeed = defaultSpeed;
				writer.write_untyped(instance, instance_handle);
				jointPositions[11] = servoTwoPosition;
			}
		}
		else if(jointType.equals("right_wrist"))
		{
			x = kinectDataInstance.getX();
			z = kinectDataInstance.getZ();
			
			servoOnePosition = mapper.map( 
					-z, 
					SensorConfig.WRIST_RIGHT_X_MAX, 
					SensorConfig.WRIST_RIGHT_X_MIN, 
					MasterArmsConfig.WRIST_ROLL_RIGHT_MAX, 
					MasterArmsConfig.WRIST_ROLL_RIGHT_MIN);
			
			servoTwoPosition = mapper.map( 
					-x, 
					SensorConfig.WRIST_RIGHT_Y_MAX, 
					SensorConfig.WRIST_RIGHT_Y_MIN, 
					MasterArmsConfig.FOREARM_YAW_RIGHT_MAX, 
					MasterArmsConfig.FOREARM_YAW_RIGHT_MIN);

			if(servoTwoPosition != jointPositions[12])
			{
				instance.servoId = 34;
				instance.servoPositon = servoTwoPosition;
				instance.servoSpeed = defaultSpeed;
				writer.write(instance, instance_handle);
				jointPositions[12] = servoTwoPosition;
			}

			if(servoOnePosition != jointPositions[13])
			{
				instance.servoId = 35;
				instance.servoPositon = servoOnePosition;
				instance.servoSpeed = defaultSpeed;
				writer.write(instance, instance_handle);
				jointPositions[13] = servoOnePosition;
			}
		}
		else if(jointType.equals("null"))
		{
			try
			{
				Thread.sleep(1000);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			instance.servoId = 10;
			instance.servoPositon = MasterArmsConfig.HEAD_PITCH_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 11;
			instance.servoPositon = MasterArmsConfig.HEAD_YAW_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 20;
			instance.servoPositon = MasterArmsConfig.ARM_PITCH_LEFT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 21;
			instance.servoPositon = MasterArmsConfig.ARM_ROLL_LEFT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 22;
			instance.servoPositon = MasterArmsConfig.ARM_YAW_LEFT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 23;
			instance.servoPositon = MasterArmsConfig.ELBOW_ROLL_LEFT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 24;
			instance.servoPositon = MasterArmsConfig.FOREARM_YAW_LEFT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 25;
			instance.servoPositon = MasterArmsConfig.WRIST_ROLL_LEFT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 30;
			instance.servoPositon = MasterArmsConfig.ARM_PITCH_RIGHT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 31;
			instance.servoPositon = MasterArmsConfig.ARM_ROLL_RIGHT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 32;
			instance.servoPositon = MasterArmsConfig.ARM_YAW_RIGHT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 33;
			instance.servoPositon = MasterArmsConfig.ELBOW_ROLL_RIGHT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 34;
			instance.servoPositon = MasterArmsConfig.FOREARM_YAW_RIGHT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 35;
			instance.servoPositon = MasterArmsConfig.WRIST_ROLL_RIGHT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);		
		}

	}

	/**
	 * CallbackInterface
	 * @author Irvin Steve Cardenas
	 * 
	 * Callback interface to retrieve parsed Data Object
	 * Purpose: GUI or other components can implement this to receive a  YEIDataModel object object
	 *
	 */
	public interface CallbackInterface{
		public void callback(KinectDataModel data);
	}
}
