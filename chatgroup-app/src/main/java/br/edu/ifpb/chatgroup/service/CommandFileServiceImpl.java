package br.edu.ifpb.chatgroup.service;

import br.edu.ifpb.chatgroup.abstration.CommandFileService;
import br.edu.ifpb.chatgroup.model.Message;
import br.edu.ifpb.chatgroup.util.RecoversFileShared;
import jcifs.smb.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.time.LocalDateTime;

public class CommandFileServiceImpl implements CommandFileService {

    private final Logger log = Logger.getLogger(CommandFileServiceImpl.class.getName());
    private SmbFile file;

    public CommandFileServiceImpl() {
        this.file = RecoversFileShared.getFileShared();
    }

    @Override
    public ArrayList<Message> readFile() {

        BufferedReader reader = instanceReader();

        ArrayList<Message> result = new ArrayList<>();
        String line;

        try {
            do {
                line = reader.readLine();
                if (line != null) { //unmarshal
                    String[] attrs = line.split("\\|\\|");
                    Message m = new Message(attrs[0], attrs[1], LocalDateTime.parse(attrs[2]));
                    result.add(m);
                }
            } while (line != null);

            reader.close();
            return result;
        } catch (IOException e) {
            log.log(Level.WARNING, "Erro: Não foi possível ler o arquivo.\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<Message> readFileFromLine(int linePosition) {

        BufferedReader reader = instanceReader();

        ArrayList<Message> result = new ArrayList<>();
        String line;
        int countLine = 0;

        try {
            do {
                line = reader.readLine();
                ++countLine;
                if (countLine >= linePosition){
                    if (line != null) { //unmarshal
                        String[] attrs = line.split("\\|\\|");
                        Message m = new Message(attrs[0], attrs[1], LocalDateTime.parse(attrs[2]));
                        result.add(m);
                    }
                }
            } while (line != null);

            reader.close();
            return result;
        } catch (IOException e) {
            log.log(Level.WARNING, "Erro: Não foi possível ler o arquivo.\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public void printMessages(ArrayList<Message> messages, String login, boolean withMe) {

        PrintStream consoleOutput = System.out;

        for (Message m : messages){
            if (!m.getId().equals(login) || withMe) {
                String line = String.format("%s (%s): %s", m.getId(),
                        m.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE),
                        m.getMessage());
                consoleOutput.print(line + "\n");
            }
        }
        consoleOutput.print("Eu: ");
    }

    @Override
    public void writeFile(Message message) {

        BufferedWriter writer = null;

        //formatando a string para escrita
        StringBuilder sb = new StringBuilder();
        sb.append(message.getId());
        sb.append("||");
        sb.append(message.getMessage());
        sb.append("||");
        sb.append(message.getTimestamp());

        try {
            //escrevendo no arquivo
            writer = new BufferedWriter(new OutputStreamWriter(new SmbFileOutputStream(file, true)));
            writer.write(sb.toString());//Marshal
            writer.newLine();
        } catch (UnknownHostException e) {
            log.log(Level.WARNING, "Erro: Host não encontrado.\n" + e.getMessage());
        } catch (SmbException e) {
            log.log(Level.WARNING, "Erro: Falha no protocolo de comunicação.\n" + e.getMessage());
        } catch (MalformedURLException e) {
            log.log(Level.WARNING, "Erro: URL não encontrada.\n" + e.getMessage());
        } catch (IOException e) {
            log.log(Level.WARNING, "Erro: Não foi possível escrever no arquivo.\n" + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    log.log(Level.WARNING, "Erro: Não foi possivel fechar conexão com o arquivo.\n" + e.getMessage());
                }
            }
        }
    }

    @Override
    public int countLinesFile() {

        BufferedReader reader = instanceReader();

        String line;
        int countLine = 0;

        try {
            do {
                line = reader.readLine();
                if (line != null) countLine++;
            } while (line != null);

            reader.close();

        } catch (IOException e) {
            log.log(Level.WARNING, e.getMessage());
        }

        return countLine;
    }

    private BufferedReader instanceReader() {
        try {
            return new BufferedReader(new InputStreamReader(new SmbFileInputStream(file)));
        } catch (UnknownHostException e) {
            log.log(Level.WARNING, "Erro: Host não encontrado.\n" + e.getMessage());
        } catch (SmbException e) {
            log.log(Level.WARNING, "Erro: Arquivo não encontrado\n" + e.getMessage());
        } catch (MalformedURLException e) {
            log.log(Level.WARNING, "Erro: URL não encontrada.\n" + e.getMessage());
        }
        return null;
    }
}
