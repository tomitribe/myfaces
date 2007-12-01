/*
 * Copyright 2004-2006 The Apache Software Foundation.
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

package javax.faces.convert;

import org.apache.shale.test.base.AbstractJsfTestCase;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

import junit.framework.Test;

public class NumberConverterTest extends AbstractJsfTestCase
{
    private NumberConverter mock;

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(NumberConverterTest.class);
    }

    public NumberConverterTest(String name)
    {
        super(name);
    }

    protected void setUp() throws Exception
    {
        super.setUp();

        mock = new NumberConverter();
        mock.setLocale(Locale.FRANCE);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.GERMANY);

    }

    protected void tearDown() throws Exception
    {
        super.tearDown();

        mock = null;
    }

//    public void testFranceLocale()
//    {
//
//        UIInput input = new UIInput();
//	mock.setType("currency");
//        Number number = (Number) mock.getAsObject(FacesContext.getCurrentInstance(), input, "12 345,68 �");
//        assertNotNull(number);
//    }
}
