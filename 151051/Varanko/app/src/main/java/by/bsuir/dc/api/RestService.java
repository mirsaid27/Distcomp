package by.bsuir.dc.api;

import java.util.List;

public interface RestService<REQ, RES> {
    List<RES> findAll();
    RES findById(long id);
    RES create(REQ request);
    RES update(REQ request);
    boolean removeById(long id);
}
