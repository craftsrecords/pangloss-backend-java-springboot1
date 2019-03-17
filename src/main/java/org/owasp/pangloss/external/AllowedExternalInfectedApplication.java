package org.owasp.pangloss.external;

import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.util.Scanner;

public class AllowedExternalInfectedApplication extends NanoHTTPD {

    private AllowedExternalInfectedApplication() throws IOException {
        super("0.0.0.0", 9571);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    public static void main(String[] args) throws IOException {
        new AllowedExternalInfectedApplication();
        System.out.println("http://" + Inet4Address.getLocalHost().getHostAddress() + ":9571");
    }

    @Override
    public Response serve(IHTTPSession session) {

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("snippets/xsrf.html");
        String content = new Scanner(is).useDelimiter("\\A").next();

        return newFixedLengthResponse(content);
    }
}