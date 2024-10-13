package org.example.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.datastore.DataStore;
import org.example.serialization.CloningUtility;

@WebListener
public class CreateDataSource implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute("datasource", new DataStore(new CloningUtility()));
    }
}
