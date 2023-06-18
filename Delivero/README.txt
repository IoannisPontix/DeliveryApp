What Maps Contain: 

categoriesSet -> All categories
addRestaurantMap -> (Restaurant Name , Categories)


addDishesMap <String,String>  ->"Dish+":"+price" , RestaurantName

DishesByPrice <String,List<String>> -> Restaurant + (Dishes)

allDishesForRestaurantMap<String,List<String>> ->Restaurant + (Dishes)

orderMap<String,List<String>> (customerName+":"+restaurantName+":"+deliveryTime+":"+deliveryDistance  ,  Array[] dishName+":"+ QTY )


markRestaurant Name , Value

averageRatingRestaurant  Name , Value

orderPerCategoryMap Category , Number of orders

newCategoryRestaurant Category , Restaurants