package com.example.anime;

public class AnimeTitle {

    private String name;
    private String path_to_img;

    public AnimeTitle(String name, String path_to_img){
        this.name = name;
        this.path_to_img = path_to_img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath_to_img() {
        return path_to_img;
    }

    public void setPath_to_img(String path_to_img) {
        this.path_to_img = path_to_img;
    }

    @Override
    public String toString(){
        return this.getName();
    }
}
