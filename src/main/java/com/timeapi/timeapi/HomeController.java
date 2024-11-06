package com.timeapi.timeapi;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HomeController {
    @FXML
    private Button api1;
    @FXML
    private Button api2;
    @FXML
    private TextArea TextArea;
    // Urls de las apis
    private static final String OPEN_METEO_URL = "https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.405&current_weather=true";
    // Se necesita la api key y para ello debo registrarme e introducir datos que prefiero no introducir
    private static final String WEATHERSTACK_URL = "http://api.weatherstack.com/current?access_key=YOUR_WEATHERSTACK_API_KEY&query=New%20York";
    @FXML
    public void initialize() {
        // Llamamos a las apis
        api1.setOnAction(event -> Data(1));
        api2.setOnAction(event -> Data(2));
    }

    private void Data(int apiChoice) {
        // Creamos y empezamos un hilo
        new Thread(() -> {
            // variable para guardar los datos
            String result = "";
            try {
                // Eleccion de que api recoger los datos
                if (apiChoice == 1) {
                    result = OpenMeteo();
                } else if (apiChoice == 2) {
                    result = Weatherstack();
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = "Error al obtener datos";
            }
            // Actualizmos la interfaz en el hilo principal
            final String finalresult = result;
            Platform.runLater(() -> TextArea.setText(finalresult));
        }).start();
    }

    private String OpenMeteo() throws Exception {
        // objeto de la url de la api
        URL url = new URL(OPEN_METEO_URL);
        // Establece la conexion con la url
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // Solicitud que pedimos que haga
        connection.setRequestMethod("GET");
        // Leemos la respuesta
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        // Generamos la respuesta
        StringBuilder result = new StringBuilder();
        String line;
        // Leemos las lines una por una
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();
        return "Open-Meteo: " + result.toString();
    }

    private String Weatherstack() throws Exception {
        // objeto de la url de la api
        URL url = new URL(WEATHERSTACK_URL);
        // Establece la conexion con la url
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // Solicitud que pedimos que haga
        connection.setRequestMethod("GET");
        // Leemos la respuesta
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        // Generamos la respuesta
        StringBuilder result = new StringBuilder();
        String line;
        // Leemos las lines una por una
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();
        return "Weatherstack: " + result.toString();
    }
}

