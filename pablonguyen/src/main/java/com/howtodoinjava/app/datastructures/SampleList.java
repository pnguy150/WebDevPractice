package com.howtodoinjava.app.datastructures;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SampleList {
  private class MyComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
      return 0;
    }
  }

  List<String> strings = new LinkedList<>();


  private void foo() {
    String first = strings.get(0);
  }


  public List<Pet> convertToPets(List<Dog> dogs) {
    Stream<Pet> petStream = dogs.stream().map(dog -> {
      Pet pet = null;
      switch(dog.breed) {
        case "labrador":
          pet = new Pet("dog", true, true);
          break;
        case "bulldog":
          pet = new Pet("dog", true, false);
          break;
        case "poodle":
          pet = new Pet("dog", false, true);
        }
        return pet;
    });
    return petStream.collect(Collectors.toList());
  }

  public List<Pet> simpleConvertToPets(List<Dog> dogs) {
    Stream<Pet> petStream = dogs.stream().map(dog -> new Pet("dog", true, dog.friendly));
    return petStream.collect(Collectors.toList());
  }

  public List<Canine> convertToCanines(List<Dog> dogs){
    Stream<Canine> canineStream = dogs.stream().map(dog -> new Canine("dog", dog.friendly));
    return canineStream.collect(Collectors.toList());
  }

  public List<String> sortAscending(List<String> sss) {
    //Comparator<String> c = Comparator.naturalOrder();
//    sss.sort(new Comparator<String>() {
//      @Override
//      public int compare(String o1, String o2) {
//        return o1.compareTo(o2);
//      }
//    });
    sss.sort(new MyComparator());

    return sss;
  }

  public List<String> sortDescending(List<String> sss) {
    //Comparator<String> c = Comparator.naturalOrder();
    sss.sort(new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return o2.compareTo(o1);
      }
    });

    return sss;
  }

  public List<Dog> getFriendlyDogs(List<Dog> dogs) {
    List<Dog> friendlyDogs = dogs.stream().filter(dog -> dog.friendly).collect(Collectors.toList());
    return friendlyDogs;
  }


  public List<Dog> getBreedDogs(List<Dog> dogs, String givenBreed) {
    List<Dog> breedDogs = dogs.stream().filter(dog -> dog.breed.equals(givenBreed)).collect(Collectors.toList());
    return breedDogs;
  }

  public List<Dog> getUnfriendlyDogs(List<Dog> dogs) {
    List<Dog> unfriendlyDogs = dogs.stream().filter(dog -> !dog.friendly).collect(Collectors.toList());
    return unfriendlyDogs;
  }

  public List<Dog> getLabradors(List<Dog> dogs) {
    List<Dog> labradors = dogs.stream().filter(dog -> dog.breed.equals("labrador")).collect(Collectors.toList());
    return labradors;
  }

  public Dog getFirstLabradors(List<Dog> dogs) {
    //Optional<Dog> optionalDog = dogs.stream().filter(dog -> dog.breed.equals("labrador")).findFirst();

//    if (optionalDog.isEmpty()){
//      return null;
//    }
//    else{
//      return optionalDog.get();
//    }
    //return optionalDog.isEmpty() ? null : optionalDog.get();

    for (Dog d: dogs){
      if (d.breed.equals("labrador")) {
        return d;
      }
    }
    return null;
  }
  public List<Dog> getLargeDogs(List<Dog> dogs) {
    List<Dog> labradors = dogs.stream().filter(dog -> dog.size.equals("large")).collect(Collectors.toList());
    return labradors;
  }

  public static void main(String[] args) {

    Dog lab = new Dog("labrador", "large", false);
    Dog bulldog = new Dog("bulldog", "medium", true);
    Dog poodle = new Dog("poodle", "small", true);
    Dog lab2 = new Dog("labrador", "medium", false);
    List<Dog> dogList = new ArrayList<>(Arrays.asList(lab, bulldog, poodle, lab2));

    SampleList sl = new SampleList();

    List<Dog> friendly_dogs = sl.getFriendlyDogs(dogList);


    List<Dog> dog_by_breed = sl.getBreedDogs(dogList, "bulldog");


    List<Dog> unfriendlyDogs = sl.getUnfriendlyDogs(dogList);



    List<Dog> labradors = sl.getLabradors(dogList);


    Dog labradorFirst = sl.getFirstLabradors(dogList);


    List<Dog> largeDogs = sl.getLargeDogs(dogList);


    List<String> stringList = new ArrayList<>(Arrays.asList("aasd", "ohfsd", "lidugs", "dkjhfg", "sdfhoihs"));

    List<String> sortedStringList = sl.sortAscending(stringList);
    List<String> descendingStringList = sl.sortDescending(stringList);


    List<Pet> pets = sl.convertToPets(dogList);
    List<Canine> canines = sl.convertToCanines(dogList);
    }


  }


