package hu.nye.pandragon.wumpus.xml;

import hu.nye.pandragon.wumpus.model.Directions;
import hu.nye.pandragon.wumpus.model.Items;
import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.service.game.Level;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class XmlLevelConverterTest {

	static String XML_CODE = """
			<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
			<Level size="4" startX="3" startY="3" steps="0">
			    <Hero arrows="2" direction="W" hasGold="true"/>
			    <Entities>
			        <Entity posX="1" posY="1">W</Entity>
			        <Entity posX="4" posY="1">W</Entity>
			        <Entity posX="1" posY="4">W</Entity>
			        <Entity posX="1" posY="2">W</Entity>
			        <Entity posX="4" posY="2">W</Entity>
			        <Entity posX="2" posY="1">W</Entity>
			        <Entity posX="2" posY="4">W</Entity>
			        <Entity posX="4" posY="4">W</Entity>
			        <Entity posX="1" posY="3">W</Entity>
			        <Entity posX="4" posY="3">W</Entity>
			        <Entity posX="3" posY="1">W</Entity>
			        <Entity posX="3" posY="4">W</Entity>
			        <Entity posX="3" posY="3">H</Entity>
			    </Entities>
			</Level>
			""";

	Level level;

	@BeforeEach
	public void setup () {
		level = new Level(4);
		var hero = new Hero();
		level.placeEntity(3, 3, hero);
		hero.setDirection(Directions.West);
		hero.setAmmoAmount(2);
		hero.addItem(Items.Gold);
	}

	@Test
	public void shouldConvertToXMLCorrectly () {
		var expected =  XML_CODE;
		String result;
		try {
			result = XmlLevelConverter.toXML(level.toLevelVO(), true);
			log.debug("toXML: " + result);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void shouldConvertToLevelVOCorrectly () {
		var input = XML_CODE;
		var expected = level.toLevelVO();
		LevelVO result;
		try {
			result = XmlLevelConverter.toLevelVO(input);
			log.debug("staticEntites equals: " + expected.getStaticEntities().equals(result.getStaticEntities()));
			log.debug("expected - result static entities: {} - {}", expected.getStaticEntities().size(), result.getStaticEntities().size());
			log.debug("xml -> levelVO -> xml: " + XmlLevelConverter.toXML(result, true));
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
		Assertions.assertEquals(expected, result);
	}
}