<h1>Ecommerce website</h1>

<h2>https://web-barclays-ecommerce.herokuapp.com/</h2>
<h2>What it does</h2>


<p>It is an web applciation, it let allow the user to login into the application. User can view list of product that available and user can order products, add and remove products from the cart. User can make payment by any of the following, net banking, debit card and credit card. The payment option handelled by the third party payment gateway provider Instamojo. After payment order will be created in order table to allow the users to view and track them. </p>

----
<h2>Solution Description</h2>


  - Ecommerce application have functional capable of listing the product in proper pagination, let the user to search the product by using the name and user can view the products by sorting of the rating. (low to high and high to low).
  - Application allow the user to add the one or more products to their cart and user can order them either from home page or cart page. Adding or removing products will create or delete a record in cart table against the user.
  - The backend API are exposed to consume and have with any other frontend UI.
  
----
<h2>API Exposed from Ecommerce Microservice</h2>

| Method | Operation Id | URL | Description |  
|:-----------|:-----------|:-----------|:-----------|
| POST | createUser | /barclays/user | Create a user |  
| GET | logIn | /barclays/logIn/{emailId} | Validate user emailId for log in |
| GET | getProduct | /barclays/product/{productId} | Fetch the products from product table.</br>API Will return all the products by passing All in param |  
| POST | addProductInCart | /barclays/cart | Add the product to cart, userId and productId should pass in request body |
| GET | getProductFromCart | barclays/cart/all/{userId} | Fetch products from the cart for particular userId |  
| POST | createOrder | /barclays/payment | API helps to make payment and create a record in order table |
| DELETE | removeProductFromCart | /barclays/cart/{productId}/{userId} | Delete a product from cart based on userId and productId |  

----
<h2>Languages & Frameworks</h2> 

1. Backend
- Java
- Spring Boot
- MongoDB
2. FrontEnd
- Angular 10
- [UI Repository](https://github.com/subishsubash/ecommerce-ui)

----
```
docker pull mongo
docker pull docker pull subash12396/ecommerce:ecommerce
docker run --name mongodb -d mongo
docker run -p 8080:8080 --link mongodb:mongo --name ecommerce-container  -d subash12396/ecommerce:ecommerce
```

<h2>Testing</h2>

>Microservice APIs

 ```
 http://192.168.99.100:8080
 ```

 >Note: Replace the IP with docker enviornment
 

