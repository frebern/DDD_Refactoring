package video.rental.demo.application;

import video.rental.demo.domain.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Interactor {
    private Repository repository;

    public Interactor(Repository repository) {
        this.repository = repository;
    }

    public Repository getRepository() {
        return repository;
    }

    public void clearRentals(int customerCode) {
        Customer foundCustomer = getRepository().findCustomerById(customerCode);

        if (foundCustomer == null) {
            System.out.println("No customer found");
        } else {
            System.out.println("Id: " + foundCustomer.getCode() + "\nName: " + foundCustomer.getName() + "\tRentals: "
                    + foundCustomer.getRentals().size());
            for (Rental rental : foundCustomer.getRentals()) {
                System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ");
                System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode());
            }

            List<Rental> rentals = new ArrayList<Rental>();
            foundCustomer.setRentals(rentals);

            getRepository().saveCustomer(foundCustomer);
        }
    }

    public void returnVideo(int customerCode, String videoTitle) {
        Customer foundCustomer = getRepository().findCustomerById(customerCode);
        if (foundCustomer == null)
            return;

        List<Rental> customerRentals = foundCustomer.getRentals();

        for (Rental rental : customerRentals) {
            if (rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented()) {
                Video video = rental.returnVideo();
                video.setRented(false);
                getRepository().saveVideo(video);
                break;
            }
        }

        getRepository().saveCustomer(foundCustomer);
    }

    public void rentVideo(int code, String videoTitle) {
        Customer foundCustomer = getRepository().findCustomerById(code);
        if (foundCustomer == null)
            return;
        Video foundVideo = getRepository().findVideoByTitle(videoTitle);

        if (foundVideo == null)
            return;

        if (foundVideo.isRented() == true)
            return;

        Boolean status = foundVideo.rentFor(foundCustomer);
        if (status == true) {
            getRepository().saveVideo(foundVideo);
            getRepository().saveCustomer(foundCustomer);
        } else {
            return;
        }
    }

    public void registerCustomer(String name, int code, String dateOfBirth) {
        Customer customer = new Customer(code, name, dateOfBirth);
        getRepository().saveCustomer(customer);
    }

    public void registerVideo(String title, int videoType, int priceCode, int videoRating) {
        Date registeredDate = new Date();
        Rating rating;
        if (videoRating == 1) rating = Rating.TWELVE;
        else if (videoRating == 2) rating = Rating.FIFTEEN;
        else if (videoRating == 3) rating = Rating.EIGHTEEN;
        else throw new IllegalArgumentException("No such rating " + videoRating);

        Video video = new Video(title, videoType, priceCode, rating, registeredDate);

        getRepository().saveVideo(video);
    }

    public String getCustomerReport(int code) {
        Customer foundCustomer = getRepository().findCustomerById(code);
        return foundCustomer==null?null:foundCustomer.getReport();
    }
}
