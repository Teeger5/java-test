# Tervezési minták egy OO programozási nyelvben. MVC,

## MVC

Az MVC tervezési minta egy olyan módszer, amellyel felépíthetünk és szervezhetünk 
webes alkalmazásokat. Az MVC három fő részből áll: a modellből, a nézetből és a vezérlőből. 
Ezek a részek különállóak, de együttműködnek egymással.

### Modell
A modell az alkalmazás adatrétege, amely az adatok tárolásáért, 
lekérdezéséért és manipulálásáért felelős. A modell tartalmazza az üzleti logikát, 
a szabályokat és a validációkat, amelyek meghatározzák, hogyan kezeljük az adatokat.

### Nézet
A nézet az alkalmazás felhasználói felülete, amely megjeleníti az adatokat 
a felhasználó számára. A nézet általában HTML, CSS és JavaScript kódot tartalmaz, 
amelyek formázást, stílust és interaktivitást biztosítanak. A nézet 
nem tartalmazza az adatok kezelésének logikáját, csak a megjelenítését.

### Vezérlő
A vezérlő az alkalmazás irányító rétege, amely összeköti a modellt és a nézetet. 
A vezérlő fogadja a felhasználó kéréseit, és továbbítja őket a megfelelő modellnek 
vagy nézetnek. A vezérlő feladata, hogy eldöntse, milyen adatokat kell lekérni 
vagy frissíteni a modellben, és milyen nézetet kell megjeleníteni a felhasználónak.

### Előnyei
- Segít elkerülni a kódismétlést 
- Növeli a kód újrafelhasználhatóságát és karbantarthatóságát
- Megkönnyíti a fejlesztők közötti együttműködést

### Hátrányai
- Bonyolultabbá teheti az alkalmazás szerkezetét
- Több fájlt és osztályt igényelhet

# Más tervezési minták
**Szerkezeti tervezési minták jönnek**

## Facade

A Facade minta egy olyan szerkezeti tervezési minta, amely egy egyszerűsített interfészt
biztosít egy komplex rendszerhez, amely több alrendszerből tevődik össze. 
A Facade minta célja, hogy elrejtse a rendszer bonyolultságát, és egységesítsen 
több metódus hívását egyetlen metódusban. 

### Előnyei
- Csökkenti a kódismétlést 
- Növeli a kód olvashatóságát és karbantarthatóságát

### Hátrányai
- Csökkentheti a rendszer rugalmasságát és testreszabhatóságát

### Példa
```java
// A Facade, amely egy egyszerű API-t biztosít a sárkányhoz
class DragonFacade {
	private Nose nose;
	private Eyes eyes;
	private Mouth mouth;
	private Body body;
	private Wings wings;

	// Egy konstruktor, amely létrehozza a sárkány objektumot
public DragonFacade() {
	nose = new Nose();
	eyes = new Eyes();
	mouth = new Mouth();
	body = new Body();
	wings = new Wings();
}
	// Ez a metódus felébreszti a sárkányt
	public void wakeUp() {
		System.out.println("Sárkány ébresztése...");
		// Több lépés, amelyek szükségesek a sárkány felébresztéséhez
		eyes.openEyes();
		body.stretchWings();
		wings.startFlying();
		mouth.breatheFire();
		mouth.roarLoudly();
		System.out.println("A sárkány felébredt, és munkára kész");
	}

	// Egy metódus, amely aludni küldi sárkányt
	public void sleep() {
		System.out.println("Sárkány küldése aludni...");
		// Több lépés, amelyek szükségesek a sárkány alvásához
		wings.stopFlying();
		body.curlUp();
		eyes.closeEyes();
		nose.snoreSoftly();
		System.out.println("A sárkány alszik, és szépeket álmodik.");
	}
}
```

## Adapter

Az adapter tervezési minta lehetővé teszi egy meglévő osztály / interface használatát 
egy másik interface-en keresztül.

Például legyen adott egy USB-hub és egy kártyaolvasó. Az adapter tervezési minta 
lényege az, hogy az adaptálandó osztály (a kártyaolvasó) 
átadja az adapternek (az USB-hub) aaz adatait (a memóriakártyát), 
és ezáltal az adapter meg tudja valósítani az illesztést (csatlakoztatni a számítógéphez).

### Előnyei
- Nem kell régi kódot átalakítani vagy újraírni kompatibilisan
- Növeli a modularitást
- Csökkenti az ismétlődő kódot

