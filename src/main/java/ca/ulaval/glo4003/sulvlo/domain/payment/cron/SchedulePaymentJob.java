package ca.ulaval.glo4003.sulvlo.domain.payment.cron;

import ca.ulaval.glo4003.sulvlo.domain.payment.service.PaymentDomainService;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulePaymentJob {

  private final PaymentDomainService paymentDomainService;

  public SchedulePaymentJob(PaymentDomainService paymentDomainService) {
    this.paymentDomainService = paymentDomainService;
  }

  public void start() throws SchedulerException {
    initializeSendSalesSummary();
    initializeSendBill();
    initializePayMonthly();
    initializeBlockAccount();
    initializeStudentBlockAccount();
    initializeStudentActivationAccount();
  }

  private void initializeSendSalesSummary() throws SchedulerException {
    JobDetail job = JobBuilder.newJob(SendSalesSummaryJob.class).build();
    job.getJobDataMap().put("paymentDomainService", paymentDomainService);
    Trigger trigger = TriggerBuilder
        .newTrigger()
        .withSchedule(
            CronScheduleBuilder.cronSchedule("0 0 10 L * ?"))
        .build();

    Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    scheduler.start();
    scheduler.scheduleJob(job, trigger);
  }

  private void initializeSendBill() throws SchedulerException {
    JobDetail job = JobBuilder.newJob(SendBillJob.class).build();
    job.getJobDataMap().put("paymentDomainService", paymentDomainService);
    Trigger trigger = TriggerBuilder
        .newTrigger()
        .withSchedule(
            CronScheduleBuilder.cronSchedule("0 0 12 L * ?"))
        .build();

    Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    scheduler.start();
    scheduler.scheduleJob(job, trigger);
  }

  private void initializePayMonthly() throws SchedulerException {
    JobDetail job = JobBuilder.newJob(PayMonthlyJob.class).build();
    job.getJobDataMap().put("paymentDomainService", paymentDomainService);
    Trigger trigger = TriggerBuilder
        .newTrigger()
        .withSchedule(
            CronScheduleBuilder.cronSchedule("0 30 12 L * ?"))
        .build();
    Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    scheduler.start();
    scheduler.scheduleJob(job, trigger);
  }

  private void initializeBlockAccount() throws SchedulerException {
    JobDetail job = JobBuilder.newJob(BlockAccountJob.class).build();
    job.getJobDataMap().put("paymentDomainService", paymentDomainService);
    Trigger trigger = TriggerBuilder
        .newTrigger()
        .withSchedule(
            CronScheduleBuilder.cronSchedule("0 0 12 15 * ?"))

        .build();
    Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    scheduler.start();
    scheduler.scheduleJob(job, trigger);
  }

  private void initializeStudentBlockAccount() throws SchedulerException {
    JobDetail job = JobBuilder.newJob(BlockStudentPassJob.class).build();
    job.getJobDataMap().put("paymentDomainService", paymentDomainService);
    Trigger trigger = TriggerBuilder
        .newTrigger()
        .withSchedule(
            CronScheduleBuilder.cronSchedule("0 0 12 * * ?"))

        .build();
    Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    scheduler.start();
    scheduler.scheduleJob(job, trigger);
  }

  private void initializeStudentActivationAccount() throws SchedulerException {
    JobDetail job = JobBuilder.newJob(ActivateStudentPassJob.class).build();
    job.getJobDataMap().put("paymentDomainService", paymentDomainService);
    Trigger trigger = TriggerBuilder
        .newTrigger()
        .withSchedule(
            CronScheduleBuilder.cronSchedule("0 0 12 * * ?"))

        .build();
    Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    scheduler.start();
    scheduler.scheduleJob(job, trigger);
  }
}
