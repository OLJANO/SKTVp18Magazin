/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import entity.Buyer;
import entity.Car;
import entity.History;
import interfaces.Saveble;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class App {

    List<Car> listCars = new ArrayList<>();
    List<Buyer> listBuyers = new ArrayList<>();
    List<History> listHistories = new ArrayList<>();
    Saveble saveble;

    public App(String flag) {
        if (flag.equals("base")) {
            saveble = new SaveToBase();
        } else if (flag.equals("file")) {
            saveble = new SaveToFile();
        } else {
            saveble = new SaveToBase();
        }
        listCars = saveble.loadCars();
        listBuyers = saveble.loadBuyers();
        listHistories = saveble.loadHistories();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        HistoryProvider historyProvider = new HistoryProvider();

        boolean flagExit = true;
        do {

            System.out.println("Список задач: ");
            System.out.println("0. Закрыть программу");
            System.out.println("1. Новый автомобиль");
            System.out.println("2. Новый клиент");
            System.out.println("3. Список автомобилей");
            System.out.println("4. Список клиентов");
            System.out.println("5. Продажа автомобиля");
            System.out.println("6. Проданные автомобили");
            System.out.println("Введите номер задачи:");
            String numberTask = scanner.nextLine();
            if (null != numberTask) {

                switch (numberTask) {
                    case "0":
                        flagExit = false;
                        System.out.println("Заканчиваем работу программы");
                        break;

                    case "1":
                        System.out.println("Новый автомобиль.");
                        CarProvider carProvider = new CarProvider();
                        Car car = carProvider.createCar();
                        listCars.add(car);
                        //SaveToFile saveToFile = new SaveToFile();
                        //saveToBase.saveCars(listCars);
                        saveble.saveCars(listCars);
                        for (Car c : listCars) {
                            System.out.println(c.toString());
                        }
                        break;

                    case "2":
                        System.out.println("Новый клиент.");
                        BuyerProvider buyerProvider = new BuyerProvider();
                        Buyer buyer = buyerProvider.createBuyer();
                        listBuyers.add(buyer);
                        //SaveToFile saveToFile = new SaveToFile();
                        //saveToBase.saveBuyers(listBuyers);
                        saveble.saveBuyers(listBuyers);
                        for (Buyer b : listBuyers) {
                            System.out.println(b.toString());
                        }
                        break;

                    case "3":
                        System.out.println("Список автомобилей:");
                        int i = 1;
                        for (Car c : listCars) {
                            // Пповерям наличие автомобилей по счетчику
                            if(c.getCount()>0){
                               System.out.println(i + ". " + c.toString()); 
                            }
                            
                            i++;
                        }
                        break;

                    case "4":
                        System.out.println("Список клиентов:");
                        for (int j = 0; j < listBuyers.size(); j++) {
                            System.out.println(j + 1 + ". " + listBuyers.get(j).toString());
                        }

                        break;
                    case "5":
                        System.out.println("Продажа автомобиля");
                        History history = historyProvider.createHistory(listCars, listBuyers);
                        if (history != null) {
                            listHistories.add(history);
                            //saveToBase.saveHistories(listHistories);
                            saveble.saveHistories(listHistories);
                            System.out.println("Машина продана");

                        } 
                        break;

                    case "6":
                        System.out.println("Проданные автомобили");
                        if (!listHistories.isEmpty()) {
                            for (History h : listHistories) {
                                System.out.println(h.getCar().getMarka()
                                        + " " + h.getCar().getModel()
                                        + " " + h.getCar().getPrice()
                                        + " " + h.getBuyer().getName()
                                        + " " + h.getBuyer().getLastname()
                                        + " " + h.getTakeOn()
                                );
                            }
                        }
                        break;

                    default:
                        System.err.println("Num?");
                }

            }

        } while (flagExit);

    }

}
