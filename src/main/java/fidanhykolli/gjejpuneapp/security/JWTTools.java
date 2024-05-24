package fidanhykolli.gjejpuneapp.security;

import fidanhykolli.gjejpuneapp.entities.Company;
import fidanhykolli.gjejpuneapp.entities.User;
import fidanhykolli.gjejpuneapp.exceptions.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {
    @Value("${jwt.secret}")
    private String secret;

    public String createToken(User user) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 365))
                .subject(String.valueOf(user.getId()))
                .claim("type", "user")
                .claim("email", user.getEmail())
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public String createToken(Company company) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 365))
                .subject(String.valueOf(company.getId()))
                .claim("type", "company")
                .claim("email", company.getEmail())
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public void verifyToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token);
        } catch (Exception ex) {
            throw new UnauthorizedException("Token issues, verify if token is still active or it has been modified");
        }
    }

    public String idFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        String userType = (String) claims.get("type");

        if (userType != null && userType.equals("user")) {
            return claims.getSubject();
        } else if (userType != null && userType.equals("company")) {
            return claims.getSubject();
        } else {
            throw new UnauthorizedException("Invalid token type");
        }
    }


    public boolean isCompanyToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        String userType = (String) claims.get("type");
        return userType != null && userType.equals("company");
    }
}
