package hu.nye.pandragon.wumpus.service.command;

/**
 * Ez az osztály a parancs értelmezésének az eredményét írja le
 * 3 állapota lehet:
 *  - ez nem az a parancs -> a parancsot nem lehet végrehajtani
 *  - van benne hibaüzenet -> a parancsot nem lehet végrehajtani
 *  - nincs benne hibaüzenet -> a parancsot végre lehet hajtani
 */
public class CommandMatcherResult {

	/**
	 * A hibaüzenet, ha hiba történt
	 */
	private final String message;
	private final boolean commandMatches;

	/**
	 * Ez nem az a parancs
	 * @return ezt az állapotot leíró CommandMatcherResult
	 */
	public static CommandMatcherResult ofNotMatchingCommand () {
		return new CommandMatcherResult(null, false);
	}

	/**
	 * Ez az a parancs, de szintaktikai probléma van
	 * @param errorMessage a hibaüzenet, vagy a parancs használatának leírása
	 * @return ezt az állapotot leíró CommandMatcherResult
	 */
	public static CommandMatcherResult ofInproperSyntax (String errorMessage) {
		return new CommandMatcherResult(errorMessage, true);
	}

	/**
	 * Ez az a parancs, és szintaktikailag is helyes
	 * @return ezt az állapotot leíró CommandMatcherResult
	 */
	public static CommandMatcherResult ofCorrectMatchingCommand () {
		return new CommandMatcherResult(null, true);
	}

	private CommandMatcherResult(String message, boolean commandMatches) {
		this.message = message;
		this.commandMatches = commandMatches;
	}

	/**
	 * Helyes-e és végrehajtható a parancs
	 * Ha ez az a parancs, és nincs hibaüzenet, akkor végrehajtható
	 * @return igaz, ha végrehajtható
	 */
	public boolean canBeProcessed () {
		return commandMatches && message == null;
	}

	public boolean isCommandMatches() {
		return commandMatches;
	}

	/**
	 * A hibaüzenet lekérdezése, ha elérhető
	 * @return a hibaüzenet
	 */
	public String getMessage() {
		return message;
	}
}
