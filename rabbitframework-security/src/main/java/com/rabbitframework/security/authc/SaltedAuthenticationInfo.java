package com.rabbitframework.security.authc;
import com.rabbitframework.commons.codec.ByteSource;

/**
 * Interface representing account information that may use a salt when hashing credentials.  This interface
 * exists primarily to support environments that hash user credentials (e.g. passwords).
 * <p/>
 * Salts should typically be generated from a secure pseudo-random number generator so they are effectively
 * impossible to guess.  The salt value should be safely stored along side the account information to ensure
 * it is maintained along with the account's credentials.
 * <p/>
 * This interface exists as a way for Shiro to acquire that salt so it can correctly perform
 * {@link CredentialsMatcher credentials matching} during login attempts.
 * See the {@link HashedCredentialsMatcher HashedCredentialsMatcher} JavaDoc for
 * more information on hashing credentials with salts.
 * @see HashedCredentialsMatcher
 *
 */
public interface SaltedAuthenticationInfo extends AuthenticationInfo {
    /**
     * Returns the salt used to salt the account's credentials or {@code null} if no salt was used.
     *
     * @return the salt used to salt the account's credentials or {@code null} if no salt was used.
     */
    ByteSource getCredentialsSalt();
}