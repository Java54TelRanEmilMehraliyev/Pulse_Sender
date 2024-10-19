package telran.monitoring.pulse.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

public record SensorData(long seqNumber, long patientId, int value, long timestamp) {
    public static SensorData getSensorData(String json) {
        JSONObject jsonObj = new JSONObject(json);
        long timestamp = parseTimestamp(jsonObj.getString("timestamp"));
        return new SensorData(
            jsonObj.getLong("seqNumber"),
            jsonObj.getLong("patientId"),
            jsonObj.getInt("value"),
            timestamp
        );
    }

	public static String formatTimestamp(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date resultDate = new Date(timestamp);
		return sdf.format(resultDate);
	}

	private static long parseTimestamp(String timestampString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(timestampString).getTime(); 
		} catch (ParseException e) {
			throw new RuntimeException("Invalid timestamp format: " + timestampString, e);
		}
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