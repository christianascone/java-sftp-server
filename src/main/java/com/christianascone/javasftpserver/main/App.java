package com.christianascone.javasftpserver.main;

import com.christianascone.javasftpserver.helpers.SftpServerUtils;
import org.apache.commons.cli.*;

import java.io.IOException;

public class App {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String PORT = "port";

    private static Option helpOption = Option.builder("h")
            .longOpt("help")
            .required(false)
            .hasArg(false)
            .build();

    private static int port;
    private static String username;
    private static String password;

    private static boolean checkForHelp(String[] args) throws ParseException {
        boolean hasHelp = false;

        Options options = new Options();


        try {
            options.addOption(helpOption);

            CommandLineParser parser = new DefaultParser();

            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption(helpOption.getOpt())) {
                hasHelp = true;
            }

        } catch (ParseException e) {
            throw e;
        }

        return hasHelp;
    }

    public static void main(String[] args) throws IOException {
        Options options = new Options();

        Option portOption = Option.builder("p")
                .longOpt(PORT)
                .required(true)
                .hasArg(false)
                .build();

        Option usernameOption = Option.builder("u")
                .longOpt("username")
                .required(true)
                .hasArg(false)
                .build();

        Option passwordOption = Option.builder("w")
                .longOpt("password")
                .required(true)
                .hasArg(false)
                .build();

        options.addOption(helpOption);
        options.addOption(portOption);
        options.addOption(usernameOption);
        options.addOption(passwordOption);

        try {
            if (checkForHelp(args)) {
                HelpFormatter fmt = new HelpFormatter();
                fmt.printHelp("Help", options);
                return;
            }

            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);


            if (cmd.hasOption(PORT))
                port = Integer.parseInt(cmd.getOptionValue(PORT));

            if (cmd.hasOption(USERNAME))
                username = cmd.getOptionValue(USERNAME);

            if (cmd.hasOption(PASSWORD))
                password = cmd.getOptionValue(PASSWORD);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return; // Exit
        }

        SftpServerUtils.setupSftpServer(username, password, port);
    }

}
