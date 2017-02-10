/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.primefaces.validate.ClientValidator;

@FacesValidator("javaUtil.UrlValidator")
public class UrlValidator implements Validator, ClientValidator {

    private Pattern pattern;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public UrlValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }
        boolean primer = true;
        if (!pattern.matcher(value.toString()).matches()) {
            primer = false;
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Formato de email es incorrecto",
                    value + " el email es incorrecto;"));
        }
        if (primer) {
            String servidor = value.toString();
            if (servidor != null) {
                String partir[] = servidor.split("@");
                if (partir.length > 1) {
                    String validado = comprovarServidor(partir[1]);
                    if (validado == null) {
                        primer = false;
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese un servidor de correo valido",
                                partir[1] + " El servidor es inadecuado;"));
                    }

                }
                if (primer) {
                    if (partir.length > 1) {

                        boolean dato = validacion(partir[1]);
                        if (!dato) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El dominio de su correo no es el adecuado por favor ingrese un correo distinto",
                                    " Error en el correo;"));
                        }

                    }
                }                
            }

        }
    }

    public Map<String, Object> getMetadata() {
        return null;
    }

    public String getValidatorId() {
        return "javaUtil.UrlValidator";
    }

    public String comprovarServidor(String servidor) {

        HttpURLConnection connection = null;
        try {
            long inicio = System.currentTimeMillis();
            URL u = new URL("http://www." + servidor);
            connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("HEAD");

            int code = connection.getResponseCode();
            long fin = System.currentTimeMillis();
            String dato = "Codigo: " + code + " t:" + (fin - inicio);
            return dato;
        } catch (MalformedURLException e) {
            System.out.println("Error de URL: " + e);
            return null;
        } catch (IOException e) {
            System.out.println("Error de conexion: " + e);
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

   
    private boolean validacion(String nombre_dominio) {
        String comparacion[] = {"com", "net", "org", "biz", "coop", "info", "museum", "name",
            "pro", "edu", "gov", "int", "mil", "ac", "ad", "ae", "af", "ag",
            "ai", "al", "am", "an", "ao", "aq", "ar", "as", "at", "au", "aw",
            "az", "ba", "bb", "bd", "be", "bf", "bg", "bh", "bi", "bj", "bm",
            "bn", "bo", "br", "bs", "bt", "bv", "bw", "by", "bz", "ca", "cc",
            "cd", "cf", "cg", "ch", "ci", "ck", "cl", "cm", "cn", "co", "cr",
            "cu", "cv", "cx", "cy", "cz", "de", "dj", "dk", "dm", "do", "dz",
            "ec", "ee", "eg", "eh", "er", "es", "et", "fi", "fj", "fk", "fm",
            "fo", "fr", "ga", "gd", "ge", "gf", "gg", "gh", "gi", "gl", "gm",
            "gn", "gp", "gq", "gr", "gs", "gt", "gu", "gv", "gy", "hk", "hm",
            "hn", "hr", "ht", "hu", "id", "ie", "il", "im", "in", "io", "iq",
            "ir", "is", "it", "je", "jm", "jo", "jp", "ke", "kg", "kh", "ki",
            "km", "kn", "kp", "kr", "kw", "ky", "kz", "la", "lb", "lc", "li",
            "lk", "lr", "ls", "lt", "lu", "lv", "ly", "ma", "mc", "md", "mg",
            "mh", "mk", "ml", "mm", "mn", "mo", "mp", "mq", "mr", "ms", "mt",
            "mu", "mv", "mw", "mx", "my", "mz", "na", "nc", "ne", "nf", "ng",
            "ni", "nl", "no", "np", "nr", "nu", "nz", "om", "pa", "pe", "pf",
            "pg", "ph", "pk", "pl", "pm", "pn", "pr", "ps", "pt", "pw", "py",
            "qa", "re", "ro", "rw", "ru", "sa", "sb", "sc", "sd", "se", "sg",
            "sh", "si", "sj", "sk", "sl", "sm", "sn", "so", "sr", "st", "sv",
            "sy", "sz", "tc", "td", "tf", "tg", "th", "tj", "tk", "tm", "tn",
            "to", "tp", "tr", "tt", "tv", "tw", "tz", "ua", "ug", "uk", "um",
            "us", "uy", "uz", "va", "vc", "ve", "vg", "vi", "vn", "vu", "ws",
            "wf", "ye", "yt", "yu", "za", "zm", "zw"};

        String comprobacion = nombre_dominio;
        boolean val = true;
        int punto = comprobacion.indexOf(".");
        nombre_dominio = comprobacion.substring(0, punto);
        String extension2 = comprobacion.substring(punto, comprobacion.length());
        String extension3 = extension2.replace(".", "@");
        String pu[] = extension3.split("@");
        for (int f = 1; f < pu.length; f++) {
            String extension = pu[f];

            for (int i = 0; i < comparacion.length; i++) {
                if (extension.contentEquals(comparacion[i])) {
                    val = true;
                    break;
                } else {
                    val = false;
                }
            }
            if (val == false) {
                return false;
            }
        }
        if (pu.length == 0) {
            String extension = extension2.replace(".", "");

            for (int i = 0; i < comparacion.length; i++) {
                if (extension.contentEquals(comparacion[i])) {
                    val = true;
                    break;
                } else {
                    val = false;
                }
            }
            if (val == false) {
                return false;
            }
        }
        return true;

    }
}
