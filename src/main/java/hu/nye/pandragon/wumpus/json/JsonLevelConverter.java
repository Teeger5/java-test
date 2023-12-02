package hu.nye.pandragon.wumpus.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.nye.pandragon.wumpus.json.model.JsonLevel;
import hu.nye.pandragon.wumpus.model.LevelVO;

import java.io.StringWriter;

public class JsonLevelConverter {
	public String toJSON (LevelVO levelVO) throws JsonProcessingException {
		var objectMapper = new ObjectMapper();
		var writer = new StringWriter();
		return  objectMapper.writeValueAsString(new JsonLevel(levelVO));
	}

	public JsonLevel toJsonLevel (String json) throws JsonProcessingException {
		var objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, JsonLevel.class);
	}
}
