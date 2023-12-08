# Tervezési minták egy OO programozási nyelvben. MVC,

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
- Megkönnyíti a fejlesztők közötti együttműködést. 

### Hátrányai
- Bonyolultabbá teheti az alkalmazás szerkezetét
- Több fájlt és osztályt igényelhet

# Más tervezési minták
Szerkezeti tervezési minták jönnek.

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

Viselkedési tervezési minták jönnek

## Iterator

## Chain of Responsibility

## Command

## Template Method

**Létrehozási tervezési minták jönnek**

## Singleton

## Strategy

## Builder

## Factory Method

## Prototype

