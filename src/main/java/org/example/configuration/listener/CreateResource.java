package org.example.configuration.listener;

import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.ServletContextEvent;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

@WebListener
public class CreateResource implements ServletContextListener {
    @Override
    @SneakyThrows
    public void contextInitialized(ServletContextEvent event) {
        String avatarDirectoryPath = event.getServletContext().getInitParameter("avatarDirectory");
        Path avatarDirectory = Paths.get(avatarDirectoryPath);
        event.getServletContext().setAttribute("avatarDirectory", avatarDirectory);
    }
}
