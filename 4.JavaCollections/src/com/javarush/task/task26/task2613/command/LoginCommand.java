package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

public class LoginCommand implements Command {
    private ResourceBundle validCreditCards = ResourceBundle
            .getBundle(CashMachine.RESOURCE_PATH + "verifiedCards");
    private ResourceBundle res = ResourceBundle
            .getBundle(CashMachine.RESOURCE_PATH + "login_en");


    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        while (true) {
            ConsoleHelper.writeMessage(res.getString("specify.data"));

            String userNumberCard = ConsoleHelper.readString();
            String userPINCode = ConsoleHelper.readString();

            if (validCreditCards.containsKey(userNumberCard)) {
                if (validCreditCards.getString(userNumberCard).equals(userPINCode)) {
                    ConsoleHelper.writeMessage(String
                            .format(res.getString("success.format"), userNumberCard));
                } else {
                    ConsoleHelper
                            .writeMessage(String
                                    .format((res.getString("not.verified.format")), userNumberCard));
                    ConsoleHelper
                            .writeMessage(res.getString("try.again.or.exit"));
                    continue;
                }
            } else {
                    ConsoleHelper
                            .writeMessage(res.getString("try.again.with.details"));
                    continue;
            }
            break;
        }
    }
}
