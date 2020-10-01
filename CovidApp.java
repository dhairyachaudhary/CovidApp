/**
 *    AP Assignment 1 (COVID-19 Command Line Application)
 *    Name: Dhairya Chaudhary
 *    Roll No.: 2019035
 */

import java.util.ArrayList;
import java.util.Scanner;

class Patient{
    /**
     *  This class represents a patient present in the camp or in a health institute.
     *
     *  ATTRIBUTES:
     *  name- Name of the patient [Type: String]. We use final keyword since the name of the patient is constant.
     *  age- Age of the patient at the time of registration [Type: Integer]. This is final since this is the age at the time of registration.
     *  oxygenLevel- The oxygen level measured at the time of registration [Type: Integer]. This is final since it is measured only at the time of registration.
     *  bodyTemperature- The body temperature of the patient at the time of registration [Type: Float]. This is final since it is measured only at the time of registration.
     *  admissionStatus- This tells us whether or not a patient has been admitted to an institute [Type: Boolean]. It is initially set to false, but is changed to true when the patient gets admitted.
     *  recoveryDays- The number of days needed for the patient to recover [Type: Integer]. This is taken as input from the user after a hospital has been assigned.
     *  institute- The institute a patient is admitted to [Type: String]. This is initialized as null string and later modified.
     *  regID- This is the unique registration ID assigned to each patient [Type: Integer]. Subsequent patients get reg IDs differing by 1.
     *  regCounter- Used to implement the unique regID [Type: Integer]. This is static as it is shared by all the objects of the class. It increments by 1 in the initialization block whenever a new patient is added.
     *
     *  METHODS:
     *  Includes accessors for all the attributes and mutators for all the modifiable attributes. These are used to implement the required functionalities using the Camp and HealthCoordinator classes. Also includes the constructor.
     */

    private final String name;
    private final int age;
    private final int oxygenLevel;
    private final float bodyTemperature;
    private boolean admissionStatus;
    private int recoveryDays;
    private String institute="";

    private final int regID;
    private static int regCounter;

    { //Initialization block
        this.regID=++regCounter;
    }

    public Patient(String name, float bodyTemperature, int oxygenLevel, int age){
        //Class Constructor
        this.name=name;
        this.bodyTemperature=bodyTemperature;
        this.oxygenLevel=oxygenLevel;
        this.age=age;
    }

    //Accessors
    public String getName(){ return this.name; }

    public int getAge(){ return this.age; }

    public int getOxygenLevel(){ return this.oxygenLevel; }

    public float getBodyTemperature(){ return this.bodyTemperature; }

    public int getRecoveryDays(){ return this.recoveryDays; }

    public boolean getAdmissionStatus(){ return this.admissionStatus; }

    public int getRegID(){ return this.regID; }

    public String getInstitute(){ return this.institute; }

    //Mutators
    public void setRecoveryDays(int days){ this.recoveryDays=days; }

    public void setAdmissionStatus(boolean currentState){ this.admissionStatus=currentState; }

    public void setInstitute(String newInstitute){ this.institute=newInstitute; }
}

class Camp {
    /**
     *  This class represents the health camp which contains the patients and is managed by the health coordinator
     *
     *  ATTRIBUTES:
     * patients- This is all the patients registered with the camp [Type: arraylist of patients]/
     *
     *  METHODS:
     *  newEntry(String name, float bodyTemperature, int oxygenLevel, int age)- Adds a new patient to the camp
     *  getAti(int i)- Returns the ith element of patients list
     *  getNumberOfPatients()- returns the number of patients registered with the camp
     *  getDetails(int id)- Prints the details of patient with specified Registration ID
     *  allAdmitted()- Returns a boolean value denoting whether or not all patients have been admitted
     *  display()- Displays details of all patients
     *  showNowAdmitted()- Prints the number of patients not admitted anywhere
     *  deleteAccount(int i)- Deletes the account of the patient at given index in the list
     *  delete(int id)- Deletes the account of the patient with specified Regitrastion ID
     */

    private static ArrayList<Patient> patients=new ArrayList<Patient>();

    public static void newEntry(String name, float bodyTemperature, int oxygenLevel, int age){
        //Registers new patient
        Patient a=new Patient(name, bodyTemperature,oxygenLevel, age);
        a.setAdmissionStatus(false);
        patients.add(a);
    }

    public static Patient getAti(int i){ return patients.get(i); }

