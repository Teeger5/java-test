package hu.nye.pandragon.wumpus.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import hu.nye.pandragon.wumpus.json.model.JsonLevel;
import hu.nye.pandragon.wumpus.model.Directions;
import hu.nye.pandragon.wumpus.model.Items;
import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.service.game.Level;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class JsonLevelConverterTest {

	static String JSON_CODE = """
{"size":4,"startX":3,"startY":3,"steps":3,"entities":[{"symbol":"W","posX":1,"posY":1},{"symbol":"W","posX":4,"posY":1},{"symbol":"W","posX":1,"posY":4},{"symbol":"W","posX":1,"posY":2},{"symbol":"W","posX":4,"posY":2},{"symbol":"W","posX":2,"posY":1},{"symbol":"W","posX":2,"posY":4},{"symbol":"W","posX":4,"posY":4},{"symbol":"W","posX":1,"posY":3},{"symbol":"W","posX":4,"posY":3},{"symbol":"W","posX":3,"posY":1},{"symbol":"W","posX":3,"posY":4},{"symbol":"H","posX":3,"posY":3}],"hero":{"arrows":2,"direction":"W","hasGold":true}}""";

	JsonLevelConverter jsonLevelConverter;
	LevelVO levelVO;

	@BeforeEach
	public void setup () {
		jsonLevelConverter = new JsonLevelConverter();
		var level = new Level(4);
		var hero = new Hero();
		level.placeEntity(3, 3, hero);
		hero.setDirection(Directions.West);
		hero.setAmmoAmount(2);
		hero.addItem(Items.Gold);
		levelVO = level.toLevelVO(3);
	}

	@Test
	public void shouldConvertToJson () {
		var result = "";
		try {
			result = jsonLevelConverter.toJSON(levelVO);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		log.debug("expected: " + JSON_CODE);
		log.debug("result:   " + result);
		Assertions.assertEquals(JSON_CODE, result);
	}

	@Test
	public void shouldConvertToJsonLevel () {
		var expected = new JsonLevel(levelVO);
		JsonLevel result;
		try {
			result = jsonLevelConverter.toJsonLevel(JSON_CODE);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		Assertions.assertEquals(expected, result);
	}
}