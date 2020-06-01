# Table of Contents
* [Project purpose](#purpose)
* [Project structure](#structure)
* [For developer](#developer-start)
* [Authors](#authors)

# <a name="purpose"></a>Project purpose
This is a template for creating an internet shop.
<hr>
It has login and registration forms.

There are controllers for working with products, users, orders and shopping carts:
<hr>
Inject - for injection mock data,

Registration - for registering new users,

Login -  for user authentication and authorization,

Users - for displaying a list of all app users. Available for users with an ADMIN role only,

Products - for displaying  all products in stock,

Shopping cart - for displaying  user’s shopping cart. Available for users with a USER role only,

Orders - for displaying user’s order history,

Logout - for logging out.
<hr>

# <a name="structure"></a>Project Structure
* Java 11
* Maven 3.6.1
* postgresql 42.2.12
* javax.servlet 3.1.0
* jstl 1.2
* log4j 1.2.17
* ibatis-core 3.0
* maven-checkstyle-plugin
<hr>

# <a name="developer-start"></a>For developer

To run this project you need to install:

* Java 11
* Tomcat
* PostgreSQL (optional)

Open the project in your IDE.

Add it as maven project.

Configure Tomcat:
* add artifact;
* add sdk 11.0.3
<hr>

Add sdk 11.0.3 in project struсture.

Change a path in interntetshop.src.main.java.resources.log4j.properties

Run the project.

For test purposes you can inject test data, using "Inject test data" link on Navbar
By default there are 3 users:

* Role ADMIN - login "admin", passkey "admin"
* Role USER - login "Shion", passkey "123"
* Role USER - login "Benio", passkey "123"
<hr>

# <a name="authors"></a>Authors
* [NikolayAbysov](https://github.com/NikolayAbysov)