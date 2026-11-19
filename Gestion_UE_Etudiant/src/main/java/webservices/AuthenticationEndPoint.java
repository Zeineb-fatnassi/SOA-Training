package webservices;

import entities.Credentials;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import rest.utilities.JwtKey;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Path("authentication")
public class AuthenticationEndPoint {

    private static final Map<String, String> USERS = new HashMap<String, String>();

    static {
        USERS.put("ines", "1234");
        USERS.put("Mariem", "mariem");
    }

    @Context
    private UriInfo uriInfo;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("username") String username,
                                     @FormParam("password") String password) {
        return authenticateAndIssueToken(username, password);
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Credentials cred) {
        if (cred == null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return authenticateAndIssueToken(cred.getUsername(), cred.getPassword());
    }

    private Response authenticateAndIssueToken(String username, String password) {
        try {
            authenticate(username, password);
            String token = issueToken(username);
            return Response.ok(token).build();
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    private void authenticate(String username, String password) {
        if (username == null || password == null || !password.equals(USERS.get(username))) {
            throw new SecurityException("Identifiants invalides");
        }
    }

    private String issueToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, JwtKey.get())
                .compact();
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
