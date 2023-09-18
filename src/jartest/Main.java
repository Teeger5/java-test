package jartest;

import java.lang.String;

class Main {
	public static void main (String[] args) {
//		System.out.println("Hello World");
		if (args.length == 0) {
			System.out.println("Ez a program üdvözöl egy opcionálisan megadott nyelven, alapértelmezetten magyarul.\nPéldául: program.jar Csaba -> Szia, Csaba!\n\tprogram.jar Csaba en -> Hello, Csaba!");
			return;
		}
		var name = args[0];
		var lang = "hu";
		if (args.length == 2) { lang = args[1]; }
		String greeting;
		if (lang.equalsIgnoreCase("hu")) { greeting = "Szia"; }
		else if (lang.equalsIgnoreCase("en")) { greeting = "Hello"; }
		else if (lang.equalsIgnoreCase("ja")) { greeting = "さん、 よおこそ"; }
		else {
			System.out.println("Hiba: ismeretlen nyelv: '" + lang + "'\nTámogatott nyelvek: hu, en, ja");
			return;
		}
		if (lang.equalsIgnoreCase("ja")) { greeting = name + greeting; }
		else { greeting = greeting + ", " + name; }
		System.out.println(greeting);
	}
	
	public static String getString () {
		return "Hello";
	}
}