package org.example.view;

import org.example.entity.area.Area;

public interface View {
    void showWorldMap(Area area);

    void showStatistics(Area area);

    void showIterationNumber();

    void showDiedToday();

    void showAliveToday();

    void showTotalDied();

    void showTotalAliveOrganisms(Area area);
}
