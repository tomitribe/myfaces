/*
 * MyFaces - the free JSF implementation
 * Copyright (C) 2003, 2004  The MyFaces Team (http://myfaces.sourceforge.net)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package net.sourceforge.myfaces.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * Wrapper for value binding expression Strings.
 *
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ValueBindingExpression
{
    private static final Log log = LogFactory.getLog(ValueBindingExpression.class);

    private String _valueBindingExpression;

    public ValueBindingExpression(String valueBindingExpression)
    {
        _valueBindingExpression = valueBindingExpression;
    }

    public ValueBinding getValueBinding(FacesContext facesContext)
    {
        return getValueBinding(facesContext, null);
    }

    /**
     * @param facesContext
     * @param desiredType   Class, this value binding is checked against, or null for no check
     * @return the ValueBinding
     */
    public ValueBinding getValueBinding(FacesContext facesContext, Class desiredType)
    {
        ValueBinding vb = facesContext.getApplication().createValueBinding(_valueBindingExpression);
        if (desiredType != null)
        {
            if (!desiredType.isAssignableFrom(vb.getType(facesContext)))
            {
                log.error("Type of value binding " + vb.getExpressionString() + " is not of desired type for map entry in faces-config.");
            }
        }
        return vb;
    }
}
