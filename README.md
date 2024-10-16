# food_delivery_system

1. Restaurants should be able to register themselves on the system.
2. Restaurants should be able to update their menu.
3. Customers should be able to place an order by giving multiple items and quantity details. Ignore
cart management and payment processing for simplicity.
a. Customer here just inputs the item and its quantity,the system depending on the restaurant
selection strategy should select and place the order accordingly
a. Items of the given quantity can be served from multiple restaurants.
4. Restaurants should not breach their processing capacity.
5. The restaurant selection strategy should be configurable.
6. Implement the lower cost restaurant selection strategy.
7. Concurrency must be taken care of and demoed as well during the evaluation process.

Adding curls for this application

Register Restaurant request

curl --location 'http://localhost:8080/api/restaurants' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Test Restaurant",
    "capacity": 100,
    "rating": 4.5
}'


Get Restaurant by ID request

curl --location 'http://localhost:8080/api/restaurants/1' \
--data ''
