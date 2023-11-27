package hu.nye.pandragon.wumpus.persistence.impl;

import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.model.PlayernameVO;
import hu.nye.pandragon.wumpus.xml.XmlLevelConverter;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

@Slf4j
class JdbcGameStateRepositoryTest {

	/**
	 * A teszteléskor ennek a felhasználónak
	 * az adatait ellenőrizzük, amelyek állandóak
	 * a cél miatt
	 */
	static final PlayernameVO PLAYERNAME = new PlayernameVO("Doragon ga arimasen");
	static final int PLAYER_SCORE = 85;
	LevelVO levelVO;
	JdbcGameStateRepository jdbcGameStateRepository;

	@BeforeEach
	public void setup () {
		try {
			jdbcGameStateRepository = new JdbcGameStateRepository();
			levelVO = XmlLevelConverter.toLevelVO("""
					<?xml version="1.0" encoding="UTF-8"?>
					<Level size="9">
						<Hero arrows="2" direction="E" hasGold="false" />
						<Entities>
							<Entity posX="1" posY="4">W</Entity>
							<Entity posX="4" posY="1">W</Entity>
							<Entity posX="7" posY="9">W</Entity>
							<Entity posX="9" posY="8">W</Entity>
							<Entity posX="5" posY="1">W</Entity>
							<Entity posX="6" posY="9">W</Entity>
							<Entity posX="9" posY="7">W</Entity>
							<Entity posX="1" posY="3">W</Entity>
							<Entity posX="6" posY="1">W</Entity>
							<Entity posX="5" posY="9">W</Entity>
							<Entity posX="9" posY="6">W</Entity>
							<Entity posX="7" posY="1">W</Entity>
							<Entity posX="4" posY="9">W</Entity>
							<Entity posX="9" posY="5">W</Entity>
							<Entity posX="2" posY="1">W</Entity>
							<Entity posX="1" posY="8">W</Entity>
							<Entity posX="9" posY="4">W</Entity>
							<Entity posX="1" posY="7">W</Entity>
							<Entity posX="3" posY="9">W</Entity>
							<Entity posX="3" posY="1">W</Entity>
							<Entity posX="1" posY="6">W</Entity>
							<Entity posX="9" posY="3">W</Entity>
							<Entity posX="1" posY="5">W</Entity>
							<Entity posX="2" posY="9">W</Entity>
							<Entity posX="1" posY="1">W</Entity>
							<Entity posX="9" posY="2">W</Entity>
							<Entity posX="1" posY="9">W</Entity>
							<Entity posX="1" posY="2">W</Entity>
							<Entity posX="8" posY="1">W</Entity>
							<Entity posX="9" posY="1">W</Entity>
							<Entity posX="9" posY="9">W</Entity>
							<Entity posX="8" posY="9">W</Entity>
							<Entity posX="5" posY="4">H</Entity>
						</Entities>
					</Level>
					""");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void shouldGetScoreboard () {
		var scoreboard = jdbcGameStateRepository.getScoreboard();
		log.debug("Scoreboard: " + scoreboard);
		Assertions.assertEquals(scoreboard.get(PLAYERNAME), PLAYER_SCORE);
	}

	@Test
	public void shouldLoadLevel () {
		var result = jdbcGameStateRepository.load(PLAYERNAME);
		Assertions.assertEquals(levelVO, result);
	}

	@Test
	public void shouldSaveAndLoadLevel () {
		var playername = new PlayernameVO("Doragon ga arimasu ka");
		jdbcGameStateRepository.save(playername, levelVO);
		var result = jdbcGameStateRepository.load(playername);
		Assertions.assertEquals(levelVO, result);
	}
}