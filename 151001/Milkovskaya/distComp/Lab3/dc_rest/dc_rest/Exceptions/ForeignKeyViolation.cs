namespace dc_rest.Exceptions;

public class ForeignKeyViolation : Exception
{
    public int Code;

    public ForeignKeyViolation(string errorMessage, int code) : base(errorMessage)
    {
        Code = code;
    }
}