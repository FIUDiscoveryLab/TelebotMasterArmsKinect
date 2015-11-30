package discoverylab.telebot.master.arms.parser;

import java.util.StringTokenizer;

import discoverylab.telebot.master.arms.model.KinectDataModel;
import discoverylab.telebot.master.core.parser.CoreDataParser;

public class KinectDataParser extends CoreDataParser{

	@Override
	public Object parse(String str) {
		StringTokenizer tokenizer = parseUsingTokenizer(str);
		KinectDataModel instance = new KinectDataModel();
		
		if(str.equals("null"))
		{
			instance.setJointType(tokenizer.nextToken());
			instance.setX(-1);
			instance.setY(-1);
			instance.setZ(-1);
		}
		else
		{
			instance.setJointType(tokenizer.nextToken());
			instance.setX(Integer.parseInt(tokenizer.nextToken()));
			instance.setY(Integer.parseInt(tokenizer.nextToken()));
			instance.setZ(Integer.parseInt(tokenizer.nextToken()));
		}

		return instance;
	}
}
