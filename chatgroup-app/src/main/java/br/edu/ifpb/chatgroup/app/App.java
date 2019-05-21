package br.edu.ifpb.chatgroup.app;

import br.edu.ifpb.chatgroup.controller.CommandFileController;
import br.edu.ifpb.chatgroup.service.MonitorMessageService;
import br.edu.ifpb.chatgroup.service.CommandFileServiceImpl;
import br.edu.ifpb.chatgroup.service.ReaderWritterService;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

	public static void main(String[] args) {

	    CommandFileController commandFileController = new CommandFileController(new CommandFileServiceImpl());
        PrintStream consoleOuput = System.out;

        //autorização
        List<String> authorizedLogin = new ArrayList<>();
        authorizedLogin.add("cliente01");
        authorizedLogin.add("cliente02");

        //Lendo o login
        System.out.print("Digite seu username para login: ");
        Scanner scannerLogin = new Scanner(System.in);
        String login = scannerLogin.nextLine();

        if (authorizedLogin.contains(login)){

            //caso o login dê certo apresenta uma mensagem
            consoleOuput.println("\n--- Mensagens Anteriores ---");
            commandFileController.printMessages(commandFileController.readFile(), login, true);

            int qntLinesCliente = commandFileController.countLinesFile();

            new Thread(new MonitorMessageService(login, qntLinesCliente)).start();
            new Thread(new ReaderWritterService(login)).start();

        } else consoleOuput.println("Login inválido!");
	}
}
