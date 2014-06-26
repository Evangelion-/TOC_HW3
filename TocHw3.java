import org.json.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class TocHw3 {

	public static void main(String[] args) throws Exception {
        String web = args[0];
        String section = args[1];
        String road = args[2];
        int year = Integer.parseInt(args[3]);
        int culPrice = 0;
        int countPrice = 0;
        
        readURL(web);
        File f = new File("URL.txt");
		FileReader ff = new FileReader(f);
		JSONArray jsonArray = new JSONArray(new JSONTokener(ff));
		ff.close();
		
		Map<String, List<Trade> > info = new HashMap<String, List<Trade> >();
		
		for (int i = 0; i < jsonArray.length(); i++)
		{    
            JSONObject jsonItem = jsonArray.getJSONObject(i);
            String address = jsonItem.getString("土地區段位置或建物區門牌");
            int date = jsonItem.getInt("交易年月");
            int price = jsonItem.getInt("總價元");
            Trade tmpT = new Trade(address, date, price);
            
            if(!info.containsKey(jsonItem.getString("鄉鎮市區")))
            {
            	List<Trade> tmpL = new LinkedList<Trade>();
            	info.put(jsonItem.getString("鄉鎮市區"), tmpL);
            }
            else info.get(jsonItem.getString("鄉鎮市區")).add(tmpT);
        }
		Iterator<Trade> it = info.get(section).iterator();
		while (it.hasNext())
		{
			Trade tmpT = it.next();
			if(tmpT.address.contains(road) && (tmpT.date > (year * 100)))
			{
				culPrice += tmpT.price;
				countPrice++;
			}
		}
		System.out.println(culPrice/countPrice);
    }
	
	public static void readURL( String strURL ) {
        try {
            String line = null;

            URL url = new URL(strURL);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            uc.connect();
            InputStream is = uc.getInputStream();
            
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8")); 
            BufferedWriter bw = new BufferedWriter(new FileWriter("URL.txt", false));    
            
            while ((line = br.readLine()) != null) {
                bw.write(line);            
            }
            br.close();
            bw.close();
        }
        catch(Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
