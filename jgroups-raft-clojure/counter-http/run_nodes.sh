#!/usr/bin/env bash

for i in `seq 5`; do
    echo "Starting node n$i"
    java -Djava.net.preferIPv4Stack=true -Djgroups.bind_addr=127.0.0.1 -jar target/counter-0.1.0-SNAPSHOT-standalone.jar -port 300$i -name n$i > node$i.log  2>&1&
done
