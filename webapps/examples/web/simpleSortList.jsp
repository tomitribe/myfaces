<%@ page session="false"
%><%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"
%><%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"
%><%@ taglib uri="http://myfaces.sourceforge.net/tld/myfaces_ext_0_9.tld" prefix="x"
%><html>

<!--
/**
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
//-->

<%@include file="inc/head.inc" %>

<body>

<!--
managed beans used:
    list
-->

<f:view>

    <f:loadBundle basename="net.sourceforge.myfaces.examples.resource.example_messages" var="example_messages"/>

    <x:panel_layout id="page" layout="#{globalOptions.pageLayout}"
            styleClass="pageLayout"
            headerClass="pageHeader"
            navigationClass="pageNavigation"
            bodyClass="pageBody"
            footerClass="pageFooter" >

        <%@include file="inc/page_header.jsp" %>
        <%@include file="inc/navigation.jsp"  %>

        <f:facet name="body">
            <h:panel_group id="body">

                <x:data_table styleClass="standardTable"
                        headerClass="standardTable_SortHeader"
                        footerClass="standardTable_Footer"
                        rowClasses="standardTable_Row1,standardTable_Row2"
                        var="car"
                        value="#{list.cars}"
                        sortColumn="#{list.sort}"
                        sortAscending="#{list.ascending}">

                    <h:column>
                        <f:facet name="header">
                            <x:command_sortheader columnName="type">
                                <h:output_text value="#{example_messages['sort_cartype']}" />
                            </x:command_sortheader>
                        </f:facet>
                        <h:output_text value="#{car.type}" />
                        <f:facet name="footer">
                            <h:output_text id="ftr1" value="(footer 1)"  />
                        </f:facet>
                    </h:column>

                    <h:column>
                        <f:facet name="header">
                            <x:command_sortheader columnName="color">
                                <h:output_text value="#{example_messages['sort_carcolor']}" />
                            </x:command_sortheader>
                        </f:facet>
                        <h:output_text value="#{car.color}" />
                        <f:facet name="footer">
                            <h:output_text id="ftr2" value="(footer 2)"  />
                        </f:facet>
                    </h:column>

                </x:data_table>

            </h:panel_group>
        </f:facet>

            <%@include file="inc/page_footer.jsp" %>

    </x:panel_layout>

</f:view>

</body>

</html>