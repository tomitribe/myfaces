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
    countryList
-->

<f:view>

    <f:loadBundle basename="net.sourceforge.myfaces.examples.resource.example_messages" var="example_messages"/>

    <x:panelLayout id="page" layout="#{globalOptions.pageLayout}"
            styleClass="pageLayout"
            headerClass="pageHeader"
            navigationClass="pageNavigation"
            bodyClass="pageBody"
            footerClass="pageFooter" >

        <%@include file="inc/page_header.jsp" %>
        <f:facet name="navigation">
            <f:subview id="menu" >
                <jsp:include page="inc/navigation.jsp" />
            </f:subview>
        </f:facet>

        <f:facet name="body">
            <h:panelGroup id="body">

                <x:dataTable id="data"
                        styleClass="scrollerTable"
                        headerClass="standardTable_Header"
                        footerClass="standardTable_Header"
                        rowClasses="standardTable_Row1,standardTable_Row2"
                        columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
                        var="car"
                        value="#{scrollerList.list}"
                        preserveDataModel="true"
                        rows="10"
                   >
                   <h:column>
                       <f:facet name="header">
                       </f:facet>
                       <h:outputText value="#{car.id}" />
                   </h:column>

                   <h:column>
                       <f:facet name="header">
                          <h:outputText value="#{example_messages['label_cars']}" />
                       </f:facet>
                       <h:outputText value="#{car.type}" />
                   </h:column>

                   <h:column>
                       <f:facet name="header">
                          <h:outputText value="#{example_messages['label_color']}" />
                       </f:facet>
                       <h:outputText value="#{car.color}" />
                   </h:column>

                   <f:facet name="footer" >
                        <x:dataScroller id="scroll_1" >
                            <f:facet name="first">
                                <h:outputText value="first" />
                            </f:facet>
                            <f:facet name="previous">
                                <h:outputText value="previous" />
                            </f:facet>
                            <f:facet name="next">
                                <h:outputText value="next" />
                            </f:facet>
                            <f:facet name="last">
                                <h:outputText value="last" />
                            </f:facet>
                        </x:dataScroller>
                    </f:facet>
                </x:dataTable>

                <h:panelGrid columns="1" styleClass="scrollerTable2" columnClasses="standardTable_ColumnCentered" >
                    <x:dataScroller id="scroll_2" for="data">
                        <f:facet name="first" >
                            <h:outputText value="first" />
                        </f:facet>
                        <f:facet name="last">
                            <h:outputText value="last" />
                        </f:facet>
                        <f:facet name="previous">
                            <h:outputText value="previous" />
                        </f:facet>
                        <f:facet name="next">
                            <h:outputText value="next" />
                        </f:facet>
                    </x:dataScroller>
                </h:panelGrid>


            </h:panelGroup>
        </f:facet>

        <%@include file="inc/page_footer.jsp" %>

    </x:panelLayout>

</f:view>

</body>

</html>