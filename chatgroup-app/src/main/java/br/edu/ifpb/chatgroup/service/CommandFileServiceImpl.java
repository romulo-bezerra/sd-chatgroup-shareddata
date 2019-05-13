package br.edu.ifpb.chatgroup.service;

import br.edu.ifpb.chatgroup.abstration.CommandFileService;
import br.edu.ifpb.chatgroup.model.Message;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

import java.io.*;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandFileServiceImpl implements CommandFileService {

    private final Logger log = Logger.getAnonymousLogger();

    @Override
    public String readFile(SmbFile file) {

        String conteudo = "";
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(new SmbFileInputStream(file)));
        } catch (UnknownHostException e) {
            log.log(Level.WARNING, "Erro: Host não encontrado.\n" + e.getMessage());
        } catch (SmbException e) {
            log.log(Level.WARNING, e.getMessage());
        } catch (MalformedURLException e) {
            log.log(Level.WARNING, "Erro: URL mal formada.\n" + e.getMessage());
        }

        String line = null;
        try {
            line = reader.readLine();
            while (line != null) {
                conteudo += line+"\n";
                line = reader.readLine();
            }
            reader.close();
            return conteudo;
        } catch (IOException e) {
            log.log(Level.WARNING, "Erro: Não foi possível ler o arquivo.\n" + e.getMessage());
            return "";
        }
    }

    @Override
    public void writeFile(SmbFile file, Message message) {

        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(new SmbFileOutputStream(file, true)));
            writer.write(message.toString());
            writer.newLine();
            writer.flush();
        } catch (UnknownHostException e) {
            log.log(Level.WARNING, e.getMessage());
        } catch (SmbException e) {
            log.log(Level.WARNING, e.getMessage());
        } catch (MalformedURLException e) {
            log.log(Level.WARNING, e.getMessage());
        } catch (IOException e) {
            log.log(Level.WARNING, e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                } catch (IOException e) {
                    log.log(Level.WARNING, e.getMessage());
                }
            }
        }
    }

}
