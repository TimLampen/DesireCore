package com.desiremc.core.slack;

import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;

public class Slack {
	/**
	 * 
	 * @param hook Slack Provided URL
	 * @param channel Slack Channel Name
	 * @param user Slack user
	 * @param message Message
	 */
	public static void send(String hook, String channel, String user, String message) {
		SlackApi api = new SlackApi(hook);
		api.call(new SlackMessage(channel, user, message));
	}

}
