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
package org.apache.myfaces.custom.tree.renderkit.html;

import java.io.IOException;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectMany;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.myfaces.custom.tree.HtmlTreeCheckbox;
import org.apache.myfaces.renderkit.RendererUtils;
import org.apache.myfaces.renderkit.html.HtmlCheckboxRendererBase;

/**
 * @author <a href="mailto:dlestrat@apache.org">David Le Strat </a>
 */
public class HtmlTreeCheckboxRenderer extends HtmlCheckboxRendererBase
{

    /**
     * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException
    {
        HtmlTreeCheckbox checkbox = (HtmlTreeCheckbox) component;

        String forAttr = checkbox.getFor();
        if (forAttr == null)
        {
            throw new IllegalStateException("Mandatory attribute 'for'");
        }

        UIComponent uiComponent = checkbox.findComponent(forAttr);
        if (uiComponent == null)
        {
            throw new IllegalStateException("Could not find component '" + forAttr
                    + "' (calling findComponent on component '" + checkbox.getClientId(context) + "')");
        }
        if (!(uiComponent instanceof UISelectMany))
        {
            throw new IllegalStateException("UISelectMany expected");
        }

        UISelectMany uiSelectMany = (UISelectMany) uiComponent;
        Set lookupSet = RendererUtils.getSelectedValuesAsSet(uiSelectMany);

        Converter converter;
        try
        {
            converter = RendererUtils.findUISelectManyConverter(context, uiSelectMany);
        }
        catch (FacesException e)
        {
            converter = null;
        }

        Object itemValue = checkbox.getItemValue();
        String itemStrValue = null;
        if (converter == null)
        {
            if (null != itemValue)
            {
                itemStrValue = itemValue.toString();
            }
        }
        else
        {
            itemStrValue = converter.getAsString(context, uiSelectMany, itemValue);
        }

        renderCheckbox(context, uiSelectMany, itemStrValue, checkbox.getItemLabel(), lookupSet.contains(itemValue),
                true);
    }

    /**
     * @see org.apache.myfaces.renderkit.html.HtmlCheckboxRendererBase#isDisabled(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    protected boolean isDisabled(FacesContext facesContext, UIComponent uiComponent)
    {
        return super.isDisabled(facesContext, uiComponent);
    }

    /**
     * @see javax.faces.render.Renderer#decode(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public void decode(FacesContext facesContext, UIComponent uiComponent)
    {
        if (uiComponent instanceof HtmlTreeCheckbox)
        {
            //nothing to decode
        }
        else
        {
            super.decode(facesContext, uiComponent);
        }
    }
}
