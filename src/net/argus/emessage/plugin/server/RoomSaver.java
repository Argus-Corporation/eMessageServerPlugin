package net.argus.emessage.plugin.server;

import java.io.IOException;
import java.util.List;

import net.argus.cjson.Array;
import net.argus.cjson.CJSON;
import net.argus.cjson.CJSONParser;
import net.argus.cjson.value.CJSONInteger;
import net.argus.cjson.value.CJSONNull;
import net.argus.cjson.value.CJSONObject;
import net.argus.cjson.value.CJSONString;
import net.argus.cjson.value.CJSONValue;
import net.argus.emessage.server.MainServer;
import net.argus.file.CJSONFile;
import net.argus.net.server.room.Room;
import net.argus.util.Error;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class RoomSaver {

	private static final CJSONFile FILE = new CJSONFile("room_saver", "/");
	private static final CJSON SAVER = CJSONParser.getCJSON(FILE);
	
	public static void save(Room room) {
		Array roomsArray = SAVER.getArray("rooms");
		CJSONObject mainRoomObj = new CJSONObject();
		
		mainRoomObj.addItem("name", new CJSONString(room.getName()));
		mainRoomObj.addItem("size", new CJSONInteger(room.getSize()));
		mainRoomObj.addItem("password", room.getPassword()==null?new CJSONNull():new CJSONString(room.getPassword()));

		roomsArray.addValue(mainRoomObj);
		
		write();
	}
	
	public static void remove(Room room) {
		Array roomsArray = SAVER.getArray("rooms");
		
		List<CJSONValue> roomsValue = roomsArray.getArray();
		
		for(int i = 0; i < roomsValue.size(); i++) {
			CJSONObject roomObj = roomsValue.get(i).getObject();
			
			if(roomObj.getValue("name").getValue().equals(room.getName())) {
				roomsValue.remove(i);
				roomsArray.setArray(roomsValue);
				write();
				return;
			}
		}
		
	}
	
	private static void write() {
		try {
			FILE.write(SAVER);
		}catch(IOException e) {Debug.log("Error on write room saver file", Info.ERROR);Error.createErrorFileLog(e);}
	}
	
	public static void init() {
		Array roomsArray = SAVER.getArray("rooms");
		
		for(CJSONValue room : roomsArray.getArray()) {
			String name = (String) room.getValue("name").getValue();
			String password = null;
			int size = (int) room.getValue("size").getValue();
			
			CJSONValue passwordVal = room.getValue("password");
			if(passwordVal instanceof CJSONString)
				password = (String) passwordVal.getValue();
			
			new Room(name, size, password, MainServer.getServer());
			
		}
	}
	
}
