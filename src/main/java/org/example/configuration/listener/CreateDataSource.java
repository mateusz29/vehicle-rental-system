package org.example.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.datastore.DataStore;
import org.example.serialization.CloningUtility;

import java.nio.file.Path;

@WebListener
public class CreateDataSource implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        Path avatarDirectory = (Path) event.getServletContext().getAttribute("avatarDirectory");
        //avatarDirectory = Path.of("D:\\studia\\semestr_7\\narzedzia_i_aplikacje_JEE\\laby\\vehicle-rental-system\\src\\main\\resources\\avatar");
        event.getServletContext().setAttribute("datasource", new DataStore(new CloningUtility(), avatarDirectory));
    }
}
