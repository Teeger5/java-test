package hu.nye.pandragon.wumpus.service.command;

import java.util.List;

public class CanProcessResult {
	private final String message;

	public CanProcessResult(String message) {
		this.message = message;
	}

	public CanProcessResult () {
		message = null;
	}

	public boolean canProcess () {
		return message == null;
	}

	public String getMessage() {
		return message;
	}
}
