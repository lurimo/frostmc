package br.com.frostmc.common.permission.injector;

import java.util.regex.*;

import br.com.frostmc.common.permission.injector.loader.LoaderNetUtil;
import br.com.frostmc.common.permission.injector.loader.LoaderNormal;
import br.com.frostmc.common.permission.injector.permissible.PermissionMatcher;

import java.lang.reflect.*;

public class RegExpMatcher implements PermissionMatcher{
	
    public static final String RAW_REGEX_CHAR = "$";
    protected static Pattern rangeExpression;
    private Object patternCache;
    
    static {
        RegExpMatcher.rangeExpression = Pattern.compile("(\\d+)-(\\d+)");
    }
    
    public RegExpMatcher() {
        final Class<?> cacheBuilder = this.getClassGuava("com.google.common.cache.CacheBuilder");
        final Class<?> cacheLoader = this.getClassGuava("com.google.common.cache.CacheLoader");
        try {
            final Object obj = cacheBuilder.getMethod("newBuilder", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
            final Method maximumSize = obj.getClass().getMethod("maximumSize", Long.TYPE);
            final Object obj2 = maximumSize.invoke(obj, 500);
            Object loader = null;
            if (this.hasNetUtil()) {
                loader = new LoaderNetUtil();
            }
            else {
                loader = new LoaderNormal();
            }
            final Method build = obj2.getClass().getMethod("build", cacheLoader);
            this.patternCache = build.invoke(obj2, loader);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean isMatches(final String expression, final String permission) {
        try {
            final Method get = this.patternCache.getClass().getMethod("get", Object.class);
            get.setAccessible(true);
            final Object obj = get.invoke(this.patternCache, expression);
            return ((Pattern)obj).matcher(permission).matches();
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
        catch (NoSuchMethodException e4) {
            e4.printStackTrace();
        }
        catch (SecurityException e5) {
            e5.printStackTrace();
        }
        return false;
    }
    
    private Class<?> getClassGuava(String str) {
        Class<?> clasee = null;
        try {
            if (this.hasNetUtil()) {
                str = "net.minecraft.util." + str;
            }
            clasee = Class.forName(str);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clasee;
    }
    
    private boolean hasNetUtil() {
        try {
            Class.forName("net.minecraft.util.com.google.common.cache.LoadingCache");
            return true;
        }
        catch (ClassNotFoundException ex) {
            return false;
        }
    }
}
