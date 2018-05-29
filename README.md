# Shopping Cart Service (REST API)


## Requirements

* Java 8 or Above
* Maven
* GIT

## Installation
```
  > git clone https://github.com/ripsamm004/temp_moo.git
  > cd temp_moo
  > mvn clean install

```

## Run the server
```
 > java -jar target/backend-coding-test-1.0-SNAPSHOT.jar
```

## REST service running on port [4567]

```
 > http://localhost:4567

```

## Service details

```

# Shopping cart service endpoint : /cart

```
Valid cart id can be use for this api [1, 2, 3]
```

* Add item in to shopping cart

# Sample request

URL : http://localhost:4567/cart
Request-Type: PUT
Content-Type: application/json
Request Body :
{
"code":"ABCD",
"quantity":10
}


# Sample response

Content-Type : application/json
Response code : 200
Response Body :
[
    {
        "product": {
            "code": "ABCD",
            "name": "Product-ABCD",
            "price": 1.5
        },
        "quantity": 10
    }
]

# Sample Error response

Content-Type : application/json
Response code : 400

Response Body 01 :
{
    "code": "A0002",
    "message": "PRODUCT CODE NOT CORRECT"
}


Response Body 02:
{
    "code": "A0004",
    "message": "INVALID REQUEST BODY"
}


Response Body 03:
{
    "code": "A0003",
    "message": "REQUEST BODY FORMAT INVALID"
}

Response Body 04:
{
    "code": "A0001",
    "message": "CART NOT FOUND"
}


* Get cart items by cart id

> http://localhost:4567/cart/{id}

# Sample request

Request-Method : GET
URL : http://localhost:4567/cart/1

# Sample response

Content-Type : application/json
Response code : 200
Response Body:
[
    {
        "product": {
            "code": "ABCD",
            "name": "Product-ABCD",
            "price": 1.5
        },
        "quantity": 10
    },
    {
        "product": {
            "code": "UXYZ",
            "name": "Product-UXYZ",
            "price": 5.5
        },
        "quantity": 10
    }
]


* Clear cart items by cart id

> http://localhost:4567/cart/{id}

# Sample request

Request-Method : DELETE
URL : http://localhost:4567/cart/1

# Sample response

Response code : 200

```

