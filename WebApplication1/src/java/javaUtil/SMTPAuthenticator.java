/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaUtil;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author Admin
 */
public class SMTPAuthenticator extends Authenticator{
    private String fUser;
    private String fPassword;

    public SMTPAuthenticator(String fUser, String fPassword) {
        this.fUser = fUser;
        this.fPassword = fPassword;
    }
    
    @Override
    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(fUser, fPassword);
    }
    
}
