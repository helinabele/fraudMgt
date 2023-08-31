package org.audit.app.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String PRESIDENT = "ROLE_PRESIDENT";

    public static final String VICE_PRESIDENT = "ROLE_VICE_PRESIDENT";

    public static final String DIRECTOR = "ROLE_DIRECTOR";

    public static final String MANAGER = "ROLE_MANAGER";

    public static final String TEAM_LEADER = "ROLE_TEAM_LEADER";

    public static final String AUDITOR = "ROLE_AUDITOR";

    public static final String OTHER = "ROLE_OTHER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {}
}
