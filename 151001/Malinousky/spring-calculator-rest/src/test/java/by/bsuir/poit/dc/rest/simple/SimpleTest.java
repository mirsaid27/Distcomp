package by.bsuir.poit.dc.rest.simple;

import lombok.AllArgsConstructor;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author Name Surname
 * 
 */

@Fork(value = 1, warmups = 1)
public class SimpleTest {
    public interface SimpleShape {
	float getArea();
    }

    @AllArgsConstructor
    private static class Square implements SimpleShape {
	private float side;

	@Override
	public float getArea() {
	    return side * side;
	}
    }

    @AllArgsConstructor
    private static class Rectangle implements SimpleShape {
	private float height;
	private float width;

	@Override
	public float getArea() {
	    return height * width;
	}
    }

    @AllArgsConstructor
    private static class Triangle implements SimpleShape {
	private float base;
	private float height;

	@Override
	public float getArea() {
	    return 0.5f * base * height;
	}
    }

    @AllArgsConstructor
    private static class Circle implements SimpleShape {
	private float radius;

	@Override
	public float getArea() {
	    return (float) (Math.PI * radius * radius);
	}
    }

    @State(Scope.Benchmark)
    public static class ShapeBatch {
	@Param({"1000"})
	public int capacity;
	public SimpleShape[] shapes;
	public List<SimpleShape> shapeList;
	private final SimpleShape[] ORIGINS = {
	    new Circle(50.0f),
	    new Square(23.3f),
	    new Rectangle(43.7f, 73.5f),
	    new Triangle(10f, 53.3f)
	};

	@Setup(Level.Invocation)
	public void setUp() {
	    shapes = new SimpleShape[capacity];
	    for (int i = 0; i < capacity; i++) {
		int type = ThreadLocalRandom.current().nextInt(ORIGINS.length);
		shapes[i] = ORIGINS[type];
	    }
	    shapeList = Arrays.asList(shapes);
	}
    }

    private float getTotalAreaVTBL(SimpleShape[] shapes) {
	float sum = 0.0f;
	for (SimpleShape shape : shapes) {
	    sum += shape.getArea();
	}
	return sum;
    }

    private float getTotalAreaVTBL4(SimpleShape[] shapes) {
	float sum0 = 0.0f;
	float sum1 = 0.0f;
	float sum2 = 0.0f;
	float sum3 = 0.0f;
	int index = 0;
	while (index < shapes.length) {
	    assert index + 4 < shapes.length;
	    sum0 += shapes[index].getArea();
	    sum1 += shapes[index + 1].getArea();
	    sum2 += shapes[index + 2].getArea();
	    sum3 += shapes[index + 3].getArea();
	    index += 4;
	}
	return sum0 + sum1 + sum2 + sum3;
    }

    private float getTotalArealVTBL_iterators(List<SimpleShape> shapes) {
	float sum = 0.0f;
	for (SimpleShape shape : shapes) {
	   sum += shape.getArea();
	}
	return sum;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public float areaVTBL_Test(ShapeBatch batch) {
	return getTotalAreaVTBL(batch.shapes);
    }

    //    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public float areaVTBL4_Test(ShapeBatch batch) {
	return getTotalAreaVTBL4(batch.shapes);
    }

    //    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public float areaVTBL_iterators_Test(ShapeBatch batch) {
	return getTotalArealVTBL_iterators(batch.shapeList);
    }

    public static void main(String[] args) throws Exception {
	org.openjdk.jmh.Main.main(args);
    }
}
