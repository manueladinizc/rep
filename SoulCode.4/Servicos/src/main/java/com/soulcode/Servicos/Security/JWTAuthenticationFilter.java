package com.soulcode.Servicos.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soulcode.Servicos.Models.User;
import com.soulcode.Servicos.Util.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JWTUtils jwtUtils;

    public JWTAuthenticationFilter(AuthenticationManager manager, JWTUtils jwtUtils){

        this.authenticationManager = manager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //tenta autenticar o usuário
        try {
            //{"login": "", "password":""}
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(

                    new UsernamePasswordAuthenticationToken(
                            user.getLogin(),
                            user.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException io) {
            //caso o json da requisicao çao bater com o User.class
            throw  new RuntimeException(io.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //gerar o token e devolver para o usuário que se autenticou com sucesso
        AuthUserDetail user = (AuthUserDetail) authResult.getPrincipal();
        String token = jwtUtils.generateToken(user.getUsername());


        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, PATCH, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        // {"Authorization": "<token>"}
        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
        response.getWriter().flush();


    }

    /*atalho uns para digitar a função abaixo*/

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //customizar a resposta de erro do login que falhou
        response.setStatus(401); //unauthorized
        response.setContentType("application/json");
       /* response.getWriter().write("");*/
        response.getWriter().write(json()); //append() leva o conteudo da string para a resposta
        response.getWriter().flush();
    }

    String json() {
        long date = new Date().getTime();
        return "{"
                + "\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\" : \"Não autorizado\", "
                + "\"message\": \"Email/senha inválidos\","
                + "\"path\": \"/login\""
                + "}";
    }



}

/**
 * FRONT MANDA {"login": "jr@gmail.com", "password": "12345"}
 * A partir do JSON -> User
 * Tenta realizar autenticação
 *      Caso dê certo:
 *          - Gera o token JWT
 *          - Retorna o token para o FRONT
 */

