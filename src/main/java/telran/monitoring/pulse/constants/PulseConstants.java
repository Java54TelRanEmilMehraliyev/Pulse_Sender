package telran.monitoring.pulse.constants;

public class PulseConstants {
	public static final int PORT = 5005;
	public static final int MAX_BUFFER_SIZE = 1500;
	public static final int N_PACKETS = 100;
	public static final long TIMEOUT = 500;
	public static final int N_PATIENTS = 5;
	public static final int MIN_PULSE_VALUE = 50;
	public static final int MAX_PULSE_VALUE = 200;
	public static final String HOST = "192.168.1.104";
	public static final double JUMP_PROBABILITY = 0.15;
	public static final double JUMP_POSITIVE_PROBABILITY = 0.7;
	public static final int MIN_JUMP_PERCENT = 10;
	public static final int MAX_JUMP_PERCENT = 100;
	public static final double MAX_ERROR_PULSE_PROB = 0.05;
	public static final double MIN_ERROR_PULSE_PROB = 0.03;
}
