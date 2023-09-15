package ca.ulaval.glo4003.sulvlo.domain.payment.cron;

import ca.ulaval.glo4003.sulvlo.domain.payment.service.PaymentDomainService;
import ca.ulaval.glo4003.sulvlo.domain.payment.service.schoolFeesService.SchoolFeesService;
import java.io.IOException;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class BlockStudentPassJob implements Job {

  @Override
  public void execute(JobExecutionContext context)
      throws JobExecutionException {
    JobDataMap dataMap = context.getJobDetail().getJobDataMap();
    PaymentDomainService paymentDomainService = (PaymentDomainService) dataMap.get(
        "paymentDomainService");
    SchoolFeesService schoolFeesService = new SchoolFeesService();
    try {
      paymentDomainService.blockStudentAccountNotPaidBill(schoolFeesService);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
