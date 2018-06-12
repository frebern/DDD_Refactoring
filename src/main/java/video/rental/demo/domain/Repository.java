package video.rental.demo.domain;

import video.rental.demo.domain.Video;
import video.rental.demo.domain.Customer;

import java.util.List;

public interface Repository {
    /*
     * Database Access private methods
     */
    Customer findCustomerById(int code);

    Video findVideoByTitle(String title);

    List<Customer> findAllCustomers();

    List<Video> findAllVideos();

    void saveCustomer(Customer customer);

    void saveVideo(Video video);
}
