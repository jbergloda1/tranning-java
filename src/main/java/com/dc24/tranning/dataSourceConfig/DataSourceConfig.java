//package com.dc24.tranning.dataSourceConfig;
//
//import com.dc24.tranning.entity.UsersEntity;
//import com.dc24.tranning.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PostConstruct;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@Configuration
//@RequiredArgsConstructor
//public class DataSourceConfig {
//    // inject bởi RequiredArgsConstructor
//    private final UserRepository userRepository;
//
//    // Chỉ áp dụng trong demo :D
//    @PostConstruct
//    public void initData() {
//        // Insert 100 User vào H2 Database sau khi
//        // DatasourceConfig được khởi tạo
//        userRepository.saveAll(IntStream.range(200, 1000)
//                .mapToObj(i -> UsersEntity.builder()
//                        .username("name-" + i)
//                        .email("email@gmail.com" + i)
//                        .password("abc@123" + i)
//                        .build())
//                .collect(Collectors.toList())
//        );
//    }
//}
