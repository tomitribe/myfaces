package net.sourceforge.myfaces.webapp;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspEngineInfo;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

public class JspFactoryWrapper
    extends JspFactory
{
    private JspFactory _originalJspFactory;

    public JspFactoryWrapper(JspFactory originalJspFactory)
    {
        _originalJspFactory = originalJspFactory;
    }

    public PageContext getPageContext(Servlet servlet, ServletRequest servletRequest, ServletResponse servletResponse, String name, boolean b, int i, boolean b1)
    {
        return new PageContextWrapper(_originalJspFactory.getPageContext(servlet, servletRequest, servletResponse, name, b, i, b1));
    }

    public void releasePageContext(PageContext pageContext)
    {
        if (pageContext instanceof PageContextWrapper)
        {
            _originalJspFactory.releasePageContext(((PageContextWrapper)pageContext).getWrappedPageContext());
        }
        else
        {
            _originalJspFactory.releasePageContext(pageContext);
        }
    }

    public JspEngineInfo getEngineInfo()
    {
        return _originalJspFactory.getEngineInfo();
    }
}
