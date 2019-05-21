package br.edu.ifpb.chatgroup.controller;

import br.edu.ifpb.chatgroup.abstration.CommandFileService;
import br.edu.ifpb.chatgroup.model.Message;
import br.edu.ifpb.chatgroup.service.CommandFileServiceImpl;

import java.util.ArrayList;

public class CommandFileController {

    private CommandFileService commandFileService;

    public CommandFileController (CommandFileServiceImpl commandFileServiceImpl) {
        this.commandFileService = commandFileServiceImpl;
    }

    public ArrayList<Message> readFile() {
        return commandFileService.readFile();
    }

    public void writeFile(Message message) {
        commandFileService.writeFile(message);
    }

    public int countLinesFile() {
        return commandFileService.countLinesFile();
    }

    public ArrayList<Message> readFileFromLine(int linePosition) {
        return commandFileService.readFileFromLine(linePosition);
    }

    public void printMessages(ArrayList<Message> messages, String login, boolean withMe) {
        commandFileService.printMessages(messages, login, withMe);
    }
}
