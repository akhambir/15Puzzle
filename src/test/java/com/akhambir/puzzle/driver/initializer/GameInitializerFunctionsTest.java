package com.akhambir.puzzle.driver.initializer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static com.akhambir.puzzle.driver.initializer.GameInitializerFunctions.checkInversionsAndLineOfEmptyPos;
import static com.akhambir.puzzle.driver.initializer.GameInitializerFunctions.checkSolvability;
import static com.akhambir.puzzle.driver.initializer.GameInitializerFunctions.compareInversionsToLineNumber;
import static com.akhambir.puzzle.driver.initializer.GameInitializerFunctions.countInversionsForWholeGameState;
import static com.akhambir.puzzle.driver.initializer.GameInitializerFunctions.countInversionsForSingleEntry;
import static com.akhambir.puzzle.driver.initializer.GameInitializerFunctions.defineLineOfPosFromTheBottom;
import static com.akhambir.puzzle.driver.initializer.GameInitializerFunctions.shuffle;
import static com.akhambir.puzzle.driver.initializer.GameInitializerFunctions.shuffle2;
import static com.akhambir.puzzle.util.Constants.EMPTY_CELL;
import static com.akhambir.puzzle.util.Constants.FIRST_TILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

@RunWith(MockitoJUnitRunner.class)
public class GameInitializerFunctionsTest {

    private int[] solvableGameState;

    private int[] unsolvableGameState;

    @Before
    public void init() {
        solvableGameState = new int[] {-1, 12, 1, 10, 2, 7, 11, 4, 14, 5, 0, 9, 15, 8, 13, 6, 3};
        unsolvableGameState = new int[] {-1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 14, 0};
    }

    @Test
    public void testCheckSolvability_ShouldReturnSameGameState() {
        int[] actual = checkSolvability.apply(solvableGameState);
        assertEquals(true, Arrays.equals(solvableGameState, actual));
    }

    @Test
    public void testCheckSolvability_ShouldReturnDifferentGameStateAndShouldPassSolvabilityTest() {
        int[] actual = checkSolvability.apply(unsolvableGameState);
        assertEquals(false, Arrays.equals(unsolvableGameState, actual));

        int[] againValidated = checkSolvability.apply(actual);
        assertEquals(true, Arrays.equals(actual, againValidated));
    }

    @Test
    public void testCheckInversionsAndLineOfEmptyPos_ShouldReturnFalseOnUnsolvableGameState() {
        unsolvableGameState[EMPTY_CELL] = 1;
        int inversions = countInversionsForWholeGameState.apply(solvableGameState, this.unsolvableGameState[EMPTY_CELL]);
        boolean actual = checkInversionsAndLineOfEmptyPos.apply(inversions).apply(unsolvableGameState);
        assertEquals(false, actual);
    }

    @Test
    public void testCheckInversionsAndLineOfEmptyPos_ShouldReturnTrueOnSolvableGameState() {
        solvableGameState[EMPTY_CELL] = 1;
        int inversions = countInversionsForWholeGameState.apply(solvableGameState, this.solvableGameState[EMPTY_CELL]);
        boolean actual = checkInversionsAndLineOfEmptyPos.apply(inversions).apply(solvableGameState);
        assertEquals(true, actual);
    }

    @Test
    public void testDefineLineOfPosFromTheBottom() {
        int pos = 1;
        int actual = defineLineOfPosFromTheBottom.apply(pos);
        assertEquals(4, actual);
    }

    @Test
    public void testCompareInversionsToLineNumberOnSolvableCase() {
        int lineNumberFromTheBottom = 2;
        int inversions = 11;

        boolean actual = compareInversionsToLineNumber
                .apply(lineNumberFromTheBottom)
                .apply(inversions);
        assertEquals(true, actual);
    }

    @Test
    public void testCompareInversionsToLineNumberOnUnsolvableCase() {
        int lineNumberFromTheBottom = 1;
        int inversions = 11;

        boolean actual = compareInversionsToLineNumber
                .apply(lineNumberFromTheBottom)
                .apply(inversions);
        assertEquals(false, actual);
    }

    @Test
    public void testCountInversionsForWholeGameState_SouldReturnValidAmountOfInversionsFromWholeGameState() {
        solvableGameState[EMPTY_CELL] = 1;
        int expected = 49;
        int actual = countInversionsForWholeGameState.apply(solvableGameState, solvableGameState[EMPTY_CELL]);
        assertEquals(expected, actual);
    }

    @Test
    public void testCountInversionsForSingleEntry_ShouldReturnValidAmountOfInversionsFromFirstElem() {
        solvableGameState[EMPTY_CELL] = 1;
        int expected = 11;
        int actual = countInversionsForSingleEntry.apply(solvableGameState, solvableGameState[EMPTY_CELL]);
        assertEquals(expected, actual);
    }

    @Test
    public void testCountInversionsForSingleEntry_ShouldReturnValidAmountOfInversionsFromNonFirstElem() {
        solvableGameState[EMPTY_CELL] = 6;
        int expected = 6;
        int actual = countInversionsForSingleEntry.apply(solvableGameState, solvableGameState[EMPTY_CELL]);
        assertEquals(expected, actual);
    }

    @Test
    public void testShuffle() {
        int[] initialState = Arrays.copyOf(solvableGameState, solvableGameState.length);
        int[] actual = shuffle.apply(initialState);
        assertNotSame(initialState, actual);
        assertEquals(false, Arrays.equals(initialState, actual));
    }

    @Test
    public void testShuffle2() {
        int[] initialState = Arrays.copyOf(solvableGameState, solvableGameState.length);
        int[] actual = shuffle2.apply(initialState, FIRST_TILE);
        assertEquals(false, Arrays.equals(solvableGameState, actual));
    }
}