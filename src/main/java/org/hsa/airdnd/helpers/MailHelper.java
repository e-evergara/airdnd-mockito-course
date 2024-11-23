package org.hsa.airdnd.helpers;

import java.util.Objects;

public class MailHelper {
    public void sendMail(final String bookingId, final String emailAddress) {
        System.out.println("Sending mail to 1: " + emailAddress);
        if (Objects.isNull(emailAddress)) throw new IllegalArgumentException("Mail cant be null");
        System.out.println("Sending mail to: " + emailAddress);
        //Send mail...
    }
}
