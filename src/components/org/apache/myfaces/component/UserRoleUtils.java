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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.util.StringTokenizer;

/**
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
 * Revision 1.1  2004/03/31 11:58:33  manolito
 * custom component refactoring
 *
 */
public class UserRoleUtils
{
    //private static final Log log = LogFactory.getLog(UserRoleUtils.class);

    /**
     * Gets the comma separated list of visibility user roles from the given component
     * and checks if current user is in one of these roles.
     * @param component a user role aware component
     * @return true if no user roles are defined for this component or
     *              user is in one of these roles, false otherwise
     */
    public static boolean isVisibleOnUserRole(UIComponent component)
    {
        String userRole;
        if (component instanceof UserRoleAware)
        {
            userRole = ((UserRoleAware)component).getVisibleOnUserRole();
        }
        else
        {
            userRole = (String)component.getAttributes().get(UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR);
        }

        if (userRole == null)
        {
            // no restriction
            return true;
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();
        StringTokenizer st = new StringTokenizer(userRole, ",");
        while (st.hasMoreTokens())
        {
            if (facesContext.getExternalContext().isUserInRole(st.nextToken().trim()))
            {
                return true;
            }
        }
        return false;
    }


    /**
     * Gets the comma separated list of enabling user roles from the given component
     * and checks if current user is in one of these roles.
     * @param component a user role aware component
     * @return true if no user roles are defined for this component or
     *              user is in one of these roles, false otherwise
     */
    public static boolean isEnabledOnUserRole(UIComponent component)
    {
        String userRole;
        if (component instanceof UserRoleAware)
        {
            userRole = ((UserRoleAware)component).getEnabledOnUserRole();
        }
        else
        {
            userRole = (String)component.getAttributes().get(UserRoleAware.ENABLED_ON_USER_ROLE_ATTR);
        }

        if (userRole == null)
        {
            // no restriction
            return true;
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();
        StringTokenizer st = new StringTokenizer(userRole, ",");
        while (st.hasMoreTokens())
        {
            if (facesContext.getExternalContext().isUserInRole(st.nextToken().trim()))
            {
                return true;
            }
        }
        return false;
    }

}
