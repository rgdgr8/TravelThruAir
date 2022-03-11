Implement a web application for “Travel Thru Air” using servlets to support the following two use cases
1. A list of current special deals must appear on the home page. Each special deal must display the departure city, the arrival city, and the cost. These special deals are set up by the marketing department and change during the day, so it can’t be static. Special deals are only good for a limited amount of time.
2. A user may search for flights, given a departure city, time and an arrival city. The results must display the departure city, the arrival city, the total cost, and how many legs the flight will have.

OffersServlet has been used to display offers
SearchServlet has been used to search for the required flights using the FlightsDAO
FlightsDAO is a database access object that access a MySQL DB.
home.jsp is the template page for home and search pages.
