package br.edu.ifpb.chatgroup.service;

import br.edu.ifpb.chatgroup.app.App;
import br.edu.ifpb.chatgroup.controller.CommandFileController;
import br.edu.ifpb.chatgroup.model.Message;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MonitorMessageService implements Runnable {

    private final Logger log = Logger.getLogger(App.class.getName());
    private final CommandFileController commandFileController;
    private String login;

    private int qntLinesViewLogin;

    public MonitorMessageService(String login, int qntLinesViewLogin) {
        commandFileController = new CommandFileController(new CommandFileServiceImpl());
        this.qntLinesViewLogin = qntLinesViewLogin;
        this.login = login;
    }

    @Override
    public void run() {

        PrintStream consoleOutput = System.out;
        ArrayList<Message> messagesNew;
        int qntLinesFile;

        while (true) {
            try {
                Thread.sleep(1000); //01 segundo
            } catch (InterruptedException e) {
                log.log(Level.WARNING, e.getMessage());
            }

            qntLinesFile = commandFileController.countLinesFile();

            if (qntLinesViewLogin != qntLinesFile) {
                int linePosition = qntLinesViewLogin + 1;
                messagesNew = commandFileController.readFileFromLine(linePosition);
                commandFileController.printMessages(messagesNew, login, false);
                qntLinesViewLogin = qntLinesFile;
            }
        }
    }
}
