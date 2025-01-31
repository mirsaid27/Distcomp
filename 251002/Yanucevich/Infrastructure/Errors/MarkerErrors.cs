using System;
using Domain.Shared;

namespace Infrastructure.Errors;

public static class MarkerErrors
{
    public static readonly Error MarkerNotFoundError =
        new Error("40411", "Marker with the specified ID was not found");

    public static readonly Error MarkerNotUniqueError = 
        new Error("40311", "Markers must have a unique name");     
}
