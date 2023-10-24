package hu.nye.pandragon.wumpus.service.command.performer;

import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.model.MapVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class used to write a number to a given position of a map.
 */
public class HeroTurnPerformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeroTurnPerformer.class);

    /**
     * Writes a number to a given position into the provided map.
     *
     * A write can only be performed, if there is no fixed number at
     * the requested position.
     *
     * @param mapVO       the map to update
     * @param rowIndex    the index of the row
     * @param columnIndex the index of the column
     * @param number      the number to write into the map
     */
    public LevelVO perform(LevelVO levelVO, ) {
        LOGGER.info("Performing put operation with map = {}, rowIndex = {}, columnIndex = {}, number = {}",
            mapVO, rowIndex, columnIndex, number);

        int[][] map = mapVO.getMap();
        boolean[][] fixed = mapVO.getFixed();

        if (fixed[rowIndex][columnIndex]) {
            LOGGER.warn("Can't perform put operation, as position at rowIndex = {} and columnIndex = {} is fixed",
                rowIndex, columnIndex);
        }

        map[rowIndex][columnIndex] = number;

        return new MapVO(mapVO.getNumberOfRows(), mapVO.getNumberOfColumns(), map, fixed);
    }

}