### Hátrányai
- Növelheti a kód hosszát és bonyolultságát, ha sok adapter osztályt kelllétrehoznunk
- Csökkentheti a teljesítményt vagy a stabilitást

### Példa
Ez a Java `Iterator` és `Enaumeration` interfészeit mutatja be, mint adaptált és adapter.
```java
public interface Iterator<E> {

    boolean hasNext();

    E next();

    default void remove() {
        throw new UnsupportedOperationException("remove");
    }

    default void forEachRemaining(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        while (hasNext())
            action.accept(next());
    }
}

public interface Enumeration<E> {

	boolean hasMoreElements();

	E nextElement();

	// Az adapterként szlgáló metódus
	default Iterator<E> asIterator() {
		return new Iterator<>() {
			@Override public boolean hasNext() {
				return hasMoreElements();
			}
			@Override public E next() {
				return nextElement();
			}
		};
	}
}
```

## Decorator

A Decorator tervezési minta egy olyan minta, amely lehetővé teszi, hogy 
egy objektum viselkedését dinamikusan bővítsük anélkül, hogy megváltoztatnánk 
az objektum osztályát vagy öröklési hierarchiáját. A Decorator minta hasznos, 
ha sokféle variáció van egy alapvető viselkedésen, és nem akarunk létrehozni 
sok osztályt, hogy kezeljünk minden variációt.   
A Decorator minta egy absztrakt osztályt vagy interfészt használ, amely 
meghatározza az alapvető viselkedést, és egy vagy több dekorátor osztályt,
amely felülírja a viselkedésüket, kiegészítve valamivel, és tartalmaz egy referenciát 
az alap objektumra. A dekorátor osztályok egymásra  is épülhetnek, hogy különböző 
kombinációkat hozzanak létre.  

Szükséges részek:
- Egy absztrakt objektum, ami az alap viselkedést írja le
- Egy osztály, ami ezt implementálja
- Egy másik osztály (a dekorátor), ami implementálja
- Dekorátor osztályok, amik az objektum dekorátor megvalósítását öröklik

A dekorátor osztályokat tetszőleges számban lehet egymásba ágyazni a 
példányosításukkor, és mind érvényesíteni fogják a hatásukat az objektumon.

### EElőnyei
- Rugalmas
- Könnyen bővíthető
- Elkerüli az osztályhierarchia túlzott bonyolítását

### Hátrányai
- Sok kód
- Bonyolítja a hibakeresést
- Nagyobb memőria-használat

### Példa
```java
public interface Shape {
   void draw();
}

// Ez az osztály a Shape egy implementációja
public class Rectangle implements Shape {

   @Override
   public void draw() {
      System.out.println("Téglalap");
   }
}

// Ez egy másik implementációja
public class Circle implements Shape {

   @Override
   public void draw() {
      System.out.println("Kör");
   }
}

// Az absztrakt dekorátor osztály, ami szintén implementálja a Shape-et
public abstract class ShapeDecorator implements Shape {
   protected Shape decoratedShape;

   public ShapeDecorator(Shape decoratedShape){
      this.decoratedShape = decoratedShape;
   }

   public void draw(){
      decoratedShape.draw();
   }	
}

// Ez egy konkrét dekorátor, ami örökli az absztrakt osztályt
public class RedShapeDecorator extends ShapeDecorator {

   public RedShapeDecorator(Shape decoratedShape) {
      super(decoratedShape);		
   }

   @Override
   public void draw() {
      decoratedShape.draw();	       
      setRedBorder(decoratedShape);
   }

   private void setRedBorder(Shape decoratedShape){
      System.out.println("A színe piros");
   }
}

// A használatuk így történik:
    public void test() {
	    Shape redCircle = new RedShapeDecorator(new Circle());

	    Shape redRectangle = new RedShapeDecorator(new Rectangle());
    }

redCircle.draw();
redRectangle.draw();
```

Viselkedési tervezési minták jönnek

## Iterator
Az iterator tervezési minta használatával bejárhatunk egy tárolóban lévő elemeket, 
anélkül, hogy tudnánk a konkrét tároló belső működését. 

Az iterator minta általában a következőképp épül fel:
- Van egy tároló, amelynek van egy olyan metódusa, amely visszaad egy iterátort, 
- és egy másik metódusa, 
amely visszaadja az elemet a következő bejáráshoz. Az iterátort úgy lehet létrehozni, 
hogy egy tárolót adunk hozzá, és megvizsgáljuk, hogy rendelkezik-e még elemekkel. 
Ha igen, akkor használjuk az iterátort a for ciklusban vagy más hasonló ciklusokban.

