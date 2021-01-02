import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {
    public static void main(String[] args) {

        //  read the list of ship's passengers into a ListArray of text
        List<String> titanic = FileToList("C:/projects/csv/titanic.csv");

        //  read the list of jobs
        List<String> arOfJobs = FileToList("C:/projects/csv/JobSearch.csv");

        //  take the List of passenger String data and convert into a List of Passengers
        List<Job> jobs = listToJobs(arOfJobs);

        //  take the List of passenger String data and convert into a List of Passengers
        List<Passenger> passengers = Passenger.listToPassengers(titanic);

        //  allTheJobCode(jobs);

//        passengers.forEach(p -> System.out.println(p.getFirstName()));
        for (var p : passengers) {
            //  find baby passengers (age 0 just means we do not have their age from the ship's records
            if (p.getAge() < 1 && p.getAge() != 0) {
                System.out.println(p.getFirstName() + " " + p.getLastName() + " was " +
                        p.getAge() * 12 + " months old and did they survive? " + p.isSurvivor());
            }
        }   //  end of the babies for loop

        //  TODO    #A  list all survivors (make this a method)
        //  TODO    #B  Method to get a List by last Name
        //  TODO    #C  Method to get a List by First Name
        //  TODO    #D  Method to get a List by Class
        //  TODO    #E  Method to get a List by Passenger Or Crew
        //  TODO    #F  Method to get a List by Role
        //  TODO    #G  Method to get a List by age
        //  TODO    #H  Method to get a List by Survivor status. Change #A to get survivors or fatalities


    }       //  end of main

    public static void allTheJobCode(List<Job> jobs) {
        //  TODO #1 how many jobs did I apply for in May
        int countMayJobs = 0;
        for ( Job j : jobs) {
            if ( j.getDateApplied().getMonth().getValue() == 5) {
                countMayJobs++;
            }
        }
        System.out.println("Number of Jobs applied for in May: " + countMayJobs);

        //  TODO #3 how many jobs did I apply for on the 3rd of the month
        int count3rdOfMonthJobs = 0;
        for ( Job j : jobs) {
            if ( j.getDateApplied().getDayOfMonth() == 3) {
                count3rdOfMonthJobs++;
            }
        }
        System.out.println("Number of Jobs applied for on the 3rd: " + count3rdOfMonthJobs);

        //  TODO #2 make this into a method -- 3 parameters:
        //          1st param is month/day number
        //          2nd param is the list of jobs
        //          3rd param what kind of search "month", "day"
        //          returns a number of jobs applied for
        int count;
        count = countJobs(5, jobs, "month");
        System.out.println("Number of Jobs applied for in May: " + count);
        count = countJobs(3, jobs, "day");
        System.out.println("Number of Jobs applied for on the 3rd: " + count);

        //  TODO #5 how many jobs did I apply for on a Thursday. change your method
        //      Change the switch statement to switch on "weekday"
        System.out.println("\nNow we add in a new case. We can now search of Day of the Week 'weekday'\n");
        count = countJobs(5, jobs, "weekday");
        System.out.println("Number of Jobs applied for on Thursday: " + count);

        //  TODO #6 add a request in main to ask the user for
        //      the kind of search to do ("day", "month", "weekday"
        //      the number of a month or day of the week or day of the month
        //      use those two pieces of data to call your method to get the count of jobs applied for

        //  TODO #7 What day of the week did I apply for the most jobs
        //      create an array of 7 ints
        //      get the count of jobs applied for each day
        //      find the largest number in the array
        //      this is your biggest day of the week

        for (var j : jobs) {
            System.out.println(j.getDateApplied());
        }

//        passengers.forEach(p -> System.out.println(p.getFirstName()));
        for (var p : passengers) {
            //  find baby passengers (age 0 just means we do not have their age from the ship's records
            if (p.getAge() < 1 && p.getAge() != 0) {
                System.out.println(p.getFirstName() + " " + p.getLastName() + " was " + p.getAge()*12 + " months old.");
            }
        }   //  end of the for loop

    }       //  end of main

    /**
     *      ListToPassengers
     *          The parameter titanic is a ListArray of strings representing passengers on the titanic
     *          This is the format of the text
     *          ﻿Last Name/ First Name, Age, Class, Passenger or Crew, Role, Survivor
     *          we will split the text in to the individual fields and create a Passenger object with the data
     *
     * @param   titanic     ListArray of strings. All the text from a file
     * @return              return a list of Passengers
     *
     */
    private static List<Passenger> listToPassengers(List<String> titanic) {
        //  the method returns a List of Passengers so we need a variable to hold that data
        List<Passenger> passengers = new ArrayList<>();

        //  the first line in the list is the header line. We want to remove it and not try convert it to a passenger
        titanic.remove(0);

        //  for each line of text in the ListArray
        for (var line : titanic) {
            //  take the text and split on comma to create an array of the individual elements for each passenger
            String[] properties = line.split(",");

            //  convert the text age into a floating point number
            if (properties[1].length()== 0) properties[1] = "0";
            float age = Float.parseFloat(properties[1]);

            //  the name field is the first property in the array the names are like this
            //  Last Name/ First name
            //  if we split on "/" it will give us the first and last names of the passenger without any space padding
            String[] names = properties[0].split("/");

            //                           ﻿Last Name/ First Name,Age,  Class,      Passenger or Crew,    Role,      Survivor
            Passenger pax = new Passenger(names[0], names[1], age, properties[2], properties[3], properties[4], properties[5].equals("1"));
            //  add the passenger to the output variable
            passengers.add(pax);
        }                   //  end of the enhanced (for each) loop

        //  return the ListArray of passengers back to the calling code
        return passengers;
    }

    /**
     *      ListToPassengers
     *          The parameter titanic is a ListArray of strings representing passengers on the titanic
     *          This is the format of the text
     *          ﻿Last Name/ First Name, Age, Class, Passenger or Crew, Role, Survivor
     *          we will split the text in to the individual fields and create a Passenger object with the data
     *
     * @param   jobSearch     ListArray of strings. All the text from a file
     * @return              return a list of Passengers
     *
     */
    private static List<Job> listToJobs(List<String> jobSearch) {
        //  the method returns a List of Passengers so we need a variable to hold that data
        List<Job> jobs = new ArrayList<>();

        //  the first line in the list is the header line. We want to remove it and not try convert it to a passenger
        jobSearch.remove(0);

        //  for each line of text in the ListArray
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
        for (var line : jobSearch) {
            //  take the text and split on comma to create an array of the individual elements for each passenger
            String[] properties = line.split(",");

            //  convert the text entryId, week and salary into an int
            int entryId = Integer.parseInt(properties[0]);
            int week = Integer.parseInt(properties[3]);
            int salary = Integer.parseInt(properties[5]);

            //  conver the date 9-Oct to a LocalDate object. Append 2020 on to the end of the string
            LocalDate date = LocalDate.parse(properties[2] + "-2020", formatter);

            //              Entry,      Company,      Date, Week,  Location,     Salary,  Position,     Title
            Job job = new Job(entryId, properties[1], date, week, properties[4], salary, properties[6], properties[7]);
            //  add the passenger to the output variable
            jobs.add(job);
        }                   //  end of the enhanced (for each) loop

        //  return the ListArray of passengers back to the calling code
        return jobs;
    }

    /**
     * FileToList
     * We read the entire file into memory. Each line is saved to a String which is added to a ListArray
     *
     * @param from file to be read
     * @return return a list of lines of the file read
     */
    private static List<String> FileToList(String from) {
        //  list is the variable that will hold the file we are reading
        List<String> list = new ArrayList<>();
        String line;

        //  convert the string of the file name in to a File object
        File fin = new File(from);
        try {
            //  Construct BufferedReader from FileReader
            //  The BufferedReader is designed to read line based text from a file
            BufferedReader br = new BufferedReader(new FileReader(fin));

            //  while there is data to be read
            while ((line = br.readLine()) != null) {
                //  add each line to the list
                list.add(line);
            }
            //  close the file
            br.close();
        } catch (IOException e) {
            System.out.println("File IO error on read: " + e);
        }

        //  return the list of text lines
        return list;
    }           //  end of FileToList

}               //  end of the class
