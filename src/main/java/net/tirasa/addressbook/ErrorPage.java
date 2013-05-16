package net.tirasa.addressbook;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ErrorPage extends WebPage {

    private static final long serialVersionUID = 1290520325105216570L;

    public ErrorPage(final PageParameters parameters) {

        add(new Label("404", "Page Not Found: please contact us on help@addressbook.it"));

    }
}
