from flask import Flask, request
from flask_restful import Resource, Api, reqparse
from utils import log, to_json, from_json

DB_FLIGHTS_FILENAME="db/flights.json"
DB_USERS_FILENAME="db/users.json"

app = Flask(__name__)
api = Api(app)
flights = {}
parser = reqparse.RequestParser()
parser.add_argument("flight")


class FlightList(Resource):
    def get(self):
        result = to_json(flights.values())
        return result

    def post(self):
        args = parser.parse_args()
        id = int(max(flights.keys())) + 1
        flight = from_json(args["flight"])
        flight["id"] = id
        flights[id] = flight
        return to_json(flight)


class Flight(Resource):
    def get(self, id):
        result = None
        if id in flights.keys():
            result = flights[id]
        result = to_json(result)
        return result

    def put(self, data):
        flight = from_json(data)
        flights[flight["id"]] = flight
        print(flights)
        return data 


def on_start():
    global flights
    flights = load_db(DB_FLIGHTS_FILENAME)

    global api
    api.add_resource(FlightList, '/flights')
    api.add_resource(Flight, '/flights/<int:id>')


def load_db(filename):
    db_file = open(filename)
    content = db_file.read()
    item_array = from_json(content)
    item_dict = {}
    for item in item_array:
        item_dict[item["id"]] = item
    db_file.close()
    log("{0} was loaded:".format(filename))
    log(item_dict)
    return item_dict 


def save_db(db, filename):
    db_file = open(filename)
    db_file.write(to_json(db))
    db_file.close()


if __name__ == '__main__':
    on_start()
    app.run(debug=True)
