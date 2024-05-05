namespace dc_rest.Exceptions;

public class ValidatinonException : Exception
{
    public int Code;
    public ValidatinonException() : base(){}

    public ValidatinonException(string errorMessage, int code) : base(errorMessage)
    {
        Code = code;
    }
}