package hu.nye.pandragon.wumpus.xml;

import hu.nye.pandragon.wumpus.service.game.Level;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class XmlLevelConverterTest {
	@Test
	public void shouldConvertToXMLCorrectly () {
		var level = new Level(4);
//		level.placeEntity(4, 4, new Hero());
		var expected = "";
		String result;
		try {
			result = XmlLevelConverter.toXML(level.toLevelVO(), true);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
		Assertions.assertEquals(result, expected);
	}

}