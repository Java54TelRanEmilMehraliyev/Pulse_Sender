package telran.monitoring.pulse.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

public record SensorData(long seqNumber, long patientId, int value, long timestamp) {
	public static SensorData getSensorData(String json) {
		JSONObject jsonObj = new JSONObject(json);
		return new SensorData(jsonObj.getLong("seqNumber"), jsonObj.getLong("patientId"), jsonObj.getInt("value"),
				jsonObj.getLong("timestamp"));
	}
	 private static String formatTimestamp(long timestamp) {
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date resultDate = new Date(timestamp);
	        return sdf.format(resultDate);
	    }

	@Override
	public String toString() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("seqNumber", seqNumber);
		jsonObj.put("patientId", patientId);
		jsonObj.put("value", value);
		jsonObj.put("timestamp", formatTimestamp(timestamp));
		return jsonObj.toString();
	}
}