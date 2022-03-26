package scheduler;

import loaders.Test;

public class FIFO extends SchedulerQuantum{
    /* ================ CONSTRUCTORS ================ */
    public FIFO(Test test, int nbServers) {
        super(test, nbServers);
        run();
    }

    /* ================ SETTERS ================ */
    @Override
    public void runScheduleStep() {
        if(areAllServersIdle() && !arrivedJobs.isEmpty()){
            schedule.currentDate = arrivedJobs.getFirst().getArrivalDate();
            initServers();
        }

        //We compute next event date:
        double nextEventDate = getNextEventDate();
        double unitsOfWorkDone = nextEventDate - schedule.currentDate;
        schedule.currentDate += unitsOfWorkDone;

        //We decrement and deal with finished jobs:
        decrementAll(unitsOfWorkDone);

        //We deal with new arrivals:
        arrivedJobs.addAll(jobsBatch.getArrivedJobs(nextEventDate));
        assignArrivals();
    }
}
