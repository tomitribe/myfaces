/*
 * Copyright 2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.myfaces.component;

/**
 * Behavioral interface.
 * Components that support user role checking should implement this interface
 * to optimize property access.
 *
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 * $Log$
 * Revision 1.4  2004/10/13 11:50:56  matze
 * renamed packages to org.apache
 *
 * Revision 1.3  2004/07/01 21:53:10  mwessendorf
 * ASF switch
 *
 * Revision 1.2  2004/05/18 14:31:36  manolito
 * user role support completely moved to components source tree
 *
 * Revision 1.1  2004/03/31 07:19:20  manolito
 * changed name from UserRoleSupport
 *
 */
public interface UserRoleAware
{
    public static final String ENABLED_ON_USER_ROLE_ATTR = "enabledOnUserRole";
    public static final String VISIBLE_ON_USER_ROLE_ATTR = "visibleOnUserRole";

    public String getEnabledOnUserRole();
    public void setEnabledOnUserRole(String userRole);

    public String getVisibleOnUserRole();
    public void setVisibleOnUserRole(String userRole);
}
