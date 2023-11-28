package learn.vk.microservices.customer.repository;

import learn.vk.microservices.customer.entity.Customer;

public interface CustomerRepository extends org.springframework.data.jpa.repository.JpaRepository<Customer, Long> {
}
