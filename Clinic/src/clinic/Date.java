/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Basian
 */
public class Date implements IComponent {

    
    private String day;
    private List<String> hours = new ArrayList<>();
    private final List<String> hoursBusy = new ArrayList<>();

    private List<String> setHours(List<String> hours) {
        int j;
        for (int i = 8; i < 16;) {
            j = 0;
            for (j = 0; j < 61;) {
                if (j != 60) {
                    if (i < 10) {
                        if (j == 0) {
                            String hour = "0" + i + ":" + j + "0:00";
                            hours.add(hour);

                        } else {
                            String hour = "0" + i + ":" + j + ":00";
                            hours.add(hour);

                        }
                        j += 15;
                    } else {
                        if (j == 0) {
                            String hour = i + ":" + j + "0:00";
                            hours.add(hour);

                        } else {
                            String hour = i + ":" + j + ":00";
                            hours.add(hour);

                        }
                        j += 15;
                    }
                } else {
                    i++;
                    break;

                }
            }
        }
        return hours;
    }
    
    public Date(String date) {
        
        this.day = date.substring(0, 10);
        this.hours = setHours(hours);
    }
    
    public void addBusyHour(String hour){
       hoursBusy.add(hour);
    }
    
    public List<String> getHoursBusy() {
        return hoursBusy;
    }

    public List<String> getHoursFree() {
        hours.removeAll(hoursBusy);
        return hours;
    }
    
    

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public List<String> getHours() {
        return hours;
    }

    @Override
    public String print() {
        return getDay() +" "+getHoursBusy();
    }
}
