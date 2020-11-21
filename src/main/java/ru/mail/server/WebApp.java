package ru.mail.server;


import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import ru.mail.server.core.DefaultServer;
import ru.mail.server.handlers.GetHandler;

import java.net.URL;

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
        context.setWelcomeFiles(new String[]{"/index.html"});
        context.addServlet(
                new ServletHolder(
                        "default",
                        DefaultServlet.class
                ),
                "/"
        );
        ContextHandler contextGetProd = new ContextHandler("/allProducts");
        contextGetProd.setHandler(new GetHandler());

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{context, contextGetProd});
        server.setHandler(contexts);

        server.start();
       /* ArrayList<Product> list = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        list.add(new Product("Sheaker", "Nike", 200));
        list.add(new Product("Glove", "Adidas", 200));
        list.add(new Product("Scarf", "Hilfiger", 200));
        list.add(new Product("T-shirt", "Puma", 200));


        try(Writer writer=new FileWriter("src/main/resources/static/products.json")) {

            for (Product p : list) {
                System.out.println(p.getManufacturer());
                gson.toJson(p, writer);

            }
        }*/


    }
}
