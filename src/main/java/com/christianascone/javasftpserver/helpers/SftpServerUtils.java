package com.christianascone.javasftpserver.helpers;

import com.christianascone.javasftpserver.main.App;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.UserAuth;
import org.apache.sshd.server.auth.password.UserAuthPasswordFactory;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.shell.ProcessShellCommandFactory;
import org.apache.sshd.server.subsystem.sftp.SftpSubsystemFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SftpServerUtils {
    /**
     * Setup a SFTP Server in localhost with a temp directory as root
     *
     * @throws IOException If it cannot create a temp dir
     */
    public static SshServer setupSftpServer(String username, String password, int port) throws IOException {
        Path tempSftpDir = Files.createTempDirectory(App.class.getName());

        List<NamedFactory<UserAuth>> userAuthFactories = new ArrayList<>();
        userAuthFactories.add(new UserAuthPasswordFactory());

        List<NamedFactory<Command>> sftpCommandFactory = new ArrayList<>();
        sftpCommandFactory.add(new SftpSubsystemFactory());

        SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setPort(port);
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider());
        sshd.setUserAuthFactories(userAuthFactories);
        sshd.setCommandFactory(new ProcessShellCommandFactory());
        sshd.setSubsystemFactories(sftpCommandFactory);
        sshd.setPasswordAuthenticator((usernameAuth, passwordAuth, session) -> {
            if ((username.equals(usernameAuth)) && (password.equals(passwordAuth))) {
                sshd.setFileSystemFactory(new VirtualFileSystemFactory(tempSftpDir));
                return true;
            }
            return false;
        });

        sshd.start();
        System.out.println("Started SFTP server with root path: " + tempSftpDir.toFile().getAbsolutePath());
        return sshd;
    }

    private static void stopServer(SshServer sshServer) throws IOException {
        sshServer.stop();
    }
}
