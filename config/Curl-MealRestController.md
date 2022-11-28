# get all meals for authenticated user
curl -H "Accept: application/json" http://localhost:8080/topjava/rest/meals

# get meal by id
curl -H "Accept: application/json" http://localhost:8080/topjava/rest/meals/100009

# create meal using Windows cmd.exe command line
curl -H "Content-Type: application/json; charset=Windows-1251" -X POST --data-ascii "{\"dateTime\":\"2020-11-15T10:00:00\",\"description\":\"Опять захотелось покушать\",\"calories\":500}" http://localhost:8080/topjava/rest/meals

# create meal using UTF-8 json data file
curl -H "Content-Type: application/json; charset=UTF-8" -X POST --data-binary "@curl-new-meal.json" http://localhost:8080/topjava/rest/meals

# delete meal by id
curl -X DELETE http://localhost:8080/topjava/rest/meals/100009

# update meal using Windows cmd.exe command line
curl -H "Content-Type: application/json; charset=Windows-1251" -X PUT --data-ascii "{\"dateTime\":\"2022-11-15T10:00:00\",\"description\":\"Передумал кушать и съел яблоко\",\"calories\":50}" http://localhost:8080/topjava/rest/meals/100012

# update meal using UTF-8 json data file (repeat create meal step)
curl -H "Content-Type: application/json; charset=UTF-8" -X PUT --data-binary "@curl-update-meal.json" http://localhost:8080/topjava/rest/meals/100012

# let`s check update result
curl -H "Accept: application/json" http://localhost:8080/topjava/rest/meals/1000012

# filter meals list
curl -H "Accept: application/json" http://localhost:8080/topjava/rest/meals/filter

# filter meals list with only startDate param
curl -H "Accept: application/json" http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31

# filter meals list with all available params
curl -H "Accept: application/json" "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31&startTime=10:00&endDate=2020-01-31&endTime=20:00"
