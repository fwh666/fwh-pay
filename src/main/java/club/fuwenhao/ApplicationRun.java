package club.fuwenhao;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fuwenhao
 * @createDate 2023/3/22 17:50
 * @descripton
 */
@MapperScan(basePackages = {"club.fuwenhao.dao"})
@SpringBootApplication
public class ApplicationRun {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun.class, args);
    }
}

