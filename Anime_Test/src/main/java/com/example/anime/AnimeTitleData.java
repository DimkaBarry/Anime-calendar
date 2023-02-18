package com.example.anime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

public class AnimeTitleData {

    private ArrayList<AnimeTitle> animeTitles;

    public AnimeTitleData(File dataFile) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(dataFile.toString()));
            animeTitles = new ArrayList<>();

            //read file line-by-line
            String line;
            while ((line = in.readLine()) != null) {
                String[] data = line.split("\t");

                //create and add new AnimeTitle from data
                AnimeTitle animeTitle = new AnimeTitle(data[0], data[1]);
                animeTitles.add(animeTitle);
            }

            in.close();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Exception in AnimeTitleData");
        }
    }

    public ArrayList<AnimeTitle> getAnimeTitles(){
        return animeTitles;
    }

}
