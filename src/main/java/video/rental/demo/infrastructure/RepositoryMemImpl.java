package video.rental.demo.infrastructure;

import video.rental.demo.domain.Repository;
import video.rental.demo.domain.Video;
import video.rental.demo.domain.Customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RepositoryMemImpl implements Repository {

    private Map<Integer, Customer> customers = new HashMap<Integer, Customer>();
    private Map<String, Video> videos = new HashMap<String, Video>();

    public Customer findCustomerById(int code) {
        return customers.get(code);
    }

    public Video findVideoByTitle(String title) {
        return videos.get(title);
    }

    public List<Customer> findAllCustomers() {
        return customers.values().stream().collect(Collectors.toList());
    }

    public List<Video> findAllVideos() {
        return videos.values().stream().collect(Collectors.toList());
    }

    public void saveCustomer(Customer customer) {
        customers.put(customer.getCode(), customer);
    }

    public void saveVideo(Video video) {
        videos.put(video.getTitle(), video);
    }
}

