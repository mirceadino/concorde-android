from flask import Flask, request
from flask_restful import Resource, Api, reqparse
from utils import log, to_json, from_json
import copy

DB_FLIGHTS_FILENAME="db/flights.json"
DB_USERS_FILENAME="db/users.json"

app = Flask(__name__)
api = Api(app)
flights = {}
users = {}
parser = reqparse.RequestParser()
parser.add_argument("flight")
parser.add_argument("user")
parser.add_argument("username")
parser.add_argument("password")

class Flights(Resource):
    def save(self):
        save_db(flights, DB_FLIGHTS_FILENAME)

    def get(self):
        return list(flights.values())

    def post(self):
        args = parser.parse_args()
        id = 1 if len(flights) is 0 else int(max(flights.keys())) + 1
        flight = from_json(args["flight"])
        print(flight)
        print(id)
        if (not "id" in flight.keys()) or (flight["id"] <= 0):
            flight["id"] = id
        flights[flight["id"]] = flight
        self.save()
        return flight #to_json(flight)

    def delete(self):
        args = parser.parse_args()
        flight = from_json(args["flight"])
        if flight["id"] in flights.keys():
            flights.pop(flight["id"])
        self.save()
        return flight #to_json(flight)


class Users(Resource):
    def save(self):
        save_db(users, DB_USERS_FILENAME)

    def get(self):
        args = parser.parse_args()
        print(args)
        username = args["username"]
        password = args["password"]
        for u in users.values():
            if u["username"] == username and u["password"] == password:
                user = copy.deepcopy(u)
                user["password"] = ""
                return user
        return None

    def post(self):
        args = parser.parse_args()
        id = 1 if len(users) is 0 else int(max(users.keys())) + 1
        user = from_json(args["user"])
        if (not "id" in user.keys()) or (user["id"] <= 0):
            user["id"] = id
        for u in users.values():
            if u["username"] == user["username"]:
                return u
        users[user["id"]] = user
        self.save()
        return user


def on_start():
    global flights
    global users
    flights = load_db(DB_FLIGHTS_FILENAME)
    users = load_db(DB_USERS_FILENAME)

    global api
    api.add_resource(Flights, '/flights')
    api.add_resource(Users, '/users')


def load_db(filename):
    db_file = open(filename, "r")
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
    db_file = open(filename, "w")
    db_file.write(to_json(list(db.values())))
    db_file.close()


if __name__ == '__main__':
    on_start()
    app.run(host="0.0.0.0", port=5000, debug=True)
