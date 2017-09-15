package com.github.vjuranek.jgroups;

import org.jgroups.Message;
import org.jgroups.Receiver;
import org.jgroups.View;

public class EchoReciever implements Receiver {
    
    public void viewAccepted(View new_view) {
        System.out.println("view: " + new_view);
    }

    public void receive(Message msg) {
        System.out.println("<< " + msg.getObject() + " [" + msg.getSrc() + "]");
    }


}
