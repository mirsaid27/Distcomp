package by.bsuir.poit.dc.cassandra.model;

import lombok.RequiredArgsConstructor;

/**
 * @author Name Surname
 * 
 */
public class CommentBuilder {
    @RequiredArgsConstructor
    public enum Status {
	PENDING(1), DECLINED(2), APPROVED(3);
	private final int id;

	public short id() {
	    return (short) id;
	}
    }
}
