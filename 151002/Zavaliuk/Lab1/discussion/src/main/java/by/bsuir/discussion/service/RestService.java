package by.bsuir.discussion.service;

import java.util.List;

public interface RestService<Q, A> {
    List<A> findAll();
    
    A findById(Long id);
    
    A create(Q dto);
    
    A update(Q dto);
    
    void removeById(Long id);
}
