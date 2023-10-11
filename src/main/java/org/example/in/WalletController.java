package org.example.in;

import org.example.entity.Player;
import org.example.entity.Transaction;
import org.example.service.WalletService;
import org.example.utils.BalanceConverter;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Контроллер
 */
public class WalletController {
    /**
     * Сервис Wallet
     */
    private final WalletService walletService;

    private final String DEAFAULT_MENU = "1.Регистрация\n2.Войти\n";
    private final String START_MENU = "3.Дебит\n4.Кредит\n5.История транзакций\n6.Посмотреть баланс\n0.Завершить\n";
    private final String TRANSACTION_TYPE_MENU = "1.Дебит\n2.Кредит\n3.Все транзакции";

    /**
     * Объект отвечающий за логгирование
     */
    private final Logger logger = Logger.getLogger("WalletControllerLogger");

    /**
     * Конструктор
     *
     * @param walletService
     */
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }


    /**
     * Цикл обработки входящей команды
     *
     * @throws IOException
     * @throws SecurityException
     */
    public void event_loop() throws IOException, SecurityException {
        FileHandler fh;
        try {
            logger.setUseParentHandlers(false);
            fh = new FileHandler("audit.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            logger.info("Init log");
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        String inputString;
        boolean authenticated = false;

        while (!authenticated) {
            System.out.println(DEAFAULT_MENU);
            authenticated = sign_up(scanner);
        }

        while (true) {
            System.out.println(START_MENU);
            inputString = scanner.next();
            parse_start_input(inputString, scanner);
            if (inputString.equals("0")) {
                walletService.onExit();
                System.out.println("Завершение работы программы");
                logger.info("Пользователь с id = " + walletService.getSessionId() + " завершил работу программы");
                break;
            }
        }
    }

    /**
     * Регистрация
     *
     * @param scanner
     * @return authenticated
     */
    boolean sign_up(Scanner scanner) {
        String inputString = scanner.next();
        boolean authenticated = false;
        switch (inputString) {
            case "1" -> {
                System.out.println("Введите логин");
                String username = scanner.next();
                System.out.println("Введите пароль");
                String pass = scanner.next();

                Player player = walletService.register(username, pass);
                System.out.println("Вы зарегистрировались\n");
                logger.info("Пользователь " + player.toString() + " был зарегистрирован\n");
                break;
            }
            case "2" -> {
                System.out.println("Введите логин");
                String username = scanner.next();
                System.out.println("Введите пароль");
                String pass = scanner.next();
                try {
                    boolean auth = walletService.login(username, pass);
                    if (auth) {
                        System.out.println("Вы авторизовались");
                        authenticated = true;
                        logger.info("Пользователь с username" + username + " был авторизован\n");
                    } else {
                        System.out.println("Неверный пароль");
                    }
                } catch (RuntimeException e) {
                    System.out.println("Ошибка авторизации: " + e.getMessage());
                }
                break;
            }
        }
        return authenticated;
    }

    /**
     * Парсит приходящую команду
     *
     * @param inputString
     * @param scanner
     */
    void parse_start_input(String inputString, Scanner scanner) {
        switch (inputString) {
            case "3" -> {
                System.out.println("Введите сумму в копейках, которую нужно снять: ");
                long amount = scanner.nextLong();

                try {
                    walletService.transaction(amount, Transaction.TransactionType.DEBIT);

                    System.out.println(BalanceConverter.convertBalance(amount) + "было снято со счета");
                    logger.info(BalanceConverter.convertBalance(amount) + "было снято со счета пользователя с id = " +
                            walletService.getSessionId() + "\n");
                } catch (Exception e) {
                    System.out.println("Ошибка проведения транзакции: " + e.getMessage());
                }
                break;
            }
            case "4" -> {
                System.out.println("Введите сумму в копейках, которую нужно начислить: ");

                long amount = scanner.nextLong();
                walletService.transaction(amount, Transaction.TransactionType.CREDIT);

                System.out.println(BalanceConverter.convertBalance(amount) + "было начислено на счет");
                logger.info(BalanceConverter.convertBalance(amount) + "было начислено на счет пользователя с id = " +
                        walletService.getSessionId() + "\n");
                break;
            }
            case "5" -> {
                System.out.println("Выберите тип транзакции:\n" + TRANSACTION_TYPE_MENU);

                String type = scanner.next();
                switch (type) {
                    case "1" -> {
                        List<Transaction> transactions = walletService.getDebitHistory();

                        if (transactions.isEmpty()) {
                            System.out.println("Транзакций такого типа не было осуществлено");
                        } else {
                            transactions.forEach(transaction -> {
                                System.out.println(transaction.toString());
                            });
                            logger.info("Пользователь с id = " + walletService.getSessionId() + " запросил историю транзакций с типом: DEBIT\n");
                        }
                        break;
                    }

                    case "2" -> {
                        List<Transaction> transactions = walletService.getCreditHistory();

                        if (transactions.isEmpty()) {
                            System.out.println("Транзакций такого типа не было осуществлено");
                        } else {
                            transactions.forEach(transaction -> {
                                System.out.println(transaction.toString());
                            });
                            logger.info("Пользователь с id = " + walletService.getSessionId() + " запросил историю транзакций с типом: CREDIT\n");
                        }
                        break;
                    }
                    case "3" -> {
                        List<Transaction> transactions = walletService.getAllTransactions();

                        if (transactions.isEmpty()) {
                            System.out.println("Транзакций не было осуществлено");
                        } else {
                            transactions.forEach(transaction -> {
                                System.out.println(transaction.toString());
                            });
                            logger.info("Пользователь с id = " + walletService.getSessionId() + " запросил всю историю транзакций\n");
                        }
                        break;
                    }
                }
                break;
            }
            case "6" -> {
                System.out.println(walletService.getBalance());
                logger.info("Пользователь с id = " + walletService.getSessionId() + " запросил баланс\n");
                break;
            }
            default -> {
                break;
            }
        }
    }
}


