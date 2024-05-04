package by.bsuir.poit.dc.rest.simple;

import lombok.AllArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.Cache;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author Name Surname
 * 
 */
@Fork(value = 1, warmups = 1)
public class SimpleStructTest {
    public enum Type {
	SQUARE, RECTANGLE, TRIANGLE, CIRCLE
    }

    @AllArgsConstructor
    public static class GenericShape {
	public Type type;
	public float width;
	public float height;
    }

    private static float getAreaSwitch(GenericShape shape) {
	return switch (shape.type) {
	    case SQUARE -> shape.width * shape.width;
	    case RECTANGLE -> shape.width * shape.height;
	    case TRIANGLE -> 0.5f * shape.width * shape.height;
	    case CIRCLE -> (float) Math.PI * shape.width * shape.width;
	};
    }

    private static float getAreaUnion(GenericShape shape) {
	final float[] PREFIX = {1.0f, 1.0f, 0.5f, (float) Math.PI};
	return PREFIX[shape.type.ordinal()] * shape.width * shape.height;
    }

    float getTotalAreaSwitch(GenericShape[] shapes) {
	float sum = 0.0f;
	for (GenericShape shape : shapes) {
	    sum += getAreaSwitch(shape);
	}
	return sum;
    }

    float getTotalAreaSwitch4(GenericShape[] shapes) {
	float sum0 = 0.0f;
	float sum1 = 0.0f;
	float sum2 = 0.0f;
	float sum3 = 0.0f;
	int index = 0;
	while (index < shapes.length) {
	    assert index + 4 < shapes.length;
	    sum0 += getAreaSwitch(shapes[index]);
	    sum1 += getAreaSwitch(shapes[index + 1]);
	    sum2 += getAreaSwitch(shapes[index + 2]);
	    sum3 += getAreaSwitch(shapes[index + 3]);
	    index += 4;
	}
	return sum0 + sum1 + sum2 + sum3;
    }

    float getTotalAreaSwitch_iterators(List<GenericShape> shapes) {
	float sum = 0.0f;
	for (GenericShape shape : shapes) {
	    sum += getAreaSwitch(shape);
	}
	return sum;
    }

    float getTotalAreaTable(GenericShape[] shapes) {
	float sum = 0.0f;
	for (GenericShape shape : shapes) {
	    sum += getAreaUnion(shape);
	}
	return sum;
    }

    float getTotalAreaTable4(GenericShape[] shapes) {
	float sum0 = 0.0f;
	float sum1 = 0.0f;
	float sum2 = 0.0f;
	float sum3 = 0.0f;
	int index = 0;
	while (index < shapes.length) {
	    assert index + 4 < shapes.length;
	    sum0 += getAreaUnion(shapes[index]);
	    sum1 += getAreaUnion(shapes[index + 1]);
	    sum2 += getAreaUnion(shapes[index + 2]);
	    sum3 += getAreaUnion(shapes[index + 3]);
	    index += 4;
	}
	return sum0 + sum1 + sum2 + sum3;
    }

    float getTotalAreaTable_iterators(List<GenericShape> shapes) {
	float sum = 0.0f;
	for (GenericShape shape : shapes) {
	    sum += getAreaUnion(shape);
	}
	return sum;
    }

    @State(Scope.Benchmark)
    public static class ShapeBatch {
	@Param({"1000"})
	public int capacity;
	public GenericShape[] shapes;
	public List<GenericShape> shapeList;
	private final GenericShape[] ORIGINS = {
	    new GenericShape(Type.CIRCLE, 30.0f, 12.0f),
	    new GenericShape(Type.RECTANGLE, 48.0f, 35.0f),
	    new GenericShape(Type.SQUARE, 25.3f, 43.7f),
	    new GenericShape(Type.TRIANGLE, 10.0f, 72.0f)
	};

	@Setup(Level.Invocation)
	public void setUp() {
	    shapes = new GenericShape[capacity];
	    for (int i = 0; i < capacity; i++) {
		int type = ThreadLocalRandom.current().nextInt(Type.values().length);
		shapes[i] = ORIGINS[type];
	    }
	    shapeList = Arrays.asList(shapes);
	}
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public float areaSwitch_Test(ShapeBatch batch) {
	return getTotalAreaSwitch(batch.shapes);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public float areaSwitch4_Test(ShapeBatch batch) {
	return getTotalAreaSwitch4(batch.shapes);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public float areaSwitch_iterators_Test(ShapeBatch batch) {
	return getTotalAreaSwitch_iterators(batch.shapeList);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public float areaUnion_Test(ShapeBatch batch) {
	return getTotalAreaTable(batch.shapes);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public float areaUnit4_Test(ShapeBatch batch) {
	return getTotalAreaTable4(batch.shapes);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public float areaUnit_iterators_Test(ShapeBatch batch) {
	return getTotalAreaTable_iterators(batch.shapeList);
    }

    public static void main(String[] args) throws IOException {
	org.openjdk.jmh.Main.main(args);
    }
}
