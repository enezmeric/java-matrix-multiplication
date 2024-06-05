import java.util.Random;

class Main {

    private static final int SIZE = 10;//satırsay*
    private static final int THREADS = 10;//sütunsay*
    private static final int ROWS_PER_THREAD = SIZE / THREADS;

    private static class Worker implements Runnable {
        private int[][] matA;
        private int[][] matB;
        private int[][] result;
        private int startRow;
        private int endRow;

        public Worker(int[][] matA, int[][] matB, int[][] result, int startRow, int endRow) {
            this.matA = matA;
            this.matB = matB;
            this.result = result;
            this.startRow = startRow;
            this.endRow = endRow;
        }

        @Override
        public void run() {
            for (int i = startRow; i < endRow; i++) {
                for (int j = 0; j < SIZE; j++) {
                    for (int k = 0; k < SIZE; k++) {
                        result[i][j] += matA[i][k] * matB[k][j];
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] matA = new int[SIZE][SIZE];
        int[][] matB = new int[SIZE][SIZE];
        int[][] result = new int[SIZE][SIZE];

        Random random = new Random();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matA[i][j] = random.nextInt(100);
                matB[i][j] = random.nextInt(100);
            }
        }

        Thread[] threads = new Thread[THREADS];

        // Create and start threads
        for (int i = 0; i < THREADS; i++) {
            int startRow = i * ROWS_PER_THREAD;
            int endRow = startRow + ROWS_PER_THREAD;
            threads[i] = new Thread(new Worker(matA, matB, result, startRow, endRow));
            threads[i].start();
        }

        for (int i = 0; i < THREADS; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();//ln enter
        }
    }
}
