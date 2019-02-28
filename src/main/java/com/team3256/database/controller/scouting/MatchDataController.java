package com.team3256.database.controller.scouting;

import com.fasterxml.jackson.annotation.JsonView;
import com.team3256.database.model.View;
import com.team3256.database.model.scouting.match.MatchComputed;
import com.team3256.database.model.scouting.match.MatchDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchDataController {
    @Autowired
    private MatchDataRepository matchDataRepository;

    @GetMapping("/computed")
    public void getComputedData(HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition","attachment;filename=data.csv");
        ServletOutputStream out = response.getOutputStream();
        List<MatchComputed> matchComputedList = matchDataRepository.computeMatches();
        String csv = "Team,Auto Hab Level Average,Auto Crossed Baseline Count,Total Cargo Count,Total Hatch Count,Average Cargo Time,Average Hatch Time,Climb Hab Average,Climb Average Cycle Time,Climb Average Time,Climb Support Count,Disconnect Count,Average Disconnect Duration,Total Foul Count\n";
        for (MatchComputed matchComputed : matchComputedList) {
            csv += toCsvRow(
                    matchComputed.getTeam(),
                    matchComputed.getAuto_hab_level_average(),
                    matchComputed.getAuto_crossed_count(),
                    matchComputed.getTotal_cargo_count(),
                    matchComputed.getTotal_hatch_count(),
                    matchComputed.getAverage_cargo_time(),
                    matchComputed.getAverage_hatch_time(),
                    matchComputed.getClimb_hab_average(),
                    matchComputed.getClimb_average_cycle_time(),
                    matchComputed.getClimb_average_time(),
                    matchComputed.getClimb_support_count(),
                    matchComputed.getDisconnect_count(),
                    matchComputed.getAverage_disconnect_duration(),
                    matchComputed.getFoul_count()) + "\n";
        }
        out.print(csv);
        out.flush();
        out.close();
    }

    @GetMapping("/computed/json")
    public List<MatchComputed> getJsonComputedData() {
        return matchDataRepository.computeMatches();
    }

    public String toCsvRow(
            String team,
            double auto_hab_level_average,
            int auto_crossed_count,
            int total_cargo_count,
            int total_hatch_count,
            double average_cargo_time,
            double average_hatch_time,
            double climb_hab_average,
            double climb_average_cycle_time,
            double climb_average_time,
            int climb_support_count,
            int disconnect_count,
            double average_disconnect_duration,
            int foul_count
    ) {
        String csvRow = "";
        for (String value : Arrays.asList(
                team.replace("frc", ""),
                String.valueOf(auto_hab_level_average),
                String.valueOf(auto_crossed_count),
                String.valueOf(total_cargo_count),
                String.valueOf(total_hatch_count),
                String.valueOf(average_cargo_time),
                String.valueOf(average_hatch_time),
                String.valueOf(climb_hab_average),
                String.valueOf(climb_average_cycle_time),
                String.valueOf(climb_average_time),
                String.valueOf(climb_support_count),
                String.valueOf(disconnect_count),
                String.valueOf(average_disconnect_duration),
                String.valueOf(foul_count)
        )) {
            String processed = value;
            if (value.contains("\"") || value.contains(",")) {
                processed = "\"" + value.replaceAll("\"", "\"\"") + "\"";
            }
            csvRow += "," + processed;
        }
        return csvRow.substring(1);
    }
}
