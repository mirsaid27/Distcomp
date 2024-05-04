package by.bsuir.poit.dc.cassandra.api.dto.response;

/**
 * @author Name Surname
 * 
 */
public record PresenceDto(boolean isPresent) {

    public static PresenceDto wrap(boolean isDeleted) {
	return new PresenceDto(isDeleted);
    }

    public PresenceDto ifPresent(Runnable task) {
	if (isPresent) {
	    task.run();
	}
	return this;
    }

    public PresenceDto orElse(Runnable task) {
	if (!isPresent) {
	    task.run();
	}
	return this;
    }
}
