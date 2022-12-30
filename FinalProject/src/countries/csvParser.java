package countries;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class csvParser{

    public List<Countries> countriesList = new ArrayList<>();

    private static void Test(){
        String s = "129,170,290";
        String[] val = s.split(",");
        String f = String.join("", val);
        System.out.println(f);
    }

    public csvParser(){
        try (CSVReader reader = new CSVReader(new FileReader("FinalProject\\src\\resources\\Country.csv"))){
            String[] val;
            reader.readNext();

            int k = 0;
            while ((val = reader.readNext()) != null){
                //System.out.println(values[0]);

                Countries countries = new Countries(k, val[0], val[1], val[2], val[3], val[4]);

                countriesList.add(countries);
                k++;
            }
        }
        catch (IOException err){

            err.printStackTrace();

        }
    }

}