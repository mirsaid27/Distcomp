package by.bsuir.test_rw.repository.implementations.in_memory;

import by.bsuir.test_rw.model.entity.interfaces.EntityModel;

import java.util.concurrent.atomic.AtomicLong;

public class InMemoryRepoLongId<E extends EntityModel<Long>> extends DefaultInMemoryRepo<Long, E> {
    private final AtomicLong currentId;

    protected InMemoryRepoLongId() {
        this.currentId = new AtomicLong();
    }

    @Override
    public Long incrementAndGetId() {
        return currentId.incrementAndGet();
    }
}