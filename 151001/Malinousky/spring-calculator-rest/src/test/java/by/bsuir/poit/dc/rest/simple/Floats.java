package by.bsuir.poit.dc.rest.simple;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Name Surname
 * 
 */
@Fork(value = 1, warmups = 1)
public class Floats {
    @State(Scope.Benchmark)
    public static class HugeArray {
	@Param({"3600000"})
	private int capacity = 3_600_000;
	public float[] numbers;

	@Setup(Level.Invocation)
	public void setUp() {
	    numbers = new float[capacity];
	    for (int i = 0; i < capacity; i++) {
		numbers[i] = ThreadLocalRandom.current().nextFloat();
	    }
	}
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void doDivision(HugeArray array) {
	float[] numbers = array.numbers;
	for (int i = 0; i < numbers.length; i++) {
	    numbers[i] /= 500.0f;
	}
    }

    public static void main(String[] args) throws IOException {
//	HugeArray hugeArray = new HugeArray();
//	hugeArray.setUp();
//	Floats floats = new Floats();
//	long start = System.nanoTime();
//	floats.doDivision(hugeArray);
//	long end = System.nanoTime();
//	System.out.println(end - start);
	org.openjdk.jmh.Main.main(args);
    }
}
