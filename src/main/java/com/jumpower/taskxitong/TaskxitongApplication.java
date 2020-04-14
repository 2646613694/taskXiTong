package com.jumpower.taskxitong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.jumpower.taskxitong.mapper")
@SpringBootApplication
public class TaskxitongApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskxitongApplication.class, args);
        System.out.println("                   .::::.\n" +
                "                 .::::::::.\n" +
                "                :::::::::::\n" +
                "             ..:::::::::::'\n" +
                "          '::::::::::::'\n" +
                "            .::::::::::\n" +
                "       '::::::::::::::..\n" +
                "            ..::::::::::::.\n" +
                "          ``::::::::::::::::\n" +
                "           ::::``:::::::::'        .:::.\n" +
                "          ::::'   ':::::'       .::::::::.\n" +
                "        .::::'      ::::     .:::::::'::::.\n" +
                "       .:::'       :::::  .:::::::::' ':::::.\n" +
                "      .::'        :::::.:::::::::'      ':::::.\n" +
                "     .::'         ::::::::::::::'         ``::::.\n" +
                " ...:::           ::::::::::::'              ``::.\n" +
                "```` ':.          ':::::::::'                  ::::..\n" +
                "                   '.:::::'                    ':'````..");
    }

}
