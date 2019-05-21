package br.edu.ifpb.chatgroup.abstration;

import br.edu.ifpb.chatgroup.model.Message;

import java.util.ArrayList;

public interface CommandFileService {

    ArrayList<Message> readFile(); //lê o arquivo
    void writeFile(Message message); //escreve no arquivo
    int countLinesFile(); //conta linhas arquivo
    ArrayList<Message> readFileFromLine(int linePosition); //ler arquivo a partir de uma posição
    void printMessages(ArrayList<Message> messages, String login, boolean withMe); //imprime no console

}
