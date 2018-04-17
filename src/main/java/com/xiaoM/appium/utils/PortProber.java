package com.xiaoM.appium.utils;

import static java.lang.Math.max;
import org.openqa.selenium.Platform;
import org.openqa.selenium.net.EphemeralPortRangeDetector;
import org.openqa.selenium.net.FixedIANAPortRange;
import org.openqa.selenium.net.LinuxEphemeralPortRangeDetector;
import org.openqa.selenium.net.OlderWindowsVersionEphemeralPortDetector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Random;

public class PortProber {

	private static final Random random = new Random();
	private static final EphemeralPortRangeDetector ephemeralRangeDetector;

	static {
		final Platform current = Platform.getCurrent();

		if (current.is(Platform.LINUX)) {
			ephemeralRangeDetector = LinuxEphemeralPortRangeDetector.getInstance();
		} else if (current.is(Platform.XP)) {
			ephemeralRangeDetector = new OlderWindowsVersionEphemeralPortDetector();
		} else {
			ephemeralRangeDetector = new FixedIANAPortRange();
		}
	}
	public static final int HIGHEST_PORT = 65535;
	public static final int START_OF_USER_PORTS = 1024;
	/**
	 * 获取可用端口
	 * @return
	 */
	public static int getFreePort() {
		for (int i = 0; i < 5; i++) {
			int seedPort = createAcceptablePort();
			int suggestedPort = checkPortIsFree(seedPort);
			if (suggestedPort != -1) {
				return suggestedPort;
			}
		}
		throw new RuntimeException("Unable to find a free port");
	}

	private static int createAcceptablePort() {
		synchronized (random) {
			final int FIRST_PORT;
			final int LAST_PORT;

			int freeAbove = HIGHEST_PORT - ephemeralRangeDetector.getHighestEphemeralPort();
			int freeBelow = max(0, ephemeralRangeDetector.getLowestEphemeralPort() - START_OF_USER_PORTS);

			if (freeAbove > freeBelow) {
				FIRST_PORT = ephemeralRangeDetector.getHighestEphemeralPort();
				LAST_PORT = 65535;
			} else {
				FIRST_PORT = 1024;
				LAST_PORT = ephemeralRangeDetector.getLowestEphemeralPort();
			}

			if (FIRST_PORT == LAST_PORT) {
				return FIRST_PORT;
			}
			if (FIRST_PORT > LAST_PORT) {
				throw new UnsupportedOperationException("Could not find ephemeral port to use");
			}
			final int randomInt = random.nextInt();
			final int portWithoutOffset = Math.abs(randomInt % (LAST_PORT - FIRST_PORT + 1));
			return portWithoutOffset + FIRST_PORT;
		}
	}

	private static int checkPortIsFree(int port) {
		ServerSocket socket;
		try {
			socket = new ServerSocket();
			socket.setReuseAddress(true);
			socket.bind(new InetSocketAddress("localhost", port));
			int localPort = socket.getLocalPort();
			socket.close();
			return localPort;
		} catch (IOException e) {
			return -1;
		}
	}

	public static void main(String[] args) {
		System.out.println(getFreePort());
	}
}
