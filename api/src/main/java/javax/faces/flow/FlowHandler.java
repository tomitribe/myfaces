/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package javax.faces.flow;

import javax.faces.context.FacesContext;

/**
 *
 * @since 2.2
 */
public abstract class FlowHandler
{

    public abstract java.util.Map<java.lang.Object, java.lang.Object> getCurrentFlowScope();

    public abstract Flow getFlow(FacesContext context,
        java.lang.String definingDocumentId,
        java.lang.String id);

    public abstract void addFlow(FacesContext context,
        Flow toAdd);

    public abstract Flow getCurrentFlow(FacesContext context);

    public Flow getCurrentFlow()
    {
        return getCurrentFlow(FacesContext.getCurrentInstance());
    }

    public abstract void transition(FacesContext context,
        Flow sourceFlow,
        Flow targetFlow,
        FlowCallNode outboundCallNode);

    public abstract boolean isActive(FacesContext context,
        java.lang.String definingDocument,
        java.lang.String id);
}
