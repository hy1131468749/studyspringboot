package com.glodio.util;

import java.util.UUID;

public class Uuid {
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		
		return uuid.toString();
	}
}
