/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.rabbitframework.security.authc.pam;

import com.rabbitframework.security.authc.AuthenticationException;
import com.rabbitframework.security.authc.AuthenticationInfo;
import com.rabbitframework.security.authc.AuthenticationToken;
import com.rabbitframework.security.util.CollectionUtils;

/**
 * <tt>AuthenticationStrategy</tt> implementation that requires <em>at least one</em> configured realm to
 * successfully process the submitted <tt>AuthenticationToken</tt> during the log-in attempt.
 * <p/>
 * <p>This means any number of configured realms do not have to support the submitted log-in token, or they may
 * be unable to acquire <tt>AuthenticationInfo</tt> for the token, but as long as at least one can do both, this
 * Strategy implementation will allow the log-in process to be successful.
 * <p/>
 * <p>Note that this implementation will aggregate the account data from <em>all</em> successfully consulted
 * realms during the authentication attempt. If you want only the account data from the first successfully
 * consulted realm and want to ignore all subsequent realms, use the
 * {@link FirstSuccessfulStrategy FirstSuccessfulAuthenticationStrategy} instead.
 * <p/>
 * 只要有一个 Realm 验证成功即可,和 FirstSuccessfulStrategy 不同,返回所有 Realm 身份验证成功的认证信息
 *
 * @see FirstSuccessfulStrategy FirstSuccessfulAuthenticationStrategy
 * @since 0.2
 */
public class AtLeastOneSuccessfulStrategy extends AbstractAuthenticationStrategy {

    /**
     * Ensures that the <code>aggregate</code> method argument is not <code>null</code> and
     * <code>aggregate.{@link com.rabbitframework.security.authc.AuthenticationInfo#getPrincipals() getPrincipals()}</code>
     * is not <code>null</code>, and if either is <code>null</code>, throws an AuthenticationException to indicate
     * that none of the realms authenticated successfully.
     */
    public AuthenticationInfo afterAllAttempts(AuthenticationToken token, AuthenticationInfo aggregate) throws AuthenticationException {
        //we know if one or more were able to succesfully authenticate if the aggregated account object does not
        //contain null or empty data:
        if (aggregate == null || CollectionUtils.isEmpty(aggregate.getPrincipals())) {
            throw new AuthenticationException("Authentication token of type [" + token.getClass() + "] " +
                    "could not be authenticated by any configured realms.  Please ensure that at least one realm can " +
                    "authenticate these tokens.");
        }

        return aggregate;
    }
}
