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
package org.apache.myfaces.custom.layout;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.renderkit.html.HTML;
import org.apache.myfaces.taglib.html.HtmlComponentBodyTagBase;

import javax.faces.component.UIComponent;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 * $Log$
 * Revision 1.6  2004/10/13 11:50:57  matze
 * renamed packages to org.apache
 *
 * Revision 1.5  2004/07/01 21:53:09  mwessendorf
 * ASF switch
 *
 * Revision 1.4  2004/05/18 14:31:37  manolito
 * user role support completely moved to components source tree
 *
 * Revision 1.3  2004/04/05 11:04:53  manolito
 * setter for renderer type removed, no more default renderer type needed
 *
 * Revision 1.2  2004/04/01 12:57:40  manolito
 * additional extended component classes for user role support
 *
 * Revision 1.1  2004/03/31 12:15:26  manolito
 * custom component refactoring
 *
 */
public class HtmlPanelLayoutTag
        extends HtmlComponentBodyTagBase
{
    public String getComponentType()
    {
        return HtmlPanelLayout.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return "org.apache.myfaces.Layout";
    }

    // UIComponent attributes --> already implemented in UIComponentBodyTagBase

    // HTML universal attributes --> already implemented in MyFacesTag

    // HTML event handler attributes --> already implemented in MyFacesTag

    // UIPanel attributes --> value attribute already implemented in UIComponentBodyTagBase

    // HtmlPanelLayout attributes
    private String _layout;
    private String _headerClass;
    private String _navigationClass;
    private String _bodyClass;
    private String _footerClass;
    private String _headerStyle;
    private String _navigationStyle;
    private String _bodyStyle;
    private String _footerStyle;

    // HTML table attributes
    private String _align;
    private String _bgcolor;
    private String _border;
    private String _cellpadding;
    private String _cellspacing;
    private String _datafld;
    private String _datasrc;
    private String _dataformatas;
    private String _frame;
    private String _rules;
    private String _summary;
    private String _width;

    // User Role support
    private String _enabledOnUserRole;
    private String _visibleOnUserRole;

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, "layout", _layout);
        setStringProperty(component, "headerClass", _headerClass);
        setStringProperty(component, "navigationClass", _navigationClass);
        setStringProperty(component, "bodyClass", _bodyClass);
        setStringProperty(component, "footerClass", _footerClass);
        setStringProperty(component, "headerStyle", _headerStyle);
        setStringProperty(component, "navigationStyle", _navigationStyle);
        setStringProperty(component, "bodyStyle", _bodyStyle);
        setStringProperty(component, "footerStyle", _footerStyle);

        setStringProperty(component, HTML.ALIGN_ATTR, _align);
        setStringProperty(component, HTML.BGCOLOR_ATTR, _bgcolor);
        setStringProperty(component, HTML.BORDER_ATTR, _border);
        setStringProperty(component, HTML.CELLPADDING_ATTR, _cellpadding);
        setStringProperty(component, HTML.CELLSPACING_ATTR, _cellspacing);
        setStringProperty(component, HTML.DATAFLD_ATTR, _datafld);
        setStringProperty(component, HTML.DATASRC_ATTR, _datasrc);
        setStringProperty(component, HTML.DATAFORMATAS_ATTR, _dataformatas);
        setStringProperty(component, HTML.FRAME_ATTR, _frame);
        setStringProperty(component, HTML.RULES_ATTR, _rules);
        setStringProperty(component, HTML.SUMMARY_ATTR, _summary);
        setStringProperty(component, HTML.WIDTH_ATTR, _width);

        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
    }

    public void setLayout(String layout)
    {
        _layout = layout;
    }

    public void setHeaderClass(String headerClass)
    {
        _headerClass = headerClass;
    }

    public void setNavigationClass(String navigationClass)
    {
        _navigationClass = navigationClass;
    }

    public void setBodyClass(String bodyClass)
    {
        _bodyClass = bodyClass;
    }

    public void setFooterClass(String footerClass)
    {
        _footerClass = footerClass;
    }

    public void setHeaderStyle(String headerStyle)
    {
        _headerStyle = headerStyle;
    }

    public void setNavigationStyle(String navigationStyle)
    {
        _navigationStyle = navigationStyle;
    }

    public void setBodyStyle(String bodyStyle)
    {
        _bodyStyle = bodyStyle;
    }

    public void setFooterStyle(String footerStyle)
    {
        _footerStyle = footerStyle;
    }

    public void setAlign(String align)
    {
        _align = align;
    }

    public void setBgcolor(String bgcolor)
    {
        _bgcolor = bgcolor;
    }

    public void setBorder(String border)
    {
        _border = border;
    }

    public void setCellpadding(String cellpadding)
    {
        _cellpadding = cellpadding;
    }

    public void setCellspacing(String cellspacing)
    {
        _cellspacing = cellspacing;
    }

    public void setDatafld(String datafld)
    {
        _datafld = datafld;
    }

    public void setDatasrc(String datasrc)
    {
        _datasrc = datasrc;
    }

    public void setDataformatas(String dataformatas)
    {
        _dataformatas = dataformatas;
    }

    public void setFrame(String frame)
    {
        _frame = frame;
    }

    public void setRules(String rules)
    {
        _rules = rules;
    }

    public void setSummary(String summary)
    {
        _summary = summary;
    }

    public void setWidth(String width)
    {
        _width = width;
    }

    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }
}
