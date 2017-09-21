package com.github.vjuranek.jgroups;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgroups.Address;
import org.jgroups.Message;
import org.jgroups.Receiver;
import org.jgroups.View;

public class EchoReciever implements Receiver {

    private static final Logger LOG = LogManager.getLogger(EchoReciever.class);
    private View currentView = null;

    public void receive(Message msg) {
        LOG.info("<< " + msg.getObject() + " [" + msg.getSrc() + "]");
    }

    @Override
    public void viewAccepted(View newView) {
        LOG.info(String.format("Got view with members %s", nodesToString(newView.getMembersRaw())));
        Address[][] viewDiff = View.diff(currentView, newView);
        LOG.info(String.format("Joined nodes:  %s", nodesToString(viewDiff[0])));
        LOG.info(String.format("Nodes left:  %s", nodesToString(viewDiff[1])));
        currentView = newView;
    }

    @Override
    public void suspect(Address suspected) {
        LOG.info("Suspected %s", suspected.toString());
    }

    private String nodesToString(Address[] nodes) {
        if (nodes == null || nodes.length == 0) {
            return "(no nodes)";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (Address adr : nodes) {
            sb.append(adr.toString()).append(',');
        }
        sb.replace(sb.length() - 1, sb.length(), "]");
        return sb.toString();
    }
}
