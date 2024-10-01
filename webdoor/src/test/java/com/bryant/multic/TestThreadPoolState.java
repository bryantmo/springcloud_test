package com.bryant.multic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestThreadPoolState {

    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static  final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    public static void main(String[] args) {
        log.info("isRunning(RUNNING) = {}", isRunning(RUNNING));
    }

    private static boolean isRunning(int c) {
        return c < SHUTDOWN;
    }

}
