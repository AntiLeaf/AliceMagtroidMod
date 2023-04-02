package AliceMagtroidMod.doll.localization;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;

public class DollStrings {
	public String NAME;
	public String DESCRIPTION;
	public String[] EXTENDED_DESCRIPTION;
	
	public DollStrings(String name, String description, String[] extendedDescription) {
		this.NAME = name;
		this.DESCRIPTION = description;
		this.EXTENDED_DESCRIPTION = extendedDescription;
	}
	
	public DollStrings(String name, String description) {
		this.NAME = name;
		this.DESCRIPTION = description;
		this.EXTENDED_DESCRIPTION = new String[0];
	}
	
	static HashMap<String, DollStrings> dollStrings = null;
	
	public static void loadDollStrings(String json) {
		Type type = new com.google.gson.reflect.TypeToken<HashMap<String, DollStrings>>(){}.getType();
		dollStrings = new Gson().fromJson(json, type);
	}
	
	public static DollStrings getDollString(String ID) {
		if (dollStrings == null || !dollStrings.containsKey(ID))
			return getMockDollString();
		return dollStrings.get(ID);
	}
	
	public static DollStrings getMockDollString() {
		return new DollStrings("[MISSING_DOLL_NAME]", "[MISSING_DOLL_DESCRIPTION]");
	}
}
