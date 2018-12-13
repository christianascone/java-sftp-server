package com.christianascone.javasftpserver.beans;

import org.apache.sshd.server.SshServer;

import java.nio.file.Path;

public class SftpServerBean {
    private SshServer sshServer;
    private Path rootPath;

    public SftpServerBean(SshServer sshServer, Path rootPath) {
        this.sshServer = sshServer;
        this.rootPath = rootPath;
    }

    public SshServer getSshServer() {
        return sshServer;
    }

    public Path getRootPath() {
        return rootPath;
    }
}
