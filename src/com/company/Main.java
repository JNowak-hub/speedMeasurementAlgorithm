package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        List<Integer> numbers = new ArrayList<>();
        FileReader fileReader = new FileReader("src/pomiar.txt");

        try (BufferedReader br = new BufferedReader(fileReader)) {
            String line;
            while ((line = br.readLine()) != null) {
                numbers.add(Integer.valueOf(line));
            }
        }
        System.out.println(numbers);
        numbers.remove(numbers.get(0));
        List<List<Integer>> measurements = new ArrayList<>();
        for(int i = 0 ; i < numbers.size(); i++){
            int iteration = numbers.get(i);
            List<Integer> measurementPart = new ArrayList<>();
            int index = i+1;
            for (int j = 0; j  < iteration; j++){
                measurementPart.add(numbers.get(index));
                index ++;
                i++;
            }
            measurements.add(measurementPart);
        }
        List<List<List<Integer>>> results = new ArrayList<>();
        for (List<Integer> measurementPart : measurements) {
            List<List<Integer>> result = new ArrayList<>();
            for (int j = 0; j < measurementPart.size(); j++) {
                List<Integer> singleResult = new ArrayList<>();
                if (j == 0) {
                    singleResult.add(-measurementPart.get(j));
                    singleResult.add(measurementPart.get(j));
                } else {
                    for (int k = 0; k < result.get(j - 1).size(); k++) {
                        singleResult.add(result.get(j - 1).get(k) - measurementPart.get(j));
                        singleResult.add(result.get(j - 1).get(k) + measurementPart.get(j));
                    }
                }
                result.add(singleResult);
            }
            results.add(result);
        }
        List<List<Integer>> finalResults = new ArrayList<>();
        for (List<List<Integer>> result : results) {
            int min = result.stream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList())
                    .stream().mapToInt(Math::abs).min().getAsInt();
            int max = result.stream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList())
                    .stream().mapToInt(Math::abs).max().getAsInt();
            List<Integer> finalSingleResult = Arrays.asList(min, max);
            finalResults.add(finalSingleResult);
        }
        for (List<Integer> element: finalResults) {
            System.out.println(element.get(0) + " " + element.get(1));
        }
    }
}
