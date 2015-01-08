package at.renehollander.interactivecanvas.demo;

import java.util.*;

public class Bench {

    public static void main(String[] args) {

        new Bench();

    }

    public Bench() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            list.add(UUID.randomUUID().toString());
        }

        int RUNS = 10000;

        // ArrayList
        List<String> arrayList = new ArrayList<>(list);
        // warmup
        run(arrayList, RUNS, false);
        // actual run
        run(arrayList, RUNS, false);
        System.out.println("Arraylist:");
        System.out.println("While Iterator:\t" + iterwhile + "ns, " + (iterwhile / 1000000) + "ms for " + RUNS + " runs");
        System.out.println("ForEach:\t\t" + foreach + "ns, " + (foreach / 1000000) + "ms for " + RUNS + " runs");
        System.out.println("New ForEach:\t" + newforeach + "ns, " + (newforeach / 1000000) + "ms for " + RUNS + " runs");
        System.out.println("ForLoop:\t\t" + forloop + "ns, " + (forloop / 1000000) + "ms for " + RUNS + " runs");

        // ArrayList
        List<String> linkedList = new LinkedList<>(list);
        // warmup
        run(linkedList, RUNS, true);
        // actual run
        run(linkedList, RUNS, true);
        System.out.println("LinkedList:");
        System.out.println("While Iterator:\t" + iterwhile + "ns, " + (iterwhile / 1000000) + "ms for " + RUNS + " runs");
        System.out.println("ForEach:\t\t" + foreach + "ns, " + (foreach / 1000000) + "ms for " + RUNS + " runs");
        System.out.println("New ForEach:\t" + newforeach + "ns, " + (newforeach / 1000000) + "ms for " + RUNS + " runs");
    }

    long iterwhile;
    long foreach;
    long newforeach;
    long forloop;

    int sum;

    public void run(List<String> list, int runs, boolean dontforloop) {
        long start;

        sum = 0;
        start = System.nanoTime();
        for (int runcount = 0; runcount < runs; runcount++) {
            Iterator<String> iter = list.iterator();
            while (iter.hasNext()) {
                String s = iter.next();
                sum += s.length();
            }
        }
        iterwhile = System.nanoTime() - start;

        sum = 0;
        start = System.nanoTime();
        for (int runcount = 0; runcount < runs; runcount++) {
            for (String s : list) {
                sum += s.length();
            }
        }
        foreach = System.nanoTime() - start;

        sum = 0;
        start = System.nanoTime();
        for (int runcount = 0; runcount < runs; runcount++) {
            list.forEach((s) -> {
                sum += s.length();
            });
        }
        newforeach = System.nanoTime() - start;

        if (!dontforloop) {
            sum = 0;
            start = System.nanoTime();
            for (int runcount = 0; runcount < runs; runcount++) {
                for (int i = 0; i < list.size(); i++) {
                    String s = list.get(i);
                    sum += s.length();
                }
            }
            forloop = System.nanoTime() - start;
        }
    }

}
