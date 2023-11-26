package hu.nye.pandragon.wumpus.persistence.impl;

import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.model.PlayernameVO;
import hu.nye.pandragon.wumpus.persistence.GameStateRepository;
import hu.nye.pandragon.wumpus.xml.XmlLevelConverter;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class XmlGameStateRepository implements GameStateRepository {
	@Override
	public void save(PlayernameVO playername, LevelVO levelVO) {
		var file = getPlayerName(playername);
		try {
			var xml = XmlLevelConverter.toXML(levelVO, true);
			Files.writeString(Path.of(file), xml);
		} catch (IOException | JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public LevelVO load(PlayernameVO playername) throws JAXBException, IOException {
		var xml = Files.readString(Path.of(getPlayerName(playername)));
		return XmlLevelConverter.toLevelVO(xml);
	}

	@Override
	public void close() {}

	private String getPlayerName (PlayernameVO playername) {
		return String.format("gamestate_%s.xml", playername.toString().trim().replaceAll("\\s+", "_"));
	}
}
