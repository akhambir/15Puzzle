package com.akhambir.puzzle.driver;

public class Response {

    private final int[] gameState;
    private final Result result;

    private Response(int[] gameState, Result result) {
        this.gameState = gameState;
        this.result = result;
    }


    public int[] getGameState() {
        return gameState;
    }

    public Result getResult() {
        return result;
    }

    public static Response of(int[] gameState, Result result) {
        return new Response(gameState, result);
    }


    public enum Result {
        NEXT_TURN, WRONG_REQUEST, FINISHED
    }
}