## Chain of Responsibility
A Chain of Responsibility tervezési minta egy olyan viselkedési tervezési minta, 
amelynek az alapelemei egy feldolgozandó kérés, és egy sor feldolgozási objektum. 
Minden feldolgozási objektum tartalmaz logikát, amely meghatározza, hogy milyen 
típusú kérést képes kezelni. Amikor egy nem tudja kezelni a feldolgozandó kérést,
akkor  átadja a következőnek a sorban.

### Példa
```java

// A sárkánynak van neve és ereje
// Ez lesz a kérésben a feldolgozandó objektum
public class Dragon {
	private String name;
	private int power;

	public Dragon(String name, int power) {
		this.name = name;
		this.power = power;
	}

	public String getName() {
		return name;
	}

	public int getPower() {
		return power;
	}
}

// Az absztrakt alap osztály a kérés kezelő objektumoknak
// Ezzel rövidebb lehet a kód, mint egy interfésszel
public abstract class DragonHandler {
	protected DragonHandler nextHandler; // a következő kezelő a láncban

	public void setNextHandler(DragonHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	// a kérés feldolgozása
	public abstract void handle(Dragon dragon);
}

// Egy konkrét kezelő osztály, amely a tűz erejű sárkányokat kezeli
public class FireDragonHandler extends DragonHandler {

	@Override
	public void handle(Dragon dragon) {
		if (dragon.getPower() == 1) { // ha a sárkány tűz erejű
			System.out.println(dragon.getName() + " egy tűz erejű sárkány, aki lángokat okád");
		} else if (nextHandler != null) { // ha nem, továbbítja a kérést a következő kezelőnek
			nextHandler.handle(dragon);
		} else { // ha nincs több kezelő, akkor nem tudja feldolgozni a kérést
			System.out.println("Nem tudom kezelni ezt a sárkányt: " + dragon.toString());
		}
	}
}

// Egy konkrét kezelő osztály, amely a villám erejű sárkányokat kezeli
public class LightningDragonHandler extends DragonHandler {

	@Override
	public void handle(Dragon dragon) {
		if (dragon.getPower() == 2) {
			System.out.println(dragon.getName() + " egy villám erejű sárkány, aki villanásokat szór");
		} else if (nextHandler != null) {
			nextHandler.handle(dragon);
		} else {
			System.out.println("Nem tudom kezelni ezt a sárkányt: " + dragon.toString());
		}
	}
}

// Egy konkrét kezelő osztály, amely a jég erejű sárkányokat kezeli
public class IceDragonHandler extends DragonHandler {

	@Override
	public void handle(Dragon dragon) {
		if (dragon.getPower() == 3) {
			System.out.println(dragon.getName() + " egy jég erejű sárkány, aki jégcsapokat lő");
		} else if (nextHandler != null) {
			nextHandler.handle(dragon);
		} else {
			System.out.println("Nem tudom kezelni ezt a sárkányt: " + dragon.toString());
		}
	}
}

public void test() {
	DragonHandler fireHandler = new FireDragonHandler();
	DragonHandler iceHandler = new IceDragonHandler();
	DragonHandler lightningHandler = new LightningDragonHandler();

	fireHandler.setNextHandler(iceHandler);
	iceHandler.setNextHandler(lightningHandler);

	Dragon fireDragon = new Dragon("Thornr", 1);
	Dragon iceDragon = new Dragon("Shruikan", 2);
	Dragon lightningDragon = new Dragon("Glaedr", 3);
	Dragon unknownDragon = new Dragon("Doragon ga arimasu ka", 4);

	fireHandler.handle(fireDragon);
	fireHandler.handle(iceDragon);
	fireHandler.handle(lightningDragon);
	fireHandler.handle(unknownDragon);
}
```

## Command
A Command tervezési minta egy olyan viselkedési minta, amely egy objektumot használ arra, 
hogy reprezentáljon egy műveletet, amelyet egy másik objektumon kell végrehajtani. 
Ezzel a mintával elválaszthatjuk a hívó és a fogadó objektumokat.

