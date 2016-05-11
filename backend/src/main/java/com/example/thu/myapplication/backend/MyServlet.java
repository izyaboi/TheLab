/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.example.thu.myapplication.backend;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.*;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class MyServlet extends HttpServlet {

    private final String VALID_USER = "user@domain.com:password";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.sendError(HttpServletResponse.SC_FORBIDDEN,"No GET access.");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("username");
        String pass = req.getParameter("password");
        resp.setContentType("application/json");
        if(name == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            String json = String.format("{\"error\": \"%s\"}", "Username is required");
            resp.getWriter().print(json);
            return;
        }
        if (pass == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            String json = String.format("{\"error\": \"%s\"}", "Password is required");
            resp.getWriter().print(json);
            return;
        }

        if ((name + ":" + pass).equals(VALID_USER)) {
            String token = createJWT("0", "My Company", name, 10000);
            String json = String.format("{\"token\": \"%s\"}", token);
            resp.getWriter().print(json);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            String json = String.format("{\"error\": \"%s\"}", "Username or Password is invalid");
            resp.getWriter().print(json);
        }
    }


    private String createJWT(String id, String issuer, String audience, long ttlMillis) {

        //The JWT signature algorithm we will be using to sign the token
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("MySecret");

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(SignatureAlgorithm.HS256, apiKeySecretBytes);



        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }
}
