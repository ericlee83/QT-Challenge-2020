package com.challenage;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalLong;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadFileParser {
    private static final String OUTPUT_PATH = String.format("%s%s%s", System.getProperty("user.dir"), System.getProperty("file.separator"), "output.txt");
    private static final String INPUT_PATH = MultiThreadFileParser.class.getClassLoader().getResource("input.txt").getPath();
    private static DecimalFormat df = new DecimalFormat("#.##");
    private volatile List<Integer> indexes = new ArrayList<>();

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        new MultiThreadFileParser().run();
        System.out.println("Output file: " + OUTPUT_PATH);
    }

    private void run() throws IOException, ExecutionException, InterruptedException {
        File output = new File(OUTPUT_PATH);
        if (!output.exists()) {
            output.createNewFile();
        }

        PrintWriter writer = new PrintWriter(OUTPUT_PATH);
        writer.print("");
        writer.close();


        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Callable<Long>> numberMinMaxAvgRunners = Arrays.asList(
                new NumberMinMaxAvgRunner(),
                new NumberMinMaxAvgRunner(),
                new NumberMinMaxAvgRunner(),
                new NumberMinMaxAvgRunner(),
                new NumberMinMaxAvgRunner()
        );
        OptionalLong max = executor.invokeAll(
                numberMinMaxAvgRunners
        ).stream().mapToLong(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }).max();

        executor.shutdown();
        System.out.println("Max time:" + max.getAsLong());
    }

    public class NumberMinMaxAvgRunner implements Callable {
        public Object call() {
            long start = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName());
            try (BufferedReader br = new BufferedReader(new FileReader(INPUT_PATH));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_PATH, true));
            ) {
                String line;
                int index = 1;
                while ((line = br.readLine()) != null) {
                    if (!indexes.contains(index)) {
                        String output = String.format("%d,%s%s", index, parseLine(line), System.lineSeparator());
                        //System.out.println(output);
                        writer.write(output);
                        indexes.add(index);
                        index++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return System.currentTimeMillis() - start;
        }

        public String parseLine(String line) {
            double avg = 0.00;
            int v = 0;
            int count = 1;
            int min = Integer.MAX_VALUE;
            int max = 0;
            for (String num : line.split(",")) {
                v = Integer.parseInt(num);
                min = v < min ? v : min;
                max = v > max ? v : max;
                avg = avg - (avg - v) / count++;
            }
            return String.format("%d,%s,%d,%d", count - 1, df.format(avg), max, min);
        }

    }
}