A Command tervezési minta a következő elemekből áll:
- Command: egy interfész vagy absztrakt osztály, amely definiálja a műveletet, 
amelyet a fogadón kell végrehajtani. 
- ConcreteCommand: egy osztály, amely implementálja / örökli a Command-ot, 
és tárolja a fogadó (Receiver) referenciáját és a művelet paramétereit. 
- Receiver: egy osztály, amely tartalmazza a művelet logikáját és a végrehajtásához 
szükséges adatokat. 
- Invoker: egy osztály, amely tárolja és kezeli a Command objektumokat, 
és meghívja őket a megfelelő időben. 
- Client: egy osztály, amely létrehozza a ConcreteCommand objektumokat, és átadja 
őket az Invoker-nek.

### Példa
```java
// Command interfész
public interface Command {
	void execute();
}

// ConcreteCommand osztály, amely a sárkány tűzokádását reprezentálja
public class FireBreathCommand implements Command {
	private Dragon dragon;

	public FireBreathCommand(Dragon dragon) {
		this.dragon = dragon;
	}

	@Override
	public void execute() {
		dragon.breatheFire();
	}
}

// ConcreteCommand osztály, amely a sárkány repülését reprezentálja
public class FlyCommand implements Command {
	private Dragon dragon;

	public FlyCommand(Dragon dragon) {
		this.dragon = dragon;
	}

	@Override
	public void execute() {
		dragon.fly();
	}
}

// Receiver osztály
public class Dragon {
	private String name;

	public Dragon(String name) {
		this.name = name;
	}

	public void breatheFire() {
		System.out.println(name + " tüzet okád");
	}

	public void fly() {
		System.out.println(name + " repül");
	}
}

// Invoker osztály
public class Dragonrider {
	private String name;
	private List<Command> commands;

	public Dragonrider(String name) {
		this.name = name;
		commands = new ArrayList<>();
	}

	public void addCommand(Command command) {
		commands.add(command);
	}

	public void executeCommands() {
		System.out.println(name + " parancsokat ad a sárkánynak");
		for (Command command : commands) {
			command.execute();
		}
	}
}

public void test() {
		Dragon dragon = new Dragon("Thorn");
	Dragonrider dragonrider = new Dragonrider("Murtagh");
	Command fireBreath = new FireBreathCommand(dragon);
	Command fly = new FlyCommand(dragon);

	dragonrider.addCommand(fireBreath);
	dragonrider.addCommand(fly);
	dragonrider.executeCommands();
}
```

## Template Method

A Template Method tervezési minta meghatározza egy algoritmus vázát egy metódusban, 
és néhány lépést a leszármazott osztályokra bíz. A Template Method lehetővé teszi 
a leszármazott osztályoknak, hogy bizonyos lépéseket újra meghatározzanak 
az algoritmus szerkezetének megváltoztatása nélkül.

### Példa
```java
// Absztrakt osztály, amely a sárkány létrehozásának vázát adja meg
public abstract class Dragon {
	// Template metódus, amely a sárkány létrehozásának lépéseit hívja meg
	public final void createDragon() {
		createBody();
		createWings();
		createTail();
		createHead();
	}

	//Absztrakt metódusok, amelyeket a leszármazott osztályoknak meg kell valósítaniuk
	public abstract void createBody();
	public abstract void createWings();
	public abstract void createTail();
	public abstract void createHead();
}

// Konkrét osztály, amely egy piros sárkányt hoz létre
public class RedDragon extends Dragon {
	@Override
	public void createBody() {
		System.out.println("Vörös pikelyekkel borított sárkánytest készítése");
	}

	@Override
	public void createWings() {
		System.out.println("2 nagy vörös szárny készítése");
	}

	@Override
	public void createTail() {
		System.out.println("Hosszú, vörös, tüskés farok készítése");
	}

	@Override
	public void createHead() {
		System.out.println("Vörös fej készítése szarvakkal és éles fogakkal");
	}
}

//Konkrét osztály, amely egy kék sárkányt hoz létre
public class BlueDragon extends Dragon {
	@Override
	public void createBody() {
		System.out.println("Kék pikkelyekkel borított sárkánytetst készítése");
	}

	@Override
	public void createWings() {
		System.out.println("2 kis kék szárny készítése");
	}

	@Override
	public void createTail() {
		System.out.println("Rövid, kék farok készítése");
	}

	@Override
	public void createHead() {
		System.out.println("Kék fej készítése szarvakkal és éles fogakkal");
	}
}

public void test() {
	Dragon redDragon = new RedDragon();
	redDragon.createDragon();
	Dragon blueDragon = new BlueDragon();
	blueDragon.createDragon();
}
```

