package br.com.frostmc.common.permission.injector.permissible;

import org.bukkit.entity.*;
import org.bukkit.permissions.*;
import java.lang.reflect.*;
import java.util.logging.*;
import java.util.*;

public abstract class PermissibleInjector {
	
    protected final String clazzName;
    protected final String fieldName;
    protected final boolean copyValues;
    
    public PermissibleInjector(final String clazzName, final String fieldName, final boolean copyValues) {
        this.clazzName = clazzName;
        this.fieldName = fieldName;
        this.copyValues = copyValues;
    }
    
    public Permissible inject(final Player player, final Permissible permissible) throws NoSuchFieldException, IllegalAccessException {
        final Field permField = this.getPermissibleField(player);
        if (permField == null) {
            return null;
        }
        final Permissible oldPerm = (Permissible)permField.get(player);
        if (this.copyValues && permissible instanceof PermissibleBase) {
            final PermissibleBase newBase = (PermissibleBase)permissible;
            final PermissibleBase oldBase = (PermissibleBase)oldPerm;
            this.copyValues(oldBase, newBase);
        }
        permField.set(player, permissible);
        return oldPerm;
    }
    
    public Permissible getPermissible(final Player player) throws NoSuchFieldException, IllegalAccessException {
        return (Permissible)this.getPermissibleField(player).get(player);
    }
    
    private Field getPermissibleField(final Player player) throws NoSuchFieldException {
        Class<?> humanEntity;
        try {
            humanEntity = Class.forName(this.clazzName);
        }
        catch (ClassNotFoundException e) {
            Logger.getLogger("PermissionsEx").warning("[PermissionsEx] Unknown server implementation being used!");
            return null;
        }
        if (!humanEntity.isAssignableFrom(player.getClass())) {
            Logger.getLogger("PermissionsEx").warning("[PermissionsEx] Strange error while injecting permissible!");
            return null;
        }
        final Field permField = humanEntity.getDeclaredField(this.fieldName);
        permField.setAccessible(true);
        return permField;
    }
    
    @SuppressWarnings("unchecked")
	private void copyValues(final PermissibleBase old, final PermissibleBase newPerm) throws NoSuchFieldException, IllegalAccessException {
        final Field attachmentField = PermissibleBase.class.getDeclaredField("attachments");
        attachmentField.setAccessible(true);
        final List<Object> attachmentPerms = (List<Object>)attachmentField.get(newPerm);
        attachmentPerms.clear();
        attachmentPerms.addAll((Collection<?>)attachmentField.get(old));
        newPerm.recalculatePermissions();
    }
    
    public abstract boolean isApplicable(final Player p0);
    
    public static class ServerNamePermissibleInjector extends PermissibleInjector
    {
        protected final String serverName;
        
        public ServerNamePermissibleInjector(final String clazz, final String field, final boolean copyValues, final String serverName) {
            super(clazz, field, copyValues);
            this.serverName = serverName;
        }
        
        @Override
        public boolean isApplicable(final Player player) {
            return player.getServer().getName().equalsIgnoreCase(this.serverName);
        }
    }
    
    public static class ClassPresencePermissibleInjector extends PermissibleInjector
    {
        public ClassPresencePermissibleInjector(final String clazzName, final String fieldName, final boolean copyValues) {
            super(clazzName, fieldName, copyValues);
        }
        
        @Override
        public boolean isApplicable(final Player player) {
            try {
                return Class.forName(this.clazzName).isInstance(player);
            }
            catch (ClassNotFoundException ex) {
                return false;
            }
        }
    }
    
    public static class ClassNameRegexPermissibleInjector extends PermissibleInjector
    {
        private final String regex;
        
        public ClassNameRegexPermissibleInjector(final String clazz, final String field, final boolean copyValues, final String regex) {
            super(clazz, field, copyValues);
            this.regex = regex;
        }
        
        @Override
        public boolean isApplicable(final Player player) {
            return player.getClass().getName().matches(this.regex);
        }
    }
}