    public static int getNumberOfPatients(){
        //Returns number of patients registered currently
        return patients.size();
    }

    public void getDetails(int id){
        /*
        Prints the details of the patient with the specified ID
         */
        int i=0;
        while (i!=patients.size()){
            if (patients.get(i).getRegID()==id){
                System.out.println(patients.get(i).getName());
                System.out.println("Temperature is "+ patients.get(i).getBodyTemperature());
                System.out.println("Oxygen levels is "+ patients.get(i).getOxygenLevel());

                if (patients.get(i).getAdmissionStatus()){
                    System.out.println("Admission Status – Admitted");
                } else {
                    System.out.println("Admission Status – Not Admitted");
                }

                System.out.println("Admitting Institute - "+patients.get(i).getInstitute());
            }
            i++;
        }
    }

    public static boolean allAdmitted(){
        /*
        Returns true if all patients have been admitted, otherwise returns false
         */

        int i=0;
        while (i!=patients.size()){
            if (patients.get(i).getAdmissionStatus()==false){
                return false;
            }
            i++;
        }
        return true;
    }

    public void display(){
        /*
        Prints name and Registration ID of all the registered patients
         */

        int i=0;
        while (i!=patients.size()){
            System.out.println(patients.get(i).getRegID()+" "+patients.get(i).getName());
            i++;
        }
    }

    public void showNotAdmitted(){
        /*
        Prints the number of patients who have not been admitted anywhere
         */

        int i=0;
        int count=0;
        while (i!=patients.size()){
            if (patients.get(i).getAdmissionStatus()==false){
                count++;
            }
            i++;
        }
        System.out.println(count+ " patients");
    }

    public static void deleteAccount(int i){ patients.remove(i); }

    public static void delete(int id){
        /*
        Deletes account with the registration ID passed as argument
         */

        int i=0;
        while (i!=patients.size()){
            if (patients.get(i).getRegID()==id){
                patients.remove(i);
                break;
            }
            i++;
        }
    }
}

class HealthCoordinator{
    /**
     *  This class represents the health coordinator of the camp. The coordinator manages the date of the camp and assigns patients institutes.
     *
     *  ATTRIBUTES:
     * institutes- A list of all the institutes that a patient can be sent to.
     *
     *  METHODS:
     *  addNewHealthcareInstitute(String name, float temperatureCriteria, int oxygenLevels, int availableBeds)- calls the constructor for the new health institute, initializes it and adds it to the list of available institutes.
     *  addPatients()- A helper function for the addNewHealthcareInstitute function. This assigns patients that can be cured by that hospital to it by checking both the independent criteria in turn. This is private as it is called by another class member.
     *  removeAdmitted()- Removes the admitted patients from the patient list of the camp.
     *  removeClosed()- Removes the institutes that have been filled from the institutes list.
     *  getOpenInstitutes()- Prints the number of institutes that are accepting patients
     *  provideInstituteInfo(String name)- Calls the display info function of the hospital class for the required member.
     *  provideInstitutePatientInfo(String name)- Calls the displayInstitutePatientInfo function of the hospital class for the required member
     */

    private ArrayList<HealthCareInstitute> institutes = new ArrayList<HealthCareInstitute>();
    Scanner input=new Scanner(System.in);

    public void addNewHealthcareInstitute(String name, float temperatureCriteria, int oxygenLevels, int availableBeds){
        //Sets up the new institute: calls the constructor, sets the status, adds it to the list of all institutes and adds patients to it
        HealthCareInstitute h=new HealthCareInstitute(name, temperatureCriteria, oxygenLevels, availableBeds);

        if (h.getAvailableBeds()>0){
            h.setStatus(true);
        }

        institutes.add(h);
        addPatients();
    }

