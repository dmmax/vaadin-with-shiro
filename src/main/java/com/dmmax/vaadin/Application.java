package com.dmmax.vaadin;

import com.dmmax.vaadin.config.VaadinShiroServer;

public class Application {

    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        VaadinShiroServer server = new VaadinShiroServer(8080);

        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
