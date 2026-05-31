package dev.intership.manufacturing_execution_system.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.intership.manufacturing_execution_system.entity.Customer;
import dev.intership.manufacturing_execution_system.entity.UserAccount;
import dev.intership.manufacturing_execution_system.enums.RoleType;
import dev.intership.manufacturing_execution_system.repository.CustomerRepository;
import dev.intership.manufacturing_execution_system.repository.UserAccountRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userAccountRepository.count() > 0) {
            return;
        }

        UserAccount user_admin = new UserAccount(
                "ADMIN",
                passwordEncoder.encode("123456"),
                RoleType.ADMIN
        );

        UserAccount user_manager = new UserAccount(
                "manager_Chi",
                passwordEncoder.encode("123456"),
                RoleType.MANAGER
        );

        UserAccount user_operator1 = new UserAccount(
                "operator_Lin",
                passwordEncoder.encode("123456"),
                RoleType.OPERATOR
        );

        UserAccount user_operator2 = new UserAccount(
                "operator_Zo",
                passwordEncoder.encode("123456"),
                RoleType.OPERATOR
        );

        userAccountRepository.save(user_admin);
        userAccountRepository.save(user_manager);
        userAccountRepository.save(user_operator1);
        userAccountRepository.save(user_operator2);

        Customer customer_1 = new Customer(
                "Test Customer 1",
                "D-1",
                "0912345678",
                "Taipei",
                user_manager
        );

        Customer customer_2 = new Customer(
                "Test Customer 2",
                "C-2",
                "0912345678",
                "Taipei",
                user_manager
        );

        customerRepository.save(customer_1);
        customerRepository.save(customer_2);
    }
}