    private void addPatients(){
        /*
        Helper function for addNewHealthcareInstitute(String name, float temperatureCriteria, int oxygenLevels, int availableBeds)
        Adds suitable patients to the newly registered institute
         */

        HealthCareInstitute h=institutes.get(institutes.size()-1);
        //Prints the details of the new institute
        h.display();

        int i=0;
        while (i!=Camp.getNumberOfPatients() && !Camp.allAdmitted()){
            //We first admit patients on the basis of their oxygen level
            Patient x=Camp.getAti(i);
            if (x.getOxygenLevel()>=h.getOxygenLevels() && h.getAvailableBeds()>0 && !x.getAdmissionStatus()){
                x.setAdmissionStatus(true);
                x.setInstitute(h.getName());
                h.setAvailableBeds(h.getAvailableBeds()-1);
                h.getList().add(x);
                //Taking the recovery days as input
                System.out.print("Recovery days for admitted patient ID "+x.getRegID()+" - ");
                x.setRecoveryDays(input.nextInt());
            }
            i++;
        }

        if (h.getAvailableBeds()==0){
            //Checking if the hospital is full
            h.setStatus(false);
        }

        if (h.getStatus()==true && !Camp.allAdmitted()){
            //Admitting on the basis of body temperature
            int j=0;
            while (j!=Camp.getNumberOfPatients()){
                Patient x=Camp.getAti(j);
                if (x.getBodyTemperature()<=h.getTemperatureCriteria() && h.getAvailableBeds()>0 && !x.getAdmissionStatus()){
                    x.setAdmissionStatus(true);
                    h.setAvailableBeds(h.getAvailableBeds()-1);
                    h.getList().add(x);
                    System.out.print("Recovery days for admitted patient ID "+x.getRegID()+" - ");
                    x.setRecoveryDays(input.nextInt());
                }
                j++;
            }

            if (h.getAvailableBeds()==0){
                //Checking if the hospital is full
                h.setStatus(false);
            }
        }
    }

    public void removeAdmitted(){
        /*
        Removes all the admitted patients from the list. The admitted accounts are deleted from the camp class.
         */

        System.out.println("Account ID removed of admitted patients");

        int i=0;
        while (i!=Camp.getNumberOfPatients()){
            if (Camp.getAti(i).getAdmissionStatus()==true){
                System.out.println(Camp.getAti(i).getRegID());
                Camp.deleteAccount(i);
                i--;
            }
            i++;
        }
    }

    public void removeClosed(){
        /*
        Removes the closed institutes from the institutes and list and deletes the accounts of the patients admitted in these from the camp class
         */

        System.out.println("Accounts removed of Institute whose admission is closed");

        int i=0;
        while (i!=institutes.size()){
            if (institutes.get(i).getStatus()==false){
                int j=0;
                while (j!=institutes.get(i).getList().size()){
                    int id=institutes.get(i).getPatient(j).getRegID();
                    Camp.delete(id);
                    j++;
                }
                System.out.println(institutes.get(i).getName());
                institutes.remove(i);
                i--;
            }
            i++;
        }
    }

    public void getOpenInstitutes(){
        /*
        Prints the number of institutes that have empty beds
         */

        int i=0;
        int count=0;
        while (i!=institutes.size()) {
            if (institutes.get(i).getStatus()) {
                count++;
            }
            i++;
        }
        System.out.println(count+" institutes are admitting patients currently");
    }

    public void provideInstituteInfo(String name){
        /*
        Calls the InstituteInformationDisplay function from the health institute class for the institute of given name
         */

        int i=0;
        while (i!=institutes.size()){
            if (institutes.get(i).getName().equals(name)){
                institutes.get(i).display();
                break;
            }
            i++;
        }
    }

    public void provideInstitutePatientInfo(String name){
        /*
        Calls the InstitutePatientInformationDisplay function from the health institute class for the institute of given name
         */

        int i=0;
        while (i!=institutes.size()){
            if (institutes.get(i).getName().equals(name)){
                institutes.get(i).displayPatient();
                break;
            }
            i++;
        }
    }
}

class HealthCareInstitute{
    /**
     *  This class represents a health care institute that is registered with the application.
     *
     *  ATTRIBUTES:
     *  name- This is the name of the institute [Type: String]. This is final as the name will stay constant.
     *  temperatureCriteria- The maximum temperature accepted by the institute [Type: Float]. This is final as it is given capability.
     *  oxygenLevels- The minimum oxygen level at which a patient is admitted [Type: Integer]. This is final as it is given capability.
     *  availableBeds- The number of unoccupied beds in the hospital [Type: Integer]. As new patients are added, this is decreased.
     *  HealthcareAccountStatus- This tells us whether or not the hospital is accepting patients [Type: Boolean]. This is initialized to true, but set to false if the hospital has zero beds.
     *  admittedPatients- A list of all the patients admitted to the institute [Type: Arraylist of Patients].
     *
     *  METHODS:
     *  Contains accessors for all parameters and mutators for the ones whose values can be modified.
     *  Contains the class constructor
     *  display()- Prints information about the institute
     *  displayPatient()- Print information about the patients present in the institute.
     */

