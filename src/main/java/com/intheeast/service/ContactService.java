package com.intheeast.service;

import com.intheeast.entity.UserMessage;

public interface ContactService {
	
	/**
	 * <p>
	 * Saves the user's message in the database, forwards it to the admin, and
	 * sends a confirmation message to the user.
	 * </p>
	 * 
	 * @param msg
	 *            user message
	 * @throws IllegalArgumentException
	 *             if <code>msg</code> is <code>null</code>
	 */
	void saveUserMessage(UserMessage msg);
}