## Strategy

A Strategy tervezési minta egy viselkedési minta, amely lehetővé teszi, hogy
egy osztály viselkedését futásidőben kicseréljük egy másikra, amely ugyanazt
az interfészt valósítja meg. Ez hasznos, ha többféle algoritmus közül kell
választani, amelyek ugyanazt a feladatot végzik el, eltérő módon.

A Strategy tervezési minta alkalmazásához három fő komponensre van szükség:
- Egy stratégia interfész, amely meghatározza az algoritmusok közös műveleteit
- Egy vagy több konkrét stratégia osztály, 
amely implementálja a stratégia interfészt, és megvalósítja az algoritmust
- Egy kontextus osztály, amely tartalmaz egy stratégia interfész típusú attribútumot, 
és egy metódust, amely átadja a feladatot a stratégia objektumnak

### Előnyei
- Növeli a kód rugalmasságát
- Elválasztja a viselkedést az osztálytól, amely használja

### Példa
```java
// A stratégia interfész
public interface Fire {
	void breatheFire();
}

// Egy konkrét stratégia osztály, amely normál tüzet okád
public class NormalFire implements Fire {
	@Override
	public void breatheFire() {
		System.out.println("A sárkány normál tüzet okád, ami elégeti az ellenfeleit");
	}
}

// Egy másik konkrét stratégia osztály, amely jeges tüzet okád
public class IceFire implements Fire {
	@Override
	public void breatheFire() {
		System.out.println("A sárkány jeges tüzet okád, ami lefagyasztja az ellenfeleit");
	}
}

// Egy harmadik konkrét stratégia osztály, amely villámokat okád
public class LightningFire implements Fire {
	@Override
	public void breatheFire() {
		System.out.println("A sárkány villámokat okád, ami elkábítja az ellenfeleit");
	}
}

// A kontextus osztály, amely használja a tűz objektumokat
public class Dragon {
	private Fire fire;

	public Dragon(Fire fire) {
		this.fire = fire;
	}

	public void breatheFire() {
		fire.breatheFire();
	}

	public void setFire(Fire fire) {
		this.fire = fire;
	}
}

public void test() {
	Dragon dragon = new Dragon(new NormalFire());
	dragon.breatheFire();

	dragon.setFire(new IceFire());
	dragon.breatheFire();

	dragon.setFire(new LightningFire());
	dragon.breatheFire();
}
```

**Létrehozási tervezési minták jönnek**

## Singleton
A Singleton tervezési minta olyan létrehozási minta, amely garantálja, 
hogy egy osztálynak csak egy példánya létezik, és egy globális hozzáférési pontot 
biztosít hozzá. A Singleton minta hasznos lehet olyan osztályok esetében, 
amelyek a naplózásért, az adatbázis-kezelésért vagy 
más erőforrások megosztásáért felelősek.

A Singleton minta megvalósításához általában szükség van 
egy privát konstruktorra, egy statikus mezőre, amely tárolja az osztály példányát, 
és egy nyilvános statikus metódusra, amely visszaadja ezt a példányt. 
A metódus neve általában `getInstance`.

### Példa
```java
public class Dragon {
	private static Dragon instance;

	private Dragon() {}

	public static Dragon getInstance() {
		if (instance == null) {
			instance = new Dragon();
		}
		return instance;
	}
}
```

## Builder
A Builder tervezési minta egy olyan tervezési minta, amely lehetővé teszi, 
hogy összetett objektumokat hozzunk létre lépésről lépésre, anélkül, 
hogy megváltoztatnánk az objektum reprezentációját. Ezt a mintát akkor használjuk, 
amikor különböző változatlan objektumokat akarunk létrehozni ugyanazzal 
az objektum-építési folyamattal

A Builder minta hasznos lehet, ha az objektumunk sok tulajdonsággal rendelkezik, 
és néhányuk opcionális. Ebben az esetben elkerülhetjük a túlterhelt konstruktorokat 
vagy a setter metódusokat, és helyette egy folyékony interfészt használhatunk, 
amely visszaadja ugyanazt a Builder objektumot minden tulajdonság beállítása után. 
Végül a Builder objektum egy build () metódust biztosít, amely visszaadja 
a kívánt objektumot

## Factory Method

## Prototype

