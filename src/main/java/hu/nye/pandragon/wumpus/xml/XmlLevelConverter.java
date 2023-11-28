package hu.nye.pandragon.wumpus.xml;

import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.xml.model.XmlLevel;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.StringReader;
import java.io.StringWriter;

/**
 * Ez az osztály képes
 *  - LevelVO-t XML-lé,
 *  - XML-t LevelVO-vá, és
 *  - XML-kódot XmlLevel-lé alakítani
 * A metódusok azért statikusak, mert így is tesztelhetőek
 *
 * Kérdések
 *    Az XML modellek miatt hasznos lehet
 *  - Praktikus-e az eredeti model osztályokat átalakítani az XML-hez?
 *    Azaz megjelölni őket a megfelelő annotációkkal
 */
public class XmlLevelConverter {
	/**
	 * LevelVO átalakítása XML-lé
	 * @param levelVO a pálya, amelyből XML lesz
	 * @return az XML kód
	 * @throws JAXBException a kivétel tovább dobódik
	 */
	public static String toXML (LevelVO levelVO, boolean formatted) throws JAXBException {
		var jaxbContext = JAXBContext.newInstance(XmlLevel.class);
		var marshaller = jaxbContext.createMarshaller();
		var writer = new StringWriter();
		if (formatted) {
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		}
		marshaller.marshal(new XmlLevel(levelVO), writer);
		return writer.toString();
	}

	/**
	 * XML átalakítása LevelVO-vá
	 * @param xml a bemeneti XML kód
	 * @return az beolvasott pálya
	 * @throws JAXBException a kivétel tovább dobódik
	 */
	public static LevelVO toLevelVO (String xml) throws JAXBException {
		var xmlLevel = toXmlLevel(xml);
		return xmlLevel.toLevelVO();
	}

	/**
	 * XML-kód átalakítása XmlLevel objektummá
	 * @param xml bemeneti XML-kód
	 * @return az eredmény XmlLevel objektum
	 * @throws JAXBException
	 */
	public static XmlLevel toXmlLevel (String xml) throws JAXBException {
		var jaxbContext = JAXBContext.newInstance(XmlLevel.class);
		var reader = new StringReader(xml);
		var unmarshaller = jaxbContext.createUnmarshaller();
		return (XmlLevel) unmarshaller.unmarshal(reader);
	}
}
