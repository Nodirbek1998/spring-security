package uz.example.springsecurity.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import uz.example.springsecurity.entity.User;

import java.util.Date;

@Component
public class JwtTokenProvider {
    long durationLiveToken=1000*60*60;
    String keyword="security-key";

    public String generateToken(User user){
        Date expireDate=new Date(new Date().getTime()+durationLiveToken);
        String token= Jwts
                .builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .claim("roles",user.getRoles())
                .signWith(SignatureAlgorithm.HS512,keyword)
                .compact();
        return token;
    }

    public boolean validToken(String token){
        try{
            Jwts
                    .parser()
                    .setSigningKey(keyword)
                    .parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException expiredJwtException){
            System.err.println("EXPIRED");
        }catch (MalformedJwtException malformedJwtException){
            System.err.println("MALFORMED");
        }catch (IllegalArgumentException illegalArgumentException){
            System.err.println("ILLEGAL_ARGUMENT");
        }catch (SignatureException signatureException){
            System.err.println("SIGNATURE_ERROR");
        }
        return false;
    }

    public String getUserId(String token){
        try{
            return Jwts
                    .parser()
                    .setSigningKey(keyword)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }catch (Exception e){
            return null;
        }
    }

}
