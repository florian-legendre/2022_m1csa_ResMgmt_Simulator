package scheduler;

import components.Job;
import loaders.Test;

import java.util.Comparator;
import java.util.function.BiPredicate;

public class EDFe extends SchedulerPriority{
    private static final Comparator<Job> JOBS_COMPARISON_KEY = Comparator.comparingDouble(Job::getADeadline);
    private static final BiPredicate<Job, Job> JOBS_COMPARISON_PREDICATE = (Job j1, Job j2) -> j1.getADeadline() < j2.getADeadline();

    /* ================ CONSTRUCTORS ================ */
    public EDFe(Test test, int nbServers){
        super(test, nbServers);
        run();
    }

    /* ================ SETTERS ================ */
    @Override
    protected void runScheduleStep() {
        arrivedJ.sort(JOBS_COMPARISON_KEY);
        if(serversM.areAllServersIdle() && !arrivedJ.isEmpty()){
            schedule.currentDate = arrivedJ.getFirst().getArrivalDate();
            serversM.initServers();
        }

        //We start with frequencies at minimum:
        serversM.resetFreqs();
        //We increase frequencies if jobs are going to be late from the current date:
        serversM.setFreqs(schedule.currentDate);

        //We compute next event date:
        double nextEventDate = getNextEventDate();
        double unitsOfWorkDone = nextEventDate - schedule.currentDate;
        schedule.currentDate += unitsOfWorkDone;

        //We decrement and deal with finished jobs:
        serversM.decrementAll(unitsOfWorkDone);

        //We deal with new arrivals:
        arrivedJ.addAll(jobsB.getArrivedJobs(nextEventDate));
        arrivedJ.sort(JOBS_COMPARISON_KEY);
        assignArrivals(JOBS_COMPARISON_KEY, JOBS_COMPARISON_PREDICATE);
    }
}
