package by.bsuir.poit.dc.kafka.service;

import by.bsuir.poit.dc.kafka.dto.ResponseEvent;
import by.bsuir.poit.dc.kafka.dto.StatusResponse;
import com.google.errorprone.annotations.Keep;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.management.DescriptorKey;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author Name Surname
 * 
 */
@Slf4j
public abstract class AbstractReactiveKafkaService<T extends StatusResponse> {
    private final Map<UUID, Consumer<ConsumerRecord<UUID, ? extends T>>> requestMap = new ConcurrentHashMap<>();

    protected UUID nextSessionId() {
	UUID uuid;
	do {
	    uuid = UUID.randomUUID();
	} while ((requestMap.putIfAbsent(uuid, (_) -> {
	})) != null);
	return uuid;
    }

    protected <S extends T> void consumerResponse(ConsumerRecord<UUID, S> record) {
	UUID id = record.key();
	Consumer<ConsumerRecord<UUID, ? extends T>> consumer = requestMap.get(id);
	if (consumer == null) {
	    log.warn(STR."Attempt to fetch not existing consumer with id=\{id}");
	    return;
	}
	consumer.accept(record);
    }

    @SuppressWarnings("unchecked")
    protected <S extends T> Mono<S> nextMonoResponse(UUID sessionId) {
	return Mono.create(sink -> {
	    requestMap.put(sessionId, (ConsumerRecord<UUID, ? extends T> record) -> {
		assert record.key().equals(sessionId);
		S response = (S) record.value();
		if (response == null) {
		    sink.error(newUnknownResponseException(sessionId));
		    return;
		}
		if (response.status() == ResponseEvent.OK) {
		    sink.success(response);
		    return;
		}
		unwrapError(sessionId, response.status())
		    .ifPresentOrElse(sink::error, this::unreachable);
	    });
	    sink.onDispose(() -> {
		requestMap.remove(sessionId);
		log.trace("Mono is cancelled by sessionId {}", sessionId);
	    });
	});
    }

    private void unreachable() {
	throw new IllegalStateException("Unreachable");
    }

    private Optional<Throwable> unwrapError(UUID sessionId, ResponseEvent status) {
	var e = switch (status) {
	    case OK -> null;
	    case NOT_FOUND -> newEntityNotFoundException(sessionId);
	    case INVALID_FORMAT -> newBadRequestException(sessionId);
	    case BUSY -> newServerBusyException(sessionId);
	};
	return Optional.ofNullable(e);
    }

    @Keep
    @SuppressWarnings("unchecked")
    protected <S extends T> Flux<S> nextFluxResponse(UUID sessionId) {
	return Flux.create(sink -> {
	    requestMap.put(sessionId, (ConsumerRecord<UUID, ? extends T> record) -> {
		assert sessionId.equals(record.key());
		S response = (S) record.value();
		if (response == null) {
		    sink.error(newUnknownResponseException(sessionId));
		    return;
		}
		if (response.status() == ResponseEvent.OK) {
		    sink.next(response);
		    sink.complete();//otherwise it will infinite loop
		    return;
		}
		unwrapError(sessionId, response.status())
		    .ifPresentOrElse(sink::error, this::unreachable);
	    });
	    sink.onDispose(() -> {
		requestMap.remove(sessionId);
		log.trace("Flux for sessionId={} is cancelled", sessionId);
	    });
	});
    }

    protected abstract Throwable newServerBusyException(UUID sessionId);

    protected abstract Throwable newEntityNotFoundException(UUID sessionId);

    protected abstract Throwable newBadRequestException(UUID sessionId);

    protected Throwable newUnknownResponseException(UUID sessionId) {
	throw new IllegalStateException(STR."Unknown format of response with id=\{sessionId}");
    }

}
