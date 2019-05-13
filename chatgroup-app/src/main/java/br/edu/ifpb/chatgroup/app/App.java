package br.edu.ifpb.chatgroup.app;

import br.edu.ifpb.chatgroup.controller.CommandFileController;
import br.edu.ifpb.chatgroup.model.Message;
import br.edu.ifpb.chatgroup.service.CommandFileServiceImpl;
import jcifs.smb.*;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    public static void main(String[] args) {

        CommandFileController commandFileController = new CommandFileController(new CommandFileServiceImpl());
        SmbFile file = null;

        final Logger log = Logger.getAnonymousLogger();

        final String username = "romulo";
        final String password = "orlo8m";
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, username, password);

        try {
            file = new SmbFile("smb://192.168.1.103/dir-server-shared/db-message.csv", auth);
        } catch (MalformedURLException e) {
            log.log(Level.WARNING, "Arquivo não encontrado!\n" + e.getMessage());
        }

        final int OPTION_LOGIN = 0;
        final int OPTION_MESSAGE = 1;
        final int OPTION_EXIT = 3;

        List<String> authorizedLogin = new ArrayList<>();
        authorizedLogin.add("cliente01");
        authorizedLogin.add("cliente02");
        authorizedLogin.add("cliente03");
        authorizedLogin.add("cliente04");

        int optionMenu = OPTION_LOGIN;

        System.out.print("Digite seu nickname para login: ");
        Scanner scannerLogin = new Scanner(System.in);
        String login = scannerLogin.nextLine();

        if (authorizedLogin.contains(login)){

            System.out.println("\n--- Mensagens Anteriores ---");
            System.out.println(commandFileController.readFile(file));

            while (optionMenu != OPTION_EXIT) {

                System.out.println("\n--- MENU ---");
                System.out.println("'1' para escrever uma mensagem.");
                System.out.println("'3' para sair do chat.\n");

                System.out.print("Informe a opção desejada: ");
                Scanner scannerOptionMenu = new Scanner(System.in);
                optionMenu = scannerOptionMenu.nextInt();

                switch (optionMenu) {
                    case OPTION_MESSAGE:

                        Scanner scannerMessage = new Scanner(System.in);
                        System.out.print("Escreva a mensagem: ");
                        String messageText = scannerMessage.nextLine();

                        Message message = new Message(login, messageText, LocalDateTime.now());
                        commandFileController.writeFile(file, message);

                        System.out.println("\n--- Mensagens Anteriores ---");
                        System.out.println(commandFileController.readFile(file));

                        break;
                    case 3:
                        System.out.println("\nLogout realizado com sucesso!\n");
                        optionMenu = OPTION_EXIT;
                        break;
                    default:
                        System.out.println("Esta não é uma opção válida!");
                }
            }

        } else System.out.println("Login inválido!");

    }

}
