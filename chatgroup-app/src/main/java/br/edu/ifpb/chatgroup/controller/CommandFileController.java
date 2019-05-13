package br.edu.ifpb.chatgroup.controller;

import br.edu.ifpb.chatgroup.abstration.CommandFileService;
import br.edu.ifpb.chatgroup.model.Message;
import br.edu.ifpb.chatgroup.service.CommandFileServiceImpl;
import jcifs.smb.SmbFile;

public class CommandFileController {

    private CommandFileService commandFileService;

    public CommandFileController (CommandFileServiceImpl commandFileServiceImpl) {
        this.commandFileService = commandFileServiceImpl;
    }

    public String readFile(SmbFile file) {
        return commandFileService.readFile(file);
    }

    public void writeFile(SmbFile file, Message message){
        commandFileService.writeFile(file, message);
    }

}
