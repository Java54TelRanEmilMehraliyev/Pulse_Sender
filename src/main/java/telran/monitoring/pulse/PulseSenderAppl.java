package telran.monitoring.pulse;

import java.net.*;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;

import telran.monitoring.pulse.dto.SensorData;
import static telran.monitoring.pulse.constants.PulseConstants.*;

public class PulseSenderAppl {

	private static Random random = new Random();
	static DatagramSocket socket;

	private static HashMap<Long, Integer> previousPulseValues = new HashMap<>();

	public static void main(String[] args) throws Exception {
		socket = new DatagramSocket();
		IntStream.rangeClosed(1, N_PACKETS).forEach(PulseSenderAppl::sendPulse);
	}

	static void sendPulse(int seqNumber) {
		SensorData data = getRandomSensorData(seqNumber);
		String jsonData = data.toString();
		sendDatagramPacket(jsonData);
		try {
			Thread.sleep(TIMEOUT);
		} catch (InterruptedException e) {

		}
	}

	private static void sendDatagramPacket(String jsonData) {
		byte[] buffer = jsonData.getBytes();
		try {
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(HOST), PORT);
			socket.send(packet);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static SensorData getRandomSensorData(int seqNumber) {
		long patientId = random.nextInt(1, N_PATIENTS + 1);
		int value = getRandomPulseValue(patientId);
		return new SensorData(seqNumber, patientId, value, System.currentTimeMillis());
	}

	private static int getRandomPulseValue(long patientId) {
		int previousValue = previousPulseValues.getOrDefault(patientId, generateInitialPulse(patientId));

		if (random.nextInt(100) < JUMP_PROBABILITY) {
			int newValue = calculateNewPulseValue(previousValue);
			previousPulseValues.put(patientId, newValue);
			return newValue;
		}

		return previousValue;
	}

	private static int generateInitialPulse(long patientId) {
		int newValue = random.nextInt(MIN_PULSE_VALUE, MAX_PULSE_VALUE + 1);
		previousPulseValues.put(patientId, newValue);
		return newValue;
	}

	private static int calculateNewPulseValue(int previousValue) {
		boolean isPositiveJump = random.nextInt(100) < JUMP_POSITIVE_PROBABILITY;
		int jumpPercent = random.nextInt(MIN_JUMP_PERCENT, MAX_JUMP_PERCENT + 1);
		int jumpAmount = previousValue * jumpPercent / 100;

		int newValue = isPositiveJump ? previousValue + jumpAmount : previousValue - jumpAmount;

		return Math.max(MIN_PULSE_VALUE, Math.min(newValue, MAX_PULSE_VALUE));
	}
}