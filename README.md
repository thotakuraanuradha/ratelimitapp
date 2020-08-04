### Problem statment
A stundent app with a post request to save student details and a get request to get student by Id has been designed to demonstrate the rate limiting of the api as per request per user(IP) per second.A throttle the request filter is designed to throtte  requests per second for each user (ip address). This solution requires Google's Guava library.If the request count exceeds the number agreed by the
service owner and the user (in a decided window time), the rate limiter blocks all the excess
calls (say by throwing exceptions).

##Solution
1. **FORK** this repository in your Github account

	github url:https://github.com/thotakuraanuradha/ratelimitapp

**Other Options**

We can use bucket4j library with token-bucket algorithm to rate-limit access to the REST api. You can configure it via application properties file. 
We can extend Zuul gateway and, again, use the token bucket algorithm.

**Rate-limiting Algorithms**

	1.Leaky Bucket
	2.Fixed Window
	3.Sliding Log
	4.Sliding Window
