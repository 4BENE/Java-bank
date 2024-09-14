package main;

import console.StartScenario;
import exceptions.DateAccessException;
import exceptions.LowAmountException;
import exceptions.NotVerifiedLimitException;

import java.io.IOException;


public class Main {
        public static void main(String[] args) throws LowAmountException, DateAccessException, NotVerifiedLimitException{
            StartScenario.run();
        }
}
