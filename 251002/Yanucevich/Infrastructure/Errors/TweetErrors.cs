using System;
using Domain.Shared;

namespace Infrastructure.Errors;

public static class TweetErrors
{
    public static readonly Error TweetNotUniqueError = 
        new Error("40331", "Tweets must have a unique title");

    public static readonly Error TweetNotFoundError = 
        new Error("40332", "Tweet with the provided ID has not been found");
}
