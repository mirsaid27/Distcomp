namespace Lab2.Exceptions
{
    public static class ErrorMessages
    {
        public static string CreatorNotFoundMessage(long id) => $"Creator with id {id} was not found.";
        public static string NewsNotFoundMessage(long id) => $"News with id {id} was not found.";
        public static string StickerNotFoundMessage(long id) => $"Sticker with id {id} was not found.";
        public static string NoteNotFoundMessage(long id) => $"Note with id {id} was not found.";

    }
}
