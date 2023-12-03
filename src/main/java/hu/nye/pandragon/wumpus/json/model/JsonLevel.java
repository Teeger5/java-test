package hu.nye.pandragon.wumpus.json.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class JsonLevel {
	private int size;
	private int startX;
	private int startY;
	private int steps;
	@JsonProperty("hero")
	private JsonHero jsonHero;
	private List<JsonEntity> entities;

	public JsonLevel(LevelVO levelVO) {
		size = levelVO.getSize();
		startX = levelVO.getStartpoint().x;
		startY = levelVO.getStartpoint().y;
		steps = levelVO.getNumberOfMoves();
		var heroOpt = levelVO.getLivingEntities().values().stream()
				.filter(e -> e instanceof Hero)
				.findFirst()
				.orElse(null);
		if (heroOpt instanceof Hero hero) {
			jsonHero = new JsonHero(hero);
		}
		entities = new ArrayList<>();
		entities.addAll(levelVO.getStaticEntities().entrySet().stream()
				.map(entry -> new JsonEntity(
						entry.getValue().getCompatibilitySymbol(),
						entry.getKey().x, entry.getKey().y)
				).collect(Collectors.toList()));
		entities.addAll(levelVO.getLivingEntities().entrySet().stream()
				.map(entry -> new JsonEntity(
						entry.getValue().getCompatibilitySymbol(),
						entry.getKey().x, entry.getKey().y)
				).collect(Collectors.toList()));
	}
}
