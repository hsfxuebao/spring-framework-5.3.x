#!/bin/bash
set -e

case "$1" in
	java8)
		 echo "https://github.com/bell-sw/Liberica/releases/download/8u333+2/bellsoft-jdk8u333+2-linux-amd64.tar.gz"
	;;
	java11)
		 echo "https://github.com/bell-sw/Liberica/releases/download/11.0.15.1+2/bellsoft-jdk11.0.15.1+2-linux-amd64.tar.gz"
	;;
	java17)
		echo "https://github.com/bell-sw/Liberica/releases/download/17.0.3.1+2/bellsoft-jdk17.0.3.1+2-linux-amd64.tar.gz"
	;;
	java18)
		echo "https://github.com/bell-sw/Liberica/releases/download/18.0.1.1+2/bellsoft-jdk18.0.1.1+2-linux-amd64.tar.gz"
	;;
	*)
		echo $"Unknown java version"
		exit 1
esac
