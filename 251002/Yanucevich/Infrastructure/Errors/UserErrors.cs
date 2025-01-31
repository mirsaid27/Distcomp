using System;
using Domain.Shared;

namespace Infrastructure.Errors;

public static class UserErrors
{
    public static readonly Error UserNotUniqueError = 
        new Error("40321", "User login must be unique");
    
    public static readonly Error UserNotFoundError = 
        new Error("40322", "User with the provided ID was not found");
        
}
