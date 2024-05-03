package by.bsuir.poit.dc.rest.simple;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author Name Surname
 * 
 */
@Fork(value = 1, warmups = 2)
public class ConcatenationTest {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void doDivision() {
	var builder = new StringBuilder();
	builder.append("Hello World");
	for (int i = 0; i < 10; i++) {
	    builder.append((char) i);
	}
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public String LinQTest() {
	char[] a = new char[10];
	for (int i = 0; i < a.length; i++) {
	    a[i] = (char) (i + '0');
	}
	return STR."Hello World\{a[0]}\{a[1]}\{a[2]}\{a[3]}\{a[4]}\{a[5]}\{a[6]}\{a[7]}\{a[8]}\{a[9]}";
    }

    public static void main(String[] args) throws Exception {
	org.openjdk.jmh.Main.main(args);
    }
}
