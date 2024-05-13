package com.islem.tasks.schedulingtasks;


import com.islem.tasks.entity.Project;
import com.islem.tasks.entity.User;
import com.islem.tasks.service.impl.ProjectServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.islem.tasks.service.EmailService;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ScheduledTasks {

    private final EmailService emailService;
    private final ProjectServiceImpl projectService;
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //@Scheduled(fixedRate = 5000)
    @Scheduled(cron = "0 0 0 * * ?")
    public void projectDelayEndSoon() {
        List<Project> projects = projectService.findProjectsEndingToday();
        for (Project project : projects) {
            User user = project.getUser().get(0);
            String email = user.getEmail();
            String subject = "Projectify : Project Deadline Alert";
            String text = String.format("%s will end soon at %s", project.getName(), project.getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            emailService.sendSimpleMessage(email, subject, text);
            log.info("Message sent");
        }
    }



}