    private final String name;
    private final float temperatureCriteria;
    private final int oxygenLevels;
    private int availableBeds;
    private boolean HealthcareAccountStatus=true;
    private ArrayList<Patient> admittedPatients = new ArrayList<Patient>();

    public HealthCareInstitute(String name, float temperatureCriteria, int oxygenLevels, int availableBeds){
        this.name=name;
        this.temperatureCriteria=temperatureCriteria;
        this.oxygenLevels=oxygenLevels;
        this.availableBeds=availableBeds;
    }

    //Accessors
    public String getName(){ return name; }

    public float getTemperatureCriteria(){ return temperatureCriteria; }

    public int getOxygenLevels(){ return oxygenLevels; }

    public int getAvailableBeds(){ return availableBeds; }

    public boolean getStatus(){ return HealthcareAccountStatus; }

    public ArrayList getList(){ return this.admittedPatients; }

    public Patient getPatient(int i){ return admittedPatients.get(i); }

    //Mutators

    public void setAvailableBeds(int beds){ this.availableBeds=beds; }

    public void setStatus(boolean newStatus){ this.HealthcareAccountStatus=newStatus; }

    public void display(){
        /*
        Print institute details, that is name, temperature criteria, oxygen levels, available beds and whether or not it is open to new patients.
         */
        System.out.println(this.name);
        System.out.println("Temperature should be <= "+this.temperatureCriteria);
        System.out.println("Oxygen levels should be >= "+this.oxygenLevels);
        System.out.println("Number of Available beds – "+this.availableBeds);

        if (this.HealthcareAccountStatus){
            System.out.println("Admission Status – OPEN");
        } else {
            System.out.println("Admission Status – CLOSED");
        }
    }

    public void displayPatient() {
        /*
        Prints the name and recovery date of all the patients
         */
        int i=0;
        while (i!=this.admittedPatients.size()){
            System.out.println(admittedPatients.get(i).getName()+", recovery time is "+admittedPatients.get(i).getRecoveryDays()+" days");
            i++;
        }
    }
}

public class CovidApp {
    /**
     *  This is the class used to call the main method. The user of the program will be interacting with this class.     *
     */

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Camp consideredCamp = new Camp();
        HealthCoordinator coordinator = new HealthCoordinator();

        int numberOfPatients = input.nextInt();

        while (numberOfPatients != 0) {
            consideredCamp.newEntry(input.next(), input.nextFloat(), input.nextInt(), input.nextInt());
            numberOfPatients--;
        }

        while (!consideredCamp.allAdmitted()) {
            int queryNumber = input.nextInt();

            if (queryNumber == 1) {
                //Add new healthcare institute
                String name = input.next();
                System.out.print("Temperature Criteria – ");
                float temperatureCriteria = input.nextFloat();
                System.out.print("Oxygen Levels – ");
                int OxygenLevels = input.nextInt();
                System.out.print("Number of Available beds – ");
                int beds = input.nextInt();
                coordinator.addNewHealthcareInstitute(name, temperatureCriteria, OxygenLevels, beds);
            }

            if (queryNumber == 2) {
                //Removes the accounts of patients who have been admitted
                coordinator.removeAdmitted();
            }

            if (queryNumber == 3) {
                //Removes accounts of healthcare institutes that have been closed
                coordinator.removeClosed();
            }

            if (queryNumber == 4) {
                //Displays the number of patients who are still in the camp
                consideredCamp.showNotAdmitted();
            }

            if (queryNumber == 5) {
                //Displays the number of healthcare institutes with spare beds
                coordinator.getOpenInstitutes();
            }

            if (queryNumber == 6) {
                //Displays the details of an institute
                String name = input.next();
                coordinator.provideInstituteInfo(name);
            }

            if (queryNumber == 7) {
                //Displays the details of a patient, given their Registration ID
                int id = input.nextInt();
                consideredCamp.getDetails(id);
            }

            if (queryNumber == 8) {
                //Displays all the patients and their IDs
                consideredCamp.display();
            }

            if (queryNumber == 9) {
                //Displays all the patients in an institute, given it's name, and the time they need for recovery
                String name = input.next();
                coordinator.provideInstitutePatientInfo(name);
            }
        }
    }
}
