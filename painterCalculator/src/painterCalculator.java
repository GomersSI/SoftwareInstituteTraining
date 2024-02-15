import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class painterCalculator{
    private float paintCost = 0f;
    private float paintTotal = 0f;
    private float wallCoverage = 0f;
    private final Hashtable<String, Float> walls = new Hashtable<String, Float>();

    public String getTinsNeeded(){
        if (paintCost == 0){
            return "No Paint Data!";
        }
        float totalArea = totalArea();
        float totalCoverage = totalArea / wallCoverage;
        double tinsNeeded = Math.ceil((totalCoverage * 100)/paintTotal);
        return tinsNeeded + "";
    }
    public void addPaint(){
        paintCost = getPaintCost();
        paintTotal = getPaintTotal();
        wallCoverage = getPaintCoverage();
    }
    public String calculateTotal(){
        if (paintCost == 0){
            return "No Paint Data";
        }
        float totalArea = totalArea();
        float totalCoverage = totalArea / wallCoverage;
        float tinsNeeded = (totalCoverage * 100)/paintTotal;
        float totalCost = tinsNeeded * paintCost;
        return "£"+totalCost;
    }
    public void removeWall(){
        boolean wallFound = false;
        Scanner reader = new Scanner(System.in);
        showWalls();
        System.out.println("What is the name of the wall you would like to remove :");
        String name = reader.next();
        for (Map.Entry wall:walls.entrySet()){
            String currentWall = (String) wall.getKey();
            if (Objects.equals(currentWall, name)){
                walls.remove(name);
                wallFound = true;
            }
        }
        if (wallFound = false){
            System.out.println("No wall with that name could be found.");
        }
    }
    public float totalArea(){
        float totalArea = 0;
        for (Map.Entry wall:walls.entrySet()){
            totalArea += (float) wall.getValue();
        }
        if (totalArea < 0){
            totalArea = 0;
        }
        return totalArea;
    }
    public void addWindowDoor(){
        String name = getWallName();
        String shape = getWallShape();
        float width = getWidth();
        float height = getHeight();
        float area = (Objects.equals(shape, "1")) ? (width * height) : ((width*height)/2);
        walls.put(name, -area);
    }

    public void addWall(){
        String name = getWallName();
        String shape = getWallShape();
        float width = getWidth();
        float height = getHeight();
        float area = (Objects.equals(shape, "1")) ? (width * height) : ((width*height)/2);
        walls.put(name, area);
    }

    public void showWalls(){
        for (Map.Entry wall:walls.entrySet()){
            System.out.println(wall.getKey() + " : " + wall.getValue() + "m^2");
        }
    }
    private float getPaintCost(){
        float priceFloat;
        do {
            Scanner reader = new Scanner(System.in);
            System.out.println("Please enter the cost of your paint :");
            String price = reader.next();
            priceFloat = Float.parseFloat(price);
        }while (checkValue(priceFloat));
        return priceFloat;
    }
    private float getPaintTotal(){
        float totalFloat;
        do {
            Scanner reader = new Scanner(System.in);
            System.out.println("Please total size of your paint can in ml :");
            String total = reader.next();
            totalFloat = Float.parseFloat(total);
        }while (checkValue((float)totalFloat));
        return totalFloat;
    }
    private float getPaintCoverage(){
        float totalCoverage;
        do {
            Scanner reader = new Scanner(System.in);
            System.out.println("Please enter the surface area in m^2 that 100ml of your paint covers : ");
            String input = reader.next();
            totalCoverage = Float.parseFloat(input);
        }while (checkValue((float)totalCoverage));
        return totalCoverage;
    }
    private String getWallName(){
        Scanner reader = new Scanner(System.in);
        System.out.println("Please name your wall, window or door:");
        String name = reader.next();
        for (Map.Entry wall:walls.entrySet()){
            String currentName = (String) wall.getKey();
            if (Objects.equals(currentName, name)){
                System.out.println("Wall, window or door with that name already exists.");
                return getWallName();
            }
        }
        return name;
    }
    private float getHeight(){
        String input = "";
        float inputFloat;
        do{
            Scanner reader = new Scanner(System.in);
            System.out.println("Please define the height of your wall (in meters):");
            input = reader.next();
            try {
                inputFloat = Float.parseFloat(input);
            }catch(Exception e){
                System.out.println("Invalid data.");
                inputFloat = getHeight();
            }
        }while (checkValue(inputFloat));
        return inputFloat;
    }
    private float getWidth(){
        String input = "";
        float inputLong = -1L;
        do{
            Scanner reader = new Scanner(System.in);
            System.out.println("Please define the width of your wall (in meters):");
            input = reader.next();
            try {
                inputLong = Float.parseFloat(input);
            }catch(Exception e){
                System.out.println("Invalid data.");
                inputLong = getWidth();
            }
        }while (checkValue(inputLong));
        return inputLong;
    }
    private String getWallShape(){
        String input = "";
        do{
            Scanner reader = new Scanner(System.in);
            System.out.println("Please define the shape of your wall:\n1: Square\n2: Triangle");
            input = reader.next();
        }while (checkWallType(input));
        return input;
    }
    private boolean checkValue(float input){
        return !(input > 0L);
    }
    private boolean checkWallType(String input) {
        boolean output;
        switch (input) {
            case "1":
                output = false;
                break;
            case "2":
                output = false;
                break;
            default:
                output = true;
                break;
        }
        return output;
    }
}