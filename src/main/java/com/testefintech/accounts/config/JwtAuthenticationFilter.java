package com.testefintech.accounts.config;

import com.testefintech.accounts.service.JwtService;
import com.testefintech.accounts.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // --- LOGS DE DEBUG ---
        System.out.println(">>> FILTRO: Requisição para " + request.getRequestURI());

        // 1. Verifica se tem o cabeçalho Authorization
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println(">>> FILTRO: Sem token ou header inválido.");
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extrai o token
        jwt = authHeader.substring(7);

        try {
            userEmail = jwtService.extractEmail(jwt);
            System.out.println(">>> FILTRO: E-mail extraído do token: " + userEmail);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // CORREÇÃO AQUI: Usamos userRepository direto, em vez de userDetailsService
                var userEntity = userRepository.findByEmail(userEmail)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado no banco"));

                // Transformamos seu User (Entidade) em UserDetails (Spring Security)
                UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                        userEntity.getEmail(),
                        userEntity.getPassword(),
                        new ArrayList<>()
                );

                // 3. Valida se o token bate com o usuário
                // (Aqui simplificamos a validação para focar no e-mail correto)
                if (userEmail.equals(userDetails.getUsername())) {

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println(">>> FILTRO: Autenticação realizada com sucesso!");
                } else {
                    System.out.println(">>> FILTRO: Token inválido ou e-mail não bate.");
                }
            }
        } catch (Exception e) {
            System.out.println(">>> FILTRO ERRO: " + e.getMessage());
            e.printStackTrace(); // Vai mostrar o erro completo no console se explodir
        }

        filterChain.doFilter(request, response);
    }
}