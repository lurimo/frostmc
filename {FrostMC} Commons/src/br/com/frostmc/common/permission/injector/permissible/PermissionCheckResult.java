package br.com.frostmc.common.permission.injector.permissible;

public enum PermissionCheckResult {
	
    UNDEFINED("UNDEFINED", 0, false), 
    TRUE("TRUE", 1, true), 
    FALSE("FALSE", 2, false);
    
    protected boolean result;
    
    private PermissionCheckResult(final String s, final int n, final boolean result) {
        this.result = result;
    }
    
    public boolean toBoolean() {
        return this.result;
    }
    
    @Override
    public String toString() {
        return (this == PermissionCheckResult.UNDEFINED) ? "undefined" : Boolean.toString(this.result);
    }
    
    public static PermissionCheckResult fromBoolean(final boolean result) {
        return result ? PermissionCheckResult.TRUE : PermissionCheckResult.FALSE;
    }
    
}
