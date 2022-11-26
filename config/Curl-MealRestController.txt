# get all meals for authenticated user
curl -H "Accept: application/json" http://localhost:8080/topjava/rest/meals

# get meal by id
curl -H "Accept: application/json" http://localhost:8080/topjava/rest/meals/100009

# create meal
curl -H "Content-Type: application/json; charset=UTF-8" -X POST --data-binary "@curl-new-meal.json" http://localhost:8080/topjava/rest/meals

### delete meal by id
curl -X DELETE http://localhost:8080/topjava/rest/meals/100009

### update meal (repeat create meal step)
curl -H "Content-Type: application/json; charset=UTF-8" -X PUT --data-binary "@curl-update-meal.json" http://localhost:8080/topjava/rest/meals/100012

### let`s check update result
curl -H "Accept: application/json" http://localhost:8080/topjava/rest/meals/1000012

### filter meals list
curl -H "Accept: application/json" http://localhost:8080/topjava/rest/meals/filter

### filter meals list with only startDate param
curl -H "Accept: application/json" http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31

### filter meals list with all available params
curl -H "Accept: application/json" "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31&startTime=10:00&endDate=2020-01-31&endTime=20:00"
