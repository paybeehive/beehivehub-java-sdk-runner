package br.com.paybeehive.runner;

public class Main {
    public static void main(String[] args) {
        CheckEnv.validate();
        new Cli().run();
    }
}
