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
package org.apache.myfaces.component.html.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.xml.sax.InputSource;

/**
 * @author Sylvain Vieujot (latest modification by $Author$)
 * @version $Revision$ $Date$
 * $Log$
 * Revision 1.5  2004/12/22 01:55:29  svieujot
 * Fix a buffering problem.
 *
 * Revision 1.4  2004/12/02 02:13:15  svieujot
 * *** empty log message ***
 *
 * Revision 1.3  2004/12/02 02:12:32  svieujot
 * Clean log
 *
 * Revision 1.2  2004/12/02 00:26:58  oros
 * i18n issues
 * 
 * Revision 1.1  2004/12/01 20:25:10  svieujot
 * Make the Extensions filter support css and image resources.
 * Convert the popup calendar to use this new filter.
 * 
 */
public class ExtensionsResponseWrapper extends HttpServletResponseWrapper {
    private ByteArrayOutputStream stream = null;
    private PrintWriter writer = null;

    public ExtensionsResponseWrapper(HttpServletResponse response){
        super( response );
        stream = new ByteArrayOutputStream();
    }


    public byte[] getBytes() {
        return stream.toByteArray();
    }

    public String toString(){
    	try{
    		return stream.toString(getCharacterEncoding());
    	}catch(UnsupportedEncodingException e){
    		// an attempt to set an invalid character encoding would have caused this exception before
            throw new RuntimeException("Response accepted invalid character encoding " + getCharacterEncoding());
    	}
    }
    
    /** This method is used by Tomcat.
     */
    public PrintWriter getWriter(){
        if( writer == null )
        	writer = new PrintWriter(stream, true); // autoFlush is true
        return writer;
    }
    
	/** This method is used by Jetty.
	*/
	public ServletOutputStream getOutputStream(){
		return new MyServletOutputStream( stream );
	}
    
    public InputSource getInputSource(){
		ByteArrayInputStream bais = new ByteArrayInputStream( stream.toByteArray() );
		return new InputSource( bais );
    }

     /**
     *  Prevent content-length being set as the page might be modified.
     */
    public void setContentLength(int contentLength) {
        // noop
    }
    
    public void flushBuffer() throws IOException{
    	stream.flush();
    }
    
    public void finishResponse() {
        try {
            if (writer != null) {
                writer.close();
            } else {
                if (stream != null) {
                    stream.close();
                }
            }
        } catch (IOException e) {
        }
    }
    
    /** Used in the <code>getOutputStream()</code> method.
     */ 
    private class MyServletOutputStream extends ServletOutputStream {
		private ByteArrayOutputStream outputStream;
		
		public MyServletOutputStream(ByteArrayOutputStream outputStream){
			this.outputStream = outputStream;
		}
		
		public void write(int b){
		    outputStream.write( b );
		}
		
		public void write(byte[] bytes) throws IOException{
		    outputStream.write( bytes );
		}
		
		public void write(byte[] bytes, int off, int len){
		    outputStream.write(bytes, off, len);
		}
    }
}