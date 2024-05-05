package by.bsuir.test_rw.exception.model.not_found;

public class EntityNotFoundException extends NotFoundException{
    public EntityNotFoundException(){
        super("Not found entity object");
    }

    public EntityNotFoundException(Object id){
        super(String.format("Can't find entity with id %s", id.toString()));
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
