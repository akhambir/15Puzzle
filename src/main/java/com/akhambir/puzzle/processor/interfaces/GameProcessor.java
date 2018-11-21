package com.akhambir.puzzle.processor.interfaces;

import com.akhambir.puzzle.driver.Request;

public interface GameProcessor {

    void process(Request request);

    void terminate(String msg);
}
