import json


def log(message):
    print("### " + str(message))


def from_json(item):
    return json.loads(item)


def to_json(item):
    content = json.dumps(item, sort_keys=True, indent=2, separators=(',', ': '));
    return content
