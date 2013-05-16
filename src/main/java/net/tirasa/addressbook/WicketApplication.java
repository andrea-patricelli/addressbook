package net.tirasa.addressbook;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class WicketApplication extends WebApplication {

    @Override
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }

    @Override
    public void init() {
        super.init();
        // Installation of Component Listener for Spring Bean objects
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        mountPage("/error404", ErrorPage.class);
    }
}
