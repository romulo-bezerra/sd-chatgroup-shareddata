package br.edu.ifpb.chatgroup.util;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;

import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecoversFileShared {

    private static final Logger log = Logger.getLogger(RecoversFileShared.class.getName());

    //credenciais para acesso ao arquivo compartilhado
    private static final String username = "romulo";
    private static final String password = "orlo8m";

    //parâmetros para url de acesso ao arquivo compartilhado
    private static final String communicationProtocol = "smb://";
    private static final String adressIp = "192.168.1.104";
    private static final String dirShared = "/dir-server-shared";
    private static final String fileShared = "/db-messages.txt";

    public static SmbFile getFileShared() {
        //url do arquivo compartilhado
        final String urlAccessFileShared = communicationProtocol + adressIp + dirShared + fileShared;

        //autentica a entrada na pasta compartilhada
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, username, password);

        try { //tentando acessar o arquivo
            return new SmbFile(urlAccessFileShared, auth);
        } catch (MalformedURLException e) {
            log.log(Level.WARNING, "Arquivo não encontrado!\n" + e.getMessage());
            return null;
        }
    }
}
