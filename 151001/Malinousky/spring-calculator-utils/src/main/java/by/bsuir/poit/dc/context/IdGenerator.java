package by.bsuir.poit.dc.context;

import java.util.UUID;

/**
 * @author Name Surname
 * 
 */
public class IdGenerator {
    public long nextLong() {
	return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
}
