/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import entity.Car;
import entity.History;
import entity.Buyer;
import interfaces.Saveble;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author user
 */
//public class SaveToBase implements Saveble{
public class SaveToBase implements Saveble{
    EntityManager em;
    EntityTransaction tx;

    public SaveToBase() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SKTVp18MagazinPU");
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }
    
    @Override
    public void saveCars(List<Car> listCars){
        List<Car> listCarsSaved = loadCars();
        //tx.begin();
        for(int i=0; i<listCars.size();i++){
            if(listCarsSaved.contains(listCars.get(i))
                    && !listCarsSaved.get(i).equals(listCars.get(i))){
                 tx.begin();
                em.merge(listCars.get(i));
                tx.commit();
              }else if(listCars.get(i).getId() == null){
                tx.begin();
                em.persist(listCars.get(i));
                tx.commit();
            }else{
                continue;
            }
        }
    }
    
    @Override
    public List<Car> loadCars(){
        return em.createQuery("SELECT c FROM Car c")
                .getResultList();
    }
    
    @Override
    public void saveBuyers(List<Buyer> listBuyers){
        List<Buyer> listBuyersSaved = loadBuyers();
        //tx.begin();
        for(int i=0; i<listBuyers.size();i++){
            if(listBuyersSaved.contains(listBuyers.get(i))
                    && !listBuyersSaved.get(i).equals(listBuyers.get(i))){
                tx.begin();
                em.merge(listBuyers.get(i));
                tx.commit();
                 }else if(listBuyers.get(i).getId()==null){
                tx.begin();
                em.persist(listBuyers.get(i));
                tx.commit();
            }else{
                continue;
            }
        }
        
    }

    @Override
    public List<Buyer> loadBuyers(){
        return em.createQuery("SELECT b FROM Buyer b")
                .getResultList();
    }

    //public 
    @Override
        public void saveHistories(List<History> listHistories) {
        for(History delHistory : listHistories){
            int flag = 0;
            for(int i=0;i<listHistories.size();i++){
                if(delHistory.getBuyer().equals(listHistories.get(i).getBuyer())){
                    if(delHistory.getCar().getId() == listHistories.get(i).getCar().getId()){
                        flag++;
                    }
                    if(flag >1){
                        listHistories.get(i).getCar().setCount(listHistories.get(i).getCar().getCount()+1);
                        listHistories.remove(listHistories.get(i));
                        System.out.println("Эту машину клиент уже покупал");
                        break;
                    }
                }
            }
    if(flag > 1) break;
        }
        List<History> listHistoriesSaved = loadHistories();
        History newHistory = null;
        History editHistory = null;
        History returnHistory = null;
        int i = 0;
        for(History h : listHistories){
            if(!listHistoriesSaved.contains(h) && h.getId() == null){
                newHistory = h;
                break;
            }
            if(listHistoriesSaved.contains(h) && !listHistoriesSaved.get(i).equals(h)){
                editHistory = h;
                break;
            }
//            if(listHistoriesSaved.get(i).getId() == h.getId()
//                    && listHistoriesSaved.get(i).getReturnDate() == null && h.getReturnDate()!=null){
//                returnHistory = h;
//                break;
//            }
//            i++;
        }
        if(newHistory != null){
            tx.begin();
            em.persist(newHistory);
            em.flush();
            em.merge(newHistory.getCar());
            tx.commit();
        }
        if(editHistory != null){
            tx.begin();
            em.merge(editHistory);
            em.merge(editHistory.getCar());
            tx.commit();
        }
        if(returnHistory != null){
            tx.begin();
            em.merge(returnHistory);
            em.flush();
            em.merge(returnHistory.getCar());
            tx.commit();
        }
    }
    @Override
    public List<History> loadHistories() {
        return em.createQuery("SELECT h FROM History h")
                .getResultList();
    }


}
