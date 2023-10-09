package hu.nye.maventest;

import java.lang.String;

class Main {
	public static void main (String[] args) {
//		System.out.println("Hello World");
		if (args.length == 0) {
			System.out.println("Ez a program üdvözöl egy opcionálisan megadott nyelven, alapértelmezetten magyarul.\nPéldául: program.jar Csaba -> Szia, Csaba!\n\tprogram.jar Csaba en -> Hello, Csaba!");
			return;
		}
		var name = args[0];
		var lang = Nyelv.Magyar;
		if (args.length == 2) {
			try {
				lang = Nyelv.parse(args[1]);
			} catch (NemkezeltNyelvException e) {
				e.printStackTrace();
			}
		}
		var greetings = lang.getGreetingsText(name);
		System.out.println(greetings);
	}

	public enum Nyelv {
		Magyar("hu"),
		Angol("en"),
		Japan("ja");

		public String code;
		Nyelv(String lang) {
			code = lang;
		}

		public String getGreetingsText (String name) {
			String greeting;
			if (this == Nyelv.Magyar) { greeting = "Szia"; }
			else if (this == Nyelv.Angol) { greeting = "Hello"; }
			else if (this == Nyelv.Japan) { greeting = "さん、 よおこそ"; } // XY-san, yooukoso
			else {
				throw new RuntimeException("Hiba: a program nem tud köszönteni ezen a nyelven: '" + code + "'\n\tTámogatott nyelvek: hu, en, ja");
			}
			if (this == Nyelv.Japan) { greeting = String.format("%s%s (%s-san, yookoso / Üdvözöllek, %s)", name, greeting, name, name); }
			else { greeting = greeting + ", " + name; }
			return greeting;
		}

		public static Nyelv parse (String code) throws NemkezeltNyelvException {
			for (Nyelv x : Nyelv.values()) {
				if (x.code.equalsIgnoreCase(code)) {
					return x;
				}
			}
			throw new NemkezeltNyelvException(code);
		}
	}

	public static class NemkezeltNyelvException extends Exception {
		public NemkezeltNyelvException(String lang) {
			super("Nem ismert nyelv: " + lang);
		}
	}
}