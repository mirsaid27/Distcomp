package by.bsuir.poit.dc.rest.context;

import com.aayushatharva.brotli4j.encoder.BrotliOutputStream;
import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import io.netty.handler.codec.compression.Brotli;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Name Surname
 * 
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProtobufRedisSerializer<T> implements RedisSerializer<T> {
    private final Codec<T> codec;

    //todo: add rollback support if brotli is not available
    static {
	try {
	    Brotli.ensureAvailability();
	} catch (Throwable e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public byte[] serialize(T value) throws SerializationException {
	try {
	    byte[] bytes = codec.encode(value);
	    return bytes;
	} catch (IOException e) {
	    throw new SerializationException(e.getMessage());
	}
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
	T dto;
	try {
	    dto = codec.decode(bytes);
	} catch (IOException e) {
	    log.warn(e.getMessage());
	    throw new SerializationException(e.getMessage());
	}
	return dto;
    }

    @SneakyThrows
    private byte[] zip(byte[] bytes) {
	var byteStream = new ByteArrayOutputStream();
	try (BrotliOutputStream brotli = new BrotliOutputStream(byteStream)) {
	    brotli.write(bytes);
	}
	return byteStream.toByteArray();
    }

    @SneakyThrows
    private byte[] unzip(byte[] bytes) {
	try (ByteArrayInputStream brotli = new ByteArrayInputStream(bytes)) {
	    return brotli.readAllBytes();
	}
    }

    public static <T> ProtobufRedisSerializer<T> with(Class<T> clazz) {
	var codec = ProtobufProxy.create(clazz);
	return new ProtobufRedisSerializer<>(codec);
    }
}
