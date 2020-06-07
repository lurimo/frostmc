package br.com.frostmc.common.json;

import java.util.Arrays;

import org.json.simple.JSONObject;

/**
 * User: bobacadodl Date: 10/27/13 Time: 9:03 PM
 */
public class JSONChatExtra {
	private JSONObject chatExtra;

	@SuppressWarnings("unchecked")
	public JSONChatExtra(String text) {
		chatExtra = new JSONObject();
		chatExtra.put("text", text);

	}

	@SuppressWarnings("unchecked")
	public void setClickEvent(JSONChatClickEventType action, String value) {
		JSONObject clickEvent = new JSONObject();
		clickEvent.put("action", action.getTypeString());
		clickEvent.put("value", value);
		chatExtra.put("clickEvent", clickEvent);
	}

	@SuppressWarnings("unchecked")
	public void setHoverEvent(JSONChatHoverEventType action, String value) {
		JSONObject hoverEvent = new JSONObject();
		hoverEvent.put("action", action.getTypeString());
		hoverEvent.put("value", value);
		chatExtra.put("hoverEvent", hoverEvent);
	}

	@SuppressWarnings("unchecked")
	public void setHoverEvent(JSONChatHoverEventType action, String[] value) {
		JSONObject hoverEvent = new JSONObject();
		hoverEvent.put("action", action.getTypeString());
		hoverEvent.put("value", Arrays.asList(value));
		chatExtra.put("hoverEvent", hoverEvent);
	}

	public JSONObject toJSON() {
		return chatExtra;
	}
}
