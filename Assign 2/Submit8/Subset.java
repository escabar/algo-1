
/**
 * Created by supatel on 2/10/2016.
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.util.NoSuchElementException;

public class Subset {

    public static void main(String[] args) {
        int N = 0;
        int k = 0;

        if (args.length != 1) {
            throw new RuntimeException("Please Enter a single integer!");
        } else {
            k = Integer.parseInt(args[0]);
        }

        RandomizedQueue<String> queue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            queue.enqueue(item);
            N++;
        }


        if (k > N) {
            throw new RuntimeException("Please ensure k < N! k=" + k + " N=" + N);
        } else {
            while (k > 0){
                System.out.println(queue.dequeue());
                k--;
            }
        }
    }
}

