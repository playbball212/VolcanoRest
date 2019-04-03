package com.volcanoes.volcanoes.Services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.volcanoes.volcanoes.Models.Volcano;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VolcanoService {

    public List<Volcano> volcanoes;

    public void loadVolcanos() throws FileNotFoundException {

        String path = new File("").getAbsolutePath();
        path+="/src/main/resources/volcano.json";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        volcanoes = new Gson().fromJson(bufferedReader, new TypeToken<ArrayList<Volcano>>() {

        }.getType());

    }

    public void displayVolcanoes(){
        volcanoes.forEach(volcano -> System.out.println(volcano.getName() + volcano.getVEI()));
    }


    /**
     * Volcanoes that erupted in 1970
     */
    public List<Volcano> displayVolcanoesErupted1970(Integer year) {
        List<Volcano> volcanoesyear = volcanoes.stream().filter(volcano -> (volcano.getYear() == year)
        ).collect(Collectors.toList());

        return volcanoesyear;
    }

    //Return an array of the names of volcanoes that had an eruption with a Volcanic Explosivity Index (VEI) of 7 or higher

    public void displayVolcanoesIndex7() {
        volcanoes.stream().filter(volcano -> (volcano.getVEI()>=7)
        ).forEach(volcano -> System.out.println(volcano.getName()));




    }


    //Return the eruption with the highest number of recorded deaths.


    public void displayHighestNumberOfDeaths() {

        String name = volcanoes.stream().max(Comparator.comparingInt(Volcano::getDEATHS)).get().getName();
        System.out.println(name);




    }

//Return the percentage of eruptions that caused mudflows (Agent_Code "M" for Mudflow).

    public void displayMudflowPercentage() {

        double mudflowCount =  volcanoes.stream().filter(volcano -> volcano.getAgent().equals("M")).count();
        double size = volcanoes.size();
        double average = mudflowCount / size;
        System.out.println("The average is " + average);
    }

    //Return the most common type of volcano in the set

    public String displayMostCommonType() {

        List<String> type = volcanoes.stream().map(volcano -> volcano.getType()).collect(Collectors.toList());
        HashMap<String , Integer> countOccurences = new HashMap<String , Integer>();
        for(String volcanoType : type) {

            if(countOccurences.get(volcanoType) != null) {
                int count = countOccurences.get(volcanoType);
                count++;
                countOccurences.put(volcanoType , count);
            }
            else {
                countOccurences.put(volcanoType , 1);
            }
        }

        String mostCommonType = "";
        int max = 0;
        for (Map.Entry<String, Integer> entry : countOccurences.entrySet()) {

            if(entry.getValue() > max) {
                max = entry.getValue();
                mostCommonType = entry.getKey();
            }
        }

        return mostCommonType;

    }

    //Return the number of eruptions when supplied a country as an argument

    public int countryTotalEruptions(String countryName) {
        return  volcanoes.stream().filter(volcano -> volcano.getCountry().equals(countryName)).collect(Collectors.toList()).size();
    }


    //Return the average elevation of all eruptions.

    public double averageElevation() {
        return  volcanoes.stream().mapToInt(Volcano::getElevation).summaryStatistics().getAverage();
    }


    //Return an array of types of volcanoes.

    public String[] typesOfVolcanoes(){


        return volcanoes.stream().map(volcano -> volcano.getType()).distinct().toArray(String[]::new);
    }


}
