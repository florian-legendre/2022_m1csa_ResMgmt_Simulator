package simulator;

import loaders.Test;
import scheduler.*;

import java.io.IOException;


public class MultiServer {
    public MultiServer(Test test){
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%% MULTISERVER %%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n");
        int nbServers = test.getServersLoader().getServers().size();

        // =================================== OUTPUT FILES GENERATION ===========================================
        System.out.println(">>> SCHEDULING USING FIFO");
        FIFO fifoScheduler = new FIFO(test, nbServers);
        fifoScheduler.getSchedule().write("../out/" + test.getFileName() + "_multiServer_fifo.txt");
        fifoScheduler.getSchedule().print();
        new Metrics(fifoScheduler.getSchedule()).print();

        System.out.println(">>> SCHEDULING USING ROUND ROBIN");
        RR rrScheduler = new RR(test, nbServers, 2);
        rrScheduler.getSchedule().write("../out/" + test.getFileName() + "_multiServer_rr.txt");
        rrScheduler.getSchedule().print();
        new Metrics(rrScheduler.getSchedule()).print();

        System.out.println(">>> SCHEDULING USING EARLIEST DEADLINE FIRST");
        EDF edfScheduler = new EDF(test, nbServers);
        edfScheduler.getSchedule().write("../out/" + test.getFileName() + "_multiServer_edf.txt");
        edfScheduler.getSchedule().print();
        new Metrics(edfScheduler.getSchedule()).print();

        System.out.println(">>> SCHEDULING USING RATE MONOTONIC");
        RMS rmsScheduler = new RMS(test, nbServers);
        rmsScheduler.getSchedule().write("../out/" + test.getFileName() + "_multiServer_rms.txt");
        rmsScheduler.getSchedule().print();
        new Metrics(rmsScheduler.getSchedule()).print();

        // =================================== PYTHON GRAPHS GENERATION ===========================================
        String plotterCmd = "python3 ../lib/etu/savePlot.py ";
        try {
            Runtime.getRuntime().exec(plotterCmd + "../out/" + test.getFileName() + "_multiServer_fifo.txt");
            Runtime.getRuntime().exec(plotterCmd + "../out/" + test.getFileName() + "_multiServer_rr.txt");
            Runtime.getRuntime().exec(plotterCmd + "../out/" + test.getFileName() + "_multiServer_edf.txt");
            Runtime.getRuntime().exec(plotterCmd + "../out/" + test.getFileName() + "_multiServer_rms.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    }
}
