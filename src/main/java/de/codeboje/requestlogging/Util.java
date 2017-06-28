package de.codeboje.requestlogging;

import java.util.UUID;

/**
 * Simple Helper class
 * @author Jens Boje
 *
 */
public final class Util {

	private Util() {
		
	}
	
	public static String createId() {
		final UUID uuid = java.util.UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}
}
