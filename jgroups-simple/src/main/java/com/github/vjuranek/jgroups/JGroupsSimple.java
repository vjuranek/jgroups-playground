package com.github.vjuranek.jgroups;

import org.jgroups.JChannel;
import org.jgroups.util.Util;

public class JGroupsSimple {
    public static void main(String[] args) throws Exception {
        try (JChannel channel = new JChannel("tcp-test.xml")) {
            channel.setReceiver(new EchoReciever());
            channel.connect("test-cluster");
            readAndSendLoop(channel);
        }
    }
    
    private static void readAndSendLoop(JChannel channel) throws Exception {
        for (;;) {
            String line = Util.readStringFromStdin(": ");
            channel.send(null, line);
        }
    }
}
