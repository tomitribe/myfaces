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
package javax.faces.component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.el.ValueBinding;
import javax.faces.event.ValueChangeEvent;
import javax.faces.render.Renderer;
import java.lang.reflect.Array;
import java.util.List;

/**
 * see Javadoc of JSF Specification
 *
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UISelectMany
        extends UIInput
{
    public static final String INVALID_MESSAGE_ID = "javax.faces.component.UISelectMany.INVALID";

    public Object[] getSelectedValues()
    {
        return (Object[])getValue();
    }

    public void setSelectedValues(Object[] selectedValues)
    {
        setValue(selectedValues);
    }

    public ValueBinding getValueBinding(String name)
    {
        if (name == null) throw new NullPointerException("name");
        if (name.equals("selectedValues"))
        {
            return super.getValueBinding("value");
        }
        else
        {
            return super.getValueBinding(name);
        }
    }

    public void setValueBinding(String name,
                                ValueBinding binding)
    {
        if (name == null) throw new NullPointerException("name");
        if (name.equals("selectedValues"))
        {
            super.setValueBinding("value", binding);
        }
        else
        {
            super.setValueBinding(name, binding);
        }
    }

    /**
     * @return true if Objects are different (!)
     */
    protected boolean compareValues(Object previous,
                                    Object value)
    {
        if (previous == null)
        {
            // one is null, the other not
            return value != null;
        }
        else if (value == null)
        {
            // one is null, the other not
            return previous != null;
        }
        else
        {
            if (previous instanceof Object[] &&
                value instanceof Object[])
            {
                return compareObjectArrays((Object[])previous, (Object[])value);
            }
            else if (previous instanceof List &&
                     value instanceof List)
            {
                return compareLists((List)previous, (List)value);
            }
            else if (previous.getClass().isArray() &&
                     value.getClass().isArray())
            {
                return comparePrimitiveArrays(previous, value);
            }
            else
            {
                //Objects have different classes
                return true;
            }
        }
    }

    private boolean compareObjectArrays(Object[] previous,
                                        Object[] value)
    {
        int length = ((Object[])value).length;
        if (((Object[])previous).length != length)
        {
            //different length
            return true;
        }

        boolean[] scoreBoard = new boolean[length];
        for (int i = 0; i < length; i++)
        {
            Object p = previous[i];
            boolean found = false;
            for (int j = 0; j < length; j++)
            {
                if (scoreBoard[j] == false)
                {
                    Object v = value[j];
                    if ((p == null && v == null) ||
                        (p != null && v != null && p.equals(v)))
                    {
                        scoreBoard[j] = true;
                        found = true;
                        break;
                    }
                }
            }
            if (!found)
            {
                return true;    //current element of previous array not found in new array
            }
        }

        return false;   // arrays are identical
    }

    private boolean compareLists(List previous, List value)
    {
        int length = value.size();
        if (previous.size() != length)
        {
            //different length
            return true;
        }

        boolean[] scoreBoard = new boolean[length];
        for (int i = 0; i < length; i++)
        {
            Object p = previous.get(i);
            boolean found = false;
            for (int j = 0; j < length; j++)
            {
                if (scoreBoard[j] == false)
                {
                    Object v = value.get(j);
                    if ((p == null && v == null) ||
                        (p != null && v != null && p.equals(v)))
                    {
                        scoreBoard[j] = true;
                        found = true;
                        break;
                    }
                }
            }
            if (!found)
            {
                return true;    //current element of previous List not found in new List
            }
        }

        return false;   // Lists are identical
    }

    private boolean comparePrimitiveArrays(Object previous, Object value)
    {
        int length = Array.getLength(value);
        if (Array.getLength(previous) != length)
        {
            //different length
            return true;
        }

        boolean[] scoreBoard = new boolean[length];
        for (int i = 0; i < length; i++)
        {
            Object p = Array.get(previous, i);
            boolean found = false;
            for (int j = 0; j < length; j++)
            {
                if (scoreBoard[j] == false)
                {
                    Object v = Array.get(value, j);
                    if ((p == null && v == null) ||
                        (p != null && v != null && p.equals(v)))
                    {
                        scoreBoard[j] = true;
                        found = true;
                        break;
                    }
                }
            }
            if (!found)
            {
                return true;    //current element of previous array not found in new array
            }
        }

        return false;   // arrays are identical
    }

    protected void validateValue(FacesContext context, Object convertedValue)
    {
        boolean empty =
            convertedValue == null
                || ((convertedValue instanceof Object[]) && (((Object[]) convertedValue).length == 0))
                || ((convertedValue instanceof List) && ((List) convertedValue).isEmpty());
        if (isRequired() && empty)
        {
            _MessageUtils.addErrorMessage(context, this, REQUIRED_MESSAGE_ID,new Object[]{getId()});
            setValid(false);
            return;
        }

        if (!empty)
        {
            _ComponentUtils.callValidators(context, this, convertedValue);
        }

        //TODO: see javadoc: iterate through UISelectItem and UISelectItems and check
        //current values against these items
    }

    /**
     * First part is identical to super.validate except the empty condition.
     * Second part: iterate through UISelectItem and UISelectItems and check
     *              current values against these items
     */
    public void validate(FacesContext context)
    {
        // TODO : Setting the submitted value to null in the super class causes a bug, if set to null, you'll get the following error :
        // java.lang.NullPointerException at org.apache.myfaces.renderkit._SharedRendererUtils.getConvertedUISelectManyValue(_SharedRendererUtils.java:118)
        super.validate(context);
    }


    protected Object getConvertedValue(FacesContext context, Object submittedValue)
    {
        try
        {
            Renderer renderer = getRenderer(context);
            if (renderer != null)
            {
                return renderer.getConvertedValue(context, this, submittedValue);
            }
            else if (submittedValue == null)
            {
                return null;
            }
            else if (submittedValue instanceof String[])
            {
                return _SharedRendererUtils.getConvertedUISelectManyValue(context, this,
                                                                          (String[])submittedValue);
            }
        }
        catch (ConverterException e)
        {
            FacesMessage facesMessage = e.getFacesMessage();
            if (facesMessage != null)
            {
                context.addMessage(getClientId(context), facesMessage);
            }
            else
            {
                _MessageUtils.addErrorMessage(context, this, CONVERSION_MESSAGE_ID,new Object[]{getId()});
            }
            setValid(false);
        }
        return submittedValue;
    }




    //------------------ GENERATED CODE BEGIN (do not modify!) --------------------

    public static final String COMPONENT_TYPE = "javax.faces.SelectMany";
    public static final String COMPONENT_FAMILY = "javax.faces.SelectMany";
    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Listbox";


    public UISelectMany()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }


    //------------------ GENERATED CODE END ---------------------------------------
}
