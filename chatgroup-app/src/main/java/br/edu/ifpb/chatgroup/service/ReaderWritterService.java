package br.edu.ifpb.chatgroup.service;

import br.edu.ifpb.chatgroup.app.App;
import br.edu.ifpb.chatgroup.controller.CommandFileController;
import br.edu.ifpb.chatgroup.model.Message;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.logging.Logger;

public class ReaderWritterService implements Runnable {

    private final Logger log = Logger.getLogger(App.class.getName());
    private CommandFileController commandFileController;
    private String login;

    public ReaderWritterService(String login) {
        this.commandFileController = new CommandFileController(new CommandFileServiceImpl());
        this.login = login;
    }

    public void run() {

        PrintStream consoleOutput = System.out;
        Scanner scannerMessage = new Scanner(System.in);

        while (true) {

            String messageText = scannerMessage.nextLine();

            //construir a mensagem e escrever no arquivo
            Message message = new Message(login, messageText, LocalDateTime.now());
            commandFileController.writeFile(message);
        }
    }
}
