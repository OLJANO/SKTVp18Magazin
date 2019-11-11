/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import entity.Buyer;
import entity.Car;
import entity.History;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class HistoryProvider {
List<Buyer> listBuyers;


SaveToFile sf = new SaveToFile();

    Scanner scanner = new Scanner(System.in);

    public HistoryProvider() {
        
        listBuyers = new ArrayList<>();
    }

    
    
    
    
    
    
    public History createHistory(List<Car> listCars, List<Buyer> listBuyers) {
        History history = new History();

        System.out.println("Список автомобилей: ");
        int countCurrentCars = 0;
        for (int i = 0; i < listCars.size(); i++) {
            if (listCars.get(i).getCount() > 0) {
                System.out.printf("%d. Марка: %s, модель: %s, дата выпуска: %d, стоимость: %s%n, количество: %s%n",
                        i + 1,
                        listCars.get(i).getMarka(),
                        listCars.get(i).getModel(),
                        listCars.get(i).getYear(),
                        listCars.get(i).getPrice(),
                        listCars.get(i).getCount()
                );
                countCurrentCars++;
            }
        }
        if (countCurrentCars == 0) {
            System.out.println("Все автомобили проданы.");
            return null;
        }

        System.out.print("Выберите номер покупаемого автомобиля: ");
        int takeCarNum = scanner.nextInt();
        Car car = listCars.get(takeCarNum - 1);

        System.out.println(car.getMarka());


        System.out.println("Список клиентов: ");
        int i = 0;
        for (Buyer b : listBuyers) {
            System.out.printf("%d. Имя и фамилия клиента: %s %s, email: %s, количество денег: %s%n",
                    i + 1,
                    b.getName(),
                    b.getLastname(),
                    b.getEmail(),
                    b.getMoney()
            );
            i++;
        }

        System.out.println("Выберите номер клиента: ");
        int buyerNum = scanner.nextInt();

        Buyer buyer = listBuyers.get(buyerNum - 1);

        // Проверка на наличие денег у клиента
        if (car.getPrice() > buyer.getMoney()) {
            System.err.println("Сделка не состоялась. У клиента " + buyer.getName() + " " + buyer.getLastname() + " не хватает денег");

            return null;
        }

        buyer.setMoney(buyer.getMoney() - car.getPrice());
        car.setCount(car.getCount() - 1);
        history.setCar(car);
        history.setBuyer(buyer);
        history.setTakeOn(new Date());

        return history;
    }

}
