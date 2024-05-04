package by.bsuir.poit.dc.rest.context;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * @author Name Surname
 * 
 */
public class TweetStickerKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
	return null;
    }
}
