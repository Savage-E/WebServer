package ru.mail.server;


import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.DoSFilter;
import org.eclipse.jetty.servlets.QoSFilter;
import org.eclipse.jetty.util.resource.Resource;
import ru.mail.server.core.DefaultServer;
import ru.mail.server.handlers.SecurityHandlerBuilder;
import ru.mail.server.servlets.GetProducts;
import ru.mail.server.servlets.PostProduct;

import javax.servlet.DispatcherType;
import java.net.URL;
import java.util.EnumSet;

@SuppressWarnings({"Duplicates", "NotNullNullableValidation"})
public final class WebApp {
    public static void main(String[] args) throws Exception {
        final Server server = new DefaultServer().build();

        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.NO_SESSIONS
        );
        context.setContextPath("/");
        final URL resource = DefaultServlet.class.getResource("/static");
        context.setBaseResource(Resource.newResource(resource.toExternalForm()));
        context.setWelcomeFiles(new String[]{"/index"});
        context.addServlet(
                new ServletHolder(
                        "default",
                        DefaultServlet.class
                ),
                "/"
        );
        context.addServlet(new ServletHolder(new PostProduct()), "/submit");
        context.addServlet(new ServletHolder(new GetProducts()), "/allProducts");

        final QoSFilter filter = new QoSFilter();
        final FilterHolder filterHolder = new FilterHolder(filter);
        filterHolder.setInitParameter("maxRequest", "1");
        context.addFilter(filterHolder, "/submit", EnumSet.of(DispatcherType.REQUEST));

        final DoSFilter doSFilter = new DoSFilter();
        final FilterHolder filterHolder1 = new FilterHolder(doSFilter);
        filterHolder1.setInitParameter("maxRequestPerSec", "1");
        context.addFilter(filterHolder1, "/", EnumSet.of(DispatcherType.REQUEST));


        final String hashConfig = WebApp.class.getResource("/hash_config").toExternalForm();

        final HashLoginService hashLoginService = new HashLoginService("login", hashConfig);
        final ConstraintSecurityHandler securityHandler = new SecurityHandlerBuilder().build(hashLoginService);
        server.addBean(hashLoginService);
        securityHandler.setHandler(context);

        HandlerCollection collection = new HandlerCollection();
        collection.setHandlers(new Handler[]{securityHandler, context});
        server.setHandler(collection);
        server.start();
        server.join();
    }
}