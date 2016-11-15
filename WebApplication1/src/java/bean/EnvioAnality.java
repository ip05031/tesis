/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.AnalyticsScopes;
import com.google.api.services.analytics.model.Accounts;
import com.google.api.services.analytics.model.GaData;
import com.google.api.services.analytics.model.Profiles;
import com.google.api.services.analytics.model.Webproperties;
import controller.VisitasJPA;
import entity.Visitas;
import java.io.File;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;


/**
 *
 * @author Flever
 */
@Named(value = "envioAnality")
@SessionScoped
public class EnvioAnality implements Serializable {

    /**
     * Creates a new instance of EnvioAnality
     */
    public EnvioAnality() {
    }

   
    private static final String APPLICATION_NAME = "Hello Analytics";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_EMAIL = "analiticmuna@proyectomuna.iam.gserviceaccount.com";

    public String prueva() throws Exception {
        Analytics analytics = initializeAnalytics();

        String profile = getFirstProfileId(analytics);
        System.out.println("First Profile Id: " + profile);
        printResults(getResults(analytics, profile));
        return "Base Actualizada";
    }

    private static Analytics initializeAnalytics() throws Exception {

        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/libs/proyectoMuna-24eefc45ccfa.p12");

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
                .setServiceAccountPrivateKeyFromP12File(new File(realPath))
                .setServiceAccountScopes(AnalyticsScopes.all())
                .build();

        return new Analytics.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME).build();
    }

    private static String getFirstProfileId(Analytics analytics) throws IOException {

        String profileId = null;
        Accounts accounts = analytics.management().accounts().list().execute();

        if (accounts.getItems().isEmpty()) {
            System.err.println("No se encontro la cuenta");
        } else {
            String firstAccountId = accounts.getItems().get(0).getId();

            Webproperties properties = analytics.management().webproperties()
                    .list(firstAccountId).execute();

            if (properties.getItems().isEmpty()) {
                System.err.println("No se encontro la propiedad web");
            } else {
                String firstWebpropertyId = properties.getItems().get(0).getId();

                Profiles profiles = analytics.management().profiles()
                        .list(firstAccountId, firstWebpropertyId).execute();

                if (profiles.getItems().isEmpty()) {
                    System.err.println("No se encontro identificador");
                } else {
                    profileId = profiles.getItems().get(0).getId();
                }
            }
        }
        return profileId;
    }

    private static GaData getResults(Analytics analytics, String profileId) throws IOException {
        VisitasJPA visita = new VisitasJPA();
        Date inicio = visita.MaximaFechaVisitas();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaConFormato = sdf.format(inicio);
        System.out.println("fecha de inicio" + fechaConFormato);
        return analytics.data().ga()
                .get("ga:" + profileId, fechaConFormato, "yesterday", "ga:sessions").setDimensions("ga:date")
                .execute();
    }

    private static void printResults(GaData results) {
        if (results != null && !results.getRows().isEmpty()) {
            System.out.println("Vista Name: "
                    + results.getProfileInfo().getProfileName());
            Integer f = results.getRows().size();
            VisitasJPA visita = new VisitasJPA();
            Date inicio = visita.MaximaFechaVisitas();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fechaConFormato = sdf.format(inicio);
            for (int i = 0; i < f; i++) {
                System.out.println("Fecha: " + results.getRows().get(i).get(0) + " Seccion " + results.getRows().get(i).get(1));
                if (!(fechaConFormato.equalsIgnoreCase(results.getRows().get(i).get(0) + "")) && !("0".equalsIgnoreCase(results.getRows().get(i).get(1) + ""))) {
                    visita = new VisitasJPA();
                    Visitas vis = new Visitas();
                    String fechades = results.getRows().get(i).get(0) + "";
                    String año = fechades.substring(0, 4);
                    String mes = fechades.substring(4, 6);
                    String dia = fechades.substring(6);
                    String fecha = dia + "-" + mes + "-" + año;
                    Integer cantidad = 0;
                    try {
                        cantidad = Integer.parseInt(results.getRows().get(i).get(1) + "");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    String date_s = fecha + " 00:00:00.0";
                    SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = null;
                    try {
                        date = dt.parse(date_s);
                    } catch (ParseException ex) {
                        Logger.getLogger(EnvioAnality.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    vis.setFechav(date);
                    vis.setCantidadv(cantidad);
                    try {
                        visita.saveVisitas(vis);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

            }
        } else {
            System.out.println("No se encontraron Resultados");
        }
    }
}
