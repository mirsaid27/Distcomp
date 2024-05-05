package by.bsuir.poit.dc.context;

/**
 * @author Name Surname
 * 
 */
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface CatchLevel {
    Class<? extends Throwable> value();
}
