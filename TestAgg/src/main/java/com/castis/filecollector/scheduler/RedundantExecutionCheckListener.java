package com.castis.filecollector.scheduler;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedundantExecutionCheckListener implements TriggerListener {
	private static Logger log = LoggerFactory.getLogger(RedundantExecutionCheckListener.class);
	
	@Override
	public String getName() {
		return this.getClass().getCanonicalName();
	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context, CompletedExecutionInstruction triggerInstructionCode) {
		// do nothing.
		//log.info("trigger complete:{}", triggerInstructionCode);
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		// do nothing.
		//log.info("trigger fired");
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		// do nothing.
		//log.info("trigger misfired");
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		try {
			List<JobExecutionContext> jobs = context.getScheduler().getCurrentlyExecutingJobs();
			for (JobExecutionContext job : jobs) {
				if (job.getTrigger().equals(context.getTrigger()) && !job.getJobInstance().equals(this)) {
					log.info("Scheduler - {} is already running. Skip this job trigger.", context.getJobDetail().getKey().getName());
					return true;
				}
			}
		} catch (SchedulerException e) {
			log.error("error : ", e);
		}
		return false;
	}
}
