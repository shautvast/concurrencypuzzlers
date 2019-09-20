package chapter3;

import common.NotThreadSafe;

/*
 * fails on my machine
 */
@NotThreadSafe
public class AttemptToShowReordering {

    public static void main(String[] args) {

        for (int i = 0; i < 1000_000; i++) {

            State state = new State();

            new Thread(() -> {

                /////
                state.a = 1;
                state.b = 1;
                state.c = state.a + 1;
                /////

            }).start();

            new Thread(() -> {
                int tmpC = state.c;
                int tmpB = state.b;
                int tmpA = state.a;

                if (tmpB == 1 && tmpA == 0) {
                    System.out.println("WTF! b == 1 && a == 0");
                }
                if (tmpC == 2 && tmpB == 0) {
                    System.out.println("WTF! c == 2 && b == 0");
                }
                if (tmpC == 2 && tmpA == 0) {
                    System.out.println("WTF! c == 2 && a == 0");
                }
            }).start();
        }
    }

    static class State {
        int a = 0;
        int b = 0;
        int c = 0;
    }

}
