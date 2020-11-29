package com.olx.autos.codechallenge.features.cars.repositories

val carsInputThreeCars = """{
    "results": 100,
    "data": [
    {
        "id": "0c3ab65a-ef43-4",
        "image_url": "https://storage.googleapis.com/code-challenge/images/cayenne.jpg",
        "make": {
        "key": "porsche",
        "label": "Porsche"
    },
        "mileage": 18051,
        "name": "Cayenne",
        "price": 133202,
        "year": 2009
    },
    {
        "id": "9066e2ba-7916-4",
        "image_url": "https://storage.googleapis.com/code-challenge/images/a4.jpg",
        "make": {
        "key": "audi",
        "label": "Audi"
    },
        "mileage": 85073,
        "name": "A4",
        "price": 58458,
        "year": 2019
    },
    {
        "id": "b6cd9033-4575-4",
        "image_url": "https://storage.googleapis.com/code-challenge/images/golf.jpg",
        "make": {
        "key": "vw",
        "label": "Volkswagen"
    },
        "mileage": 94408,
        "name": "Golf",
        "price": 154240,
        "year": 2002
    }
    ]
}"""


val carsInputMalformed = """{
    "results": 100,
    "data": [
    {
        "id": "0c3ab65a-ef43-4",
        "image_url": "https://storage.googleapis.com/code-challenge/images/cayenne.jpg",
        "make": {
        "key": "porsche",
        "label": "Porsche"
    },
        "mileage_": 18051,
        "+name": "Cayenne",
        "pice": 133202,
        "year": 2009
    }
    ]
}"""