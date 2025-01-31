using System;
using Domain.Shared;

namespace Infrastructure.Errors;

public static class ReactionErrors
{
    public static readonly Error ReactionNotFoundError =
        new Error("40341", "Reaction with the provided ID has not been found");
}
