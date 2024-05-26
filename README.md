# PetstoreRestAssuredExample

--Строка запуска : clean test -P dev -DtestTag=@homeWork3

--Итоговое задание:

Rest-assured

Цель:
Написать автотесты с использованием Rest-assured.

Описание/Пошаговая инструкция выполнения домашнего задания:
Выбрать два любых метода (https://petstore.swagger.io).
Для каждого метода написать не менее двух автотестов.
Для каждого автотеста в комментариях написать, что мы проверяем.


Были выбраны методы, описание в классе PetTest.java в комментариях:
(POST /pet + 'body')
(POST /pet/{id}/uploadImage)

Также на примере класса ExampleTest.java законспектирована лекция по RestAssured
(POST /pet + /{id}+ formParam{'name', 'status'})