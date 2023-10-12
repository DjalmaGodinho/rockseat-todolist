package br.com.dlg.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.dlg.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().startsWith("/task/")) {
            var authorization = request.getHeader("Authorization");
            String auth = new String(Base64.getDecoder().decode(
                    authorization.substring("Basic".length()).trim())
            );

            String[] credentials = auth.split(":");
            var userName = credentials[0];
            var password = credentials[1];

            var user = this.userRepository.findByUserName(userName);

            if (user.isPresent()) {
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.get().getPassword());
                if (passwordVerify.verified) {
                    request.setAttribute("idUser", user.get().getId());
                    filterChain.doFilter(request, response);
                }

                response.sendError(HttpStatus.FORBIDDEN.value());
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
