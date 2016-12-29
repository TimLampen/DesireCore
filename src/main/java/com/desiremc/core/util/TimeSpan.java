package com.desiremc.core.util;

public enum TimeSpan {
	
	YEAR(31536000000L), MONTH(2628000000L), DAY(86400000L), HOUR(3600000L), MINUTE(60000L), SECOND(1000L), MILLI_SECOND(
			1L), TICK(50L);

	private long timeMillis;

	private TimeSpan(long millis) {
		this.timeMillis = millis;
	}

	public long getTimeMillis(long... amnt) {
		if (amnt == null || amnt.length == 0)
			amnt = new long[] { 1 };
		return this.timeMillis * amnt[0];
	}

	public long getTimeSeconds(long... amnt) {
		if (amnt == null || amnt.length == 0)
			amnt = new long[] { 1 };
		return (this.timeMillis * amnt[0]) / 1000L;
	}

	public long getTicks(long... amnt) {
		if (amnt == null || amnt.length == 0)
			amnt = new long[] { 1 };
		return this.getTimeSeconds() * 20 * amnt[0];
	}

	public static TimeSpan get(String name) {
		for (TimeSpan timeSpan : values())
			if (timeSpan.name().equalsIgnoreCase(name) || (timeSpan.name() + "S").equalsIgnoreCase(name))
				return timeSpan;
		return null;
	}
	
}
