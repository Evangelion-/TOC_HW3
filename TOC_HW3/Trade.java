public class Trade {
		public String address;
		int date, price;
		
		public Trade()
		{}
		
		public Trade(String address, int date, int price)
		{
			this.address = address;
			this.date = date;
			this.price = price;
		}
	}