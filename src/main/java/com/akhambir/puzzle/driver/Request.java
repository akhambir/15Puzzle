package com.akhambir.puzzle.driver;

import static com.akhambir.puzzle.util.Constants.INVALID_INPUT;

public class Request {

    private final int input;

    private Request(int input) {
        this.input = input;
    }


    public int getInput() {
        return input;
    }

    public boolean isValid() {
        return input != INVALID_INPUT;
    }

    public static Request of(int next) {
        return new Request(next);
    }
}
