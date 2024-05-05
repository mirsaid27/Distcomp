package by.bsuir.discussion.exception.model.dto_convert;

public class ToConvertException extends RuntimeException {
    public ToConvertException(String message){
        super(message);
    }

    public ToConvertException(){
        super("Dto conversion fails");
    }
}
