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
package org.apache.myfaces.taglib.html;

import org.apache.myfaces.renderkit.JSFAttr;
import org.apache.myfaces.renderkit.html.HTML;

import javax.faces.component.UIComponent;


/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @author Martin Marinschek
 * @version $Revision$ $Date$
 * $Log$
 * Revision 1.3  2004/10/13 11:51:01  matze
 * renamed packages to org.apache
 *
 * Revision 1.2  2004/07/01 22:01:11  mwessendorf
 * ASF switch
 *
 * Revision 1.1  2004/04/01 12:57:44  manolito
 * additional extended component classes for user role support
 *
 */
public abstract class HtmlGraphicImageTagBase
    extends HtmlComponentTagBase
{
    // UIComponent attributes --> already implemented in UIComponentTagBase

    // user role attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // HTML img attributes relevant for graphic-image
    private String _align;  //FIXME: not in API, HTML 4.0 transitional attribute and not in strict... what to do?
    private String _alt;
    private String _border; //FIXME: not in API, HTML 4.0 transitional attribute and not in strict... what to do!
    private String _height;
    private String _hspace; //FIXME: not in API, HTML 4.0 transitional attribute and not in strict... what to do!
    private String _ismap;
    private String _longdesc;
    private String _onblur;
    private String _onchange;
    private String _onfocus;
    private String _usemap;
    private String _vspace; //FIXME: not in API, HTML 4.0 transitional attribute and not in strict... what to do!
    private String _width;

    //UIGraphic attributes
    private String _url;

    // HtmlGraphicImage attributes
    //none so far

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, HTML.ALIGN_ATTR, _align);
        setStringProperty(component, HTML.ALT_ATTR, _alt);
        setStringProperty(component, HTML.BORDER_ATTR, _border);
        setStringProperty(component, HTML.HEIGHT_ATTR, _height);
        setStringProperty(component, HTML.HSPACE_ATTR, _hspace);
        setBooleanProperty(component, HTML.ISMAP_ATTR, _ismap);
        setStringProperty(component, HTML.LONGDESC_ATTR, _longdesc);
        setStringProperty(component, HTML.ONBLUR_ATTR, _onblur);
        setStringProperty(component, HTML.ONCHANGE_ATTR, _onchange);
        setStringProperty(component, HTML.ONFOCUS_ATTR, _onfocus);
        setStringProperty(component, HTML.USEMAP_ATTR, _usemap);
        setStringProperty(component, HTML.VSPACE_ATTR, _vspace);
        setStringProperty(component, HTML.WIDTH_ATTR, _width);

        setStringProperty(component, JSFAttr.URL_ATTR, _url);
   }

    public void setAlign(String align)
    {
        _align = align;
    }

    public void setAlt(String alt)
    {
        _alt = alt;
    }

    public void setBorder(String border)
    {
        _border = border;
    }

    public void setHeight(String height)
    {
        _height = height;
    }

    public void setHspace(String hspace)
    {
        _hspace = hspace;
    }

    public void setIsmap(String ismap)
    {
        _ismap = ismap;
    }

    public void setLongdesc(String longdesc)
    {
        _longdesc = longdesc;
    }

    public void setOnblur(String onblur)
    {
        _onblur = onblur;
    }

    public void setOnchange(String onchange)
    {
        _onchange = onchange;
    }

    public void setOnfocus(String onfocus)
    {
        _onfocus = onfocus;
    }

    public void setUsemap(String usemap)
    {
        _usemap = usemap;
    }

    public void setVspace(String vspace)
    {
        _vspace = vspace;
    }

    public void setWidth(String width)
    {
        _width = width;
    }

    public void setUrl(String url)
    {
        _url = url;
    }
}
