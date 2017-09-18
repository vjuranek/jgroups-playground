package com.github.vjuranek.jgroups;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgroups.Message;
import org.jgroups.Receiver;
import org.jgroups.View;

public class EchoReciever implements Receiver {
    
    private static final Logger LOG = LogManager.getLogger(JGroupsSimple.class);
    
    public void viewAccepted(View newView) {
        LOG.info("view: " + newView);
        System.out.println("view: " + newView);
    }

    public void receive(Message msg) {
        LOG.info("<< " + msg.getObject() + " [" + msg.getSrc() + "]");
        System.out.println("<< " + msg.getObject() + " [" + msg.getSrc() + "]");
    }


}
