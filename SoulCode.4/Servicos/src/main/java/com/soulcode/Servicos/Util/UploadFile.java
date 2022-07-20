package com.soulcode.Servicos.Util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


//uploadDir é o caminho. fileName é o nome do arquivo que será salvo. MultipartFile o arquivo que vai subir

public class UploadFile {
    public static void saveFile(String uploadDir, String fileName, MultipartFile file) throws IOException {

        Path uploadPath = Paths.get(uploadDir);
//uploadPath: leitura do caminho e colocando nesse caminho.
        //O if verifica se o caminho existe, se não existir ele cria
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
//Aqui vamos tentar fazer o upload do arquivo
        //Inputstream vai fazer a leitura do arquivo que quer subir
        //Faz leitura byte por byte do arquivo
        try(InputStream inputStream = file.getInputStream()){

            //nesse momento o arquivo é salvo no diretorio que passamo na assinatura do metodo
            //filePath pode ser qualquer nome envia e "resolve o upload"
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//inputStream: o arquivo já lido, caminho e tipo de copia


        }
            catch (IOException e){
            throw new IOException("Não foi possivel enviar o seu arquivo");
            }
    }
}
