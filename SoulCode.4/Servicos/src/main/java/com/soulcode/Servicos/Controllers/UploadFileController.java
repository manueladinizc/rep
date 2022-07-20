package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Services.FuncionarioService;
import com.soulcode.Servicos.Util.UploadFile;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("servicos")

public class UploadFileController {

    @Autowired
    FuncionarioService funcionarioService;

    @PostMapping("/funcionarios/envioFoto/{idFuncionario}")
    public ResponseEntity<Void> enviarFoto(@PathVariable Integer idFuncionario, MultipartFile file, @RequestParam(
            "nome") String nome){
        String fileName = nome;
        String uploadDir = "D:/Manuela Diniz/Downloads/fotoFunc";
         String nomeMaisCaminho = "D:/Manuela Diniz/Downloads/fotoFunc" + nome;

        funcionarioService.salvarFoto(idFuncionario, nomeMaisCaminho);

         try{
             UploadFile.saveFile(uploadDir, fileName,file);
             funcionarioService.salvarFoto(idFuncionario, nomeMaisCaminho);
         } catch (IOException e){
             System.out.println("O arquivo não foi enviado: " + e.getMessage());
         }
            return ResponseEntity.ok().build();

    }
}



