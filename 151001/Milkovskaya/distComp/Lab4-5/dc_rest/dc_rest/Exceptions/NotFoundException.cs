namespace dc_rest.Exceptions;

public class NotFoundException : Exception
{
    public int Code;

    public NotFoundException(string errorMessage, int code) : base(errorMessage)
    {
        Code = code;
    }
    
    public NotFoundException(){}
}