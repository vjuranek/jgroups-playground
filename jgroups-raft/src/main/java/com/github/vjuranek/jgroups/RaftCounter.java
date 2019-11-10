package com.github.vjuranek.jgroups;

import org.jgroups.JChannel;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.blocks.atomic.Counter;
import org.jgroups.raft.blocks.CounterService;
import org.jgroups.util.Util;

public class RaftCounter {

    protected JChannel jch;
    protected CounterService counter;

    void start(String props, String name) throws Exception {
        jch = new JChannel(props).name(name);
        counter = new CounterService(jch)
                .raftId(name)
                .replTimeout(5000);

        jch.setReceiver(new ReceiverAdapter() {
            public void viewAccepted(View view) {
                System.out.println("-- view: " + view);
            }
        });

        try {
            jch.connect("counters");
            loop();
        } finally {
            Util.close(jch);
        }
    }


    protected void loop() throws Exception {
        Counter counter = this.counter.getOrCreateCounter("counter", 0);

        while (true) {
            try {
                int key = Util.keyPress("[1] Increment\n");

                switch (key) {
                    case '1':
                        long val = counter.incrementAndGet();
                        System.out.printf("%s: %s\n", counter.getName(), val);
                        break;
                    default:
                        System.out.println(counter.getName() + ": " + counter.get() + "\n");
                        break;
                }
            } catch (Throwable t) {
                System.err.println(t.toString());
            }
        }
    }

    public static void main(final String[] args) throws Exception {
        String properties = "raft.xml";
        String name = null;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-props")) {
                properties = args[++i];
                continue;
            }
            if (args[i].equals("-name")) {
                name = args[++i];
                continue;
            }

            help();
            return;
        }

        new RaftCounter().start(properties, name);
    }

    private static void help() {
        System.out.println("RaftCounter [-props props] [-name name]");
    }
}
