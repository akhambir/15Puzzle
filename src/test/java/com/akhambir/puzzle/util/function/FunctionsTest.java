package com.akhambir.puzzle.util.function;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.akhambir.puzzle.util.Constants.EMPTY_CELL;
import static com.akhambir.puzzle.util.function.Functions.findPos;
import static com.akhambir.puzzle.util.function.Functions.linearSearch;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class FunctionsTest {

    private int[] gameState = {-1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 15};

    @Test
    public void testFindPosOfNonEmpty() {
        int actual = findPos.apply(14).apply(gameState);
        assertEquals(14, actual);
    }

    @Test
    public void testFindPosOfEmpty() {
        int actual = findPos.apply(EMPTY_CELL).apply(gameState);
        assertEquals(15, actual);
    }

    @Test
    public void testLinearSearchWhenElementIsNotPresent() {
        int actual = linearSearch.apply(gameState, 16);
        assertEquals(-1, actual);
    }

}