package delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class Delivery {
	// R1

	// Maps for R1
	SortedSet<String> categoriesSet = new TreeSet<String>();
	SortedMap<String, String> addRestaurantMap = new TreeMap<String, String>();

	// Maps for R2
	SortedMap<String, String> addDishesMap = new TreeMap<String, String>();
	SortedMap<String, List<String>> dishesByPriceMap = new TreeMap<String, List<String>>();
	SortedMap<String, List<String>> allDishesForRestaurantMap = new TreeMap<String, List<String>>();

	// Maps for R3

	SortedMap<String, List<String>> orderMap = new TreeMap<String, List<String>>();

	// Maps for R4

	SortedMap<String, List<Integer>> markRestaurant = new TreeMap<String, List<Integer>>();
	SortedMap<String, Integer> averageRatingRestaurant = new TreeMap<String, Integer>();

	// Maps for R5

	SortedMap<String, Long> orderPerCategoryMap = new TreeMap<String, Long>();

	SortedMap<String, List<String>> newCategoryRestaurant = new TreeMap<String, List<String>>();

	SortedMap<Integer, String> avgRatingReversedMap = new TreeMap<Integer, String>();

	/**
	 * adds one category to the list of categories managed by the service.
	 * 
	 * @param category name of the category
	 * @throws DeliveryException if the category is already available.
	 */
	public void addCategory(String category) throws DeliveryException {

		if (categoriesSet.contains(category)) {
			throw new DeliveryException("This catergory already exists");
		}
		categoriesSet.add(category);
	}

	/**
	 * retrieves the list of defined categories.
	 * 
	 * @return list of category names
	 */
	public List<String> getCategories() {
		return new ArrayList<>(categoriesSet);
	}

	/**
	 * register a new restaurant to the service with a related category
	 * 
	 * @param name     name of the restaurant
	 * @param category category of the restaurant
	 * @throws DeliveryException if the category is not defined.
	 */
	public void addRestaurant(String name, String category) throws DeliveryException {

		if (!categoriesSet.contains(category)) {
			throw new DeliveryException("This category does not exist.");
		}

		addRestaurantMap.put(name, category);

	}

	/**
	 * retrieves an ordered list by name of the restaurants of a given category. It
	 * returns an empty list in there are no restaurants in the selected category or
	 * the category does not exist.
	 * 
	 * @param category name of the category
	 * @return sorted list of restaurant names
	 */
	public List<String> getRestaurantsForCategory(String category) {

		ArrayList<String> restaurantsInCategory = new ArrayList<String>();

		for (String k : addRestaurantMap.keySet()) {

			if (addRestaurantMap.get(k).equals(category)) {
				restaurantsInCategory.add(k);
			}

		}

		return restaurantsInCategory;
	}

	// R2

	/**
	 * adds a dish to the list of dishes of a restaurant. Every dish has a given
	 * price.
	 * 
	 * @param name           name of the dish
	 * @param restaurantName name of the restaurant
	 * @param price          price of the dish
	 * @throws DeliveryException if the dish name already exists
	 */
	public void addDish(String name, String restaurantName, float price) throws DeliveryException {

		// addDishesMap

		for (String key : addDishesMap.keySet()) {
			String str_list[] = key.split(":");

			String dishName = str_list[0];
			float priceTaken = Float.parseFloat(str_list[1]);

			if (dishName.equals(name) && addDishesMap.get(key).equals(restaurantName)) {

				throw new DeliveryException("Dish " + name + " already exists in " + restaurantName);
			}

		}
		System.out.println("tests:");
		System.out.println(name + " " + restaurantName + " " + price);
		addDishesMap.put(name + ":" + price, restaurantName);

	}

	/**
	 * returns a map associating the name of each restaurant with the list of dish
	 * names whose price is in the provided range of price (limits included). If the
	 * restaurant has no dishes in the range, it does not appear in the map.
	 * 
	 * @param minPrice minimum price (included)
	 * @param maxPrice maximum price (included)
	 * @return map restaurant -> dishes
	 */
	public Map<String, List<String>> getDishesByPrice(float minPrice, float maxPrice) {
		// dishesByPriceMap(Restaurant Name , Dishes List)

		for (String key : addDishesMap.keySet()) {
			// (dish+pprice)

			String str_list[] = key.split(":");

			String dishName = str_list[0];
			float priceTaken = Float.parseFloat(str_list[1]);
			String restaurantName = addDishesMap.get(key);

			if (priceTaken >= minPrice && priceTaken <= maxPrice) {

				if (!dishesByPriceMap.containsKey(restaurantName)) {
					ArrayList<String> ar1 = new ArrayList<String>();
					ar1.add(dishName);
					dishesByPriceMap.put(restaurantName, ar1);
				} else {
					if (!dishesByPriceMap.get(restaurantName).contains(dishName)) {
						dishesByPriceMap.get(restaurantName).add(dishName);
					}

				}

			}

		}

		System.out.println("map: " + dishesByPriceMap);
		return dishesByPriceMap;
	}

	/**
	 * retrieve the ordered list of the names of dishes sold by a restaurant. If the
	 * restaurant does not exist or does not sell any dishes the method must return
	 * an empty list.
	 * 
	 * @param restaurantName name of the restaurant
	 * @return alphabetically sorted list of dish names
	 */
	public List<String> getDishesForRestaurant(String restaurantName) {

		// lista gia return adeia
		ArrayList<String> toReturn = new ArrayList<String>();

		// vazo sto map ta panta kai ta sortarei
		for (String key : addDishesMap.keySet()) {
			// (dish+price)

			String str_list[] = key.split(":");

			String dishName = str_list[0];
			float priceTaken = Float.parseFloat(str_list[1]);
			String restauName = addDishesMap.get(key);

			// allDishesForRestaurantMap

			if (!allDishesForRestaurantMap.containsKey(restauName)) {
				ArrayList<String> ar2 = new ArrayList<String>();
				ar2.add(dishName);
				allDishesForRestaurantMap.put(restauName, ar2);
			} else if (allDishesForRestaurantMap.containsKey(restauName)
					&& !allDishesForRestaurantMap.get(restauName).contains(dishName)) {
				allDishesForRestaurantMap.get(restauName).add(dishName);
			}

		}
		// toReturn

//        return toReturn;
		if (allDishesForRestaurantMap.containsKey(restaurantName)) {
			return allDishesForRestaurantMap.get(restaurantName);
		} else {
			return toReturn;
		}
	}

	/**
	 * retrieves the list of all dishes sold by all restaurants belonging to the
	 * given category. If the category is not defined or there are no dishes in the
	 * category the method must return and empty list.
	 * 
	 * @param category the category
	 * @return
	 */
	public List<String> getDishesByCategory(String category) {

		SortedSet<String> returnDish = new TreeSet<String>();

		if (!addRestaurantMap.containsValue(category)) {
			return new ArrayList<>();
		}

		for (String key : addRestaurantMap.keySet()) {

			if (addRestaurantMap.get(key).equals(category)) {

				for (String dishKey : addDishesMap.keySet()) {

					if (addDishesMap.get(dishKey).equals(key)) {

						String str_list[] = dishKey.split(":");

						String dishName = str_list[0];
						float priceTaken = Float.parseFloat(str_list[1]);
						returnDish.add(dishName);

					}
				}

			}
		}

		return new ArrayList<>(returnDish);
	}

	// R3

	/**
	 * creates a delivery order. Each order may contain more than one product with
	 * the related quantity. The delivery time is indicated with a number in the
	 * range 8 to 23. The delivery distance is expressed in kilometers. Each order
	 * is assigned a progressive order ID, the first order has number 1.
	 * 
	 * @param dishNames        names of the dishes
	 * @param quantities       relative quantity of dishes
	 * @param customerName     name of the customer
	 * @param restaurantName   name of the restaurant
	 * @param deliveryTime     time of delivery (8-23)
	 * @param deliveryDistance distance of delivery
	 * 
	 * @return order ID
	 */
	public int addOrder(String dishNames[], int quantities[], String customerName, String restaurantName,
			int deliveryTime, int deliveryDistance) {
		// orderMap

		ArrayList<String> ar1 = new ArrayList<String>();

		for (int i = 0; i < dishNames.length; ++i) {

			ar1.add(dishNames[i] + ":" + quantities[i]);

		}

		orderMap.put(customerName + ":" + restaurantName + ":" + deliveryTime + ":" + deliveryDistance, ar1);

		return orderMap.size();
	}

	/**
	 * retrieves the IDs of the orders that satisfy the given constraints. Only the
	 * first {@code maxOrders} (according to the orders arrival time) are returned
	 * they must be scheduled to be delivered at {@code deliveryTime} whose
	 * {@code deliveryDistance} is lower or equal that {@code maxDistance}. Once
	 * returned by the method the orders must be marked as assigned so that they
	 * will not be considered if the method is called again. The method returns an
	 * empty list if there are no orders (not yet assigned) that meet the
	 * requirements.
	 * 
	 * @param deliveryTime required time of delivery
	 * @param maxDistance  maximum delivery distance
	 * @param maxOrders    maximum number of orders to retrieve
	 * @return list of order IDs
	 */
	public List<Integer> scheduleDelivery(int deliveryTime, int maxDistance, int maxOrders) {
		int order_num = 0;

		ArrayList<Integer> numOrder = new ArrayList<Integer>();

		ArrayList<String> toRemove = new ArrayList<String>();

		for (String key : orderMap.keySet()) {
			order_num++;

			String str_key[] = key.split(":");
			String customerName = str_key[0];
			String restaurantName = str_key[1];
			Integer delTime = Integer.parseInt(str_key[2]);
			Integer distDel = Integer.parseInt(str_key[3]);

			if (delTime == deliveryTime && distDel <= maxDistance) {

				if (numOrder.size() < maxOrders) {
					numOrder.add(order_num);

					toRemove.add(key);

				}

			}

		}

		for (String key : toRemove) {

			orderMap.remove(key);
		}

		return numOrder;
	}

	/**
	 * retrieves the number of orders that still need to be assigned
	 * 
	 * @return the unassigned orders count
	 */
	public int getPendingOrders() {
		return orderMap.size();
	}

	// R4
	/**
	 * records a rating (a number between 0 and 5) of a restaurant. Ratings outside
	 * the valid range are discarded.
	 * 
	 * @param restaurantName name of the restaurant
	 * @param rating         rating
	 */
	public void setRatingForRestaurant(String restaurantName, int rating) {

		if (rating >= 0 && rating <= 5) {
			if (markRestaurant.containsKey(restaurantName)) {
				markRestaurant.get(restaurantName).add(rating);
			} else {
				ArrayList<Integer> ar1 = new ArrayList<Integer>();
				ar1.add(rating);
				markRestaurant.put(restaurantName, ar1);

			}

		}

	}

	/**
	 * retrieves the ordered list of restaurant.
	 * 
	 * The restaurant must be ordered by decreasing average rating. The average
	 * rating of a restaurant is the sum of all rating divided by the number of
	 * ratings.
	 * 
	 * @return ordered list of restaurant names
	 */
	public List<String> restaurantsAverageRating() {
		// averageRatingRestaurant

		for (String key : markRestaurant.keySet()) {
			int suma = 0;
			int avg = 0;
			for (int i = 0; i < markRestaurant.get(key).size(); ++i) {
				suma = suma + markRestaurant.get(key).get(i);

			}

			if (markRestaurant.get(key).size() > 0) {
				avg = suma / markRestaurant.get(key).size();

				averageRatingRestaurant.put(key, avg);

			}

		}
		SortedSet<Integer> sortedSet = new TreeSet<>();

		sortedSet.addAll(averageRatingRestaurant.values());
		List<Integer> sortedList = new ArrayList<>(sortedSet);
		ArrayList<String> toReturn = new ArrayList<String>();

		for (int i = sortedList.size() - 1; i >= 0; i--) {
			for (String rest : averageRatingRestaurant.keySet()) {
				if (sortedList.get(i) == averageRatingRestaurant.get(rest)) {
					toReturn.add(rest);
				}
			}
		}

		return toReturn;
	}

	// R5
	/**
	 * returns a map associating each category to the number of orders placed to any
	 * restaurant in that category. Also categories whose restaurants have not
	 * received any order must be included in the result.
	 * 
	 * @return map category -> order count
	 */
	public Map<String, Long> ordersPerCategory() {

		// orderPerCategoryMap teliko gia return
		// newCategoryRestaurant na to gemiso (category , restaurants)

//						(rest name ,categ);
		for (String key : addRestaurantMap.keySet()) {

			if (!newCategoryRestaurant.containsKey(addRestaurantMap.get(key))) {

				ArrayList<String> ar1 = new ArrayList<String>();
				ar1.add(key);

				newCategoryRestaurant.put(addRestaurantMap.get(key), ar1);

			} else {

				newCategoryRestaurant.get(addRestaurantMap.get(key)).add(key);

			}
		}

		for (String key : orderMap.keySet()) {

			String str_key[] = key.split(":");
			String customerName = str_key[0];
			String restaurantName = str_key[1];
			Integer delTime = Integer.parseInt(str_key[2]);
			Integer distDel = Integer.parseInt(str_key[3]);

			for (String categ : newCategoryRestaurant.keySet()) {

				if (newCategoryRestaurant.get(categ).contains(restaurantName)) {

					if (!orderPerCategoryMap.containsKey(categ)) {
						orderPerCategoryMap.put(categ, (long) 1);
					} else {
						long z = orderPerCategoryMap.get(categ);
						z++;
						orderPerCategoryMap.put(categ, z);
					}

				}

			}

		}

		return orderPerCategoryMap;
	}

	/**
	 * retrieves the name of the restaurant that has received the higher average
	 * rating.
	 * 
	 * @return restaurant name
	 */
	Scanner scan = new Scanner(System.in);
	List<String> list = new ArrayList<String>();

	public String bestRestaurant() {

		int highCount = 0;
		String highRestaurant = "";

		restaurantsAverageRating();
		System.out.println("best: " + averageRatingRestaurant);

		for (String name : averageRatingRestaurant.keySet()) {

			if (averageRatingRestaurant.get(name) > highCount) {
				highCount = averageRatingRestaurant.get(name);

				list.add(name);
				highRestaurant = name;

			}

		}

//		for(String name: averageRatingRestaurant.keySet()) {
//			
//			avgRatingReversedMap.put(averageRatingRestaurant.get(name), name);
//		
//			 
//		}

//		for(Integer in : avgRatingReversedMap.keySet()) {
//			
//			if(avgRatingReversedMap.containsKey(highCount)) {
//				
//			}
//		}
//	

//	return list.get(list.size()-1);
		return highRestaurant;

	}
}
