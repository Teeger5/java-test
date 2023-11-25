package hu.nye.pandragon.wumpus.xml;

import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.xml.model.XmlLevel;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

import java.io.StringReader;
import java.io.StringWriter;

/**
 * Ez az osztály képes LevelVO-t XML-lé,
 * és XML-t LevelVO-vá alakítani
 * A metódusok statikusak, mivel nem várható olyan helyzet,
 * ahol a tesztelléskor ez problémát jelentene
 */
public class XmlLevelConverter {
	/**
	 * LevelVO átalakítása XML-lé
	 * @param levelVO a pálya, amelyből XML lesz
	 * @return az XML kód
	 * @throws JAXBException
	 */
	public static String toXML (LevelVO levelVO) throws JAXBException {
		var jaxbContext = JAXBContext.newInstance(XmlLevel.class);
		var marshaller = jaxbContext.createMarshaller();
		var writer = new StringWriter();
		marshaller.marshal(new XmlLevel(levelVO), writer);
		return writer.toString();
	}

	/**
	 * XML átalakítása LevelVO-vá
	 * @param xml a bemeneti XML kód
	 * @return az beolvasott pálya
	 * @throws JAXBException
	 */
	public static LevelVO toLevelVO (String xml) throws JAXBException {
		var jaxbContext = JAXBContext.newInstance(XmlLevel.class);
		var reader = new StringReader(xml);
		var unmarshaller = jaxbContext.createUnmarshaller();
		var xmlLevel = (XmlLevel) unmarshaller.unmarshal(reader);
		return xmlLevel.toLevelVO();

	}
}
