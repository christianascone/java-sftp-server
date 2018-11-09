package com.christianascone.javasftpserver.main;

import com.christianascone.javasftpserver.helpers.SftpServerUtils;
import org.apache.commons.cli.*;

import java.io.IOException;

public class App {
    private static final String HELP = "help";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String PORT = "port";

    private static int port;
    private static String username;
    private static String password;


    public static void main(String[] args) throws IOException {
        Options options = generateOptions();

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);
            if (checkForHelp(options, cmd)) return;

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

        boolean running = true;
        SftpServerUtils.setupSftpServer(username, password, port);
        while (running) {
            // TODO: Add input check in order to interrupt
        }

    }

    /**
     * Check if user is checking help for the application
     *
     * @param options
     * @param cmd
     * @return
     */
    private static boolean checkForHelp(Options options, CommandLine cmd) {
        if (cmd.hasOption(HELP)) {
            HelpFormatter fmt = new HelpFormatter();
            fmt.printHelp("Help", options);
            return true;
        }
        return false;
    }

    /**
     * Generates a list of {@link Option} including the help flag
     *
     * @return A list of {@link Option}
     */
    private static Options generateOptions() {
        Options options = new Options();

        options.addOption("h", HELP, false, "");

        options.addOption("p", PORT, true, "");

        options.addOption("u", USERNAME, true, "");

        options.addOption("w", PASSWORD, true, "");
        return options;
    }

}
