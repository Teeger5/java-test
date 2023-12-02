package hu.nye.pandragon.wumpus.json.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class JsonEntity {
	private char symbol;
	private int posX;
	private int posY;
}
