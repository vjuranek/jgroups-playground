#!/usr/bin/env bash

PIDS=`ps --no-headers -o pid,args | grep -v grep | grep java | awk '{print $1}'`

for pid in $PIDS; do
    kill $pid;
done
