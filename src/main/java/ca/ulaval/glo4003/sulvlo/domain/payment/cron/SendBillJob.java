package ca.ulaval.glo4003.sulvlo.domain.payment.cron;

import ca.ulaval.glo4003.sulvlo.domain.payment.service.PaymentDomainService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SendBillJob implements Job {

  @Override
  public void execute(JobExecutionContext context)
      throws JobExecutionException {
    JobDataMap dataMap = context.getJobDetail().getJobDataMap();
    PaymentDomainService paymentDomainService = (PaymentDomainService) dataMap.get(
        "paymentDomainService");
    paymentDomainService.sendBillUsers();
  }

}
