package by.epam.javawebtraining.kunitski.finaltask.carrental.webapp.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class CopyRightsTag extends SimpleTagSupport {
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		out.println("© 2016 Car rental. All rights reserved.");
	}
}
