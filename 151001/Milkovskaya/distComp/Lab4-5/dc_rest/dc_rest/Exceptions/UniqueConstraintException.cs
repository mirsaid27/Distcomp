namespace dc_rest.Exceptions;

public class UniqueConstraintException : Exception
{
    public int Code;

    public UniqueConstraintException(string errorMessage, int code) : base(errorMessage)
    {
        Code = code;
    }
}