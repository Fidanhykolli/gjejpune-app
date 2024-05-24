package fidanhykolli.gjejpuneapp.security;


import fidanhykolli.gjejpuneapp.entities.Company;
import fidanhykolli.gjejpuneapp.entities.User;
import fidanhykolli.gjejpuneapp.exceptions.UnauthorizedException;
import fidanhykolli.gjejpuneapp.services.CompanyServices;
import fidanhykolli.gjejpuneapp.services.UserServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;


@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UserServices userService;

    @Autowired
    private CompanyServices companyServices;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            throw new UnauthorizedException("Authorization header is missing or invalid");
        }
        String accessToken = authHeader.substring(7);
        jwtTools.verifyToken(accessToken);
        try {
            String id = jwtTools.idFromToken(accessToken);
            if (jwtTools.isCompanyToken(accessToken)) {
                Integer companyId = Integer.parseInt(id);
                Company currentCompany = companyServices.getCompanyById(companyId);
                if (currentCompany != null) {
                    setAuthentication(currentCompany);
                } else {
                    throw new UnauthorizedException("Company not found");
                }
            } else {
                Integer userId = Integer.parseInt(id);
                User currentUser = userService.getUserById(userId);
                if (currentUser != null) {
                    setAuthentication(currentUser);
                } else {
                    throw new UnauthorizedException("User not found");
                }
            }
        } catch (NumberFormatException ex) {
            throw new UnauthorizedException("Invalid ID in JWT token");
        } catch (Exception ex) {
            throw new UnauthorizedException("Invalid JWT token");
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(Object principal) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        System.out.println(request.getServletPath());
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
