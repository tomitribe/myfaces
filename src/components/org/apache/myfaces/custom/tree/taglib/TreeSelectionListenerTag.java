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
package org.apache.myfaces.custom.tree.taglib;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.myfaces.custom.tree.HtmlTree;
import org.apache.myfaces.custom.tree.event.TreeSelectionListener;
import org.apache.myfaces.util.ClassUtils;


/**
 * Tag to add a tree selection listeners to a {@link HtmlTree]
 *
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision$ $Date$
 *          $Log$
 *          Revision 1.3  2004/10/13 11:50:58  matze
 *          renamed packages to org.apache
 *
 *          Revision 1.2  2004/07/01 21:53:06  mwessendorf
 *          ASF switch
 *
 *          Revision 1.1  2004/04/22 21:14:55  o_rossmueller
 *          TreeSelectionListener support
 *
 */
public class TreeSelectionListenerTag extends TagSupport
{

    private String type = null;


    public TreeSelectionListenerTag()
    {
    }


    public void setType(String type)
    {
        this.type = type;
    }


    public int doStartTag() throws JspException
    {
        if (type == null)
        {
            throw new JspException("type attribute not set");
        }

        //Find parent UIComponentTag
        UIComponentTag componentTag = UIComponentTag.getParentUIComponentTag(pageContext);
        if (componentTag == null)
        {
            throw new JspException("TreeSelectionListenerTag has no UIComponentTag ancestor");
        }

        if (componentTag.getCreated())
        {
            //Component was just created, so we add the Listener
            UIComponent component = componentTag.getComponentInstance();
            if (component instanceof HtmlTree)
            {
                String className;
                if (UIComponentTag.isValueReference(type))
                {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    ValueBinding valueBinding = facesContext.getApplication().createValueBinding(type);
                    className = (String) valueBinding.getValue(facesContext);
                } else
                {
                    className = type;
                }
                TreeSelectionListener listener = (TreeSelectionListener) ClassUtils.newInstance(className);
                ((HtmlTree) component).addTreeSelectionListener(listener);
            } else
            {
                throw new JspException("Component " + component.getId() + " is no HtmlTree");
            }
        }

        return Tag.SKIP_BODY;
    }
}
