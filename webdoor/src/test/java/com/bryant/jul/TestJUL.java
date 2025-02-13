package com.bryant.jul;

import java.util.logging.Logger;

public class TestJUL {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("com.bryant.jul");
        logger.info("info message ...");
        logger.warning("warning message ...");
        logger.severe("severe message ...");
        logger.warning("warning message ...");
    }
}
